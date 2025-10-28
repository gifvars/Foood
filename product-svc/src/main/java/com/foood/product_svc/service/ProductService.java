package com.foood.product_svc.service;

import com.foood.commons_svc.dto.product.AddProductRequest;
import com.foood.commons_svc.dto.product.ProductResponse;
import com.foood.commons_svc.dto.product.UpdateProductRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    ProductResponse addProduct(AddProductRequest request);
    ProductResponse getProductById(String id);
    void deleteProductById(String id);
    ProductResponse updateProduct(UpdateProductRequest request);
    List<ProductResponse> getAllProduct(Pageable pageable, String id, String RestaurantId,
                                        String title, String category);
}
