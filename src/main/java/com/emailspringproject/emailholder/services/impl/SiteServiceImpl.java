package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.Site;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.SiteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {
    private final SiteRepository siteRepository;
    private final EmailRepository emailRepository;

    // Constructor injection
    public SiteServiceImpl(SiteRepository siteRepository, EmailRepository emailRepository) {
        this.siteRepository = siteRepository;
        this.emailRepository = emailRepository;
    }

    @Override
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    public Site getSiteById(Long id) {
        return null;
    }

    @Override
    public Site createSite(Site site) {
        return null;
    }

    @Override
    public Site updateSite(Long id, Site updatedSite) {
        return null;
    }

    @Override
    public void deleteSite(Long id) {

    }

    @Override
    public void addEmailToSite(Long siteId, Long emailId) {

    }


}
