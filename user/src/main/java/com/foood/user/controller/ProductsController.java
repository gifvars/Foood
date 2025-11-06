package com.foood.user.controller;

import com.foood.commons_svc.dto.product.PaginatedProductResponse;
import com.foood.commons_svc.util.ResponseWrapper;
import com.foood.commons_svc.util.data.transformers.PaginatedProductResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductsController {

    @Value("${authservice.hostname}")
    private String hostname;

    @GetMapping("/getCategories")
    public ResponseEntity<?> getCategories(@RequestHeader("Authorization") String authHeader) {
        RestClient restClient = RestClient.create();
        System.out.println("Making request to get categories");
        var result = restClient.get().uri("http://" + hostname + ":9091/api/v1/product/getCategories")
                .header("Authorization", authHeader)
                .retrieve().body(ResponseWrapper.class);
        if (result.getErrors() != null && !result.getErrors().isEmpty()) {
            return ResponseEntity.internalServerError().body(result.getErrors());
        }
        System.out.println("Returning categories");
        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/getAllProducts")
    public PaginatedProductResponse getAllProducts(@RequestHeader("Authorization") String authHeader,
                                                   @RequestParam(required = false, defaultValue = "1") int pageNo) {
        Map<String, List<String>> queryParams = new LinkedHashMap<>();
        List<String> pageNumbers = new ArrayList<>();
        pageNumbers.add(String.valueOf(pageNo));
        queryParams.put("pageNo", pageNumbers);
        RestClient restClient = RestClient.create();
        URI uri = UriComponentsBuilder
                .fromUriString("http://" +  hostname + ":9091/api/v1/product/getProducts")
                .queryParams(MultiValueMap.fromMultiValue(queryParams)).build().toUri();
        var result = restClient.get().uri(uri)
                .header("Authorization", authHeader)
                .retrieve().body(ResponseWrapper.class);
        return PaginatedProductResponseTransformer.transform((LinkedHashMap<String, Object>) result.getData());
    }

    @PostMapping("/searchProducts")
    public PaginatedProductResponse searchProducts(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, String> params) {
        MultiValueMap<String, String> queryParams = MultiValueMap.fromSingleValue(params);
        RestClient restClient = RestClient.create();
        URI uri = UriComponentsBuilder
                .fromUriString("http://" +  hostname + ":9091/api/v1/product/getProducts")
                .queryParams(queryParams).build().toUri();
        var result = restClient.get().uri(uri)
                .header("Authorization", authHeader)
                .retrieve().body(ResponseWrapper.class);
        System.out.println(result);
        return PaginatedProductResponseTransformer.transform((LinkedHashMap<String, Object>) result.getData());
    }
}
