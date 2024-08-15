package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.EmailDTO;
import com.emailspringproject.emailholder.domain.dtos.RateDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;
import com.emailspringproject.emailholder.services.EmailService;
import com.emailspringproject.emailholder.services.SiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sites")
public class SiteController {

    private final SiteService siteService;
    private final EmailService emailService;


    @Autowired
    public SiteController(SiteService siteService, EmailService emailService) {
        this.siteService = siteService;
        this.emailService = emailService;
    }

    @GetMapping("/all")
    public ModelAndView getAllSites(@ModelAttribute("rateDTO") RateDTO rateDTO, ModelAndView modelAndView) {
        modelAndView.setViewName("sitesAll");
        modelAndView.addObject("sites", siteService.getAllSites());
        return modelAndView;
    }

    @GetMapping("/user")
    public ModelAndView getAllSitesForUser(@AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {
        modelAndView.setViewName("sites");
        modelAndView.addObject("sites", siteService.getAllSitesForUser(userDetails.getUsername()));
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView getCreateSitePage(@ModelAttribute("siteDTO") SiteImportDTO siteDTO, ModelAndView modelAndView) {
        modelAndView.setViewName("createSite");
        return modelAndView;
    }

    @PostMapping("create")
    public ModelAndView createSite(@ModelAttribute("siteDTO") @Valid SiteImportDTO siteDTO, BindingResult bindingResult,
                                   @AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("createSite");
            return modelAndView;
        }

        List<String> errors = siteService.createSite(siteDTO, userDetails);

        if (!errors.isEmpty()) {
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("createSite");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/sites/user?username=" + userDetails.getUsername());
        return modelAndView;
    }

    @GetMapping("/email/{email_id}")
    public ModelAndView getSiteByEmail(@PathVariable Long email_id, ModelAndView modelAndView) {
        modelAndView.setViewName("sites");
        List<SiteExportDTO> sites = siteService.getSitesByEmail(email_id);
        EmailDTO emailDTO = emailService.getEmailById(email_id);

        modelAndView.addObject("sites", sites);
        modelAndView.addObject("forEmail", true);
        modelAndView.addObject("email_id", emailDTO.getId());
        return modelAndView;
    }
}