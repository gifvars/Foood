package com.foood.commons_svc.dto;


import com.foood.commons_svc.enums.Category;

import java.math.BigDecimal;

public record Product(String id, String restaurantId, String title, String description, String ingredients, String additionalInfo, BigDecimal price,
                      Double discount, Category category, String size, Integer weightInGrams, String thumbnailUrl) {
}
