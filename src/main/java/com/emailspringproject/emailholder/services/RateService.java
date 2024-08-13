package com.emailspringproject.emailholder.services;

import com.emailspringproject.emailholder.domain.dtos.RateDTO;

public interface RateService {
    void addRateToSite(Long siteId, RateDTO rateDTO);
}
