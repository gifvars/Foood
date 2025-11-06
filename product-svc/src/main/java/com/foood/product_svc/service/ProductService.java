package com.foood.product_svc.service;

import com.foood.commons_svc.dto.product.AddProductRequest;
import com.foood.commons_svc.dto.product.PaginatedProductResponse;
import com.foood.commons_svc.dto.product.ProductResponse;
import com.foood.commons_svc.dto.product.UpdateProductRequest;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    ProductResponse addProduct(AddProductRequest request);
    ProductResponse getProductById(String id);

    PaginatedProductResponse getAllProduct(Pageable pageable, String id, String RestaurantId,
                                           String title, String category, String desc, Double min, Double max);

    void deleteProductById(String id);
    ProductResponse updateProduct(UpdateProductRequest request);
}
