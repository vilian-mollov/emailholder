package com.emailspringproject.emailholder.controllers;


import com.emailspringproject.emailholder.domain.Email;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {


    private final EmailRepository emailRepository;
    private final SiteRepository siteRepository;
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailRepository emailRepository,SiteRepository siteRepository, EmailService emailService){
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
        this.emailService = emailService;
    }

    @GetMapping
    public List<Email> getAllEmails() {
        return emailService.getAllEmails();
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
