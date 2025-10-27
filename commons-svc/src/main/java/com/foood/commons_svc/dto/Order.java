package com.foood.commons_svc.dto;



import com.foood.commons_svc.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record Order(Integer userId, List<CartItem> orderItems, BigDecimal orderPrice, BigDecimal tax,
                    BigDecimal discountAmount, OrderStatus orderStatus, Integer restaurantId, Integer driverId,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
}
