package com.foood.commons_svc.util.data.transformers;

import com.foood.commons_svc.dto.auth.TokenResponse;

import java.util.LinkedHashMap;

public class TokenResponseTransformer {

    public static TokenResponse transform(LinkedHashMap<String, Object> map) {
        String access_token  = (String) map.get("access_token");
        long expires_in =  (Integer) map.get("expires_in");
        long refresh_expires_in  =  (Integer) map.get("refresh_expires_in");
        String refresh_token = (String) map.get("refresh_token");
        String token_type = (String) map.get("token_type");
        long not_before_policy  = (Integer)
                (map.get("not_before_policy") != null ? map.get("not_before_policy") : map.get("not-before-policy"));
        String session_state  = (String) map.get("session_state");
        String scope =  (String) map.get("scope");

        return new TokenResponse(access_token, expires_in, refresh_expires_in, refresh_token, token_type,
                not_before_policy, session_state, scope);
    }
}
