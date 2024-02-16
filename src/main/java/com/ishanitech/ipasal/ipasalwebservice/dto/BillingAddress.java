package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author yoomes
 *
 */
public class BillingAddress implements Serializable{
	private static final long serialVersionUID = 4464811576298280160L;
	private int billingId;
	
	@Email(message = "Must be valid email address!")
	private String email;
	
	@NotBlank(message = "Must provide the street address!")
	@Max(value = 120, message = "Max 120 characterare allowed!")
	private String street;
	
	@NotBlank(message = "Must provide city name!")
	@Max(value = 50, message = "Max 50 characters are allowed!")
	private String city;
	
	@NotNull(message = "Must provide state id!")
	@Max(value = 1, message = "Max one number allowed!")
	private int state;
	private Date addedDate;
	private int zipcode;
	private double phone;
	private int userId;
	public int getBillingId() {
		return billingId;
	}
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public double getPhone() {
		return phone;
	}
	public void setPhone(double phone) {
		this.phone = phone;
	}
}
