package com.foood.register.order.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Document()
public class Order {

    private final Integer userId;
    private final List<CartItem> orderItems;
    private final BigDecimal orderPrice;
    private final BigDecimal tax;
    private final BigDecimal discountAmount;
    private final OrderStatus orderStatus;
    private final Integer restaurantId;
    private final Integer driverId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Order(Builder builder) {
            this.userId = builder.userId;;
            this.orderItems = builder.orderItems;
            this.orderPrice = builder.orderPrice;
            this.tax = builder.tax;
            this.discountAmount = builder.discountAmount;
            this.orderStatus = builder.orderStatus;
            this.restaurantId = builder.restaurantId;
            this.driverId = builder.driverId;
            this.createdAt = builder.createdAt;
            this.updatedAt = builder.updatedAt;

    }

    public Integer getUserId() {
        return userId;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public static class Builder {

        private Integer userId;
        private List<CartItem> orderItems;
        private BigDecimal orderPrice;
        private BigDecimal tax;
        private BigDecimal discountAmount;
        private OrderStatus orderStatus;
        private Integer restaurantId;
        private Integer driverId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder setOrderItems(List<CartItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder setOrderPrice(BigDecimal orderPrice) {
            this.orderPrice = orderPrice;
            return this;
        }

        public Builder setTax(BigDecimal tax) {
            this.tax = tax;
            return this;
        }

        public Builder setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public Builder setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder setRestaurantId(Integer restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public Builder setDriverId(Integer driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Order build(){
            return new Order(this);
        }

    }

}
