package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.Date;

public class OrderDetailsDTO {
    private int orderDetailsId;
    private int orderId;
    private int productid;
    private Date orderDate;
    private int shippingDetailsId;
    private int payment_details_id;
    private int orderStatus;
    private int orderBy;
    private int quantity;
    private String productName;
    private String unit;
    private float rate;


    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
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

    public int getPayment_details_id() {
        return payment_details_id;
    }

    public void setPayment_details_id(int payment_details_id) {
        this.payment_details_id = payment_details_id;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getShippingDetailsId() {
        return shippingDetailsId;
    }

    public void setShippingDetailsId(int shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }

}
