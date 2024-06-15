package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.entities.Email;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmailService {
    List<Email> getAllEmailsByUser();
    Email getEmailById(Long id);
    Email createEmail(Email email);
    Email createEmail(Long id, Email email);
    Email updateEmail(Long siteId, Email updatedEmail);
    void deleteEmail(Long id);
    ResponseEntity<Email> addSiteToEmail(Long emailId, Long siteId);
    ResponseEntity<Email> removeSiteFromEmail(Long emailId, Long siteId);
}
