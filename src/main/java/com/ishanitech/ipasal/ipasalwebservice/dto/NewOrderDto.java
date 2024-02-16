package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class NewOrderDto extends ResourceSupport{
    private int orderId;
    private Date orderDate;
    private int orderStatus;
    private Integer orderedBy;
    private ShippingDTO shippingAddress;
    private PaymentDTO paymentDetail;
    private int shippingDetailsId;
    private int paymentDetailsId;
    private List<Integer> productIds = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private List<NewOrderDetailsDto> orderDetailDtos = new ArrayList<>();
    private String status;
    private UserDTO user;
    private List<ProductDTO> products = new ArrayList<>();
    private String uniqueOrderIdentifier;

    public int getOrderId() {
        return this.orderId;
    }

    public List<NewOrderDetailsDto> getOrderDetailDtos() {
        return orderDetailDtos;
    }

    public void setOrderDetailDtos(List<NewOrderDetailsDto> orderDetailDtos) {
        this.orderDetailDtos = orderDetailDtos;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderedBy() {
        return this.orderedBy;
    }

    public void setOrderedBy(Integer orderedBy) {
        this.orderedBy = orderedBy;
    }

    public ShippingDTO getShippingAddress() {
        return this.shippingAddress;
    }

    public void setShippingAddress(ShippingDTO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public PaymentDTO getPaymentDetail() {
        return this.paymentDetail;
    }

    public void setPaymentDetail(PaymentDTO paymentDetail) {
        this.paymentDetail = paymentDetail;
    }
    
    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPaymentDetailsId(int paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }

    public int getPaymentDetailsId() {
        return paymentDetailsId;
    }

    public void setShippingDetailsId(int shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }
    
    public int getShippingDetailsId() {
        return shippingDetailsId;
    }

    public UserDTO getUser() {
        return this.user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

	public String getUniqueOrderIdentifier() {
		return uniqueOrderIdentifier;
	}

	public void setUniqueOrderIdentifier(String uniqueOrderIdentifier) {
		this.uniqueOrderIdentifier = uniqueOrderIdentifier;
	}

}