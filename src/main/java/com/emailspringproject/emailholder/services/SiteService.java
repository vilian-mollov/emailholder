package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.SiteExportDTO;
import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface SiteService {

    List<SiteExportDTO> getAllSites();

    List<SiteExportDTO> getAllSitesForUser(String user);

    List<SiteExportDTO> getSitesByEmail(Long email_id);

    List<String> createSite(SiteImportDTO site, UserDetails userDetails);

    Boolean updateSite(Long id, SiteImportDTO updatedSite);

    SiteExportDTO deleteSiteFromAllEmailsOfUser(Long id, UserDetails userDetails);
}
