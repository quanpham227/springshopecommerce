package com.springshopecommerce.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller ( value = "homeControllerOfWeb")

public class HomeController {
    @GetMapping("/")
    public String homePage (Model model) {
        model.addAttribute("message", "Welcome to my Spring Boot application!");
        return "web/home";
    }
}
