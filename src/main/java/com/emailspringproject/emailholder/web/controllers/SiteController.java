package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.RateDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;
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

import static com.emailspringproject.emailholder.constants.Errors.SITE_NOT_FOUND;
import static com.emailspringproject.emailholder.constants.Messages.SUC_DEL_SITE;

@Controller
@RequestMapping("/sites")
public class SiteController {

    private final SiteService siteService;


    @Autowired
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
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
    public ModelAndView getSiteByEmail(ModelAndView modelAndView, @PathVariable Long email_id) {
        modelAndView.setViewName("sites");
        List<SiteExportDTO> sites = siteService.getSitesByEmail(email_id);
        modelAndView.addObject("sites", sites);
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteSiteFromAllEmailsOfUser(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {

        SiteExportDTO expSiteDTO = siteService.deleteSiteFromAllEmailsOfUser(id, userDetails);

        if (expSiteDTO == null) {
            modelAndView.addObject("error", String.format(SITE_NOT_FOUND.toString()));
        } else {
            modelAndView.addObject("message", String.format(SUC_DEL_SITE.toString(), expSiteDTO.getDomainName()));
        }

        modelAndView.setViewName("sites");
        return modelAndView;
    }
}