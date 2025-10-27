package com.foood.commons_svc.dto;


import com.foood.commons_svc.enums.Role;

public record User(String firstName, String lastName, String email, String password, String phone, Address address, Role role) {

}
