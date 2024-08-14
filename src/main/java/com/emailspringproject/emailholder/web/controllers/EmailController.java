package com.emailspringproject.emailholder.web.controllers;


import com.emailspringproject.emailholder.domain.dtos.EmailDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.services.EmailService;
import com.emailspringproject.emailholder.services.SiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;
    private final SiteService siteService;

    @Autowired
    public EmailController(EmailService emailService, SiteService siteService) {
        this.emailService = emailService;
        this.siteService = siteService;
    }

    @GetMapping
    public ModelAndView getAllEmailsOfUser(ModelAndView modelAndView, @AuthenticationPrincipal UserDetails userDetails) {
        List<EmailDTO> emailsDTOs = emailService.getAllEmailsByUser(userDetails);
        modelAndView.addObject("emails", emailsDTOs);
        modelAndView.setViewName("emails");
        return modelAndView;
    }

    @PostMapping("/{siteId}")
    public Email createEmail(@PathVariable Long siteId, @RequestBody Email email) {
        return emailService.createEmail(siteId, email);
    }

    @GetMapping("/create")
    public ModelAndView getCreateEmail(@ModelAttribute("emailDTO") EmailDTO emailDTO, ModelAndView modelAndView) {
        modelAndView.setViewName("createEmail");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createEmail(@ModelAttribute("emailDTO") @Valid EmailDTO emailDTO, @AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {

        Email email = emailService.createEmail(emailDTO, userDetails);

        modelAndView.setViewName("redirect:/emails");
        return modelAndView;
    }

    @GetMapping("/sites/{emailId}")
    public String updateSitesToEmailPage(@PathVariable Long emailId, Model model) {
        List<Site> sites = emailService.getAllSitesForEmail();
        model.addAttribute("sites", sites);
        return "emails";
    }

    @GetMapping("/add/site/{email_id}")
    public ModelAndView getAddSiteToEmail(@PathVariable Long email_id, @ModelAttribute("siteDTO") SiteExportDTO siteDTO, ModelAndView modelAndView) {
        List<SiteExportDTO> allSites = siteService.getAllSites();
        modelAndView.setViewName("addSiteToEmail");
        modelAndView.addObject("email_id", email_id);
        modelAndView.addObject("allSitesDTO", allSites);
        modelAndView.addObject("siteDTO", siteDTO);
        return modelAndView;
    }

    @PostMapping("/add/sites/{emailId}")
    public ModelAndView addSiteToEmail(@PathVariable Long emailId, @ModelAttribute("siteDTO") SiteExportDTO siteDTO, ModelAndView modelAndView) {
        emailService.addSiteToEmail(emailId, siteDTO);
        modelAndView.setViewName("redirect:/emails");
        return modelAndView;
    }

    @DeleteMapping("/{emailId}/sites/{siteId}")
    public ResponseEntity<Email> removeSiteFromEmail(@PathVariable Long emailId, @PathVariable Long siteId) {
        return emailService.removeSiteFromEmail(emailId, siteId);
    }

    @DeleteMapping("/delete/{email_id}")
    public String deleteEmail(@PathVariable Long email_id) { //TODO Model and view
        emailService.deleteEmail(email_id);

        return "redirect:/emails";
    }

    @GetMapping("/edit/{email_id}")
    public ModelAndView editEmail(@PathVariable Long email_id, ModelAndView modelAndView) {
        EmailDTO returnedEmailDTO = emailService.getEmailById(email_id);
        modelAndView.setViewName("updateEmail");
        modelAndView.addObject("emailDTO", returnedEmailDTO);
        modelAndView.addObject("emailId", email_id);
        return modelAndView;
    }

    @PostMapping("/update/{email_id}")
    public ModelAndView updateEmail(@ModelAttribute("emailDTO") @Valid EmailDTO emailDTO, @PathVariable Long email_id, ModelAndView modelAndView) {
        emailService.updateEmail(emailDTO, email_id);
        modelAndView.setViewName("redirect:/emails");
        return modelAndView;
    }
}
