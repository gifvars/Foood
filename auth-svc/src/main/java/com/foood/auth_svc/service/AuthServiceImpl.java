package com.foood.auth_svc.service;

import com.foood.commons_svc.dto.auth.RegisterUserRequest;
import com.foood.commons_svc.dto.auth.SignInRequest;
import com.foood.commons_svc.dto.auth.TokenResponse;
import com.foood.commons_svc.dto.auth.UserResponse;
import com.foood.commons_svc.enums.Role;
import com.foood.commons_svc.exception.*;
import com.foood.commons_svc.exception.IllegalStateException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
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

import java.util.List;
import java.util.stream.Collectors;

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
    public UserResponse registerUser(RegisterUserRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.email());
        user.setEmail(request.email());
        user.setEnabled(true);
        user.setEmailVerified(false);

        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(user);
        if (response.getStatus() == 409) {
            throw new IllegalStateException("Email already exits!");
        }
        if (response.getStatus() != 201) {
            throw new EntityNotFoundException("Failed to create user in Keycloak: " + response.getStatus());
        }

        String userId = CreatedResponseUtil.getCreatedId(response);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        usersResource.get(userId).resetPassword(credential);

        RoleRepresentation role = keycloak.realm(realm).roles().get(request.userType()).toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(List.of(role));
        usersResource.get(userId).sendVerifyEmail();

        UserRepresentation createdUser = usersResource.get(userId).toRepresentation();
        List<Role> assignedRoles = usersResource.get(userId)
                .roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(RoleRepresentation::getName)
                .filter(name -> name.equals("CUSTOMER") || name.equals("DRIVER") || name.equals("RESTAURANT"))
                .map(Role::valueOf)
                .collect(Collectors.toList());

        // save

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
