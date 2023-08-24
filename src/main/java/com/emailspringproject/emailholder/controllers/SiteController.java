package com.emailspringproject.emailholder.controllers;

import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sites")
public class SiteController {


    private final SiteRepository siteRepository;

    @Autowired
    public SiteController(SiteRepository siteRepository){
        this.siteRepository = siteRepository;
    }

    @GetMapping
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Site getSiteById(@PathVariable Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Site createSite(@RequestBody Site site) {
        return siteRepository.save(site);
    }

    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable Long id) {
        siteRepository.deleteById(id);
    }
}