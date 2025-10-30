package com.foood.user.controller;

import com.foood.commons_svc.dto.auth.RegisterUserRequest;
import com.foood.commons_svc.dto.auth.SignInRequest;
import com.foood.commons_svc.dto.auth.TokenResponse;
import com.foood.commons_svc.dto.auth.UserResponse;
import com.foood.commons_svc.dto.auth.VerifyTokenRequest;
import com.foood.commons_svc.dto.auth.VerifyTokenResponse;
import com.foood.commons_svc.util.ResponseWrapper;
import com.foood.commons_svc.util.data.transformers.TokenResponseTransformer;
import com.foood.commons_svc.util.data.transformers.UserResponseTransformer;
import com.foood.commons_svc.util.data.transformers.VerifyTokenResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.LinkedHashMap;

@RestController
public class UserController {

    @Value("${authservice.hostname}")
    private String hostname;

    @Value("${keycloak.hostname}")
    private String keycloak;

    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String client_secret;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterUserRequest registerUserRequest) {
        RestClient restClient = RestClient.create();
        var result = restClient.post()
                .uri("http://" + hostname + ":9090/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(registerUserRequest).retrieve().body(ResponseWrapper.class);
        return UserResponseTransformer.transform((LinkedHashMap<String, Object>)  result.getData());
    }

    @PostMapping("/signIn")
    public TokenResponse signIn(@RequestBody SignInRequest request) {
        RestClient restClient = RestClient.create();
        var result = restClient.post()
                .uri("http://" + hostname + ":9090/api/v1/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request).retrieve().body(ResponseWrapper.class);
        return TokenResponseTransformer.transform((LinkedHashMap<String, Object>) result.getData());
    }

    @PostMapping("/validateToken")
    public VerifyTokenResponse validateToken(@RequestBody VerifyTokenRequest request) {
        String token = request.token();
        VerifyTokenRequest modifiedRequest = new VerifyTokenRequest(clientId, client_secret, token);
        System.out.println(modifiedRequest);
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("token", modifiedRequest.token());
        requestData.add("client_id", clientId);
        requestData.add("client_secret", client_secret);
        RestClient restClient = RestClient.create();
        var result = restClient.post()
                .uri("http://" + hostname + ":8080/realms/foood/protocol/openid-connect/token/introspect")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestData).retrieve().body(LinkedHashMap.class);
        return VerifyTokenResponseTransformer.transform((result));
    }
}
