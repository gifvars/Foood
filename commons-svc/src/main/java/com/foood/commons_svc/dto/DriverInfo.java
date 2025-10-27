package com.foood.commons_svc.dto;


import com.foood.commons_svc.enums.DriverStatus;

public record DriverInfo(String lat, String lon, Vehicle vehicle, DriverStatus driverStatus) {
}
