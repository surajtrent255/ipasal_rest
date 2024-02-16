package com.ishanitech.ipasal.ipasalwebservice.Services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PromotionalSalesDTO;

/**
 * 
 * @author Tanchhowpa
 * Sep 19, 2019, 9:38:30 AM
 *
 */
public interface PromotionalSalesService {

	List<ProductDTO> getProductsByPromotionTag(HttpServletRequest request, String promotionTag);

	List<PromotionalSalesDTO> getAllPromotions();

	PromotionalSalesDTO getPromotionById(Integer promotionalSalesId);

	Integer updatePromotionDetails(Integer promotionalSalesId, PromotionalSalesDTO promotionalSalesDTO);

	List<ProductDTO> getPromotionalSaleProductsById(HttpServletRequest request, Integer promotionalSalesId);

	List<PromotionalSalesDTO> getAllActivePromotions();

	int setPromotionActive(Integer promotionalSalesId);

	int setPromotionInactive(Integer promotionalSalesId);

	void updatePromotionProducts(Integer promotionalSalesId, PromotionalSalesDTO promotionalSalesDTO);
}
