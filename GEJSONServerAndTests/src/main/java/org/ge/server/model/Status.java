package org.ge.server.model;

public class Status {

    private String code;
    private String description;

    public Status() {
    }

    public Status(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}