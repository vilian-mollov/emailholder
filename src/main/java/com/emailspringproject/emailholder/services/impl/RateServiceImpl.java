package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.dtos.RateDTO;
import com.emailspringproject.emailholder.domain.entities.Rate;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.RateRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.services.RateService;
import com.emailspringproject.emailholder.utilities.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RateServiceImpl implements RateService {

    private final ModelMapper modelMapper;
    private final RateRepository rateRepository;
    private final SiteRepository siteRepository;
    private final UserRepository userRepository;

    private final CurrentUser currentUser;

    @Autowired
    public RateServiceImpl(ModelMapper modelMapper, RateRepository rateRepository, SiteRepository siteRepository, UserRepository userRepository, CurrentUser currentUser) {
        this.modelMapper = modelMapper;
        this.rateRepository = rateRepository;
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    @Override
    public void addRateToSite(Long siteId, RateDTO rateDTO) {

        Optional<User> userOpt = userRepository.findFirstByUsername(currentUser.getUsername());
        User user = userOpt.get();

        Optional<Site> siteOpt = siteRepository.findById(siteId);
        Site site = siteOpt.get();

        Optional<Rate> rateOpt = rateRepository.findFirstByUserAndSite(user, site);

        if (rateOpt.isPresent()) {
            Rate existRate = rateOpt.get();
            existRate.setRate(rateDTO.getRate());
            rateRepository.save(existRate);

            return;
        }

        Rate rate = modelMapper.map(rateDTO, Rate.class);
        rate.setSite(site);
        rate.setUser(user);
        rateRepository.save(rate);

        user.getRates().add(rate);
        userRepository.save(user);

        site.getRates().add(rate);
        siteRepository.save(site);
    }
}
