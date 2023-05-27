package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.Email;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final SiteRepository siteRepository;

    // Constructor injection
    public EmailServiceImpl(EmailRepository emailRepository, SiteRepository siteRepository) {
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @Override
    public Email getEmailById(Long id) {
        return null;
    }

    @Override
    public Email createEmail(Email email) {
        return null;
    }

    @Override
    public Email updateEmail(Long id, Email updatedEmail) {
        return null;
    }

    @Override
    public void deleteEmail(Long id) {

    }

    @Override
    public void addSiteToEmail(Long emailId, Long siteId) {

    }

    @Override
    public void removeSiteFromEmail(Long emailId, Long siteId) {

    }


}
