package com.foood.auth_svc.service;

import com.foood.commons_svc.dto.auth.RegisterUserRequest;
import com.foood.commons_svc.dto.auth.SignInRequest;
import com.foood.commons_svc.dto.auth.TokenResponse;
import com.foood.commons_svc.dto.auth.UserResponse;
import com.foood.commons_svc.enums.Role;
import com.foood.commons_svc.exception.AuthenticationServerException;
import com.foood.commons_svc.exception.InvalidCredentialsException;
import com.foood.commons_svc.exception.KeycloakCommunicationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private Keycloak keycloak;
    @Autowired
    private String realm;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String client_realm;

    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String client_secret;


    @Override
    public UserResponse registerUser(RegisterUserRequest request){
       UsersResource usersResource = keycloak.realm(realm).users();

        // 1. Skapa användare
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.email());
        user.setEmail(request.email());
        user.setEnabled(true);

        Response response = usersResource.create(user);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Misslyckades att skapa användare: " + response.getStatusInfo());
        }

        String userId = CreatedResponseUtil.getCreatedId(response);
        System.out.println("Användare skapad med ID: " + userId);

        // 2. Kontrollera att användaren finns
        UserRepresentation createdUser = usersResource.get(userId).toRepresentation();
        if (createdUser == null) {
            throw new RuntimeException("Användaren kunde inte hämtas efter skapande.");
        }

        // 3. Hämta rollen och kontrollera att den finns
        RoleRepresentation role;
        try {
            role = keycloak.realm(realm).roles().get("RESTAURANT").toRepresentation();
        } catch (NotFoundException e) {
            throw new RuntimeException("Rollen '" + "RESTAURANT" + "' finns inte i realm '" + realm + "'.");
        }

        // 4. Tilldela rollen
        usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(role));
        System.out.println("Roll '" + "RESTAURANT" + "' tilldelad till användaren.");

        // 5. Skicka verifieringsmail (kräver SMTP-konfiguration)
        try {
            usersResource.get(userId).sendVerifyEmail();
            System.out.println("Verifieringsmail skickat.");
        } catch (Exception e) {
            System.err.println("Kunde inte skicka verifieringsmail: " + e.getMessage());
        }
        List<Role> assignedRoles = usersResource.get(userId)
                .roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(RoleRepresentation::getName)
                .filter(name -> name.equals("CUSTOMER") || name.equals("DRIVER") || name.equals("RESTAURANT"))
                .map(Role::valueOf)
                .toList();

        return   new UserResponse(
                createdUser.getId(),
                createdUser.getFirstName(),
                createdUser.getLastName(),
                createdUser.getEmail(),
                assignedRoles);
    }

    @Override
    public TokenResponse signInUser(SignInRequest request) {

        BodyInserters.FormInserter<String> form = BodyInserters.fromFormData("grant_type", "password")
                .with("client_id", clientId)
                .with("username", request.email())
                .with("password", request.password())
                .with("client_secret", client_secret)
                .with("scope", "openid profile email");

        StringBuilder sb = new StringBuilder();
        String token_Url = sb.append(serverUrl).append("realms/").append(client_realm).append("/protocol")
                .append("/openid-connect/token").toString();

        try {
            return webClientBuilder.build()
                    .post()
                    .uri(token_Url)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response ->
                            response.bodyToMono(String.class)
                                    .flatMap(body -> {
                                        if (body.contains("invalid_grant")) {
                                            return Mono.error(new InvalidCredentialsException("Invalid email or password"));
                                        } else {
                                            return Mono.error(new InvalidCredentialsException("Authentication failed"));
                                        }
                                    })
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            Mono.error(new AuthenticationServerException("Keycloak server error, please try again later"))
                    )
                    .bodyToMono(TokenResponse.class)
                    .block();

        } catch (WebClientResponseException e) {
            throw new KeycloakCommunicationException("Error communicating with Keycloak", e);
        }
    }
}
