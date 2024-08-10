package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.EmailImportDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmailService {
    List<Email> getAllEmailsByUser();

    List<Site> getAllSitesForEmail();

    Email getEmailById(Long id);

    Email createEmail(EmailImportDTO email);

    Email createEmail(Long id, Email email);

    Email updateEmail(Long siteId, Email updatedEmail);

    void deleteEmail(Long id);

    ResponseEntity<Email> addSiteToEmail(Long emailId, Long siteId);

    ResponseEntity<Email> removeSiteFromEmail(Long emailId, Long siteId);
}
