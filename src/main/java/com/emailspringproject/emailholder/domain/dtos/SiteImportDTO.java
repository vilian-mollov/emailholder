package com.emailspringproject.emailholder.domain.dtos;

public class SiteImportDTO {

    private String domainName;
    private String address;

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
}
