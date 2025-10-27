package com.foood.auth_svc.service;

import com.foood.commons_svc.dto.auth.RegisterUserRequest;
import com.foood.commons_svc.dto.auth.SignInRequest;
import com.foood.commons_svc.dto.auth.TokenResponse;
import com.foood.commons_svc.dto.auth.UserResponse;

public interface AuthService {
    UserResponse registerUser(RegisterUserRequest request);
    TokenResponse signInUser(SignInRequest request);
}
