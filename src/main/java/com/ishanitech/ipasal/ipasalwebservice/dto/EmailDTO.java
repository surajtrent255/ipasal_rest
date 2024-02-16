package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.Map;

public class EmailDTO {
    private String to;
    private String from;
    private String subject;
    private String content;
    private Map<String, Object> model;

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model ) {
        this.model = model;
    }

}