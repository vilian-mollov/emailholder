package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final EmailService emailService;

    @Autowired
    public HomeController(EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping({"", "/", "/index", "/home"})
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }

    @GetMapping("/list")
    public String getListPage(Model model) {
        List<Email> emails = emailService.getAllEmails();
        model.addAttribute("emails", emails);
        return "list";
    }

}
