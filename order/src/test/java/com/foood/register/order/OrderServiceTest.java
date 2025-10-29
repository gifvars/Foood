package com.foood.register.order;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Ignore;



public class OrderServiceTest {

    private Logger logger = LogManager.getLogger(OrderServiceTest.class);;
    @Ignore
    @Test
    public void registerOrderWithSuccess(){

        RestTemplate restTemplate = new RestTemplate();

        String requestBody = "{\n" +
                "  \"userId\": 1234,\n" +
                "  \"orderItems\": [\n" +
                "    {\n" +
                "      \"productId\": 457,\n" +
                "      \"quantity\": 2,\n" +
                "      \"totalPrice\": 99.99\n" +
                "    }\n" +
                "  ],\n" +
                "  \"orderPrice\": 199.98,\n" +
                "  \"tax\": 24.00,\n" +
                "  \"discountAmount\": 10.00,\n" +
                "  \"orderStatus\": \"PENDING\",\n" +
                "  \"restaurantId\": 789,\n" +
                "  \"driverId\": 321,\n" +
                "  \"createdAt\": \"2025-10-29T08:00:00\",\n" +
                "  \"updatedAt\": \"2025-10-29T08:05:00\"\n" +
                "}\n";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject("http://localhost:4000/foood/register/order", request, String.class);
        logger.debug(response);

    }
}
