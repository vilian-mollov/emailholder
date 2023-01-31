package com.emailspringproject.emailholder.domain;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;

    @OneToMany
    @JoinColumn(name = "email_id")
    private Set<Site> sites = new HashSet<>();

    public Email() {
    }

    public Email(String address, Set<Site> sites) {
        this.address = address;
        this.sites = sites;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        return Objects.equals(id, email.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", name='" + address + '\'' +
                ", sites=" + sites +
                '}';
    }
}
