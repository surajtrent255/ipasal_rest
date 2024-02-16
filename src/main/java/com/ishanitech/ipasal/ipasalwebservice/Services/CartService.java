package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.CartDTO;

import java.util.List;

public interface CartService {
    Integer addToCart(CartDTO cartDTO);
    List<CartDTO> getCartDetailsByCustomerrId(Integer customerId);
    Integer updateCart(CartDTO cartDTO);
    void removeFromCart(Integer cartId, Integer customerId);
}
