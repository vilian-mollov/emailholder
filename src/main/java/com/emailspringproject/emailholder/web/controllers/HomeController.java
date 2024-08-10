package com.emailspringproject.emailholder.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping({"", "/", "/index", "/home"})
    public ModelAndView getIndexPage(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView getAboutPage(ModelAndView modelAndView) {
        modelAndView.setViewName("about");
        return modelAndView;
    }

}
