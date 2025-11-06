package com.foood.commons_svc.util.data.transformers;

import com.foood.commons_svc.dto.product.ProductResponse;

import java.util.LinkedHashMap;

public class ProductResponseTransformer {

    public static ProductResponse transform(LinkedHashMap<String, Object> rawResponse) {
        String id = (String) rawResponse.get("id");
        String restaurantId = (String) rawResponse.get("restaurantId");
        String title = (String) rawResponse.get("title");
        String description = (String) rawResponse.get("description");
        String additionalInfo = (String) rawResponse.get("additionalInfo");
        String ingredients = (String) rawResponse.get("ingredient");
        double price = (Double) rawResponse.get("price");
        double discount = (Double) rawResponse.get("discountPercentage");
        String category = (String) rawResponse.get("category");
        String size = (String) rawResponse.get("size");
        long weight = Long.parseLong(rawResponse.get("weight").toString());
        String thumbnailUrl = (String) rawResponse.get("thumbnail");

        return new ProductResponse(id, restaurantId, title, description, additionalInfo, ingredients, price, discount, category, size, weight, thumbnailUrl);
    }
}
