package com.emailspringproject.emailholder.domain.dtos;

import jakarta.validation.constraints.Size;

public class UserLoginDTO {

    @Size(min = 3, message = "Username must be at least 3 symbols")
    private String username;

    @Size(min = 3, message = "Password must be at least 3 symbols")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
