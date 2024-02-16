package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.ProductCategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.SearchResult;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {

    Integer addProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Integer productId);
    Integer updateProduct(Integer productId, ProductDTO productDTO);
    void removeProduct(Integer productId);
    void addProductCategory(ProductCategoryDTO productCategoryDTO);
    void addProductImage(Integer productId, String imageName);
    List<ProductDTO> getFeaturedProducts();
    void featureProduct(Integer productId);
    
    //List<ProductDTO> getProductsByCategory(Integer categoryId, Integer startingIndex, Integer limitSize, String caseQuantity);
    List<ProductDTO> getProductsByCategory(Integer categoryId, HttpServletRequest request);
    
    
    //old style
    //List<ProductDTO> getProductsByParentCategoryId(Integer parentId, Integer startingIndex, Integer maxItemPerPage, String caseQuantity);
	//new way 
    List<ProductDTO> getProductsByParentCategoryId(Integer parentCategoryId, HttpServletRequest request);
    
    //returns the total number of products in table using parentCategoryId
    Integer totalProductsInCategory(Integer categoryId);
    
    //returns the total number of products in table using subCategoryId
    Integer totalProductsInSubCategory(Integer categoryId);
    
    List<ProductDTO> searchProduct(HttpServletRequest request);

    SearchResult getWholeSearchProducts(HttpServletRequest request);
    
	List<ProductDTO> getSaleProductsforIndex();
	
	List<ProductDTO> getAllSaleProducts(HttpServletRequest request);
   
}
