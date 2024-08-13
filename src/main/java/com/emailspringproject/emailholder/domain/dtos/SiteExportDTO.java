package com.emailspringproject.emailholder.domain.dtos;

import com.emailspringproject.emailholder.domain.entities.Rate;

import java.util.Set;

public class SiteExportDTO {

    private Long id;

    private String domainName;

    private String address;

    private Boolean safety;

    private Set<Rate> rates;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getSafety() {
        return safety;
    }

    public void setSafety(Boolean safety) {
        this.safety = safety;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public Integer getAverageForSite() {
        if(this.rates == null || this.rates.isEmpty()){
            return 0;
        }
        Integer avg = 0;
        for (Rate rate : this.rates) {
            avg += rate.getRate();
        }
        return avg / rates.size();
    }
}
