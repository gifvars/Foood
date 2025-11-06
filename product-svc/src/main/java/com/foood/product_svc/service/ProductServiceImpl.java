package com.foood.product_svc.service;

import com.foood.commons_svc.dto.product.AddProductRequest;
import com.foood.commons_svc.dto.product.PaginatedProductResponse;
import com.foood.commons_svc.dto.product.ProductResponse;
import com.foood.commons_svc.dto.product.UpdateProductRequest;
import com.foood.commons_svc.enums.Category;
import com.foood.commons_svc.exception.EntityNotFoundException;
import com.foood.product_svc.entity.Product;
import com.foood.product_svc.repository.ProductRepository;
import com.foood.product_svc.specification.ProductSpecifications;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @PostConstruct
    private void init() {
        if (productRepository.count() == 0) {
            List<AddProductRequest> addProductRequests = getExampleProductList();
            for  (AddProductRequest addProductRequest : addProductRequests) {
                addProduct(addProductRequest);
            }
        }
    }

    private List<AddProductRequest> getExampleProductList() {
        List<AddProductRequest> list = new ArrayList<>();
        Random random = new Random();
        String thumbnail = "https://example.com";

        String restaurantId = UUID.randomUUID().toString();
        String title = "Classic Cheeseburger";
        String description = "A juicy grilled beef patty layered with melted cheddar cheese, crisp lettuce, ripe tomato," +
                " pickles, and onions, all sandwiched between a toasted sesame bun.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "beef, lettuce, etc.", 13.49, 0.0, Category.BURGER.name(), "Medium",
                0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Pad Thai";
        description = "A popular stir-fried noodle dish combining rice noodles, eggs, tofu, shrimp or chicken, and " +
                "bean sprouts in a tangy tamarind sauce.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "noodles, eggs, tofu, etc.", 12.49, 0.0, Category.THAI.name(), "Medium",
                0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Margherita Pizza";
        description = "A traditional Italian pizza featuring a thin crust topped with tomato sauce, fresh mozzarella," +
                " and basil for a simple yet flavorful dish.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "pizza dough, tomato sauce, mozzarella, basil, olive oil, salt", 12.99, 0.0,
                Category.PIZZA.name(), "Medium", 0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "California Roll";
        description = "A Western-style sushi roll filled with crab meat, avocado, and cucumber, wrapped in nori and " +
                "sushi rice, often topped with sesame seeds.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "sushi rice, nori, etc.", 13.99, 0.0, Category.SUSHI.name(),
                "Medium-Small", 0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Mac and Cheese";
        description = "Creamy and comforting pasta dish with elbow macaroni coated in a rich cheese sauce — a kid favorite.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "macaroni, butter, etc.", 7.99, 0.0, Category.KIDS.name(), "Medium",
                0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Mediterranean Falafel Wrap";
        description = "A vegetarian wrap made with crispy falafel balls, fresh vegetables, and tangy tahini sauce " +
                "wrapped in pita bread.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "chickpeas, onion, etc.", 11.99, 0.0, Category.OTHER.name(), "Medium",
                0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Chocolate Lava Cake";
        description = "A decadent chocolate cake with a warm, gooey molten center that oozes rich chocolate when cut " +
                "open.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "chocolate, butter, etc.", 5.49, 0.0, Category.DESSERT.name(), "Small",
                0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "BBQ Bacon Burger";
        description = "A smoky and savory burger featuring a juicy beef patty, crispy bacon, melted cheddar cheese, " +
                "onion rings, and tangy barbecue sauce for a perfect sweet-salty balance.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "ground beef, bacon strips, etc.", 14.49, 0.0, Category.BURGER.name(),
                "Medium", 0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Green Curry with Chicken";
        description = "A fragrant, mildly spicy Thai curry made with green curry paste, coconut milk, chicken, and " +
                "fresh vegetables. Served with steamed jasmine rice.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "chicken breast or thighs, thai green curry paste, etc.", 14.19, 0.0,
                Category.THAI.name(),"Medium", 0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Tiramisu";
        description = "A classic Italian dessert layered with coffee-soaked ladyfingers, creamy mascarpone filling, and " +
                "dusted with cocoa powder for a rich and elegant finish.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "ladyfingers (savoiardi biscuits), mascarpone cheese, etc.", 6.99, 0.0,
                Category.DESSERT.name(), "Small", 0, thumbnail));

        restaurantId = UUID.randomUUID().toString();
        title = "Pepperoni Supreme";
        description = "A hearty and flavorful pizza loaded with classic pepperoni, melted mozzarella, and a blend of " +
                "bell peppers, onions, and black olives on a golden, crispy crust.";

        list.add(new AddProductRequest(restaurantId, title, description, "none",
                "pizza dough, tomato pizza sauce, etc", 12.99, 0.0,
                Category.PIZZA.name(), "Medium", 0, thumbnail));

        return list;
    }

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

    private PaginatedProductResponse getPaginatedProductResponse(Pageable pageable, Page<Product> productPage) {
        int totalPages = productPage.getTotalPages();
        List<Product> products = productPage.getContent();
        return new PaginatedProductResponse(pageable.getPageNumber(), pageable.getPageSize(), totalPages, products.stream()
                .map(this::mapToProductResponse)
                .toList());
    }

    public PaginatedProductResponse getProductsDescriptionContaining(String term, Pageable pageable) {
        Page<Product> productPage = productRepository.findByDescriptionContainingIgnoreCase(term, pageable);
        return getPaginatedProductResponse(pageable, productPage);
    }

    @Override
    public PaginatedProductResponse getAllProduct(Pageable pageable, String id, String RestaurantId,
                                                  String title, String category, String desc, Double min, Double max) {
        Specification<Product> spec = Specification.allOf(
                ProductSpecifications.getSpecification(id, RestaurantId, title, category),
                ProductSpecifications.descriptionContains(desc),
                ProductSpecifications.priceBetween(min, max));
        Page<Product> productsPage = productRepository.findAll(spec, pageable);
        return getPaginatedProductResponse(pageable, productsPage);
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
