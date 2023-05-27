package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.Email;

import java.util.List;

public interface EmailService {
    List<Email> getAllEmails();
    Email getEmailById(Long id);
    Email createEmail(Email email);
    Email updateEmail(Long id, Email updatedEmail);
    void deleteEmail(Long id);
    void addSiteToEmail(Long emailId, Long siteId);
    void removeSiteFromEmail(Long emailId, Long siteId);
}
