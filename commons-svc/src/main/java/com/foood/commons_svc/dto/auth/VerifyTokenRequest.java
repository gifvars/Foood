package com.foood.commons_svc.dto.auth;

public record VerifyTokenRequest(
        String client_id,
        String client_secret,
        String token

) {
}
