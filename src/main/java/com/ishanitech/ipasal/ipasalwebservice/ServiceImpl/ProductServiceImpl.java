package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.ProductService;
import com.ishanitech.ipasal.ipasalwebservice.dao.*;
import com.ishanitech.ipasal.ipasalwebservice.dto.*;
import com.ishanitech.ipasal.ipasalwebservice.utilities.CustomQueryCreator;
import com.ishanitech.ipasal.ipasalwebservice.utilities.ImageResourceUrlCreatorUtil;
import com.ishanitech.ipasal.ipasalwebservice.utilities.NewProductCheckUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProductServiceImpl implements ProductService {

    private final DbService dbService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    ImageResourceUrlCreatorUtil imageResourceUrlCreatorUtil;
    
    @Autowired
    NewProductCheckUtil newProductCheckUtil;
    
    @Autowired
    public ProductServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    
    @Override
    @Transactional
    public Integer addProduct(ProductDTO productDTO) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        int insertedProductId = productDAO.addProducts(productDTO);

        List<RelatedProductDTO> relatedProducts = new ArrayList<>();
        for (int productId : productDTO.getRelatedProducts()) {
            RelatedProductDTO product = new RelatedProductDTO();
            product.setMainProductId(insertedProductId);
            product.setRelatedProductId(productId);
            relatedProducts.add(product);
        }

        RelatedProductDAO relatedProductDAO = dbService.getDao(RelatedProductDAO.class);
        relatedProductDAO.addRelatedProducts(relatedProducts);

        List<ProductTagsDTO> productTagsList = new ArrayList<>();
        for(String tag : productDTO.getProductTags()) {
        	ProductTagsDTO productTag = new ProductTagsDTO();
        	productTag.setProductId(insertedProductId);
        	productTag.setProductTag(tag);
        	productTagsList.add(productTag);
        }
        
        ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
        productTagsDAO.addProductTagsToProduct(productTagsList);
        
        ProductCategoryDTO productCategory = new ProductCategoryDTO();
        productCategory.setProductId(insertedProductId);
        productCategory.setCategoryId(productDTO.getCategoryId());

        ProductMerchantDTO productMerchant = new ProductMerchantDTO();
        productMerchant.setProductId(insertedProductId);
        productMerchant.setMerchantId(productDTO.getMerchantId()[0]);


        productDAO.addProductCategory(productCategory);
        productDAO.addProductMerchant(productMerchant);
        
        return insertedProductId;

    }

    @Override
    public List<ProductDTO> getAllProducts() {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        List<ProductDTO> returnedProductList = productDAO.getAllProducts();
        List<ProductDTO> returnedList = new ArrayList<ProductDTO>();
        	try {
        		returnedList = newProductCheckUtil.checkListofProducts(returnedProductList);
        	} catch (ParseException e) {
        		e.printStackTrace();
        	}
        if (returnedList != null && returnedList.size() > 0) {
            return imageResourceUrlCreatorUtil.createProductWithImages(returnedList, productDAO, ImageType.THUMBNAIL);
        }

        return returnedList;
    }

    @Override
    public ProductDTO getProductById(Integer productId) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        MerchantDAO merchantDAO = dbService.getDao(MerchantDAO.class);
        ReviewDAO reviewDAO = dbService.getDao(ReviewDAO.class);
        UserDAO userDAO = dbService.getDao(UserDAO.class);
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
        ProductDTO product = productDAO.getProductById(productId);
        
        
        if (product != null) {
            List<ImageDTO> images = productDAO.getProductImages(product.getProductId());
            product.setImages(imageResourceUrlCreatorUtil.createImgWithFullUrl(images, ImageType.ORIGINAL));
        	try {
				newProductCheckUtil.checkListSingleProduct(product);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }

        List<RelatedProductDTO> relatedProds = productDAO.getRelatedProducts(productId);

        int[] arrayRelated = new int[relatedProds.size()];
        for (int i = 0; i < relatedProds.size(); i++) {
            arrayRelated[i] = relatedProds.get(i).getRelatedProductId();
        }
        product.setRelatedProducts(arrayRelated);

        List<ProductDTO> relatedProducts = new ArrayList<ProductDTO>();

        for (int i = 0; i < arrayRelated.length; i++) {

            relatedProducts.add(i, productDAO.getProductById(arrayRelated[i]));

        }
        product.setRelatedProductDtos(relatedProducts);

        List<ProductDTO> relatedProductsList = product.getRelatedProductDtos();
        for (ProductDTO relatedProdImage : relatedProductsList) {
            if (relatedProdImage != null) {
                List<ImageDTO> relatedProductImages = productDAO.getProductImages(relatedProdImage.getProductId());
                relatedProdImage.setImages(imageResourceUrlCreatorUtil.createImgWithFullUrl(relatedProductImages, ImageType.THUMBNAIL));
            }
        }
        product.setRelatedProductDtos(relatedProductsList);


        //Adding the list merchant DTO
        List<ProductMerchantDTO> prodMerchant = productDAO.getMerchant(productId);

        int[] arrayMerchant = new int[prodMerchant.size()];
        for (int i = 0; i < prodMerchant.size(); i++) {
            arrayMerchant[i] = prodMerchant.get(i).getMerchantId();
        }
        product.setMerchantId(arrayMerchant);
        List<MerchantDTO> merchant = new ArrayList<MerchantDTO>();
        for (int i = 0; i < arrayMerchant.length; i++) {
            merchant.add(i, merchantDAO.getMerchantById(arrayMerchant[i]));
        }
        product.setMerchant(merchant);


        //Adding the review DTOs to the product

        List<ReviewDTO> reviews = reviewDAO.getAllReviewsByProductId(productId);
        for(ReviewDTO review: reviews) {
        	List<Integer> verified = reviewDAO.checkVerifiedPurchase(productId, review.getUserId());
        	if(verified.size() != 0 || verified.size() > 0) {
        		review.setVerified(true);
        	}
        }
        
        product.setReviewDtos(reviews);


        float totalRating = 0;
        //Adding the UserDTO to each respective ReviewDTO
        List<ReviewDTO> reviewsList = product.getReviewDtos();
        for (ReviewDTO reviewUserDto : reviewsList) {
            totalRating += reviewUserDto.getRating();
            reviewUserDto.setUserDto(userDAO.getUserByUserId(reviewUserDto.getUserId()));
        }

        float avgRating = (totalRating) / (reviewsList.size());
        //Adding the number of approved reviews present for a given product
        product.setNosReview(reviewDAO.getTotalNumberofReviewsForAProduct(productId));
        product.setAvgRating(avgRating);

        
        //Adding the ancestor categories to the product for bread crumbs
        product.setAncestorCategories(categoryDAO.getAncestorCategories(product.getCategoryId()));
        
        
        List<ProductTagsDTO> productTags = productTagsDAO.getProductTagsByProductId(productId);

        String[] arrayProductTags = new String[productTags.size()];
        for (int i = 0; i < productTags.size(); i++) {
            arrayProductTags[i] = productTags.get(i).getProductTag();
        }
        product.setProductTags(arrayProductTags);
        
        return product;
    }

    @Override
    public Integer updateProduct(Integer productId, ProductDTO productDTO) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        RelatedProductDAO relatedProductDAO = dbService.getDao(RelatedProductDAO.class);
        ProductTagsDAO productTagsDAO = dbService.getDao(ProductTagsDAO.class);
        
        List<RelatedProductDTO> relatedProducts = new ArrayList<>();
        for (int relatedProductId : productDTO.getRelatedProducts()) {
            RelatedProductDTO product = new RelatedProductDTO();
            product.setMainProductId(productId);
            product.setRelatedProductId(relatedProductId);
            relatedProducts.add(product);
        }
        
        relatedProductDAO.removeRelatedProducts(productId);
        
        relatedProductDAO.addRelatedProducts(relatedProducts);

        
        List<ProductTagsDTO> productTagsList = new ArrayList<>();
        for(String tag : productDTO.getProductTags()) {
        	ProductTagsDTO productTag = new ProductTagsDTO();
        	productTag.setProductId(productId);
        	productTag.setProductTag(tag);
        	productTagsList.add(productTag);
        }
        
        productTagsDAO.removeProductTagsByProductId(productId);

        productTagsDAO.addProductTagsToProduct(productTagsList);
        
        return productDAO.updateProductDetails(productId, productDTO);
    }

    @Override
    public void removeProduct(Integer productId) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        RelatedProductDAO relatedProductDAO = dbService.getDao(RelatedProductDAO.class);
        relatedProductDAO.removeRelatedProductEntry(productId);
        relatedProductDAO.removeRelatedProducts(productId);
        productDAO.removeProduct(productId);
    }

    @Override
    public void addProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        productDAO.addProductCategory(productCategoryDTO);
    }


//    @Override
//    public List<ProductDTO> getProductsByCategory(Integer categoryId, Integer startingIndex, Integer maxLimit, String caseQuantity) {
//        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
//        List<ProductDTO> returnedList = productDAO.getProductsByCategoryId(categoryId, startingIndex, maxLimit, caseQuantity);
//        return imageResourceUrlCreatorUtil.createProductWithImages(returnedList, productDAO);
//    }
    // new way of getting results with pagination support

    @Override
    public List<ProductDTO> getProductsByCategory(Integer categoryId, HttpServletRequest request) {
        String caseQuantity = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.PRODUCT);//generateQueryWithCase(request);
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        List<ProductDTO> returnedProductList = productDAO.getProductsByCategoryId(categoryId, caseQuantity);
        List<ProductDTO> returnedList = new ArrayList<>();
		try {
			returnedList = newProductCheckUtil.checkListofProducts(returnedProductList);
		} catch (ParseException e) {
			e.printStackTrace();
		}

        return imageResourceUrlCreatorUtil.createProductWithImages(returnedList, productDAO, ImageType.THUMBNAIL);
    }

    @Override
    public void addProductImage(Integer productId, String imageName) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        productDAO.addProductImage(productId, imageName);
    }

    @Override
    public List<ProductDTO> getFeaturedProducts() {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        List<ProductDTO> productListWithoutImg = productDAO.getFeaturedProducts();
        List<ProductDTO> productWithoutImg = new ArrayList<>();
		try {
			productWithoutImg = newProductCheckUtil.checkListofProducts(productListWithoutImg);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return imageResourceUrlCreatorUtil.createProductWithImages(productWithoutImg, productDAO, ImageType.THUMBNAIL);
    }

    @Override
    public void featureProduct(Integer productId) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        productDAO.featureProduct(productId);
    }

//Old Style Of getting results with pagination.
//    @Override
//    public List<ProductDTO> getProductsByParentCategoryId(Integer parentId, Integer startingPageIndex, Integer maxItemPerPage, String caseQuantity) {
//        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
//        List<ProductDTO> products =  productDAO.getProductsByParentCategoryId(caseQuantity, parentId, startingPageIndex, maxItemPerPage);    
//        
//        imageResourceUrlCreatorUtil.createProductWithImages(products, productDAO);
//    }

    @Override
    public List<ProductDTO> getProductsByParentCategoryId(Integer parentCategoryId, HttpServletRequest request) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuantity = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.PRODUCT);//generateQueryWithCase(request);
        LOGGER.info("######## CASE QUERY: " + caseQuantity);
        List<ProductDTO> productList = productDAO.getProductsByParentCategoryId(caseQuantity, parentCategoryId);
        List<ProductDTO> products = new ArrayList<>();
        try {
			products = newProductCheckUtil.checkListofProducts(productList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//        if (checkParameter("action", request)) {
//            String action = (String) getParameterFromRequestObject("action", request);
//            products = reverseProductList(action, products);
//        }

        return imageResourceUrlCreatorUtil.createProductWithImages(products, productDAO, ImageType.THUMBNAIL);
    }

    @Override
    public Integer totalProductsInCategory(Integer categoryId) {
        return dbService.getDao(ProductDAO.class).getAllProductInCategory(categoryId);
    }

    // Returns total number of products in table using sub category id
    @Override
    public Integer totalProductsInSubCategory(Integer categoryId) {
        return dbService.getDao(ProductDAO.class).getAllProductInSubCategory(categoryId);
    }

    @Override
    public List<ProductDTO> searchProduct(HttpServletRequest request) {
        // Default Values For Query Parameters
        String searchKey = "";
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.PRODUCT);//generateQueryWithCase(request);

        if (CustomQueryCreator.checkParameter("searchKey", request)) {
            searchKey = "'%" + (String) CustomQueryCreator.getParameterFromRequestObject("searchKey", request) + "%'";
        }
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        List<ProductDTO> searchResultsList = productDAO.searchProduct(searchKey, caseQuery);
        List<ProductDTO> searchResults = new ArrayList<>();
        try {
			searchResults = newProductCheckUtil.checkListofProducts(searchResultsList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//        if (CustomQueryCreator.checkParameter("action", request)) {
//            String actionName = (String) getParameterFromRequestObject("action", request);
//            searchResults = reverseProductList(actionName, searchResults);
//        }
        return imageResourceUrlCreatorUtil.createProductWithImages(searchResults, productDAO, ImageType.THUMBNAIL);
    }

    @Override
    public SearchResult getWholeSearchProducts(HttpServletRequest request) {
        // Default Values For Query Parameters
        String searchKey = "";
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.PRODUCT);//generateQueryWithCase(request);
        Set<Integer> categroyIdList = new HashSet<>();
        SearchResult searchResult = new SearchResult();
        List<CategoryDTO> listOfCategory = new ArrayList<>();
        if (CustomQueryCreator.checkParameter("searchKey", request)) {
            searchKey = "'%" + (String) CustomQueryCreator.getParameterFromRequestObject("searchKey", request) + "%'";
        }
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        List<ProductDTO> searchedProducts = productDAO.searchProduct(searchKey, caseQuery);

//        if (CustomQueryCreator.checkParameter("action", request)) {
//            String actionName = (String) CustomQueryCreator.getParameterFromRequestObject("action", request);
//            searchedProducts = reverseProductList(actionName, searchedProducts);
//        }
        categroyIdList = searchedProducts.stream().map(ProductDTO::getCategoryId).collect(Collectors.toSet());

        listOfCategory = categoryDAO.getlistOfCategory(new ArrayList<>(categroyIdList));

        searchResult.setProducts(imageResourceUrlCreatorUtil.createProductWithImages(searchedProducts, productDAO, ImageType.THUMBNAIL));
        searchResult.setCategories(listOfCategory);

        return searchResult;
    }

	@Override
	public List<ProductDTO> getSaleProductsforIndex() {
		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        List<ProductDTO> productWithoutImg = productDAO.getSaleProductsforIndex();
        return imageResourceUrlCreatorUtil.createProductWithImages(productWithoutImg, productDAO, ImageType.THUMBNAIL);
	}

	@Override
	public List<ProductDTO> getAllSaleProducts(HttpServletRequest request) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.PRODUCT);
        List<ProductDTO> productsList = productDAO.getAllSaleProducts(caseQuery);
        List<ProductDTO> products = new ArrayList<>();
        try {
			products = newProductCheckUtil.checkListofProducts(productsList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return imageResourceUrlCreatorUtil.createProductWithImages(products, productDAO, ImageType.THUMBNAIL);
	}

}
