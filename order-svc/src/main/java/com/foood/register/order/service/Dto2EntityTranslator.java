package com.foood.register.order.service;

import com.foood.register.order.model.CartItem;
import com.foood.register.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class Dto2EntityTranslator {

    private CartItem translateCartItem(com.foood.commons.dto.CartItem cartItem){

        return new CartItem.Builder()
                .setTotalPrice(cartItem.totalPrice())
                .setQuantity(cartItem.quantity())
                .setProductId(cartItem.productId())
                .build();
    }

    public Order translateOrder(com.foood.commons.dto.Order order){
        return new Order.Builder()
                .setCreatedAt(order.createdAt())
                .setDiscountAmount(order.discountAmount())
                .setOrderPrice(order.orderPrice())
                .setDriverId(order.driverId())
                .setOrderStatus(order.orderStatus())
                .updatedAt(order.updatedAt())
                .setTax(order.tax())
                .setRestaurantId(order.restaurantId())
                .setUserId(order.userId())
                .setOrderItems(order.orderItems().stream().map(this::translateCartItem).toList())
                .build();
    }
}
