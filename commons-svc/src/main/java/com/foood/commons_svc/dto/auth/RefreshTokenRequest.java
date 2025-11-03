package com.foood.commons_svc.dto.auth;

public record RefreshTokenRequest(
        String client_id,
        String grant_type,
        String refresh_token
) {
}
