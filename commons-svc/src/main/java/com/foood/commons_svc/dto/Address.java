package com.foood.commons_svc.dto;

public record Address(
        String houseNo,
        String apartmentNo,
        String street,
        String postCode,
        String city,
        String country
)
{
}
