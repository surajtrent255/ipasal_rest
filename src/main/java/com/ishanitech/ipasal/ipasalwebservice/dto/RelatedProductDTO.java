package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * 
 * @author Tanchhowpa
 *
 *         Created on: Feb 1, 2019 4:56:01 PM
 */

public class RelatedProductDTO {

	private int id;
	private int mainProductId;
	private int relatedProductId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMainProductId() {
		return mainProductId;
	}

	public void setMainProductId(int mainProductId) {
		this.mainProductId = mainProductId;
	}

	public int getRelatedProductId() {
		return relatedProductId;
	}

	public void setRelatedProductId(int relatedProductId) {
		this.relatedProductId = relatedProductId;
	}

	public RelatedProductDTO() {
		super();
	}

	public RelatedProductDTO(int id, int mainProductId, int relatedProductId) {
		super();
		this.id = id;
		this.mainProductId = mainProductId;
		this.relatedProductId = relatedProductId;
	}
}
