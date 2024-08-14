package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.RateDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface RateService {
    void addRateToSite(Long siteId, RateDTO rateDTO, UserDetails userDetails);
}
