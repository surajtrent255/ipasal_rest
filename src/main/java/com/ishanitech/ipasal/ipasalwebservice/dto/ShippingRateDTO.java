package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * @author 'Azens Eklak'
 * email: azens1995@gmail.com
 * created on Mar 01, 2019
 * since 2017
 **/
public class ShippingRateDTO {
    private int id;
    private String location;
    private int amount;
    private String information;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
