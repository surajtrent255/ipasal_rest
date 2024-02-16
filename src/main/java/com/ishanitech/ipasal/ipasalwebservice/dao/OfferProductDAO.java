package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.OfferProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.OfferProductImageDTO;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
public interface OfferProductDAO {

    // Inserting offer Product details
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO offer_product(offer_title, short_detail, category_id, offer) VALUES(:offerTitle, :shortDetail, :categoryId, :offer)")
    public Integer addOfferProductDetails(@BindBean OfferProductDTO offerProductDTO);

    // Retrieving offer product details
    @SqlQuery("SELECT * FROM offer_product WHERE offer = true ORDER BY RAND() LIMIT 1")
    public List<OfferProductDTO> getOfferProducts();

    // Uploading offer product image
    @SqlUpdate("INSERT INTO offer_image(offer_id, image_name)" +
            "VALUES(:offerId, :imageName)")
    void uploadOfferProductImage(@Bind("offerId") Integer offerId,
                                 @Bind("imageName") String imageName);

    // retrieving offer product image
    @SqlQuery("SELECT * FROM offer_image WHERE offer_id = :offerId")
    OfferProductImageDTO getOfferProductImage(@Bind("offerId") Integer offerId);

}
