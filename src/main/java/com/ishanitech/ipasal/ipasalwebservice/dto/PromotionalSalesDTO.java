package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.List;


public class PromotionalSalesDTO {

	private int promotionalSalesId;
	private String promotionalTitle;
	private String promotionalTag;
	private int promotionalProducts[];
	private List<ProductDTO> promotionalProductDtos;
	private Boolean active;
	
	
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public int getPromotionalSalesId() {
		return promotionalSalesId;
	}
	public void setPromotionalSalesId(int promotionalSalesId) {
		this.promotionalSalesId = promotionalSalesId;
	}
	public String getPromotionalTitle() {
		return promotionalTitle;
	}
	public void setPromotionalTitle(String promotionalTitle) {
		this.promotionalTitle = promotionalTitle;
	}
	public int[] getPromotionalProducts() {
		return promotionalProducts;
	}
	public void setPromotionalProducts(int[] promotionalProducts) {
		this.promotionalProducts = promotionalProducts;
	}
	public String getPromotionalTag() {
		return promotionalTag;
	}
	public void setPromotionalTag(String promotionalTag) {
		this.promotionalTag = promotionalTag;
	}
	public List<ProductDTO> getPromotionalProductDtos() {
		return promotionalProductDtos;
	}
	public void setPromotionalProductDtos(List<ProductDTO> promotionalProductDtos) {
		this.promotionalProductDtos = promotionalProductDtos;
	}
	
}
