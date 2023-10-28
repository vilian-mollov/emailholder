package com.emailspringproject.emailholder.domain.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.tuple.GenerationTiming;

import java.sql.Timestamp;

public class UserRegisterDTO {

    @NotNull
    @NotBlank(message = "Username is empty")
    @Size(min = 3, message = "Username must be at least 3 symbols")
    private String username;

    @NotNull
    @Email
    private String mainEmail;

    @NotNull
    @NotBlank(message = "Password is empty")
    @Size(min = 3, message = "Password must be at least 3 symbols")
    private String password;

    private Timestamp createdAt;

    private Timestamp lastChangedAt;

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

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "username='" + username + '\'' +
                ", mainEmail='" + mainEmail + '\'' +
                ", createdAt=" + createdAt +
                ", lastChangedAt=" + lastChangedAt +
                '}';
    }
}
