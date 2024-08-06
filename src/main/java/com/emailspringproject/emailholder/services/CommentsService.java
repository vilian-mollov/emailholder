package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;

import java.util.List;

public interface CommentsService {

    List<String> getAllCommentsForSite(SiteImportDTO siteDTO);
}
