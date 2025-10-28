package com.foood.product_svc.service;

import com.foood.commons_svc.dto.product.AddProductRequest;
import com.foood.commons_svc.dto.product.ProductResponse;
import com.foood.commons_svc.dto.product.UpdateProductRequest;
import com.foood.commons_svc.enums.Category;
import com.foood.commons_svc.exception.EntityNotFoundException;
import com.foood.product_svc.entity.Product;
import com.foood.product_svc.repository.ProductRepository;
import com.foood.product_svc.specification.ProductSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponse addProduct(AddProductRequest request) {
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .restaurantId(request.restaurantId())
                .title(request.title())
                .description(request.description())
                .additionalInfo(request.additionalInfo())
                .ingredient(request.ingredient())
                .price(BigDecimal.valueOf(request.price()))
                .discountPercentage(request.discountPercentage())
                .category(Category.valueOf(request.category()))
                .size(request.size())
                .weight(request.weight())
                .thumbnail(request.thumbnail())
                .build();

        productRepository.save(product);
        return mapToProductResponse(product);

    }


    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getRestaurantId(),
                product.getTitle(),
                product.getDescription(),
                product.getAdditionalInfo(),
                product.getIngredient(),
                product.getPrice().doubleValue(),
                product.getDiscountPercentage(),
                product.getCategory().name(),
                product.getSize(),
                product.getWeight(),
                product.getThumbnail()
        );
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return mapToProductResponse(product);
    }
    @Override
    public List<ProductResponse> getAllProduct(Pageable pageable,String id, String RestaurantId,
                                                String title, String category) {
        Specification<Product> spec = ProductSpecification.getSpecification(id, RestaurantId, title, category);
        List<Product> products = productRepository.findAll(spec,pageable).getContent();
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }
    @Override
    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }
    @Override
    public ProductResponse updateProduct(@Valid UpdateProductRequest request) {
        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setTitle(request.title());
        product.setDescription(request.description());
        product.setAdditionalInfo(request.additionalInfo());
        product.setIngredient(request.ingredient());
        product.setPrice(BigDecimal.valueOf(request.price()));
        product.setDiscountPercentage(request.discountPercentage());
        product.setCategory(Category.valueOf(request.category()));
        product.setSize(request.size());
        product.setWeight(request.weight());
        product.setThumbnail(request.thumbnail());

        productRepository.save(product);
        return mapToProductResponse(product);

    }
}
