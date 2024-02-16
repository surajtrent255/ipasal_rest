package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.OfferProductService;
import com.ishanitech.ipasal.ipasalwebservice.dao.OfferProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.OfferProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.OfferImageResourceUrlCreatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
@Service
public class OfferProductServiceImpl implements OfferProductService {

    private DbService dbService;
    @Autowired
    private OfferImageResourceUrlCreatorUtil offerImageResourceUrlCreatorUtil;

    Logger logger = LoggerFactory.getLogger(OfferProductServiceImpl.class);

    public OfferProductServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }


    //    Inserting offer product details
    @Override
    public Integer addOfferProduct(OfferProductDTO offerProductDTO) {
        if (dbService != null){
            OfferProductDAO offerProductDAO = dbService.getDao(OfferProductDAO.class);
            return offerProductDAO.addOfferProductDetails(offerProductDTO);
        }else {
            System.out.println("DbService has been returned null. Process can't be done.");
            return null;
        }

    }

//    Retrieving offer product details
    @Override
//    public List<OfferProductDTO> getOfferProducts() {
//        OfferProductDAO offerProductDAO = dbService.getDao(OfferProductDAO.class);
//        List<OfferProductDTO> offerProducts = offerProductDAO.getOfferProducts();
//        if (offerProducts != null) {
//            List<OfferProductDTO> offerProductWithImages =
//                    offerImageResourceUrlCreatorUtil.createOfferProductWithImages(offerProducts, offerProductDAO);
//            return offerProductWithImages;
//        }
//
//        return offerProducts;
//    }

//    Uploading offer product image
    
    public void addOfferImage(Integer offerId, String imageName) {
        OfferProductDAO offerProductDAO = dbService.getDao(OfferProductDAO.class);
        offerProductDAO.uploadOfferProductImage(offerId, imageName);
    }


	@Override
	public List<OfferProductDTO> getOfferProducts() {
		// TODO Auto-generated method stub
		return null;
	}
}
