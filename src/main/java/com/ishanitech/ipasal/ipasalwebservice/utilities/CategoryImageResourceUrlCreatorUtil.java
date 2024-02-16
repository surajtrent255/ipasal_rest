package com.ishanitech.ipasal.ipasalwebservice.utilities;

import com.ishanitech.ipasal.ipasalwebservice.dao.CategoryDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryImageResourceUrlCreatorUtil {
	private final String PICTURE_DIR = "Pictures/";
	Logger logger = LoggerFactory.getLogger(CategoryImageResourceUrlCreatorUtil.class);
	
	@Autowired
	private HostDetailsUtil resolveHostAddress;
	
	public List<CategoryDTO> createCategoryWithImages(List<CategoryDTO> categoryWithoutImages, CategoryDAO categoryDAO) {
        List<CategoryDTO> categoryListWithImages = new ArrayList<>();
        categoryWithoutImages.forEach(category -> {
            CategoryImageDTO image = categoryDAO.getCategoryImage(category.getCategoryId());
            category.setImage(createCategoryImageWithFullUrl(image));
            categoryListWithImages.add(category);
        });
        return categoryListWithImages;

    }

    public CategoryImageDTO createCategoryImageWithFullUrl(CategoryImageDTO images) {
	    CategoryImageDTO categoryImageDTO = new CategoryImageDTO();
	    categoryImageDTO.setCategoryId(images.getCategoryId());
        categoryImageDTO.setImageName(resolveHostAddress.getHostUrl() + PICTURE_DIR + images.getImageName());
        categoryImageDTO.setOddImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + images.getOddImage());
        categoryImageDTO.setEvenImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + images.getEvenImage());
        categoryImageDTO.setBannerImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + images.getBannerImage());

        return categoryImageDTO;
    }
}
