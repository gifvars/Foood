package com.foood.commons_svc.util.data.transformers;

import com.foood.commons_svc.dto.auth.VerifyTokenResponse;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class VerifyTokenResponseTransformer {

    public static VerifyTokenResponse transform(LinkedHashMap<String, Object> result) {
        String username = Optional.ofNullable(result.get("username").toString()).orElse("");
        Map<String, Object> realm_access = Optional.ofNullable((Map<String, Object>) result.get("realm_access")).orElse(new LinkedHashMap<>());
        Map<String, Object> resource_access = Optional.ofNullable((Map<String, Object>) result.get("resource_access")).orElse(new LinkedHashMap<>());
        Boolean active = (Boolean) result.get("active");

        return new VerifyTokenResponse(username, realm_access, resource_access, active);
    }
}
