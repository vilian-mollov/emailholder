package com.emailspringproject.emailholder.constants;

public enum Messages {

    SUCCESS_CREATE("Successfully Created"),

    SUCCESS_UPDATE("Successfully Updated"),
    SUC_DEL_SITE("Successfully deleted site %s"),
    SUC_DEL_EMAIL("Successfully deleted email %s"),
    EMAIL_EXIST("Email with this address already exist.");

    private final String message;
    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
