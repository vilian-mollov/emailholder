package com.emailspringproject.emailholder.domain.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "main_email",unique = true)
    private String mainEmail;

    @Column
    private String password;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "last_changed_at")
    private Timestamp lastChangedAt;

//    @OneToMany(mappedBy = "user",targetEntity = Email.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Email> emails = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Site> sites = new HashSet<>();
    public User() {
    }

    public User(String username, String mainEmail, String password, Timestamp createdAt, Timestamp lastChangedAt,Set<Email> emails, Set<Site> sites) {
        this.username = username;
        this.mainEmail = mainEmail;
        this.password = password;
        this.createdAt = createdAt;
        this.lastChangedAt = lastChangedAt;
        this.emails = emails;
        this.sites = sites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastChangedAt() {
        return lastChangedAt;
    }

    public void setLastChangedAt(Timestamp lastChangedAt) {
        this.lastChangedAt = lastChangedAt;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public void addEmail(Email email) {
        this.emails.add(email);
    }

    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }
}
