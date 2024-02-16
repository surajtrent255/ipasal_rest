package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.io.Serializable;

/**
 * @author Azens Eklak
 * Created On 2019-03-22
 */

public class PaymentMethodDTO implements Serializable{
	private static final long serialVersionUID = -599905439967762567L;
	private int id;
    private String paymentName;
    private String paymentStatus;
    
    public PaymentMethodDTO() {
    	
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}