package com.foood.commons_svc.dto.auth;

import java.util.Map;

public record VerifyTokenResponse(
        String username,
        Map<String, Object> realm_access,
        Map<String, Object> resource_access,
        Boolean active
) {
}
