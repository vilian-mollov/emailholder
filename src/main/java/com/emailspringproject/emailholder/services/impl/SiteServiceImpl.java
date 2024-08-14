package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.services.SiteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SiteServiceImpl implements SiteService {
    private final SiteRepository siteRepository;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SiteServiceImpl(SiteRepository siteRepository,
                           EmailRepository emailRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.siteRepository = siteRepository;
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<SiteExportDTO> getAllSites() {
        List<Site> sites = siteRepository.findAll();

        List<SiteExportDTO> sitesDTOs = new ArrayList<>();

        for (Site site : sites) {
            SiteExportDTO siteDTO = modelMapper.map(site, SiteExportDTO.class);
            sitesDTOs.add(siteDTO);
        }

        return sitesDTOs;
    }

    @Override
    public List<SiteExportDTO> getAllSitesForUser(String username) {

        Optional<User> optUser = userRepository.findFirstByUsername(username);
        User user = optUser.get();

        List<Site> sites = siteRepository.findAllByUser(user);

        List<SiteExportDTO> sitesDTOs = new ArrayList<>();

        for (Site site : sites) {
            SiteExportDTO siteDTO = modelMapper.map(site, SiteExportDTO.class);
            sitesDTOs.add(siteDTO);
        }

        return sitesDTOs;
    }

    @Override
    public List<SiteExportDTO> getSitesByEmail(Long email_id) {

        Email email = emailRepository.findFirstById(email_id);

        List<SiteExportDTO> sites = new ArrayList<>();

        for (Site site : email.getSites()) {
            sites.add(modelMapper.map(site, SiteExportDTO.class));
        }

        return sites;
    }

    @Override
    public List<String> createSite(SiteImportDTO siteDTO, UserDetails userDetails) {

        List<String> errors = new ArrayList<>();

        if (!siteDTO.getAddress().startsWith("http")) {
            errors.add("Incomplete link, sites should start with http or https");
        }

        List<Site> sitesFoundByAddress = siteRepository.findAllByAddress(siteDTO.getAddress());

        if (!sitesFoundByAddress.isEmpty()) {
            errors.add("Site with this address already exist");
        }

        if (errors.isEmpty()) {
            Site site = modelMapper.map(siteDTO, Site.class);
            site.setUser(userRepository.findFirstByUsername(userDetails.getUsername()).get());

            if (site.getAddress().startsWith("https")) {
                site.setSafety(true);
            }

            siteRepository.save(site);
        }

        return errors;
    }

    @Override
    public Boolean updateSite(Long id, SiteImportDTO updatedSite) {
        return null;
    }

    @Override
    public SiteExportDTO deleteSiteFromAllEmailsOfUser(Long id, UserDetails userDetails) {

        Optional<Site> optSite = siteRepository.findById(id);

        if (optSite.isEmpty()) {
            return null;
        }

        Site site = optSite.get();
        SiteExportDTO expSiteDTO = modelMapper.map(site, SiteExportDTO.class);


        Optional<User> optUser = userRepository.findFirstByUsername(userDetails.getUsername());

        User user = optUser.get();
        for (Email email : user.getEmails()) {
            email.removeSite(site);
        }

        return expSiteDTO;
    }

}
