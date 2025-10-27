package com.foood.commons_svc.dto;

import java.math.BigDecimal;

public record CartItem(Integer productId, Integer quantity, BigDecimal totalPrice) {
}
