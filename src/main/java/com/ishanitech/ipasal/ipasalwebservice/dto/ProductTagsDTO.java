package com.ishanitech.ipasal.ipasalwebservice.dto;
/**
 * 
 * @author Tanchhowpa
 * Sep 18, 2019, 10:29:22 AM
 *
 */
public class ProductTagsDTO {
	
	private int productTagId;
	private int productId;
	private String productTag;
	
	
	
	public int getProductTagId() {
		return productTagId;
	}
	public void setProductTagId(int productTagId) {
		this.productTagId = productTagId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductTag() {
		return productTag;
	}
	public void setProductTag(String productTag) {
		this.productTag = productTag;
	}
	
}
