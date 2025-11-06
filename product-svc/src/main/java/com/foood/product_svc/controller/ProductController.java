package com.foood.product_svc.controller;

import com.foood.commons_svc.dto.product.AddProductRequest;
import com.foood.commons_svc.dto.product.PaginatedProductResponse;
import com.foood.commons_svc.dto.product.ProductResponse;
import com.foood.commons_svc.dto.product.UpdateProductRequest;
import com.foood.commons_svc.enums.Category;
import com.foood.commons_svc.util.JsonUtils;
import com.foood.commons_svc.util.ResponseWrapper;
import com.foood.product_svc.service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    JsonUtils jsonUtils;



    @PostMapping("/addProduct")
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    public ResponseEntity<?>addProduct(@Valid @RequestBody AddProductRequest request){
        ProductResponse productResponse = productService.addProduct(request);
        ResponseWrapper<ProductResponse> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(productResponse);

        // Implementation for adding product
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonUtils.responseWithCreated(responseWrapper,"Product created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id){
        ProductResponse productResponse = productService.getProductById(id);
        ResponseWrapper<ProductResponse> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(productResponse);
        return ResponseEntity.status(HttpStatus.OK).body(jsonUtils.responseWithSuccess(responseWrapper,"Product fetched successfully"));
    }

    @GetMapping("/getCategories")
    public ResponseEntity<?> getCategories() {
        ResponseWrapper<List<String>> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(Arrays.stream(Category.values()).map(Category::name).toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(jsonUtils.responseWithSuccess(responseWrapper,"Categories fetched successfully"));
    }

    @GetMapping("/getProducts")
    public ResponseEntity<?> productList(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                         @RequestParam(required = false, defaultValue = "10")int pageSize,
                                         @RequestParam(required = false, defaultValue = "price") String sortBy,
                                         @RequestParam(required = false, defaultValue = "ASC") String sortDir,
                                         @RequestParam(required = false) String id,
                                         @RequestParam(required = false) String restaurantId,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) String category,
                                         @RequestParam(required = false) String desc,
                                         @RequestParam(required = false) Double minPrice,
                                         @RequestParam(required = false) Double maxPrice

    ){
        Sort sort = getSort(sortBy, sortDir);

        PaginatedProductResponse productResponseList = productService.getAllProduct
                                                                    (PageRequest.of(pageNo-1, pageSize, sort)
                                                                            ,id, restaurantId, title, category, desc
                                                                            ,minPrice, maxPrice);
        ResponseWrapper<PaginatedProductResponse> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(productResponseList);

        return ResponseEntity.status(HttpStatus.OK).body(jsonUtils.responseWithSuccess(
                responseWrapper,"Product fetched successfully"));
    }

    private static Sort getSort(String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("ASC")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }
        return sort;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        ResponseWrapper<ProductResponse> response = new ResponseWrapper<>();
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(jsonUtils.responseWithSuccess(response,"Successfully delete the Product"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateProductRequest request){
        ResponseWrapper<ProductResponse> response = new ResponseWrapper<>();
        ProductResponse productResponseDTOList = productService.updateProduct(request);
        response.setData(productResponseDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(jsonUtils.responseWithSuccess(response,"Successfully update the Product"));
    }
}
