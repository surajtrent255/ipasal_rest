package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.WishListDTO;

import java.util.List;

/**
 * Created by aevin on Feb 27, 2019
 **/
public interface WishListService {
    Integer addWishProduct(WishListDTO wishListDTO);
    List<ProductDTO> getAllWishListForUser(int userId);
    Integer removeWishItem(int wishId, WishListDTO wishListDTO);
    boolean checkWishProductForId(int userId, int productId);
    Integer getWishListId(int userId, int productId);
    List<ProductDTO> getWishListForUser(int userId);

}
