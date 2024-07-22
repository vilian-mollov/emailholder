package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;
import com.emailspringproject.emailholder.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.emailspringproject.emailholder.constants.Errors.SITE_NOT_FOUND;
import static com.emailspringproject.emailholder.constants.Messages.SUC_DEL_SITE;

@Controller
@RequestMapping("/sites")
public class SiteController {

    private final SiteService siteService;

    @Autowired
    public SiteController(SiteService siteService){
        this.siteService = siteService;
    }

    @GetMapping
    public ModelAndView getAllSitesForUser(ModelAndView modelAndView, @RequestParam String username) {
        modelAndView.setViewName("sites");
        modelAndView.addObject("sites", siteService.getAllSitesForUser(username));
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView getCreateSitePage(ModelAndView modelAndView) {
        modelAndView.setViewName("createSite");
        return modelAndView;
    }

    @GetMapping("/email")
    public ModelAndView getSiteByEmail(ModelAndView modelAndView, @RequestParam Long email_id) {
        modelAndView.setViewName("sites");
        List<SiteExportDTO> sites = siteService.getSitesByEmail(email_id);
        modelAndView.addObject("sites", sites);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createSite(ModelAndView modelAndView, SiteImportDTO siteImportDTO) {
        modelAndView.setViewName("sites");

        List<String> problems = siteService.createSite(siteImportDTO);

        if(!problems.isEmpty()){
            modelAndView.addObject("problems", problems);
        }

        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteSiteFromAllEmailsOfUser(ModelAndView modelAndView, @PathVariable Long id) {

        SiteExportDTO expSiteDTO = siteService.deleteSiteFromAllEmailsOfUser(id);

        if (expSiteDTO == null) {
            modelAndView.addObject("error", String.format(SITE_NOT_FOUND.toString()));
        }else {
            modelAndView.addObject("message", String.format(SUC_DEL_SITE.toString(), expSiteDTO.getDomainName()));
        }

        modelAndView.setViewName("sites");
        return modelAndView;
    }
}