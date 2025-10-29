package com.foood.commons_svc.util.data.transformers;

import com.foood.commons_svc.dto.auth.UserResponse;
import com.foood.commons_svc.enums.Role;

import java.util.LinkedHashMap;
import java.util.List;

public class UserResponseTransformer {

    public static UserResponse transform(LinkedHashMap<String,Object> map) {
        var id = (String)map.get("id");
        var email = (String)map.get("email");
        var firstName = (String)map.get("firstName");
        var lastName = (String)map.get("LastNane");
        var roles = (List<String>) map.get("roleList");
        List<Role> roleList = roles.stream().map(Role::valueOf).toList();
        return new UserResponse(id, firstName, lastName, email, roleList);
    }
}
