package com.springshopecommerce.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "controllerOfWeb")
@RequestMapping("/cart")
public class CartController {

    @GetMapping("")
    public String getCarrtPage (){
        return "web/cart.html";
    }

}
