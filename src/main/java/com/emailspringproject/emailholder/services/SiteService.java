package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.entities.Site;

import java.util.List;

public interface SiteService {
    List<Site> getAllSites();
    Site getSiteById(Long id);
    Site createSite(Site site);
    Site updateSite(Long id, Site updatedSite);
    void deleteSite(Long id);
    void addEmailToSite(Long siteId, Long emailId);
}
