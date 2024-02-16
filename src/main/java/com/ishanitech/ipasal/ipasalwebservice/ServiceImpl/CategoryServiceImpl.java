package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.CategoryService;
import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.dao.CategoryDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.ProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryImageDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ImageType;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.CategoryImageResourceUrlCreatorUtil;
import com.ishanitech.ipasal.ipasalwebservice.utilities.ImageResourceUrlCreatorUtil;
import com.ishanitech.ipasal.ipasalwebservice.utilities.NewProductCheckUtil;
import com.ishanitech.ipasal.ipasalwebservice.utilities.OfferImageResourceUrlCreatorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private DbService dbService;

    @Autowired
    CategoryImageResourceUrlCreatorUtil categoryImageResourceUrlCreatorUtil;
    @Autowired
    ImageResourceUrlCreatorUtil imageResourceUrlCreatorUtil;
    @Autowired
    OfferImageResourceUrlCreatorUtil offerImageResourceUrlCreatorUtil;
    @Autowired
    NewProductCheckUtil newProductCheckUtil;
    
    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    @Transactional
    public Integer addCategory(CategoryDTO categoryDTO) {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        return categoryDAO.addCategory(categoryDTO);
    }

    @Override
    public List<CategoryDTO> getCategoryByParentId(Integer parentId) {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        List<CategoryDTO> categories = categoryDAO.getCategoryByParentId(parentId);
        addCategoryImages(categories);
        for (CategoryDTO cDTO : categories) {
        	List<CategoryDTO> childCategory = categoryDAO.getCategoryByParentId(cDTO.getCategoryId());
        	cDTO.setChildCategories(addCategoryImages(childCategory));
        	
        }
        return categories;
    }

    //For adding images to category
    public List<CategoryDTO> addCategoryImages(List<CategoryDTO> categoryListWithoutImage) {
    	 CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
    	List<CategoryDTO> categoryListWithImage = new ArrayList<>();
    	
    	//Adding the images to each of the category
        for (CategoryDTO categoryWithoutImage : categoryListWithoutImage) {
        	if(categoryWithoutImage != null) {
	        	CategoryImageDTO image = categoryDAO.getCategoryImage(categoryWithoutImage.getCategoryId());
	        	if(image != null) {
	        		image.setCategoryId(categoryWithoutImage.getCategoryId());
	        		categoryWithoutImage.setImage(categoryImageResourceUrlCreatorUtil.createCategoryImageWithFullUrl(image));
	        	}
	        	categoryListWithImage.add(categoryWithoutImage);
        	}
        }
    	return categoryListWithImage;
    }
    
    
    
    
    @Override
    public void deleteCategory(Integer categoryId) {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        categoryDAO.deleteCategory(categoryId);

    }

    @Override
    public List<CategoryDTO> getFeaturedCategories() {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        List<CategoryDTO> featuredCategoryList = categoryDAO.getFeaturedCategories();
        if (featuredCategoryList != null && featuredCategoryList.size() > 0){
            List<CategoryDTO> featuredCategoriesWithImages = categoryImageResourceUrlCreatorUtil
                    .createCategoryWithImages(featuredCategoryList, categoryDAO);
            return featuredCategoriesWithImages;
        }
        return featuredCategoryList;
    }

    
	@Override
	public List<CategoryDTO> getOfferedCategories() {
		CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
		List<CategoryDTO> offeredCategoryList = categoryDAO.getOfferedCategories();
		if (offeredCategoryList != null && offeredCategoryList.size() > 0) {
			List<CategoryDTO> offeredCategoriesWithImages = offerImageResourceUrlCreatorUtil
					.createOfferWithImages(offeredCategoryList, categoryDAO);
			return offeredCategoriesWithImages;
		}
		return offeredCategoryList;
	}
	
    @Override
    public List<CategoryDTO> getCategoryImages(Integer categoryId) {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        List<CategoryDTO> singleCategory = categoryDAO.getSingleCategory(categoryId);
        if (singleCategory != null){
            List<CategoryDTO> singleCategoryWithImage = categoryImageResourceUrlCreatorUtil
                    .createCategoryWithImages(singleCategory, categoryDAO);
            return singleCategoryWithImage;
        }
        return singleCategory;
    }

    @Override
    public void featureCategory(Integer categoryId) {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        categoryDAO.featureCategory(categoryId);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        List<CategoryDTO> categories = categoryDAO.getAllCategories();
        List<CategoryDTO> categoryListWithImages = new ArrayList<>();
        
        categories.sort((category1, category2) -> {
			return category1.getParentId() - category2.getParentId();
		});
        
        //Adding the images to each of the category
        for (CategoryDTO categoryWithoutImage : categories) {
        	if(categoryWithoutImage != null) {
	        	CategoryImageDTO image = categoryDAO.getCategoryImage(categoryWithoutImage.getCategoryId());
	        	if(image != null) {
	        		image.setCategoryId(categoryWithoutImage.getCategoryId());
	        		categoryWithoutImage.setImage(categoryImageResourceUrlCreatorUtil.createCategoryImageWithFullUrl(image));
	        	}
	        	categoryListWithImages.add(categoryWithoutImage);
        	}
        }

        //Arranging in hierarchy
        List<CategoryDTO> categoryListWithImagesHierarchy = cleanCategoryCreator(categoryListWithImages);
        
        return categoryListWithImagesHierarchy;
    }

	@Override
	public List<ProductDTO> searchProductInCategory(Integer categoryId, String searchKey) {
		ProductDAO productDAO = dbService.getDao(ProductDAO.class);
		String extractedSearchKey = "%"+ searchKey +"%";
		List<ProductDTO> productWithoutImg =  productDAO.searchProductInCategory(categoryId, extractedSearchKey);
		return imageResourceUrlCreatorUtil.createProductWithImages(productWithoutImg, productDAO, ImageType.THUMBNAIL);
	}

    @Override
    public List<ProductDTO> getProductsBetweenRange(Integer categoryId, Float minRate, Float maxRate) {
        ProductDAO productDAO = dbService.getDao(ProductDAO.class);
        List<ProductDTO> productWithoutImage = productDAO.getCategoryBetweenRange(categoryId, minRate, maxRate);
        try {
			productWithoutImage = newProductCheckUtil.checkListofProducts(productWithoutImage);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return imageResourceUrlCreatorUtil.createProductWithImages(productWithoutImage, productDAO, ImageType.THUMBNAIL);
    }

    @Override
    public void uploadCategoryImage(Integer categoryId, String imageName,
                                    String oddImage, String evenImage,
                                    String bannerImage) {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        categoryDAO.uploadCategoryImage(categoryId, imageName, oddImage, evenImage, bannerImage);
    }


    //Retrieving all parent category
    @Override
    public List<CategoryDTO> getAllParentCategory() {
        CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
        return categoryDAO.getAllParentCategory();
    }
    
    public List<CategoryDTO> cleanCategoryCreator(List<CategoryDTO> categoryDTOS) {
    	Map<Integer, CategoryDTO> categoryMap = new HashMap<>();

        // you are using MegaMenuDTO as Linked list with next and before link 

        // populate a Map
        for(CategoryDTO p: categoryDTOS){
        	  CategoryDTO parentCategory;
        	  CategoryDTO childCategory ;
        	if(p.getParentId() != 0) {
        	//  ----- Child -----
                
                if(categoryMap.containsKey(p.getCategoryId())){
                	childCategory = categoryMap.get(p.getCategoryId());
                }
                else{
                	childCategory = new CategoryDTO();
                    categoryMap.put(p.getCategoryId(),childCategory);
                }           
                childCategory.setCategoryId(p.getCategoryId());
                childCategory.setParentId(p.getParentId());
                childCategory.setCategoryName(p.getCategoryName());
                childCategory.setImage(p.getImage());
                // no need to set ChildrenItems list because the constructor created a new empty list



                // ------ Parent ----
                //MegaMenuDTO mmdParent ;
              
                if(categoryMap.containsKey(p.getParentId())){
                	parentCategory = categoryMap.get(p.getParentId());
                	parentCategory.setCategoryId(parentCategory.getCategoryId());
                	parentCategory.setImage(parentCategory.getImage());
                	parentCategory.setParentId(parentCategory.getParentId());  
                }
                else{
                    //mmdParent = new MegaMenuDTO();
                	parentCategory = new CategoryDTO();
                	parentCategory.setCategoryId(p.getCategoryId());
                	parentCategory.setParentId(p.getParentId());  
                	parentCategory.setImage(p.getImage());
                    categoryMap.put(p.getParentId(),parentCategory);
                }
                                           
                parentCategory.addChildCategory(childCategory);
        	} else {
        		categoryMap.put(p.getCategoryId(), new CategoryDTO(p.getCategoryId(), p.getCategoryName(), p.getParentId(),  p.getImage()));
        	}
            


        }

        // Get the root
        List<CategoryDTO> finalHierarchicalCategory = new ArrayList<CategoryDTO>(); 
        for(CategoryDTO mmd : categoryMap.values()){
            if(mmd.getParentId() == 0)
            	finalHierarchicalCategory.add(mmd);
        }
        
    	return finalHierarchicalCategory;
    }

	@Override
	public CategoryDTO getCategoryByCategoryId(Integer categoryId) {
		CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
		CategoryDTO category = categoryDAO.getCategoryByCategoryId(categoryId);
		return category;
	}

	@Override
	public void deleteParentandChildCategories(List<Integer> categoryIds) {
		CategoryDAO categoryDAO = dbService.getDao(CategoryDAO.class);
		categoryDAO.deleteParentandChildCategories(categoryIds);
	}


}
