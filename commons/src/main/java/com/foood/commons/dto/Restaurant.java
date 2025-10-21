package com.foood.commons.dto;

import com.foood.commons.enums.Role;

public record Restaurant(String name, String email, String password, String phone, Address address, Role role) {
}
