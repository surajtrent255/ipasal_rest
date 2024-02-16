package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.Date;

public class OrderDTO {
    private int orderId;
    private int productId;
    private Date orderDate;
    private int orderStatus;
    private int shippingDetailsId;
    private int paymentDetailsId;
    private int orderedBy;
    private int quantity;




    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public int getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(int orderedBy) {
        this.orderedBy = orderedBy;
    }

    public int getPaymentDetailsId() {
        return paymentDetailsId;
    }

    public void setPaymentDetailsId(int paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }


    public int getShippingDetailsId() {
        return shippingDetailsId;
    }

    public void setShippingDetailsId(int shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }


    @Override
    public String toString(){
        return super.toString();
    }
}
