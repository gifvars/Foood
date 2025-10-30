package com.foood.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, Environment environment) throws Exception {
        System.out.println("Resolved Issuer URI: " + environment.getProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri"));
        http.cors(c -> c.configurationSource(
                req -> {
                    var cfg = new CorsConfiguration();
                    cfg.setAllowedOrigins(List.of("https://user:8089", "http://localhost:8089")); // Allows for SPA origin
                    cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    cfg.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    cfg.setAllowCredentials(true);
                    return cfg;
                }))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/index", "/signIn", "/register", "/about", "/validateToken").permitAll()
                        .requestMatchers("app.js", "/static/*.js", "/static/*.css", "/favicon.icon").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakAuthorities())));
        return http.build();
    }

    private JwtAuthenticationConverter keycloakAuthorities() {
        var conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return conv;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // Collect roles from realm_access.roles and resource_access.api.roles
        Set<String> roles = new HashSet<>();
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            roles.addAll((List<String>) realmAccess.getOrDefault("roles", List.of()));
        }
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null && resourceAccess.get("api") instanceof Map<?, ?> api) {
            var apiRoles = (List<String>) ((Map<String, Object>) api).getOrDefault("roles", List.of());
            roles.addAll(apiRoles);
        }
        return roles.stream()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map( r -> (GrantedAuthority) new SimpleGrantedAuthority(r))
                .toList();
    }
}
