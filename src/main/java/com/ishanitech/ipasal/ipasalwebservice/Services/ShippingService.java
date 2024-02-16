package com.ishanitech.ipasal.ipasalwebservice.Services;

import java.util.List;

import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingDTO;

public interface ShippingService {
    Integer addShippingDetails(ShippingDTO shippingDTO);
    ShippingDTO getShippingDetailsById(Integer customerId);
    Integer updateShippingDetails(ShippingDTO shippingDTO);
    List<ShippingDTO> getShippingDetailsByOrderId(Integer orderId);
}
