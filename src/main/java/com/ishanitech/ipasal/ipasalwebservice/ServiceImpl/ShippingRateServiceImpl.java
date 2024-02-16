package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.ShippingRateService;
import com.ishanitech.ipasal.ipasalwebservice.dao.ShippingRateDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 'Azens Eklak'
 * email: azens1995@gmail.com
 * created on Mar 01, 2019
 * since 2017
 **/

@Service
public class ShippingRateServiceImpl implements ShippingRateService {
    private DbService dbService;

    @Autowired
    public ShippingRateServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    /*
    * Adding Shipping information
    * */
    @Override
    public Integer addShippingInfo(ShippingRateDTO shippingRateDTO) {
        if (dbService != null){
            ShippingRateDAO shippingRateDAO = dbService.getDao(ShippingRateDAO.class);
            return shippingRateDAO.addShippingInfo(shippingRateDTO);
        }else {
            return null;
        }
    }

    /*
    * Retrieving shipping information using the given location
    * */
    @Override
    public ShippingRateDTO getShippingInfo(String location) {
        if (dbService != null){
            ShippingRateDAO shippingDAO = dbService.getDao(ShippingRateDAO.class);
            return shippingDAO.getShippingInfo(location);
        }else {
            return null;
        }
    }

    /*
    * Retrieving all shipping information
    * */
    @Override
    public List<ShippingRateDTO> getAllShippingInfo() {
        if (dbService != null){
            ShippingRateDAO shippingRateDAO = dbService.getDao(ShippingRateDAO.class);
            List<ShippingRateDTO> shippingRateList = shippingRateDAO.getAllShippingInfo();
            return shippingRateList;
        }
        return null;
    }


    /*
    * Updating shipping information with new amount for the given Id
    * */
    @Override
    public void updateShippingInfo(Integer shippingId, Integer amount) {
        if (dbService != null){
            ShippingRateDAO shippingRateDAO = dbService.getDao(ShippingRateDAO.class);
            shippingRateDAO.updateShippingInfo(amount, shippingId);
        }
    }
}
