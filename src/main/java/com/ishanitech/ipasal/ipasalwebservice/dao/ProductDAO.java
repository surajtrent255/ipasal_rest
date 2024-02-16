package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.ImageDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductCategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductMerchantDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.RelatedProductDTO;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

@UseStringTemplate3StatementLocator
public interface ProductDAO {
    @GetGeneratedKeys
    @SqlUpdate("Insert into products(product_name, entry_date, unit, rate ,available_items,user_id,short_desc, highlights, description, featured, discount_percent, actual_rate) values(:productName,:entryDate ,:unit, :rate,:availableItems,:userId,:shortDesc, :highlights, :description, :featured, :discountPercent, :actualRate)")
    public Integer addProducts(@BindBean ProductDTO productDTO);

    @SqlQuery("Select p.*,c.category_name,c.parent_id from products p inner join product_category pc on p.product_id = pc.product_id inner join category c on pc.category_id = c.category_id and p.deleted = 0 ")
    public List<ProductDTO> getAllProducts();

    @SqlQuery("Select p.*,c.* from products p inner join product_category pc on p.product_id = pc.product_id inner join category c on pc.category_id = c.category_id and p.product_id = :productId and p.deleted = 0")
    public ProductDTO getProductById(@Bind("productId") Integer productId);

    @SqlUpdate("Update products SET product_name = :productName, unit = :unit, rate = :rate, available_items = :availableItems, short_desc = :shortDesc, highlights = :highlights, description = :description, discount_percent = :discountPercent, actual_rate = :actualRate, featured = :featured where product_id = :productId")
    public Integer updateProductDetails(@Bind("productId") Integer productId, @BindBean ProductDTO productDTO);

    @SqlUpdate("Update products SET deleted = 1 where product_id = :productId")
    public void removeProduct(@Bind("productId") Integer productId);

    @SqlUpdate("Insert into product_category(product_id, category_id) values(:productId , :categoryId) ")
    public void addProductCategory(@BindBean ProductCategoryDTO productCategoryDTO);

//    @SqlQuery
//    public List<ProductDTO> getProductsByCategoryId(@Define("categoryId") Integer categoryId, @Define("startingIndex") Integer startingIndex, @Define("maxLimit") Integer maxLimit, @Define("caseQuantity") String caseQuantity);


    @SqlQuery
    public List<ProductDTO> getProductsByCategoryId(@Define("categoryId") Integer categoryId, @Define("caseQuantity") String caseQuantity);

//old style
//    @SqlQuery
//    public List<ProductDTO> getProductsByParentCategoryId(@Define("caseQuantity") String caseQuantity, @Define("parentId") Integer parentId, @Define("startingIndex") Integer startingIndex, @Define("maxItemPerPage") Integer maxItemPerPage);

    @SqlQuery
    public List<ProductDTO> getProductsByParentCategoryId(@Define("caseQuantity") String caseQuantity, @Define("parentId") Integer parentId);

    @SqlUpdate("Update products set available_items = :availableItems")
    public void updateInventory(@Bind Integer availableItems);

    @SqlUpdate("UPDATE products SET available_items = available_items - :orderedQty WHERE product_id = :productId")
    public void decreaseProductQuantity(@Bind("orderedQty") Integer orderedQty, @Bind("productId") Integer productId);
    
    @SqlUpdate("UPDATE products SET available_items = available_items + :orderedQty WHERE product_id = :productId")
    public void increaseProductQuantity(@Bind("orderedQty") Integer orderedQty, @Bind("productId") Integer productId);

    @SqlUpdate("Insert into product_images(product_id,image) values(:productId,:imageName)")
    public void addProductImage(@Bind("productId") Integer productId, @Bind("imageName") String imageName);

    @SqlQuery("Select * from product_images where product_id  = :productId")
    public List<ImageDTO> getProductImages(@Bind("productId") Integer productid);


//    @SqlQuery("Select p.*,c.category_name,c.parent_id from products p inner join product_category pc on p.product_id = pc.product_id inner join category c on pc.category_id = c.category_id and p.deleted = 0 and p.featured = 1 ORDER BY p.product_id DESC")
//    public List<ProductDTO> getFeaturedProducts();

    //Redoing this query for featured products to remove the duplicate items
    @SqlQuery("Select * from products p WHERE p.deleted = 0 and p.featured = 1 ORDER BY p.product_id DESC")
    public List<ProductDTO> getFeaturedProducts();


    @SqlUpdate("Update products set featured = 1 where product_id = :productId")
    public void featureProduct(@Bind("productId") Integer productId);

    //returns total number of items in table by parentCategoryId
    @SqlQuery("Select count(*) from products p inner join product_category pc on p.product_id = pc.product_id inner join category c on pc.category_id = c.category_id and c.parent_id= :categoryId and c.deleted = 0 and p.deleted = 0")
    public Integer getAllProductInCategory(@Bind("categoryId") Integer categoryId);

    //returns total number of items in table by sub categoryId
    @SqlQuery("SELECT count(*) FROM products p " +
            "	INNER JOIN product_category pc " +
            "    ON p.product_id = pc.product_id " +
            "    INNER JOIN category c " +
            "    ON pc.category_id = c.category_id " +
            "    WHERE pc.category_id = :categoryId " +
            "    AND c.deleted = 0 " +
            "    AND p.deleted = 0 ")
    public Integer getAllProductInSubCategory(@Bind("categoryId") Integer categoryId);

    @SqlQuery
    public List<ProductDTO> searchProduct(@Define("searchKey") String searchKey, @Define("caseQuery") String caseQuery);

    @SqlQuery("SELECT * FROM products p " +
            "INNER JOIN product_category pc " +
            "ON p.product_id = pc.product_id " +
            "INNER JOIN category c " +
            "ON pc.category_id = c.category_id " +
            "WHERE c.category_id IN (WITH RECURSIVE cte " + 
            "AS (SELECT category_id " + 
            "FROM category AS c1 " + 
            "WHERE category_id =:categoryId " + 
            "UNION ALL " + 
            "SELECT c1.category_id " +
            "FROM category AS c1 " +
            "JOIN cte " + 
            "ON c1.parent_id = cte.category_id)" + 
            "SELECT * from cte) AND p.product_name LIKE :searchKey AND p.deleted = 0 LIMIT 8 ")
                    public List<ProductDTO> searchProductInCategory(@Bind("categoryId") Integer categoryId, @Bind("searchKey") String searchKey);

    //Getting the products between the two rate range
    @SqlQuery("SELECT * FROM products p " +
            "INNER JOIN product_category pc " +
            "ON p.product_id = pc.product_id " +
            "INNER JOIN category c " +
            "ON pc.category_id = c.category_id " +
            "WHERE c.category_id  IN (WITH RECURSIVE cte " + 
            "AS (SELECT category_id " + 
            "FROM category AS c1 " + 
            "WHERE category_id =:categoryId " + 
            "UNION ALL " + 
            "SELECT c1.category_id " + 
            "FROM category AS c1 " + 
            "JOIN cte " + 
            "ON c1.parent_id = cte.category_id) " + 
            "SELECT * from cte) AND rate BETWEEN :min AND :max AND p.deleted = 0")
    public List<ProductDTO> getCategoryBetweenRange(@Bind("categoryId") Integer categoryId,
                                                    @Bind("min") Float min,
                                                    @Bind("max") Float max);

//    Retrieving the products from db in the sorted order

    @SqlQuery("SELECT * FROM related_product rp INNER JOIN products p ON rp.related_product_id = p.product_id WHERE rp.main_product_id = :product_id AND p.deleted = 0")
    public List<RelatedProductDTO> getRelatedProducts(@Bind("product_id") int productId);


    //Adding the merchant to the product

    @SqlUpdate("INSERT INTO product_merchant(product_id, merchant_id) values(:productId , :merchantId) ")
    public void addProductMerchant(@BindBean ProductMerchantDTO productMerchantDTO);

    @SqlQuery("SELECT * FROM product_merchant WHERE product_id = :product_id")
    public List<ProductMerchantDTO> getMerchant(@Bind("product_id") int productId);

    @SqlQuery
    public List<ProductDTO> getWholeSearchProduct(@Define("searchKey") String searchKey, @Define("caseQuery") String caseQuery);

    //Upon deleting a merchant, this query deletes all the products attached to that particular merchant
    @SqlUpdate("UPDATE products p INNER JOIN product_merchant pm ON p.product_id = pm.product_id SET p.deleted = 1 WHERE pm.merchant_id =:merchantId") 
	public void removeMerchantProducts(@Bind("merchantId") Integer merchantId);

	@SqlQuery("SELECT * FROM products p WHERE p.discount_percent > 0 AND p.deleted = 0 ORDER BY RAND() LIMIT 6;")
	public List<ProductDTO> getSaleProductsforIndex();
	
	@SqlQuery
	public List<ProductDTO> getAllSaleProducts(@Define("caseQuery") String caseQuery);
	
}
