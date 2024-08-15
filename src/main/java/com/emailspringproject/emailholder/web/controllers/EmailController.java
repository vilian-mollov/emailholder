package com.emailspringproject.emailholder.web.controllers;


import com.emailspringproject.emailholder.domain.dtos.EmailDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.services.EmailService;
import com.emailspringproject.emailholder.services.SiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.emailspringproject.emailholder.constants.Messages.SUCCESS_CREATE;
import static com.emailspringproject.emailholder.constants.Messages.SUCCESS_UPDATE;

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

    @GetMapping("/create")
    public ModelAndView getCreateEmail(@ModelAttribute("emailDTO") EmailDTO emailDTO, ModelAndView modelAndView) {
        modelAndView.setViewName("createEmail");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createEmail(@ModelAttribute("emailDTO") @Valid EmailDTO emailDTO, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("createEmail");
            return modelAndView;
        }

        String response = emailService.createEmail(emailDTO, userDetails);

        if (!response.equals(SUCCESS_CREATE.getMessage())) {
            modelAndView.setViewName("createEmail");
            modelAndView.addObject("hasRegisterError", true);
            modelAndView.addObject("error", response);
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/emails");
        return modelAndView;
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

    @DeleteMapping("{email_id}/sites/{site_id}")
    public ModelAndView removeSiteFromEmail(@PathVariable Long email_id, @PathVariable Long site_id, ModelAndView modelAndView) {
        emailService.removeSiteFromEmail(email_id, site_id);
        modelAndView.setViewName("redirect:/sites/email/" + email_id);
        return modelAndView;
    }

    @DeleteMapping("/delete/{email_id}")
    public ModelAndView deleteEmail(@PathVariable Long email_id, ModelAndView modelAndView) {
        emailService.deleteEmail(email_id);
        modelAndView.setViewName("redirect:/emails");
        return modelAndView;
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
    public ModelAndView updateEmail(@PathVariable Long email_id, @ModelAttribute("emailDTO") @Valid EmailDTO emailDTO, BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("updateEmail");
            return modelAndView;
        }

        String response = emailService.updateEmail(emailDTO, email_id);

        if (!response.equals(SUCCESS_UPDATE.getMessage())) {
            modelAndView.setViewName("updateEmail");
            modelAndView.addObject("hasRegisterError", true);
            modelAndView.addObject("error", response);
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/emails");
        return modelAndView;
    }
}
