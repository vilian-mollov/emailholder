package com.emailspringproject.emailholder.web.controllers;


import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping
    public String getAllEmails(Model model) {
        List<Email> emails = emailService.getAllEmails(); //TODO replace with DTO and return only emails of the current user (cookie)
        model.addAttribute("emails", emails);
        return "emails";
    }

    @GetMapping("/{id}")
    public Email getEmailById(@PathVariable Long id) {
        return emailService.getEmailById(id);
    }

    @PostMapping("/{siteId}")
    public Email createEmail(@PathVariable Long siteId, @RequestBody Email email) {
        return emailService.createEmail(siteId,email);
    }

    @PostMapping("/create")
    public Email createEmail(@RequestBody Email email) {
            return emailService.createEmail(email);
    }

    @PutMapping("/{emailId}/sites/{siteId}")
    public ResponseEntity<Email> addSiteToEmail(@PathVariable Long emailId, @PathVariable Long siteId) {
        return emailService.addSiteToEmail(emailId,siteId);
    }

    @DeleteMapping("/{emailId}/sites/{siteId}")
    public ResponseEntity<Email> removeSiteFromEmail(@PathVariable Long emailId, @PathVariable Long siteId) {
        return emailService.removeSiteFromEmail(emailId,siteId);
    }

    @DeleteMapping("/{id}")
    public void deleteEmail(@PathVariable Long id) {
        emailService.deleteEmail(id);
    }
}
