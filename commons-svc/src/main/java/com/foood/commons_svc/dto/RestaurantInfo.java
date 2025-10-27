package com.foood.commons_svc.dto;


import com.foood.commons_svc.util.OpeningHours;

public record RestaurantInfo(String name, String email, String password, String phone, Address address, OpeningHours openingHours, String orgNumber) {
}
