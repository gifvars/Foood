package com.foood.commons_svc.dto;

import java.math.BigDecimal;
import java.util.List;

public record Cart(Integer userId, List<CartItem> cartItems, BigDecimal totalPrice) {
}
