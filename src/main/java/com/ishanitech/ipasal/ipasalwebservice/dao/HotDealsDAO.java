package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealImageDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealsDTO;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
public interface HotDealsDAO {

    //Inserting the Hot Deals data
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO hot_deals(product_id, product_name, old_rate, new_rate, finish_date, hot_deal) VALUES(:productId, :productName, :oldRate, :newRate, :finishDate, :hotDeal)")
    public Integer addHotDealsData(@BindBean HotDealsDTO hotDealsDTO);

    //Retrieving single hot deals data randomly
    @SqlQuery("SELECT * FROM hot_deals WHERE hot_deal = true AND finish_date >= CURDATE()  ORDER BY RAND() LIMIT 1")
    public List<HotDealsDTO> getAllHotDeals();

    @SqlQuery("SELECT * FROM hot_deals")
    public List<HotDealsDTO> getAllHotDealsList();
    
    @SqlQuery("SELECT * FROM hot_deals WHERE hot_deal_id = :hotDealId")
    public HotDealsDTO getHotDealById(@Bind("hotDealId") Integer hotDealId);
    
    //Updating hot deals data
    @SqlUpdate("UPDATE hot_deals SET new_rate = :newRate, hot_deal = :hotDeal WHERE hot_deal_id = :hotDealId")
    public void updateHotDealData(@Bind("hotDealId") Integer hotDealId,
                                  @Bind("newRate") Float newRate,
                                  @Bind("hotDeal") Boolean hotDeal);

    //Deleting hot deals data

    //Uploading hot deals image
    @SqlUpdate("INSERT into hot_deals_image(hot_deal_id, image_name) VALUES(:hotDealId, :imageName)")
    void addHotDealsImage(@Bind("hotDealId") Integer hotDealId, @Bind("imageName") String imageName);

    //Editing hot deals image
    @SqlUpdate("UPDATE hot_deals_image SET image_name = :imageName WHERE hot_deal_id = :hotDealId")
    void editHotDealsImage(@Bind("hotDealId") Integer hotDealId, @Bind("imageName") String imageName);
    
    //retrieving hot deals image
    @SqlQuery("SELECT * FROM hot_deals_image WHERE hot_deal_id = :hotDealId")
    HotDealImageDTO getHotDealsImage(@Bind("hotDealId") Integer hotDealId);

  //Editing hot deals data
    @SqlUpdate("UPDATE hot_deals SET new_rate = :newRate, hot_deal = :hotDeal, finish_date = :finishDate WHERE hot_deal_id = :hotDealId")
	public Integer editHotDealsData(@BindBean HotDealsDTO hotDealsDTO);

    //Deleting hot deals data
    @SqlUpdate("DELETE from hot_deals where hot_deal_id = :hotDealId")
    public void deleteHotDealsData(@Bind("hotDealId") Integer hotDealId);

    //Adding discount percent to product on addition to hot deals
    @SqlUpdate("UPDATE products SET discount_percent = :discountPercent, rate= :newRate WHERE product_id = :productId")
	public void addDiscounttoProduct(@Bind("productId") int productId,@Bind("discountPercent") float discountPercent,@Bind("newRate") float newRate);
    
    //Deleting discount from product on deletion of hot deal
    @SqlUpdate("UPDATE products SET discount_percent = 0, rate = :oldRate WHERE product_id = :productId")
    public void deleteDiscountfromProduct(@Bind("productId") int productId,@Bind("oldRate") float oldRate);

    @SqlUpdate("DELETE FROM hot_deals_image where hot_deal_id= :hotDealId")
	public void deleteHotDealImage(@Bind("hotDealId") Integer hotDealId);
}
