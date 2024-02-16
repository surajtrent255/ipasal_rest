package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.HotDealsService;
import com.ishanitech.ipasal.ipasalwebservice.dao.HotDealsDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealsDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.HotDealsImageResourceUrlCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
@Service
public class HotDealsServiceImpl implements HotDealsService {

    private DbService dbService;

    @Autowired
    private HotDealsImageResourceUrlCreatorUtil hotDealsImageResourceUrlCreatorUtil;

    public HotDealsServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    //service to add the hot deal data
    @Override
    public Integer addHotDeal(HotDealsDTO hotDealsDTO) {
        if (dbService != null) {
            HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
            int productId = hotDealsDTO.getProductId();
            float oldRate = hotDealsDTO.getOldRate();
            float newRate = hotDealsDTO.getNewRate();
            float discountPercent = ((oldRate-newRate)/oldRate)*100 ;
            hotDealsDAO.addDiscounttoProduct(productId, discountPercent, newRate);
            return hotDealsDAO.addHotDealsData(hotDealsDTO);
        } else {
            System.out.println("DbService has been returned null. Process can't be done.");
            return null;
        }
    }


    //service to retrieve hot deal data
    @Override
    public List<HotDealsDTO> getAllActiveHotDeals() {
        HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
        List<HotDealsDTO> hotDealsList = hotDealsDAO.getAllHotDeals();

        if (hotDealsList != null && hotDealsList.size() > 0){
            return hotDealsImageResourceUrlCreatorUtil.createHotDealsWithImages(hotDealsList, hotDealsDAO);
        }

        return hotDealsList;

    }
    
	@Override
	public List<HotDealsDTO> getAllHotDeals() {
		HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
		List<HotDealsDTO> hotDealsList = hotDealsDAO.getAllHotDealsList();
		
		if (hotDealsList != null && hotDealsList.size() > 0){
            return hotDealsImageResourceUrlCreatorUtil.createHotDealsWithImages(hotDealsList, hotDealsDAO);
        }

        return hotDealsList;
	}

    //service to update the hot deals data
    @Override
    public void updateHotDeals(Integer id, Float newRate, Boolean isHotDeal) {
        if (dbService != null) {
            HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
            hotDealsDAO.updateHotDealData(id, newRate, isHotDeal);
        }
    }

    //Service to add the image to the database
    @Override
    public void addHotDealsImage(Integer hotDealId, String imageName) {
        HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
        hotDealsDAO.addHotDealsImage(hotDealId, imageName);
    }

    @Override
    public void editHotDealsImage(Integer hotDealId, String imageName) {
        HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
        hotDealsDAO.editHotDealsImage(hotDealId, imageName);
    }
    
    @Override
	public Integer editHotDeal(HotDealsDTO hotDealsDTO) {
		 if (dbService != null) {
	            HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
	            int productId = hotDealsDTO.getProductId();
	            float oldRate = hotDealsDTO.getOldRate();
	            float newRate = hotDealsDTO.getNewRate();
	            float discountPercent = ((oldRate-newRate)/oldRate)*100 ;
	            hotDealsDAO.addDiscounttoProduct(productId, discountPercent, newRate);
	            return hotDealsDAO.editHotDealsData(hotDealsDTO);
	        } else {
	            System.out.println("DbService has been returned null. Process can't be done.");
	            return null;
	        }
	}

	@Override
	public void deleteHotDeal(Integer hotDealId) {
		HotDealsDAO hotDealsDAO = dbService.getDao(HotDealsDAO.class);
		HotDealsDTO hotDealsDTO = hotDealsDAO.getHotDealById(hotDealId);
		Integer productId = hotDealsDTO.getProductId();
		float oldRate = hotDealsDTO.getOldRate();
		hotDealsDAO.deleteDiscountfromProduct(productId, oldRate);
		hotDealsDAO.deleteHotDealsData(hotDealId);
		hotDealsDAO.deleteHotDealImage(hotDealId);
	}


}
