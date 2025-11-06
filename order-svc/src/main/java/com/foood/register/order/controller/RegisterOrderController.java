package com.foood.register.order.controller;


import com.foood.commons.dto.Order;
import com.foood.register.order.service.RegisterOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// TODO @CrossOrigin(origins = "http://localhost:5173")
@RestController
public class RegisterOrderController {

    private RegisterOrderService registerOrderService;

    public RegisterOrderController(RegisterOrderService registerOrderService) {
        this.registerOrderService = registerOrderService;
    }

    @PostMapping("/foood/register/order")
    public ResponseEntity<String> create(@RequestBody Order order) {
        System.out.println(order);
        registerOrderService.registerOrder(order);
        return ResponseEntity.ok().body("{\"respons\": \"Success\"}");
    }

}
