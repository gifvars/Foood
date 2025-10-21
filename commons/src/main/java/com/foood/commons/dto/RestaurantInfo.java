package com.foood.commons.dto;

import com.foood.commons.util.OpeningHours;

public record RestaurantInfo(String name, String email, String password, String phone, Address address, OpeningHours openingHours, String orgNumber) {
}
