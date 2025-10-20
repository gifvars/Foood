package com.foood.register.order.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/foood")
public class RegisterOrderController {


    @PostMapping("/register")
    public ResponseEntity<String> create(@NotNull @RequestBody String message) {
        System.out.println(message);
        return ResponseEntity.ok().body("{\"respons\": \"Success\"}");
    }


}
