package com.foood.commons_svc.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddProductRequest(
        @NotBlank(message = "restaurantId cannot be empty")
        String restaurantId,
        @NotBlank(message = "Title cannot be empty")
        String title,
        @NotBlank(message = "Description cannot be empty")
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,
        @Size(max = 500, message = "Addition info cannot exceed 500 characters")
        String additionalInfo,
        @Size(max = 500, message = "ingredient cannot exceed 500 characters")
        String ingredient,

        double price,

        double discountPercentage,
        @Pattern(regexp = "BURGER|THAI|PIZZA|SUSHI|KIDS|OTHER|DESSERT", message = "Invalid category")
        String category,
        @NotBlank(message = "Size cannot be empty")
        String size,

        long weight,
        @NotBlank(message = "Thumbnail cannot be empty")
        String thumbnail
) {
}
