package com.foood.commons.dto;

import java.math.BigDecimal;

public record CartItem(Integer productId, Integer quantity, BigDecimal totalPrice) {
}
