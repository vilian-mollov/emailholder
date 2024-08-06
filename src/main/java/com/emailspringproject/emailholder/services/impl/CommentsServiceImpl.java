package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.SiteImportDTO;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final SiteRepository siteRepository;


    @Autowired
    public CommentsServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    public List<String> getAllCommentsForSite(SiteImportDTO siteDTO) {

//        siteRepository.findById(siteDTO.);  --> Find by ID DTO ID
        return null;
    }
}
