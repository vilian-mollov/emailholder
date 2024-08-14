package com.emailspringproject.emailholder.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

public class UserUpdateUsernameDTO {

    @NotNull
    @NotBlank(message = "Username is empty")
    @Size(min = 3, message = "Username must be at least 3 symbols")
    private String currentUsername;

    @NotNull
    @NotBlank(message = "Username is empty")
    @Size(min = 3, message = "Username must be at least 3 symbols")
    private String usernameNew;

    @NotNull
    @NotBlank(message = "Password is empty")
    @Size(min = 3, message = "Password must be at least 3 symbols")
    private String password;
    private Timestamp lastChangedAt;

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getUsernameNew() {
        return usernameNew;
    }

    public void setUsernameNew(String usernameNew) {
        this.usernameNew = usernameNew;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastChangedAt() {
        return lastChangedAt;
    }

    public void setLastChangedAt(Timestamp lastChangedAt) {
        this.lastChangedAt = lastChangedAt;
    }
}
