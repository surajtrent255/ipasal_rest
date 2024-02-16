package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author yoomes
 *
 */
public class PaymentDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int paymentId;
	private int paymentMethodId;
	private Double amount;
	private Date paymentDate;
	private boolean status;
	private PaymentMethodDTO paymentMethod;
	private String uniqueOrderIdentifier;
	
	public PaymentDTO() {
		
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public int getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public PaymentMethodDTO getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getUniqueOrderIdentifier() {
		return uniqueOrderIdentifier;
	}
	public void setUniqueOrderIdentifier(String uniqueOrderIdentifier) {
		this.uniqueOrderIdentifier = uniqueOrderIdentifier;
	}
	
}
