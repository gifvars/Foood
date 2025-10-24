package com.foood.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

//    @RequestMapping(method = RequestMethod.POST, path = "/login")
//    public String login(LoginRequest request) {
//
//        return "login";
//    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
