package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.io.Serializable;

public class ShippingDTO implements Serializable{
	private static final long serialVersionUID = -6929356817672594602L;
	private int shippingDetailsId;
    private int customerId;
    private int shippingTypeId;
    private String address;
    private String city;
    private String email;
    private long phone;
    private int zipcode;
    private int state;
    private String type;

    @Override
    public String toString() {
        return "{" +
            " shippingDetailsId='" + getShippingDetailsId() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", shippingTypeId='" + getShippingTypeId() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

    public int getShippingDetailsId() {
        return shippingDetailsId;
    }

    public void setShippingDetailsId(int shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public int getShippingTypeId() {
        return shippingTypeId;
    }

    public void setShippingTypeId(int shippingTypeId) {
        this.shippingTypeId = shippingTypeId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
