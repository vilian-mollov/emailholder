package com.emailspringproject.emailholder.controllers;


import com.emailspringproject.emailholder.domain.Email;
import com.emailspringproject.emailholder.domain.Site;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emails")
public class EmailController {


    private final EmailRepository emailRepository;
    private final SiteRepository siteRepository;

    @Autowired
    public EmailController(EmailRepository emailRepository,SiteRepository siteRepository){
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
    }

    @GetMapping
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @GetMapping("/{id}")
    public Email getEmailById(@PathVariable Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    @PostMapping("/{siteId}")
    public Email createEmail(@PathVariable Long siteId, @RequestBody Email email) {
        Site site = siteRepository.findById(siteId).orElse(null);
        if (site != null) {
            email.getSites().add(site); // Add the site to the email's sites set
            site.getEmails().add(email); // Add the email to the site's emails set
            return emailRepository.save(email);
        }
        return null;
    }

    @PutMapping("/{emailId}/sites/{siteId}")
    public ResponseEntity<Email> addSiteToEmail(@PathVariable Long emailId, @PathVariable Long siteId) {
        Optional<Email> optionalEmail = emailRepository.findById(emailId);
        Optional<Site> optionalSite = siteRepository.findById(siteId);

        if (optionalEmail.isPresent() && optionalSite.isPresent()) {
            Email email = optionalEmail.get();
            Site site = optionalSite.get();

            email.addSite(site);
            emailRepository.save(email);

            return ResponseEntity.ok(email);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{emailId}/sites/{siteId}")
    public ResponseEntity<Email> removeSiteFromEmail(@PathVariable Long emailId, @PathVariable Long siteId) {
        Optional<Email> optionalEmail = emailRepository.findById(emailId);
        Optional<Site> optionalSite = siteRepository.findById(siteId);

        if (optionalEmail.isPresent() && optionalSite.isPresent()) {
            Email email = optionalEmail.get();
            Site site = optionalSite.get();

            email.removeSite(site);
            emailRepository.save(email);

            return ResponseEntity.ok(email);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEmail(@PathVariable Long id) {
        emailRepository.deleteById(id);
    }
}
