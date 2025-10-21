package com.foood.commons.dto;

import com.foood.commons.enums.DriverStatus;

public record DriverInfo(String lat, String lon, Vehicle vehicle, DriverStatus driverStatus) {
}
