package com.foood.register.order.controller;


import com.foood.commons.dto.Order;
import com.foood.register.order.service.RegisterOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/foood")
public class RegisterOrderController {

    private RegisterOrderService registerOrderService;

    public RegisterOrderController(RegisterOrderService registerOrderService) {
        this.registerOrderService = registerOrderService;
    }

    @PostMapping("/register/order")
    public ResponseEntity<String> create(@NotNull @RequestBody Order order) {
        System.out.println(order);
        registerOrderService.sendOrderEvent(order);
        return ResponseEntity.ok().body("{\"respons\": \"Success\"}");
    }

}
