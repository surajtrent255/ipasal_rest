package com.ishanitech.ipasal.ipasalwebservice.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import com.ishanitech.ipasal.ipasalwebservice.dto.RelatedProductDTO;
/**
 * 
 * @author Tanchhowpa
 *
 * Created on: Feb 1, 2019 5:36:57 PM
 */
public interface RelatedProductDAO {

	//Inserts the related products into the related product field
	@SqlBatch("INSERT INTO related_product (main_product_id, related_product_id) VALUES(:mainProductId, :relatedProductId)")
	void addRelatedProducts(@BindBean List<RelatedProductDTO> relatedProducts);
	
	//Removes the products from related product field
	@SqlUpdate("DELETE FROM related_product WHERE (main_product_id = :productId)")
	void removeRelatedProducts(@Bind("productId") Integer productId);

	//Removes the entry of the deleted product form the related_product table
	@SqlUpdate("DELETE FROM related_product WHERE related_product_id = :productId")
	void removeRelatedProductEntry(@Bind("productId") Integer productId);
	
}
