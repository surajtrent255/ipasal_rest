package com.ishanitech.ipasal.ipasalwebservice.dto;
/**
 * 
 * @author Tanchhowpa
 *
 * May 13, 2019 11:36:58 AM
 */
public class ReviewDetailsDTO {

	private int reviewDetailsId;
	private int reviewId;
	private int productId;
	private int userId;
	private int reviewStatus;
	private String reviewDate;
	

	
	public int getReviewDetailsId() {
		return reviewDetailsId;
	}
	public void setReviewDetailsId(int reviewDetailsId) {
		this.reviewDetailsId = reviewDetailsId;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	
}
