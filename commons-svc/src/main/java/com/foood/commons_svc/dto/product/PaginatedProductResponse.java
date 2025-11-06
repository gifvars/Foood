package com.foood.commons_svc.dto.product;

import com.foood.commons_svc.dto.Product;

import java.util.List;

public record PaginatedProductResponse(int pageNumber, int pageSize, int totalPages, List<ProductResponse> productResponses) {
}
