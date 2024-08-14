package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.EmailDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface EmailService {
    List<EmailDTO> getAllEmailsByUser(UserDetails userDetails);

    List<Site> getAllSitesForEmail();

    EmailDTO getEmailById(Long id);

    String createEmail(EmailDTO email, UserDetails userDetails);

    Email createEmail(Long id, Email email);

    String updateEmail(EmailDTO updatedEmail, Long emailId);

    void deleteEmail(Long id);

    ResponseEntity<Email> addSiteToEmail(Long emailId, SiteExportDTO siteDTO);

    ResponseEntity<Email> removeSiteFromEmail(Long emailId, Long siteId);
}
