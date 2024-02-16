package com.ishanitech.ipasal.ipasalwebservice.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductTagsDTO;

@UseStringTemplate3StatementLocator
public interface ProductTagsDAO {

	@SqlBatch("INSERT INTO product_tags (product_id, product_tag) VALUES(:productId, :productTag)")
	void addProductTagsToProduct(@BindBean List<ProductTagsDTO> productTagsList);

	@SqlQuery("SELECT * FROM product_tags WHERE product_id = :productId")
	public List<ProductTagsDTO> getProductTagsByProductId(@Bind("productId") int productId);

	@SqlUpdate("DELETE FROM product_tags WHERE product_id = :productId")
	void removeProductTagsByProductId(@Bind("productId") int productId);
	
	@SqlUpdate("DELETE FROM product_tags WHERE product_tag = :tag")
	void removeProductsWithGivenTag(@Bind("tag") String promotionTag);
	
//	@SqlBatch("INSERT INTO product_tags (product_id, product_tag) VALUES(:productId, :productTag)")
//	void addProductsToGivenTag(@BindBean List<ProductTagsDTO> promotionalProducts);
	
	@SqlQuery("SELECT * FROM product_tags pt INNER JOIN products p ON pt.product_id = p.product_id WHERE p.deleted = 0 AND pt.product_tag = :tag")
	List<ProductTagsDTO> getAllProductIdsWithGivenTag(@Bind("tag") String tag);
	
	@SqlQuery
	public List<ProductDTO> getAllProductsWithGivenTag(@Define("caseQuery") String caseQuery, @Define("searchTag") String searchTag);

	@SqlQuery("SELECT * FROM products p INNER JOIN product_tags pt ON p.product_id = pt.product_id WHERE p.deleted = 0 AND pt.product_tag = :productTag")
	public List<ProductDTO> getAllProductsWithGivenTag(@Bind("productTag") String productTag);

	@SqlQuery
	public List<ProductDTO> getAllProductsWithGivenPromoId(@Define("caseQuery") String caseQuery, @Define("promoId") Integer promoId);
}
