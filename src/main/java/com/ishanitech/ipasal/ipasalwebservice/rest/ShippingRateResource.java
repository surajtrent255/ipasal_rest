package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.Services.ShippingRateService;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.ShippingRateDTO;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 'Azens Eklak'
 * email: azens1995@gmail.com
 * created on Mar 01, 2019
 * since 2017
 **/
@RequestMapping("api/v1/shipping-rate")
@RestController
public class ShippingRateResource {

    private ShippingRateService shippingRateService;
    private Logger logger = LoggerFactory.getLogger(ShippingRateResource.class);

    @Autowired
    public ShippingRateResource(ShippingRateService shippingRateService) {
        this.shippingRateService = shippingRateService;
    }

    /*
    * Adding shipping rate information
    * */
    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addShippingRateInfo(@RequestBody ShippingRateDTO shippingRateDTO){
        Integer shippingId = null;
        try{
            shippingId = shippingRateService.addShippingInfo(shippingRateDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while adding the shipping rate information to the database.");
        }
        return Response.ok(shippingId, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Retrieving shipping rate with the given location
    * */
    @RequestMapping(method = RequestMethod.GET, value = "/{location}")
    public Response<?> getShippingRateInfoForLocation(@PathVariable String location){
        ShippingRateDTO shippingRateDTO;
        try{
            shippingRateDTO = shippingRateService.getShippingInfo(location);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while retrieving the shipping rate information for "+location);
        }
        return Response.ok(shippingRateDTO, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Retrieving all shipping rates information
    * */
    @GetMapping
    public Response<?> getAllShippingRateInfoForLocation(){
        List<ShippingRateDTO> shippingRateInfoList;
        try{
            shippingRateInfoList = shippingRateService.getAllShippingInfo();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while retrieving shipping rate information");
        }
        return Response.ok(shippingRateInfoList, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    /*
    * Updating shipping rate information providing the id and amount
    * */



}
