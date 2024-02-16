package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.OfferProductDTO;

import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
public interface OfferProductService {
    Integer addOfferProduct(OfferProductDTO offerProductDTO);
    List<OfferProductDTO> getOfferProducts();
    void addOfferImage(Integer offerId, String imageName);
}
