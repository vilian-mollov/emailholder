package com.emailspringproject.emailholder.domain.dtos;

import jakarta.validation.constraints.*;

public class UserRegisterDTO {

    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
