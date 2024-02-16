package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.WishListDTO;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

/**
 * Created by aevin on Feb 27, 2019
 **/

/*
* Step 1: For Insert and update -> use @SqlUpdate
*       1.1 For Insert -> INSERT INTO table_name(database_column_name1, database_column_name2,...) VALUES(:DTO_ITEM NAME1, :DTO_ITEM NAME2)
*       1.2 For Update -> UPDATE table_name SET table_column_name1 = value/ :variable
* Step 2: For retrieving -> use @Query
*       2.1 For simple retrieval -> SELECT * FROM table_name (conditions)
*       2.2 For join retrieval -> SELECT * FROM table_name_1 INNER JOIN table_name_2 ON table_name_1.column_name = table_name_2.column_name (conditions)
*       conditions -> WHERE, AND, LIMIT ...
* */
public interface WishListDAO {

    /*Inserting wish list data to the database*/
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO wish_list(user_id, product_id, deleted) VALUES(:userId, :productId, :deleted)")
    public Integer addWishProduct(@BindBean WishListDTO wishListDTO);

    /*Retrieving all the wish list of the particular user
    * @Parameter userId
    * Retrieving products using the product id from the products table and wish list table
    * */
//    @SqlQuery("SELECT * FROM products INNER JOIN wish_list ON products.product_id = wish_list.product_id WHERE user_id = :userId")
//    public List<ProductDTO> getAllWishListProductsOfUser(@Bind("userId") Integer userId);
    @SqlQuery("SELECT product_id  FROM wish_list WHERE user_id = :userId AND deleted = false")
    public List<Integer> getAllWishListProductsOfUser(@Bind("userId") Integer userId);

    /*
    * Remove product from wish list give parameter
    * set delete status to 'true'
    * @parameter userId, productId
    * */
    @SqlUpdate("UPDATE wish_list SET deleted = :deleted WHERE id = :wishId")
    public Integer removeWishItem(@Bind("wishId") int wishId,
                               @BindBean WishListDTO wishListDTO);

    /*
    * Getting the wish list id for updating the wish list
    * */
    @SqlQuery("SELECT id FROM wish_list WHERE user_id = :userId AND product_id = :productId AND deleted = 0")
    public Integer getWishListIdOfProductsOfUser(@Bind("userId") int userId,
                                                     @Bind("productId") int productId);
}
