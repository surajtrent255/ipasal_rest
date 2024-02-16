package com.ishanitech.ipasal.ipasalwebservice.dto;

public class OrderUpdateDTO {
	private int orderUpdateId;
	private int orderId;
	private String commentMsg;
	private String commentDate;
	private int commentedBy;
	private UserDTO user;
	
	
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public int getOrderUpdateId() {
		return orderUpdateId;
	}
	public void setOrderUpdateId(int orderUpdateId) {
		this.orderUpdateId = orderUpdateId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getCommentMsg() {
		return commentMsg;
	}
	public void setCommentMsg(String commentMsg) {
		this.commentMsg = commentMsg;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public int getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(int commentedBy) {
		this.commentedBy = commentedBy;
	}

}
