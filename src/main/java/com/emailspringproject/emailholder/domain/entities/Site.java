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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sites_commets")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "sites_rates")
    private List<Rate> rates = new ArrayList<>();

    private Integer rate = 0;

    public Site() {
    }

    public Site(String address, String domainName, User user, List<Comment> comments, List<Rate> rates) {
        this.domainName = domainName;
        this.address = address;
        this.emails = new HashSet<>();
        this.user = user;
        this.comments = comments;
        this.rates = rates;
        this.rate = getSiteRate();
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
        this.rate = getSiteRate();
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getSiteRate() {

        Integer averageRate = 0;

        for (Rate rate : this.rates) {
            averageRate += rate.getRate();
        }

        if (this.rates.size() > 0) {
            averageRate = averageRate / this.rates.size();
        }
        return averageRate;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
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
