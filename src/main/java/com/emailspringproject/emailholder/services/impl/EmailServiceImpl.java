package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.Email;
import com.emailspringproject.emailholder.domain.Site;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final SiteRepository siteRepository;

    // Constructor injection
    public EmailServiceImpl(EmailRepository emailRepository, SiteRepository siteRepository) {
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
    }
    //TODO make validations
    @Override
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @Override
    public Email getEmailById(Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    @Override
    public Email createEmail(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public Email createEmail(Long siteId, Email email) {
        Site site = siteRepository.findById(siteId).orElse(null);
        if (site != null) {
            email.getSites().add(site); // Add the site to the email's sites set
            site.getEmails().add(email); // Add the email to the site's emails set
            return emailRepository.save(email);
        }
        return null;
    }
    //TODO implement the updateEmail method
    @Override
    public Email updateEmail(Long id, Email updatedEmail) {
        return null;
    }

    @Override
    public void deleteEmail(Long id) {
        emailRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<Email> addSiteToEmail(Long emailId, Long siteId) {
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

    @Override
    public ResponseEntity<Email> removeSiteFromEmail(Long emailId, Long siteId) {

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


}
