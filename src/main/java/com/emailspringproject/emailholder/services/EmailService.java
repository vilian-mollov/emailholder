package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.EmailDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmailService {
    List<Email> getAllEmailsByUser();

    List<Site> getAllSitesForEmail();

    EmailDTO getEmailById(Long id);

    Email createEmail(EmailDTO email);

    Email createEmail(Long id, Email email);

    EmailDTO updateEmail(EmailDTO updatedEmail, Long emailId);

    void deleteEmail(Long id);

    ResponseEntity<Email> addSiteToEmail(Long emailId, Long siteId);

    ResponseEntity<Email> removeSiteFromEmail(Long emailId, Long siteId);
}
