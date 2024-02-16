package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.Services.WishListService;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.WishListDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aevin on Feb 27, 2019
 **/

/*
* Step 1: Map the class to the API end point for access -> "api/v1/..."
*       1.1: User annotation /@RequestMapping(mapping Name)/
* Step 2: Annotate the class as the @RestController
* Step 3: Get the Service Interface object
*      3.0: If other classes are needed like for image, and file operation, get those class and annotate them with 'Autowired'
* Step 4: Construct a Constructor, service interface as argument
* Step 4: Annotate constructor as @Autowired
* Step 5: Create Methods for the various control use case
*       5.1 Adding the data to the database
*           5.1.1: Annotate the class as -> /@RequestMapping(method = RequestMethod.POST)
*           5.1.2: Annotate the passed argument as -> /@RequestBody/
*       5.2 Retrieve data to the database
*       5.3 Update the data in the database
* Step 6: Make all the return type of the methods as -> Response<?> generic class
* Step 7: User try-catch in each method for the database operations through service
* Step 8: Catch the exception and print the exception for the information
* Step 9: Throw the exception message for the Sql operation as -> /throw new CustomSqlException(message);/
* Step 10: For data retrieval
*       10.1: If the data is available, return the data
*       10.2: If the data is not available, return resource not found exception as -> /throw new ResourceNotFoundException(message)/
* */

@RequestMapping("api/v1/wish-list")
@RestController
public class WishListResources {

    private Logger logger = LoggerFactory.getLogger(WishListResources.class);

    private WishListService wishListService;

    @Autowired
    public WishListResources(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    /*
    * Adding wish item
    * */
    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addWishProduct(@RequestBody WishListDTO wishListDTO){
        Integer wishId = null;
        try{
            wishId = wishListService.addWishProduct(wishListDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while adding wish item");
        }
        return Response.ok(wishId, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Updating wish item for the delete case
    * */
    @RequestMapping(method = RequestMethod.PUT, value = "/{wishId}")
    public Response<?> removeWishProduct(@PathVariable Integer wishId,
                                         @RequestBody WishListDTO wishListDTO) {
        try{
            wishListService.removeWishItem( wishId, wishListDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error removing wish item");
        }
        return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Retrieving the wish list products
    * */

    @GetMapping(value = "/{userId}")
    public Response<?> getAllWishListOfUser(@PathVariable Integer userId){
        List<ProductDTO> wishProductsList;
        try{
            wishProductsList = wishListService.getAllWishListForUser(userId);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error occurred while retrieving wish list products");
        }
        return Response.ok(wishProductsList, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Check if the item is already in the wish list
    * */
    @GetMapping(value = "/status/{userId}/{productId}")
    public Response<?> checkProductInWishList(@PathVariable Integer userId, @PathVariable Integer productId){
        Boolean status;
        try {
            status = wishListService.checkWishProductForId(userId, productId);
        }catch (Exception e){
        	logger.error(e.getMessage());
            throw new CustomSqlException("Error occurred while checking the product status");
        }
        return Response.ok(status,HttpStatus.OK.value(),HttpStatus.OK.name());
    }

    /*
    * Getting the wish list id using the given userid and productid
    * */

    @GetMapping(value = "/{userId}/{productId}")
    public Response<?> getWishListId(@PathVariable Integer userId, @PathVariable Integer productId){
        Integer wishId;
        try {
            wishId = wishListService.getWishListId(userId, productId);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error occurred while getting wish Id");
        }
        return Response.ok(wishId,HttpStatus.OK.value(),HttpStatus.OK.name());
    }

    @GetMapping("/{userId}/list")
    public Response<?> getWishListOfUser(@PathVariable Integer userId){
        List<ProductDTO> wishProductsList;
        try{
            wishProductsList = wishListService.getWishListForUser(userId);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error occurred while retrieving wish list products");
        }
        return Response.ok(wishProductsList, HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
