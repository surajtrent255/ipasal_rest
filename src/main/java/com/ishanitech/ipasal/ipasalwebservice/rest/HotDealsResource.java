package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.FileUtils.FileUploder;
import com.ishanitech.ipasal.ipasalwebservice.Services.HotDealsService;
import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealsDTO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aevin on Feb 05, 2019
 **/
@RequestMapping("api/v1/hot-deals")
@RestController
public class HotDealsResource {

    @Autowired
    FileUploder fileUploder;
    private HotDealsService hotDealsService;
    private Logger logger = LoggerFactory.getLogger(HotDealsResource.class);

    @Autowired
    public HotDealsResource(HotDealsService hotDealsService) {
        this.hotDealsService = hotDealsService;
    }

    //retrieving all active hot deals from database
    @GetMapping
    public Response<?> getAllActiveHotDeals(){
        List<HotDealsDTO> hotDealsList;
        try {
            hotDealsList = hotDealsService.getAllActiveHotDeals();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error retrieving hot deals data from database");
        }
        if (hotDealsList != null && hotDealsList.size() > 0){
            return Response.ok(hotDealsList, HttpStatus.OK.value(), "Success");
        }
        throw  new ResourceNotFoundException("No hot deals available in the database");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public Response<?> getAllHotDeals(){
        List<HotDealsDTO> hotDealsList;
        try {
            hotDealsList = hotDealsService.getAllHotDeals();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error retrieving hot deals data from database");
        }
        if (hotDealsList != null && hotDealsList.size() > 0){
            return Response.ok(hotDealsList, HttpStatus.OK.value(), "Success");
        }
        throw  new ResourceNotFoundException("No hot deals available in the database");
    }
    
    //adding hot deals data to the database
    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addHotDealsData(@RequestBody HotDealsDTO hotDealsDTO){
        Integer result = null;
    	try {
            result = hotDealsService.addHotDeal(hotDealsDTO);
            hotDealsService.addHotDealsImage(result, "blank.jpg");
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while adding the hot deals data");
        }
    	return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
    }
    
  //editing hot deals data to the database
    @RequestMapping(method = RequestMethod.PUT)
    public Response<?> editHotDealsData(@RequestBody HotDealsDTO hotDealsDTO){
        Integer result = null;
    	try {
            result = hotDealsService.editHotDeal(hotDealsDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while editing the hot deals data");
        }
    	return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    
    //deleting hot deals data from the database
    @RequestMapping(method = RequestMethod.DELETE, value= "/{hotDealId}")
    public Response<?> deleteHotDealsData(@PathVariable("hotDealId") Integer hotDealId){
    	try {
            hotDealsService.deleteHotDeal(hotDealId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Hot Deal has been removed successfully");
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while deleting the hot deals data");
        }
    }


    //updating the hot deals data in the database

    //uploading hot deals image
    @RequestMapping(method = RequestMethod.POST, value = "/upload/{hotDealId}")
    public Response<?> uploadHotDealImage(@RequestParam("file") MultipartFile imageFile,
                                          @RequestParam("fileName") String fileName,
                                          @PathVariable("hotDealId") Integer hotDealId){
        String fileReturned = "";
        if (imageFile != null) {
            try {
                fileReturned = fileUploder.saveUploadedFiles(Arrays.asList(imageFile), fileName);
                hotDealsService.editHotDealsImage(hotDealId, fileReturned);

                return Response.ok("Hot deal image is inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
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
