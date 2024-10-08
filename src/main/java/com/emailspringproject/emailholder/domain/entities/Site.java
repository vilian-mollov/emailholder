package com.emailspringproject.emailholder.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "site")
public class Site extends BaseEntity {

    @Column(name = "site_domain", nullable = false)
    private String domainName;

    @Column
    private String address;

    @Column
    private Boolean safety;

    @ManyToMany(mappedBy = "sites", targetEntity = Email.class)
    private Set<Email> emails = new HashSet<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private Set<Rate> rates = new HashSet<>();

    public Site() {
    }

    public Site(String address, String domainName, User user, Set<Comment> comments, Set<Rate> rates) {
        this.domainName = domainName;
        this.address = address;
        this.emails = new HashSet<>();
        this.user = user;
        this.comments = comments;
        this.rates = rates;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        return Objects.equals(this.getId(), site.getId());
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
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
