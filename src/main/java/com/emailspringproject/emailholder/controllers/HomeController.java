package com.emailspringproject.emailholder.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"", "/", "/index.html"})
    public String getIndexPage(){
        return "index";
    }

    @RequestMapping("/about.html")
    public String getAboutPage(){
        return "about";
    }

    @RequestMapping("/list.html")
    public String getListPage(){
        return "list";
    }


}
