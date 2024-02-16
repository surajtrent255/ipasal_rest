package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;

import java.util.List;

public interface CategoryService {
    Integer addCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getCategoryByParentId(Integer parentId);
    void deleteCategory(Integer categoryId);
    List<CategoryDTO> getFeaturedCategories();
    List<CategoryDTO> getOfferedCategories();
    List<CategoryDTO> getCategoryImages(Integer categoryId);
    void featureCategory(Integer categoryId);
    List<CategoryDTO> getAllCategories();
    List<ProductDTO> searchProductInCategory(Integer categoryId, String searchKey);
    List<ProductDTO> getProductsBetweenRange(Integer categoryId, Float minRate, Float maxRate);
    void uploadCategoryImage(Integer categoryId, String imageName, String oddImage, String evenImage, String bannerImage);
    List<CategoryDTO> getAllParentCategory();
    CategoryDTO getCategoryByCategoryId(Integer categoryId);
	void deleteParentandChildCategories(List<Integer> categoryIds);
    
}
