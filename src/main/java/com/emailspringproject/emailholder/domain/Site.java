package com.emailspringproject.emailholder.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String siteDomain;
   private String siteName;

   @ManyToOne
   private Email email;

    public Site() {
    }

    public Site(String siteDomain, String siteName) {
        this.siteDomain = siteDomain;
        this.siteName = siteName;
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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
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
