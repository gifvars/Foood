package com.foood.commons_svc.dto;


import com.foood.commons_svc.enums.Role;

public record Restaurant(String name, String email, String password, String phone, Address address, Role role) {
}
