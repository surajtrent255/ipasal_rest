package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryImageDTO;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

@UseStringTemplate3StatementLocator
public interface CategoryDAO {

    @GetGeneratedKeys
    @SqlUpdate("Insert into category(category_name, parent_id, featured, offered) values(:categoryName, :parentId, :featured, :offered)")
    public Integer addCategory(@BindBean CategoryDTO categoryDTO);

    @SqlQuery("SELECT * FROM category WHERE parent_id = :parentId AND deleted = 0")
    public List<CategoryDTO> getCategoryByParentId(@Bind("parentId") Integer parentId);

    @SqlUpdate("Update category set deleted = 1 where category_id = :categoryId")
    public void deleteCategory(@Bind("categoryId") Integer categoryId);

    @SqlQuery("Select * from category where parent_id =0 AND featured = 1 ORDER BY RAND() LIMIT 6")
    public List<CategoryDTO> getFeaturedCategories();
    
    @SqlQuery("Select * from category where offered = 1 ORDER BY RAND() LIMIT 1")
    public List<CategoryDTO> getOfferedCategories();

    @SqlUpdate("Update category set featured = 1 where category_id = :categoryId")
    public void featureCategory(@Bind("categoryId") Integer categoryId);

    @SqlQuery("SELECT * FROM category WHERE deleted = 0")
    public List<CategoryDTO> getAllCategories();

    //    For Category Home page images
    //    For uploading category image
    @SqlUpdate("INSERT INTO category_image(category_id, image_name, odd_image, even_image, banner_image)" +
            "VALUES(:categoryId, :imageName, :oddImage, :evenImage, :bannerImage)")
    void uploadCategoryImage(@Bind("categoryId") Integer categoryId,
                             @Bind("imageName") String imageName,
                             @Bind("oddImage") String oddImage,
                             @Bind("evenImage") String evenImage,
                             @Bind("bannerImage") String bannerImage);

    //    For Retrieving category image
    @SqlQuery("SELECT * FROM category_image WHERE category_id = :categoryId")
    CategoryImageDTO getCategoryImage(@Bind("categoryId") Integer categoryId);

    //    For retrieving single category for the category banner image
    @SqlQuery("Select * from category where category_id = :categoryId")
    public List<CategoryDTO> getSingleCategory(@Bind("categoryId") Integer categoryId);

    // Retrieving parent category
    @SqlQuery("SELECT * FROM category WHERE parent_id =0 AND deleted = 0")
    public List<CategoryDTO> getAllParentCategory();

    // Retrieving child category
    @SqlQuery("SELECT * FROM category WHERE parent_id =:parentId")
    public List<CategoryDTO> getAllChildCategory(@Bind("parentId") Integer parentId);

    @SqlQuery("SELECT * FROM category WHERE category_id IN (<categoryIdList>) AND deleted = 0")
    public List<CategoryDTO> getlistOfCategory(@BindIn("categoryIdList") List<Integer> categoryIdList);

    @SqlQuery("SELECT * FROM category WHERE category_id =:categoryId AND deleted = 0")
	public CategoryDTO getCategoryByCategoryId(@Bind("categoryId") Integer categoryId);
   
    @SqlBatch("UPDATE category SET deleted = 1 WHERE category_id = :categoryIds")
	public void deleteParentandChildCategories(@Bind("categoryIds") List<Integer> categoryIds);

    @SqlQuery("WITH RECURSIVE CTE_Hierarchy AS " + 
    		"( " + 
    		"SELECT	c1.category_id, c1.parent_id, c1.category_name FROM category c1 WHERE c1.category_id =:categoryId " + 
    		"UNION ALL " + 
    		"SELECT c2.category_id, c2.parent_id, c2.category_name FROM CTE_Hierarchy " + 
    		"JOIN category c2 ON c2.category_id = CTE_Hierarchy.parent_id " + 
    		") " + 
    		"SELECT * FROM CTE_Hierarchy ")
	public List<CategoryDTO> getAncestorCategories(@Bind("categoryId") Integer categoryId);

}
