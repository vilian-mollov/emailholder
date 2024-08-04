package com.emailspringproject.emailholder.domain.dtos;

public class SiteExportDTO {

    private String domainName;

    private String address;

    private Boolean safety;

    private Integer rate;


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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }


}
