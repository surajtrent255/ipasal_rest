package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealsDTO;

import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
public interface HotDealsService {
    Integer addHotDeal(HotDealsDTO hotDealsDTO);
    List<HotDealsDTO> getAllActiveHotDeals();
    List<HotDealsDTO> getAllHotDeals();
    void updateHotDeals(Integer id, Float newRate, Boolean isHotDeal);
    void addHotDealsImage(Integer hotDealId, String imageName);

    Integer editHotDeal(HotDealsDTO hotDealsDTO);
    void deleteHotDeal(Integer hotDealId);
	
	void editHotDealsImage(Integer hotDealId, String imageName);
}
