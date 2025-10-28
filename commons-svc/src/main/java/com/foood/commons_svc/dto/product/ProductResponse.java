package com.foood.commons_svc.dto.product;

public record ProductResponse(
        String id,
        String restaurantId,
        String title,
        String description,
        String additionalInfo,
        String ingredient,
        double price,
        double discountPercentage,
        String category,
        String size,
        long weight,
        String thumbnail
) {
}
