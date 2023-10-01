package com.emailspringproject.emailholder.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "site")
public class Site extends BaseEntity{

    @Column(name = "site_domain", nullable = false)
    private String domainName;

    @Column
    private String address;

    @Column
    private Boolean safety;

    @ManyToMany(mappedBy = "sites", targetEntity = Email.class)
    private Set<Email> emails;

    public Site() {
    }

    public Site(String address, String domainName) {
        this.domainName = domainName;
        this.address = address;
        this.emails = new HashSet<>();
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

    public Set<Email> getEmails() {
        return emails;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        return Objects.equals(this.getId(), site.getId());
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Site{" +
                "siteDomain='" + domainName + '\'' +
                ", siteAddress='" + address + '\'' +
                '}';
    }
}
