package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.EmailDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.EmailService;
import com.emailspringproject.emailholder.services.UserService;
import com.emailspringproject.emailholder.utilities.ValidationUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<EmailDTO> getAllEmailsByUser(UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        List<Email> emails = emailRepository.findAllByUser(user);
        List<EmailDTO> emailDTOS = new ArrayList<>();

        for (Email email : emails) {
            EmailDTO dto = modelMapper.map(email, EmailDTO.class);
            emailDTOS.add(dto);
        }

        return emailDTOS;
    }

    @Override
    public List<Site> getAllSitesForEmail() {
        return null;
    }

    @Override
    public EmailDTO getEmailById(Long id) {
        Email email = emailRepository.findById(id).orElse(null);
        return modelMapper.map(email, EmailDTO.class);
    }

    @Override
    public Email createEmail(EmailDTO emailDTO, UserDetails userDetails) {
        if (!this.validationUtils.isValid(emailDTO)) {
            return null;
        }
        User user = userService.getCurrentUser(userDetails);
        Email email = modelMapper.map(emailDTO, Email.class);
        user.addEmail(email);
        Email savedEmail = emailRepository.save(email);
        return savedEmail;
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


    @Override
    public EmailDTO updateEmail(EmailDTO updatedEmail, Long emailId) {
        Optional<Email> emailOpt = emailRepository.findById(emailId);
        Email email = emailOpt.get();
        email.setEmailAddress(updatedEmail.getEmailAddress());
        email.setDescription(updatedEmail.getDescription());
        emailRepository.save(email);

        return updatedEmail;
    }

    @Override
    @Transactional
    public void deleteEmail(Long id) {
        Optional<Email> emailOpt = emailRepository.findById(id);
        Email email = emailOpt.get();
        emailRepository.delete(email);
    }

    @Override
    public ResponseEntity<Email> addSiteToEmail(Long emailId, SiteExportDTO siteDTO) {
        Optional<Email> optionalEmail = emailRepository.findById(emailId);
        Optional<Site> optionalSite = siteRepository.findById(siteDTO.getId());

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
