package com.foood.commons.dto;

import com.foood.commons.enums.Role;

public record User(String firstName, String lastName, String email, String password, String phone, Address address, Role role) {

}
