package com.foood.auth_svc.controller;

import com.foood.auth_svc.service.AuthServiceImpl;
import com.foood.commons_svc.dto.auth.RegisterUserRequest;
import com.foood.commons_svc.dto.auth.SignInRequest;
import com.foood.commons_svc.dto.auth.TokenResponse;
import com.foood.commons_svc.dto.auth.UserResponse;
import com.foood.commons_svc.util.JsonUtils;
import com.foood.commons_svc.util.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;
    @Autowired
    JsonUtils jsonUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register( @Valid @RequestBody RegisterUserRequest request){
        ResponseWrapper<UserResponse> responseWrapper = new ResponseWrapper<>();
        UserResponse userResponse = authService.registerUser(request);
        responseWrapper.setData(userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonUtils.responseWithCreated(responseWrapper,"New User Created Successfully!"));

    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest singInRequest){
        ResponseWrapper<TokenResponse> responseWrapper = new ResponseWrapper<>();
        TokenResponse tokenResponse = authService.signInUser(singInRequest);
        responseWrapper.setData(tokenResponse);
        return ResponseEntity.status(HttpStatus.OK).body(jsonUtils.responseWithSuccess(responseWrapper,"All user List"));

    }


}
