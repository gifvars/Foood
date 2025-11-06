package com.foood.commons_svc.util.data.transformers;

import com.foood.commons_svc.dto.Product;
import com.foood.commons_svc.enums.Category;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class ProductTransformer {

    public static Product transform(LinkedHashMap<String, Object> input) {
        String id = input.get("id").toString();
        String restaurantId = input.get("restaurantId").toString();
        String title = input.get("title").toString();
        String description = input.get("description").toString();
        String additionalInfo = input.get("additionalInfo").toString();
        String ingredient = input.get("ingredient").toString();
        BigDecimal price = BigDecimal.valueOf((Double) input.get("price"));
        Double discountPercentage = (Double) input.get("discountPercentage");
        Category category = Category.valueOf(input.get("category").toString());
        String size = input.get("size").toString();
        Integer weight = (Integer) input.get("weight");
        String thumbnail = (String) input.get("thumbnail");

        return new Product(id, restaurantId, title, description, ingredient, additionalInfo, price, discountPercentage,
                category, size, weight, thumbnail);
    }
}
