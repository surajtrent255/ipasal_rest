package com.ishanitech.ipasal.ipasalwebservice.dto;

public class NewOrderDetailsDto {
    private int orderDetailsId;
    private int orderId;
    private int productId;
    private int quantity;

    @Override
    public String toString() {
        return "{" +
            " orderDetailsId='" + getOrderDetailsId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", productId='" + getProductId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }

    public int getOrderDetailsId() {
        return this.orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}