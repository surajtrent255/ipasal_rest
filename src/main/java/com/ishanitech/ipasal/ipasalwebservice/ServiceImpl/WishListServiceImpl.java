package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.WishListService;
import com.ishanitech.ipasal.ipasalwebservice.dao.ProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dao.WishListDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ImageType;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.WishListDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.ImageResourceUrlCreatorUtil;
import com.ishanitech.ipasal.ipasalwebservice.utilities.NewProductCheckUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aevin on Feb 27, 2019
 **/

/*
 * Step 1: Annotate the class as 'Service'
 * Step 2: Get DbService
 * Step 3: Add constructor with DbService as argument
 * Step 4: Annotate constructor 'Autowired'
 * Step 5: Inside each method
 *       5.1 : Check if the DbService is null or not
 *       5.2 : Get the Dao object using dbService
 *       5.3 : Execute Dao method for function
 * Extra: Create a logger to log the information being sent or retrieved using the service class
 * */
@Service
public class WishListServiceImpl implements WishListService {

    //Logger for logging the data
    private Logger logger = LoggerFactory.getLogger(WishListServiceImpl.class);

    @Autowired
    ImageResourceUrlCreatorUtil imageResourceUrlCreatorUtil;
    private DbService dbService;

    @Autowired
    NewProductCheckUtil newProductCheckUtill;
    
    @Autowired
    public WishListServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }


    @Override
    public Integer addWishProduct(WishListDTO wishListDTO) {
        if (dbService != null) {
            WishListDAO wishListDAO = dbService.getDao(WishListDAO.class);
            return wishListDAO.addWishProduct(wishListDTO);
        } else {
            System.out.println("DBService has returned null");
            return null;
        }
    }

    @Override
    public List<ProductDTO> getAllWishListForUser(int userId) {
        List<Integer> wishProductsList;
        List<ProductDTO> wishProducts;
        List<ProductDTO> wishProductsWithImages = new ArrayList<>();
        if (dbService != null) {
            WishListDAO wishListDAO = dbService.getDao(WishListDAO.class);
            wishProductsList = wishListDAO.getAllWishListProductsOfUser(userId);
            if (wishProductsList != null && wishProductsList.size() > 0) {
                ProductDAO productDAO = dbService.getDao(ProductDAO.class);
                wishProducts = new ArrayList<>();
                for (Integer productId : wishProductsList) {
                    wishProducts.add(productDAO.getProductById(productId));
                }
                if (wishProducts != null && wishProducts.size()>0){
                	
                    wishProductsWithImages = imageResourceUrlCreatorUtil.createProductWithImages(wishProducts, productDAO, ImageType.THUMBNAIL);
                    try {
						wishProductsWithImages = newProductCheckUtill.checkListofProducts(wishProductsWithImages);
					} catch (ParseException e) {
						e.printStackTrace();
					}
                }
                return wishProductsWithImages;
            }else
                System.out.println("Wish List for "+userId + "Id returned empty.");
                return null;
            } else {
                System.out.println("DBService has returned null");
                return null;
            }
        }

        @Override
        public Integer removeWishItem (int wishId, WishListDTO wishListDTO){
            if (dbService != null) {
                WishListDAO wishListDAO = dbService.getDao(WishListDAO.class);
                return wishListDAO.removeWishItem(wishId, wishListDTO);
            } else {
                System.out.println("DBService has returned null");
                return null;
            }
        }

    @Override
    public boolean checkWishProductForId(int userId, int productId) {
        if (dbService != null){
            WishListDAO wishListDAO = dbService.getDao(WishListDAO.class);
            List<Integer> wishList = wishListDAO.getAllWishListProductsOfUser(userId);
            if (wishList.contains(productId)){
                return true;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }

    @Override
    public Integer getWishListId(int userId, int productId) {
        if (dbService != null){
            WishListDAO wishListDAO = dbService.getDao(WishListDAO.class);
            Integer wishId =wishListDAO.getWishListIdOfProductsOfUser(userId, productId);
            return  wishId;
        }else {
            logger.error("DbService is returned null");
            return null;
        }

    }

    @Override
    public List<ProductDTO> getWishListForUser(int userId) {
        List<Integer> wishProductsList;
        List<ProductDTO> wishProducts;
        if (dbService != null) {
            WishListDAO wishListDAO = dbService.getDao(WishListDAO.class);
            wishProductsList = wishListDAO.getAllWishListProductsOfUser(userId);
            if (wishProductsList != null && wishProductsList.size() > 0) {
                ProductDAO productDAO = dbService.getDao(ProductDAO.class);
                wishProducts = new ArrayList<>();
                for (Integer productId : wishProductsList) {
                    wishProducts.add(productDAO.getProductById(productId));
                }
                return wishProducts;
            } else {
                System.out.println("DBService has returned null");
                return null;
            }
        }else {
            return null;
        }
    }
}
