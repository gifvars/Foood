package com.foood.commons.dto;

import com.foood.commons.enums.Category;

import java.math.BigDecimal;

public record Product(String title, String description, String ingredients, String additionalInfo, BigDecimal price,
                      Double discount, Category category, String size, Integer weightInGrams, String thumbnailUrl) {
}
