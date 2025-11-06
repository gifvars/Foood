package com.foood.commons_svc.util.data.transformers;

import com.foood.commons_svc.dto.product.PaginatedProductResponse;
import com.foood.commons_svc.dto.product.ProductResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PaginatedProductResponseTransformer {

    public static PaginatedProductResponse transform(LinkedHashMap<String, Object> input) {
        int pageNumber = (Integer) input.get("pageNumber");
        int pageSize = (Integer) input.get("pageSize");
        int totalPages = (Integer) input.get("totalPages");
        List<Object> rawResponses = (List<Object>) input.get("productResponses");
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Object rawResponse : rawResponses) {
            productResponses.add(ProductResponseTransformer.transform((LinkedHashMap<String, Object>) rawResponse));
        }

        return new PaginatedProductResponse(pageNumber, pageSize, totalPages, productResponses);
    }
}
