package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.PromotionalSalesService;
import com.ishanitech.ipasal.ipasalwebservice.dao.ProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.ProductTagsDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.PromotionalSalesDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ImageDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ImageType;
import com.ishanitech.ipasal.ipasalwebservice.dto.PaginationTypeClass;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductTagsDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.PromotionalSalesDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.CustomQueryCreator;
import com.ishanitech.ipasal.ipasalwebservice.utilities.ImageResourceUrlCreatorUtil;
import com.ishanitech.ipasal.ipasalwebservice.utilities.NewProductCheckUtil;
/**
 * 
 * @author Tanchhowpa
 * Sep 19, 2019, 9:38:57 AM
 *
 */

@Service
public class PromotionalSalesServiceImpl implements PromotionalSalesService {

	private final DbService dbService;
	  private static final Logger LOGGER = LoggerFactory.getLogger(PromotionalSalesServiceImpl.class);
	
	@Autowired
	ImageResourceUrlCreatorUtil imageResourceUrlCreatorUtil;

	@Autowired
	NewProductCheckUtil newProductCheckUtil;
	
	
	public PromotionalSalesServiceImpl(DbService dbService) {
		super();
		this.dbService = dbService;
	}


	@Override
	public List<ProductDTO> getProductsByPromotionTag(HttpServletRequest request, String promotionTag) {
		ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.PRODUCT);
        List<ProductDTO> products = productTagsDAO.getAllProductsWithGivenTag(caseQuery, "'" + promotionTag + "'");
        return imageResourceUrlCreatorUtil.createProductWithImages(products, productDAO, ImageType.THUMBNAIL);
	}


	@Override
	public List<PromotionalSalesDTO> getAllPromotions() {
		PromotionalSalesDAO promotionalSalesDAO = dbService.getDao(PromotionalSalesDAO.class);
		return promotionalSalesDAO.getAllPromotions();
	}


	@Override
	public PromotionalSalesDTO getPromotionById(Integer promotionalSalesId) {
		PromotionalSalesDAO promotionalSalesDAO = dbService.getDao(PromotionalSalesDAO.class);
		ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
		
		PromotionalSalesDTO promotion = promotionalSalesDAO.getPromotionById(promotionalSalesId);
		
		
		String promoTag = promotion.getPromotionalTag();
		
		List<ProductDTO> promotionalProductsList = productTagsDAO.getAllProductsWithGivenTag(promoTag);
		for(ProductDTO promoProduct: promotionalProductsList) {
			if(promoProduct != null) {
				List<ImageDTO> promoProductImages = productDAO.getProductImages(promoProduct.getProductId());
				promoProduct.setImages(imageResourceUrlCreatorUtil.createImgWithFullUrl(promoProductImages, ImageType.ORIGINAL));
			}
		}
		
		List<ProductTagsDTO> taggedProducts = productTagsDAO.getAllProductIdsWithGivenTag(promoTag);
		int[] arrayTagged = new int[taggedProducts.size()];
		for (int i = 0; i < taggedProducts.size(); i++) {
			arrayTagged[i] = taggedProducts.get(i).getProductId();
		}
		
		promotion.setPromotionalProducts(arrayTagged);
		promotion.setPromotionalProductDtos(promotionalProductsList);
		
		
		return promotion;
	}


	@Override
	public Integer updatePromotionDetails(Integer promotionalSalesId, PromotionalSalesDTO promotionalSalesDTO) {
		PromotionalSalesDAO promotionalSalesDAO = dbService.getDao(PromotionalSalesDAO.class);
//		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
//		ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
//		
//		String promo = promotionalSalesDTO.getPromotionalTag();
//		
//		List<ProductTagsDTO> taggedProductsList = new ArrayList<>();
//		for (int productId: promotionalSalesDTO.getPromotionalProducts()) {
//			ProductTagsDTO taggedProduct = new ProductTagsDTO();
//			taggedProduct.setProductId(productId);
//			taggedProduct.setProductTag(promo);
//			taggedProductsList.add(taggedProduct);
//		}
//		
//		
//		productTagsDAO.removeProductsWithGivenTag(promo);
//		
//		productTagsDAO.addProductTagsToProduct(taggedProductsList);
		
		return promotionalSalesDAO.updatePromotionDetails(promotionalSalesId, promotionalSalesDTO);
	}


	@Override
	public List<ProductDTO> getPromotionalSaleProductsById(HttpServletRequest request, Integer promotionalSalesId) {
		ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.PRODUCT);
        List<ProductDTO> productsList = productTagsDAO.getAllProductsWithGivenPromoId(caseQuery, promotionalSalesId);
        List<ProductDTO> products = new ArrayList<>();
        
        try {
			products = newProductCheckUtil.checkListofProducts(productsList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return imageResourceUrlCreatorUtil.createProductWithImages(products, productDAO, ImageType.THUMBNAIL);
	}


	@Override
	public List<PromotionalSalesDTO> getAllActivePromotions() {
		PromotionalSalesDAO promotionalSalesDAO = dbService.getDao(PromotionalSalesDAO.class);
		return promotionalSalesDAO.getAllActivePromotions();
	}


	@Override
	public int setPromotionActive(Integer promotionalSalesId) {
		PromotionalSalesDAO promotionalSalesDAO = dbService.getDao(PromotionalSalesDAO.class);
		return promotionalSalesDAO.setPromotionActive(promotionalSalesId);
	}


	@Override
	public int setPromotionInactive(Integer promotionalSalesId) {
		PromotionalSalesDAO promotionalSalesDAO = dbService.getDao(PromotionalSalesDAO.class);
		return promotionalSalesDAO.setPromotionInactive(promotionalSalesId);
	}


	@Override
	public void updatePromotionProducts(Integer promotionalSalesId, PromotionalSalesDTO promotionalSalesDTO) {
//		PromotionalSalesDAO promotionalSalesDAO = dbService.getDao(PromotionalSalesDAO.class);
//		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
		ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
		
		String promo = promotionalSalesDTO.getPromotionalTag();
		LOGGER.info(promo);
		List<ProductTagsDTO> taggedProductsList = new ArrayList<>();
		for (int productId: promotionalSalesDTO.getPromotionalProducts()) {
			ProductTagsDTO taggedProduct = new ProductTagsDTO();
			taggedProduct.setProductId(productId);
			taggedProduct.setProductTag(promo);
			taggedProductsList.add(taggedProduct);
		}
		
		productTagsDAO.removeProductsWithGivenTag(promo);
		
		productTagsDAO.addProductTagsToProduct(taggedProductsList);
	}

}
