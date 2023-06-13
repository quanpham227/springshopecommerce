package com.springshopecommerce.controller.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;




@Controller ( value = "homeControllerOfWeb")
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "web/home";
    }

}
