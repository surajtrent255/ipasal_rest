package com.ishanitech.ipasal.ipasalwebservice.Services;

import java.util.List;

import com.ishanitech.ipasal.ipasalwebservice.dto.RelatedProductDTO;
/**
 * 
 * @author Tanchhowpa
 *
 * Created on: Feb 1, 2019 5:37:06 PM
 */
public interface RelatedProductService {

	void addRelatedProducts(List<RelatedProductDTO> relatedProducts);
	
	void removeRelatedProduct(Integer mainProductId, Integer relatedProductId);
}
