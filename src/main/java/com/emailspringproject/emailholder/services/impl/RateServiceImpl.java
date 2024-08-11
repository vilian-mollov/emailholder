package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.RateDTO;
import com.emailspringproject.emailholder.domain.entities.Rate;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.repositories.RateRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.services.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {

    private final ModelMapper modelMapper;
    private final RateRepository rateRepository;
    private final SiteRepository siteRepository;

    @Autowired
    public RateServiceImpl(ModelMapper modelMapper, RateRepository rateRepository, SiteRepository siteRepository) {
        this.modelMapper = modelMapper;
        this.rateRepository = rateRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public void addRiteToSite(Long siteId, RateDTO rateDTO) {
        Rate rate = modelMapper.map(rateDTO, Rate.class);
        rateRepository.save(rate);
        Optional<Site> siteOpt = siteRepository.findById(siteId);
        Site site = siteOpt.get();

        site.addRate(rate);
        siteRepository.save(site);
    }
}
