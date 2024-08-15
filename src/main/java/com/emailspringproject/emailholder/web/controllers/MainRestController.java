package com.emailspringproject.emailholder.web.controllers;

import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.services.SiteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainRestController {
    private final SiteService siteService;

    public MainRestController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping(value = "rest/eholder/sites/all")
    public List<SiteExportDTO> getAllSites() {
        return siteService.getAllSites();
    }

}
