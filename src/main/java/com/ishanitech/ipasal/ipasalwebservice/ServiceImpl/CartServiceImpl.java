package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.CartService;
import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.dao.CartDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private DbService dbService;

    @Autowired
    public CartServiceImpl(DbService dbService){
        this.dbService =dbService;
    }


    @Override
    public Integer addToCart(CartDTO cartDTO) {
        CartDAO cartDAO = dbService.getDao(CartDAO.class);
        return cartDAO.addToCart(cartDTO);
    }

    @Override
    public List<CartDTO> getCartDetailsByCustomerrId(Integer customerId) {
        CartDAO cartDAO = dbService.getDao(CartDAO.class);
        return cartDAO.getCartDetailsByCustomerId(customerId);
    }

    @Override
    public Integer updateCart(CartDTO cartDTO) {
        CartDAO cartDAO = dbService.getDao(CartDAO.class);
        return cartDAO.updateCart(cartDTO);
    }

    @Override
    public void removeFromCart(Integer cartId, Integer customerId) {
        CartDAO cartDAO = dbService.getDao(CartDAO.class);
         cartDAO.deleteFromCart(cartId, customerId);

    }
}
