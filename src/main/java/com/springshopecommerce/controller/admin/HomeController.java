package com.springshopecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller (value = "homeControllerOfAdmin")
@RequestMapping("admin")
public class HomeController {
    @GetMapping("")
    public ModelAndView home (){
        return new ModelAndView("admin/dashboard");
    }
    @GetMapping("404")
    public ModelAndView notFound (){
        return new ModelAndView("admin/errors/404");
    }
}
