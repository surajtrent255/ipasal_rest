package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.FileUtils.FileUploder;
import com.ishanitech.ipasal.ipasalwebservice.Services.SliderService;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.SliderDTO;
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
 * Created by aevin on Feb, 2019
 **/

@RequestMapping("api/v1/slider")
@RestController
public class SliderResources {
    private final Logger logger = LoggerFactory.getLogger(SliderResources.class);
    private SliderService sliderService;

    @Autowired
    FileUploder fileUploder;

    @Autowired
    public SliderResources(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    /**
     * Method to retrieve all the active sliders from the database
     * @return List<SliderDTO>
     */
    @GetMapping
    public Response<?> getAllActiveSliders() {
        List<SliderDTO> sliders;
        try {
            sliders = sliderService.getAllActiveSliders();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Could not get Sliders from database");
        }
        if (sliders != null && sliders.size() > 0) {
            return Response.ok(sliders, HttpStatus.OK.value(), "Success");
        }
        throw new ResourceNotFoundException("Currently there are no sliders available in the database.");
    }

    @GetMapping("/all")
    public Response<?> getAllSliders() {
        List<SliderDTO> sliders;
        try {
            sliders = sliderService.getAllSliders();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Could not get Sliders from database");
        }
        if (sliders != null && sliders.size() > 0) {
            return Response.ok(sliders, HttpStatus.OK.value(), "Success");
        }
        throw new ResourceNotFoundException("Currently there are no sliders available in the database.");
    }
    
    /**
     * Adding the slider data to the database
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addSliderData(@RequestBody SliderDTO sliderDTO) {
        Integer result = null;
        try {
            result = sliderService.addSlider(sliderDTO);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Error occurred while adding slider data.");
        }
        return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Response<?> updateSliderStatus(@PathVariable Integer id, @RequestParam("showSlider") Boolean sliderStatus) {
        try {
            sliderService.updateSliderStatus(id, sliderStatus);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Error occurred while updating slider data.");
        }
        return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/{sliderId}")
    public Response<?> uploadSliderImage(@RequestParam("file") MultipartFile imageFile,
                                         @RequestParam("fileName") String fileName,
                                         @PathVariable("sliderId") Integer sliderId) {
        String fileReturned = "";
        if (imageFile != null) {
            try {

                fileReturned = fileUploder.saveUploadedFiles(Arrays.asList(imageFile), fileName);
                sliderService.addSliderImage(sliderId, fileReturned);

                return Response.ok("Image is inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new CustomFileSystemException(e.getMessage());
            }
        } else {
            throw new BadRequestException("Some information are missing in your request object!",
                    HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{sliderId}")
    public Response<?> deleteSlider(@PathVariable("sliderId") Integer sliderId){
    	try {
            sliderService.deleteSlider(sliderId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Slider has been removed successfully");
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Error while deleting the slider data");
        }
    }

}
