package com.emailspringproject.emailholder.domain.dtos;

import com.emailspringproject.emailholder.domain.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CommentDTO {

    @NotBlank
    @NotNull
    private String comment;

    private LocalDateTime createdTime;

    private User user;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public String getFormattedCreatedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH);

        String formattedDateTime = createdTime.format(formatter);

        return formattedDateTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
