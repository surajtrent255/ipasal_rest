package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * 
 * @author Tanchhowpa
 *
 * Apr 12, 2019 10:50:30 AM
 */


public class MerchantDTO {
	
	private int merchantId;
	private String merchantName;
	private String businessType;
	private String merchantType;
	private String merchantDesc;
	private String street;
	private String city;
	private long contactPrimary;
	private long contactSecondary;
	
	
	
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
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
	public long getContactPrimary() {
		return contactPrimary;
	}
	public void setContactPrimary(long contactPrimary) {
		this.contactPrimary = contactPrimary;
	}
	public long getContactSecondary() {
		return contactSecondary;
	}
	public void setContactSecondary(long contactSecondary) {
		this.contactSecondary = contactSecondary;
	}
	public String getMerchantDesc() {
		return merchantDesc;
	}
	public void setMerchantDesc(String merchantDesc) {
		this.merchantDesc = merchantDesc;
	}
	@Override
	    public String toString(){
	        return super.toString();
	}
	
	

}
