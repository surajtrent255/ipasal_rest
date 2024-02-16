package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.FileUtils.FileUploder;
import com.ishanitech.ipasal.ipasalwebservice.Services.OfferProductService;
import com.ishanitech.ipasal.ipasalwebservice.dto.OfferProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.BadRequestException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomFileSystemException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
@RequestMapping("api/v1/offer-product")
@RestController
public class OfferProductResource {

    @Autowired
    private FileUploder fileUploder;
    private OfferProductService offerProductService;
    private Logger logger = LoggerFactory.getLogger(OfferProductResource.class);

    @Autowired
    public OfferProductResource(OfferProductService offerProductService) {
        this.offerProductService = offerProductService;
    }

    //retrieving the offer products
    @GetMapping
    public Response<?> getOfferProductDetails(){
        List<OfferProductDTO> offerProductList;
        try {
            offerProductList = offerProductService.getOfferProducts();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error retrieving offer product details from database");
        }
        if (offerProductList != null && offerProductList.size() > 0){
            return Response.ok(offerProductList, HttpStatus.OK.value(), "Success");
        }
        throw new ResourceNotFoundException("No offer products data available in the database");
    }

    //adding the offer product
    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addOfferProductDetails(@RequestBody OfferProductDTO offerProductDTO){
        try {
            offerProductService.addOfferProduct(offerProductDTO);
            return Response.ok(offerProductDTO, HttpStatus.OK.value(), HttpStatus.OK.name());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Could not add offer product data to the database");
        }
    }


    //adding the offer product images
    @RequestMapping(method = RequestMethod.POST, value = "/upload/{offerId}")
    public Response<?> uploadOfferProductImage(@RequestParam("file") MultipartFile imageFile,
                                          @RequestParam("fileName") String fileName,
                                          @PathVariable("offerId") Integer offerId){
        String fileReturned = "";
        if (imageFile != null) {
            try {
                fileReturned = fileUploder.saveUploadedFiles(Arrays.asList(imageFile), fileName);
                offerProductService.addOfferImage(offerId, fileReturned);

                return Response.ok("Offer Product Image is inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new CustomFileSystemException(e.getMessage());
            }
        } else {
            throw new BadRequestException("Some information are missing in your request object!",
                    HttpStatus.BAD_REQUEST);
        }
    }



}
