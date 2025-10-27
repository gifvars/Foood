package com.foood.register.order.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document()
public class CartItem {
    private Integer productId;
    private Integer quantity;
    private BigDecimal totalPrice;


    public CartItem(Builder builder) {
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.totalPrice = builder.totalPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static class Builder {

        private Integer productId;
        private Integer quantity;
        private BigDecimal totalPrice;

        public Builder setProductId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CartItem build(){
            return new CartItem(this);
        }
    }

}
