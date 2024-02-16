package com.ishanitech.ipasal.ipasalwebservice.dto;


public class PayLoadDTO <T> {

    private T payload;

    public T getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "PayLoadDTO{" +
                "payload=" + payload +
                '}';
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public PayLoadDTO() {
    }

    public PayLoadDTO(T payload) {
        this.payload = payload;
    }
}
