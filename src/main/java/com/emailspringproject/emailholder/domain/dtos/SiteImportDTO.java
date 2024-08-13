package com.emailspringproject.emailholder.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SiteImportDTO {


    @NotBlank(message = "Domain Name cannot be empty")
    private String domainName;


    @NotBlank(message = "Address cannot be empty")
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
