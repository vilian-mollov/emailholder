package com.emailspringproject.emailholder.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "emails")
public class Email extends BaseEntity {

    @Column(nullable = false)
    private String address;

    @ManyToMany
    @JoinTable(
            name = "email_site",
            joinColumns = @JoinColumn(name = "email_id"),
            inverseJoinColumns = @JoinColumn(name = "site_id")
    )
    private Set<Site> sites;

    public Email() {
    }
    public Email(String address) {
        this.address = address;
        this.sites = new HashSet<>();
    }

    public void addSite(Site site) {
        sites.add(site);
        site.getEmails().add(this);
    }

    public void removeSite(Site site) {
        sites.remove(site);
        site.getEmails().remove(this);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return Objects.equals(this.getId(), email.getId());
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + this.getId() +
                ", name='" + address + '\'' +
                '}';
    }

}
