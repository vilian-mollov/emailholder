package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.EmailImportDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.EmailService;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final SiteRepository siteRepository;
    private final UserService userService;
    private ValidationUtils validationUtils;
    private ModelMapper modelMapper;


    public EmailServiceImpl(EmailRepository emailRepository,
                            SiteRepository siteRepository,
                            UserService userService,
                            ValidationUtils validationUtils,
                            ModelMapper modelMapper) {
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
        this.userService = userService;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    //TODO make validations
    @Override
    public List<Email> getAllEmailsByUser() {
        User user = userService.getCurrentUser();
        return emailRepository.findAllByUser(user);
    }

    @Override
    public Email getEmailById(Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    @Override
    public Email createEmail(EmailImportDTO emailDTO) {
        if (this.validationUtils.isValid(emailDTO)) {
            User user = userService.getCurrentUser();
            Email email = this.modelMapper.map(emailDTO, Email.class);
            user.addEmail(email);
            Email savedEmail = emailRepository.save(email);
            return savedEmail;
        }
        return null;
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
            Email savedEmail = emailRepository.save(email);

            return ResponseEntity.ok(savedEmail);
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
            Email savedEmail = emailRepository.save(email);

            return ResponseEntity.ok(savedEmail);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
