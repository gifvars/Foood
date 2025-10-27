package com.foood.register.order.service;

import com.foood.register.order.model.CartItem;
import com.foood.register.order.model.Order;
import com.foood.register.order.model.OrderStatus;

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
                .setOrderStatus(translateOrderStatus(order.orderStatus()))
                .updatedAt(order.updatedAt())
                .setTax(order.tax())
                .setRestaurantId(order.restaurantId())
                .setUserId(order.userId())
                .setOrderItems(order.orderItems().stream().map(this::translateCartItem).toList())
                .build();
    }

    private OrderStatus translateOrderStatus(com.foood.commons.enums.OrderStatus orderStatus){

        return switch (orderStatus) {
            case com.foood.commons.enums.OrderStatus.ACCEPTED_BY_RESTAURANT -> OrderStatus.ACCEPTED_BY_RESTAURANT;
            case com.foood.commons.enums.OrderStatus.ACCEPTED_BY_RIDER -> OrderStatus.ACCEPTED_BY_RIDER;
            case com.foood.commons.enums.OrderStatus.CANCELED -> OrderStatus.CANCELED;
            case com.foood.commons.enums.OrderStatus.DELIVERED -> OrderStatus.DELIVERED;
            case com.foood.commons.enums.OrderStatus.PENDING -> OrderStatus.PENDING;
            case com.foood.commons.enums.OrderStatus.OUT_FOR_DELIVERY -> OrderStatus.OUT_FOR_DELIVERY;
        };

    }
}
