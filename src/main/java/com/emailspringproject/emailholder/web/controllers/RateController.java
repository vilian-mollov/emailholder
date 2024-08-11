package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.RateDTO;
import com.emailspringproject.emailholder.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/site")
    public ModelAndView getRateForSite(@ModelAttribute("rateDTO") RateDTO rateDTO, ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @PostMapping("/site/{site_id}")
    public ModelAndView getRateForSite(@ModelAttribute("rateDTO") RateDTO rateDTO, ModelAndView modelAndView, @PathVariable Long site_id) {
        rateService.addRiteToSite(site_id, rateDTO);
        modelAndView.setViewName("redirect:/sites/all");
        return modelAndView;
    }
}
