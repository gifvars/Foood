package com.foood.user.controller;

import com.foood.commons_svc.dto.auth.RegisterUserRequest;
import com.foood.commons_svc.dto.auth.SignInRequest;
import com.foood.commons_svc.dto.auth.TokenResponse;
import com.foood.commons_svc.dto.auth.UserResponse;
import com.foood.commons_svc.util.ResponseWrapper;
import com.foood.commons_svc.util.data.transformers.UserResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.LinkedHashMap;

@RestController
public class UserController {

    @Value("${authservice.hostname}")
    private String hostname;

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
    public TokenResponse signIn(SignInRequest request) {
        RestClient restClient = RestClient.create();
        return restClient.post()
                .uri("http://" + hostname + ":9090/api/v1/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request).retrieve().body(TokenResponse.class);
    }
}
