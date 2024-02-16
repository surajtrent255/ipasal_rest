package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.Date;

public class ReportDTO {


    private int productId;
    private String productName;
    private int categoryId;
    private String category;
    private String unit;
    private float rate;
    private int availableItems;
    private int orderId;
    private int orderDetailsId;

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    private int totalQuantity;
    private int shippingDetailsId;
    private int paymentDetailsId;
    private Date orderDate;


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }



    public int getShippingDetailsId() {
        return shippingDetailsId;
    }

    public void setShippingDetailsId(int shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }

    public int getPaymentDetailsId() {
        return paymentDetailsId;
    }

    public void setPaymentDetailsId(int paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }




    @Override
    public String toString(){
      return  super.toString();
    }

}
