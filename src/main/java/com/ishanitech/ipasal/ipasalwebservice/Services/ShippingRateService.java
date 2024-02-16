package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingRateDTO;

import java.util.List;

/**
 * @author 'Azens Eklak'
 * email: azens1995@gmail.com
 * created on Mar 01, 2019
 * since 2017
 **/
public interface ShippingRateService {

    Integer addShippingInfo(ShippingRateDTO shippingRateDTO);
    ShippingRateDTO getShippingInfo(String location);
    List<ShippingRateDTO> getAllShippingInfo();
    void updateShippingInfo(Integer shippingId, Integer amount);
}
