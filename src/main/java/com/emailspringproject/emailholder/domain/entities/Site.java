package com.emailspringproject.emailholder.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "site")
public class Site extends BaseEntity{

    @Column(name = "site_domain", nullable = false)
    private String siteDomain;

    @Column(name = "site_name", nullable = false)
    private String siteName;

    @ManyToMany(mappedBy = "sites", targetEntity = Email.class)
    private Set<Email> emails;

    public Site() {
    }

    public Site(String siteDomain, String siteName) {
        this.siteDomain = siteDomain;
        this.siteName = siteName;
        this.emails = new HashSet<>();
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public void setSiteDomain(String siteDomain) {
        this.siteDomain = siteDomain;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
                "id=" + this.getId() +
                ", siteDomain='" + siteDomain + '\'' +
                '}';
    }
}
