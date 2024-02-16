package com.ishanitech.ipasal.ipasalwebservice.utilities;

import com.ishanitech.ipasal.ipasalwebservice.dao.CategoryDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.OfferProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryImageDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.OfferProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.OfferProductImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OfferImageResourceUrlCreatorUtil {
	private final String PICTURE_DIR = "Pictures/";
	
	@Autowired
	private HostDetailsUtil resolveHostAddress;
	
//	public List<OfferProductDTO> createOfferProductWithImages(List<OfferProductDTO> offerProductWithoutImages, OfferProductDAO offerProductDAO) {
//        List<OfferProductDTO> offerProductsWithImages = new ArrayList<>();
//        offerProductWithoutImages.forEach(offerProduct -> {
//            OfferProductImageDTO image = offerProductDAO.getOfferProductImage(offerProduct.getOfferId());
//            offerProduct.setOfferProductImageDTO(createOfferImageWithFullUrl(image));
//            offerProductsWithImages.add(offerProduct);
//        });
//        return offerProductsWithImages;
//
//    }
//
//    private OfferProductImageDTO createOfferImageWithFullUrl(OfferProductImageDTO images) {
//	    OfferProductImageDTO offerProductImageDTO = new OfferProductImageDTO();
//	    offerProductImageDTO.setId(images.getId());
//        offerProductImageDTO.setImageName(resolveHostAddress.getHostUrl() + PICTURE_DIR + "thumb/" + images.getImageName());
//
//        return offerProductImageDTO;
//    }
    
	public List<CategoryDTO> createOfferWithImages(List<CategoryDTO> categoryWithoutImages, CategoryDAO categoryDAO) {
        List<CategoryDTO> categoryListWithImages = new ArrayList<>();
        categoryWithoutImages.forEach(category -> {
            CategoryImageDTO image = categoryDAO.getCategoryImage(category.getCategoryId());
            category.setImage(createOfferImageWithFullUrl(image));
            categoryListWithImages.add(category);
        });
        return categoryListWithImages;

    }

    public CategoryImageDTO createOfferImageWithFullUrl(CategoryImageDTO images) {
	    CategoryImageDTO categoryImageDTO = new CategoryImageDTO();
	    categoryImageDTO.setCategoryId(images.getCategoryId());
        categoryImageDTO.setImageName(resolveHostAddress.getHostUrl() + PICTURE_DIR + "thumb/" + images.getImageName());
        categoryImageDTO.setOddImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + "thumb/" + images.getOddImage());
        categoryImageDTO.setEvenImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + "thumb/" + images.getEvenImage());
        categoryImageDTO.setBannerImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + "thumb/" + images.getBannerImage());

        return categoryImageDTO;
    }
}
