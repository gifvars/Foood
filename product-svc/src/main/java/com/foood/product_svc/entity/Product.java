package com.foood.product_svc.entity;

import com.foood.commons_svc.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    private String id;
    private String restaurantId;
    private String title;
    private String description;
    private String additionalInfo;
    private String ingredient;
    private BigDecimal price;
    private double discountPercentage;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String size;
    private long weight;
    private String thumbnail;
}
