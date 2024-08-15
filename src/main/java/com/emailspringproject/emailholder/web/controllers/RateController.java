package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.RateDTO;
import com.emailspringproject.emailholder.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/site/{site_id}")
    public ModelAndView addRateForSite(@PathVariable Long site_id, @ModelAttribute("rateDTO") RateDTO rateDTO,
                                       @AuthenticationPrincipal UserDetails userDetails, ModelAndView modelAndView) {
        rateService.addRateToSite(site_id, rateDTO, userDetails);
        modelAndView.setViewName("redirect:/sites/all");
        return modelAndView;
    }
}
