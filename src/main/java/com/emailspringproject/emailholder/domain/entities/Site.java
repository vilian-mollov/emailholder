package com.emailspringproject.emailholder.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        return Objects.equals(id, site.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", siteDomain='" + siteDomain + '\'' +
                '}';
    }
}
