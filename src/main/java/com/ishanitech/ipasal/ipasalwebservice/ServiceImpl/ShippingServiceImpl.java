package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.ShippingService;
import com.ishanitech.ipasal.ipasalwebservice.dao.ShippingDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {
    private DbService dbService;

    @Autowired
    public  ShippingServiceImpl(DbService dbService){
        this.dbService = dbService;
    }

    @Override
    public Integer addShippingDetails(ShippingDTO shippingDTO) {
        ShippingDAO shippingDAO = dbService.getDao(ShippingDAO.class);
        return shippingDAO.addShippingDetails(shippingDTO);
    }

    @Override
    public ShippingDTO getShippingDetailsById(Integer customerId) {
        ShippingDAO shippingDAO = dbService.getDao(ShippingDAO.class);
        return shippingDAO.getShippingDetailsById(customerId);
    }

    @Override
    public Integer updateShippingDetails(ShippingDTO shippingDTO) {
        ShippingDAO shippingDAO = dbService.getDao(ShippingDAO.class);
        return shippingDAO.updateShippingDetails(shippingDTO);
    }

	@Override
	public List<ShippingDTO> getShippingDetailsByOrderId(Integer orderId) {
		ShippingDAO shippingDAO = dbService.getDao(ShippingDAO.class);
		return shippingDAO.getShippingDetailsByOrderId(orderId);
	}
}
