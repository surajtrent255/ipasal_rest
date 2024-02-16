package com.ishanitech.ipasal.ipasalwebservice.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipasal.ipasalwebservice.Services.PromotionalSalesService;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PromotionalSalesDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

/**
 * 
 * @author Tanchhowpa
 * Sep 19, 2019, 9:30:59 AM
 *
 */

@RestController
@RequestMapping("api/v1/promotionalSales")
public class PromotionalSalesResource {
	Logger logger = LoggerFactory.getLogger(PromotionalSalesResource.class);
	 
	private PromotionalSalesService promotionalSalesService;

	public PromotionalSalesResource(PromotionalSalesService promotionalSalesService) {
		this.promotionalSalesService = promotionalSalesService;
	}
	 
	@RequestMapping(method = RequestMethod.GET, value = "/tag/{promotionTag}")
	public Response<?> getProductsByPromotionTag(HttpServletRequest request, @PathVariable("promotionTag") String promotionTag) {
		List<ProductDTO> saleProducts;
		try {
			saleProducts = promotionalSalesService.getProductsByPromotionTag(request, promotionTag);
		} catch (Exception ex) {
			throw new CustomSqlException("Something went wrong while getting products from database!" +ex.getMessage());
		}
		if (saleProducts != null && saleProducts.size() > 0) {
			return Response.ok(saleProducts, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no sale products in database!");
		}
			throw new ResourceNotFoundException("No more data found! :(");
	    }
	 
	@RequestMapping(method = RequestMethod.GET)
	public Response<?> getAllPromotions() {
		List<PromotionalSalesDTO> promotionList;
		try {
			promotionList = promotionalSalesService.getAllPromotions();
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database");
		}
		if(promotionList != null && promotionList.size() > 0) {
			return Response.ok(promotionList, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("No more data found");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/active")
	public Response<?> getAllActivePromotions() {
		List<PromotionalSalesDTO> promotionList;
		try {
			promotionList = promotionalSalesService.getAllActivePromotions();
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while getting data from database");
		}
		if(promotionList != null && promotionList.size() > 0) {
			return Response.ok(promotionList, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		throw new ResourceNotFoundException("No active promotions found");
	}
	
	
    @RequestMapping(method = RequestMethod.GET, value = "/pr/{promotionalSalesId}")
    public Response<?> getPromotionById(@PathVariable("promotionalSalesId") Integer promotionalSalesId) {
        PromotionalSalesDTO promo = null;
        try {
            promo = promotionalSalesService.getPromotionById(promotionalSalesId);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from database" + ex.getMessage());
        }

        if (promo != null) {
            return Response.ok(Arrays.asList(promo), HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No more found.");
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{promoId}")
    public Response<?> updatePromotion(@PathVariable("promoId") Integer promotionalSalesId, @RequestBody PromotionalSalesDTO promotionalSalesDTO) {
        try {
            promotionalSalesService.updatePromotionDetails(promotionalSalesId, promotionalSalesDTO);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Promotion info update successfully!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong updating promotion info.");
        }
    }
    
	@RequestMapping(method = RequestMethod.GET, value = "/prListing/{promotionalSalesId}")
	public Response<?> getPromotionalSaleProductsById(HttpServletRequest request, @PathVariable("promotionalSalesId") Integer promotionalSalesId) {
		List<ProductDTO> saleProducts;
		try {
			saleProducts = promotionalSalesService.getPromotionalSaleProductsById(request, promotionalSalesId);
		} catch (Exception ex) {
			throw new CustomSqlException("Something went wrong while getting products from database!" +ex.getMessage());
		}
		if (saleProducts != null && saleProducts.size() > 0) {
			return Response.ok(saleProducts, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no sale products in database!");
		}
			throw new ResourceNotFoundException("No more data found! :(");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/setActive/{promotionalSalesId}")
	public Response<?> setPromotionActive(@PathVariable("promotionalSalesId") Integer promotionalSalesId) {
		try {
			promotionalSalesService.setPromotionActive(promotionalSalesId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "The promotion is set as active");
		} catch (Exception e) {
			throw new CustomSqlException("The set active operation failed");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/setInactive/{promotionalSalesId}")
	public Response<?> setPromotionInactive(@PathVariable("promotionalSalesId") Integer promotionalSalesId) {
		try {
			promotionalSalesService.setPromotionInactive(promotionalSalesId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "The promotion is set as Inactive");
		} catch (Exception e) {
			throw new CustomSqlException("The set Inactive operation failed");
		}
	}
	
	
    @RequestMapping(method = RequestMethod.PUT, value = "/list/{promoId}")
    public Response<?> updatePromotionProducts(@PathVariable("promoId") Integer promotionalSalesId, @RequestBody PromotionalSalesDTO promotionalSalesDTO) {
        try {
            promotionalSalesService.updatePromotionProducts(promotionalSalesId, promotionalSalesDTO);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Promotion info update successfully!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong updating promotion info.");
        }
    }
    
}
