package com.emailspringproject.emailholder.controllers;


import com.emailspringproject.emailholder.domain.Email;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class HomeController {

    private final EmailRepository emailRepository;

    @Autowired
    public HomeController(EmailRepository emailRepository){
        this.emailRepository = emailRepository;
    }

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

    @GetMapping
    public List<Email> getEmails() {
        return emailRepository.findAll();
    }

    @PostMapping
    public Email createUser(@RequestBody Email email) {
        return emailRepository.save(email);
    }

    @PutMapping("/{id}")
    public Email updateEmail(@PathVariable Long id, @RequestBody Email email) {
        email.setId(id);
        return emailRepository.save(email);
    }

}
