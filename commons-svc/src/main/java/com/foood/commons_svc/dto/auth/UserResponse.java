package com.foood.commons_svc.dto.auth;

import com.foood.commons_svc.dto.Address;
import com.foood.commons_svc.enums.Role;

import java.util.List;

public record UserResponse(
        String id,
        String firstName,
        String LastNane,
        String email,
        List<Role> roleList

) {
}
