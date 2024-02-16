package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.FileUtils.FileUploder;
import com.ishanitech.ipasal.ipasalwebservice.Services.AboutService;
import com.ishanitech.ipasal.ipasalwebservice.dto.AboutDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.BadRequestException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomFileSystemException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * Created by aevin on Feb 14, 2019
 **/
@RequestMapping("api/v1/about")
@RestController
public class AboutResource {

    private AboutService aboutService;
    private final Logger logger = LoggerFactory.getLogger(AboutResource.class);

    @Autowired
    FileUploder fileUploder;

    @Autowired
    public AboutResource(AboutService aboutService) {
        this.aboutService = aboutService;
    }

//    Retrieving about information from the database
    @GetMapping
    public Response<?> getAboutInfo(){
        AboutDTO aboutInfo;
        try{
            aboutInfo = aboutService.getAboutInfo();
            
        } catch(NullPointerException nex) {
        	throw new CustomSqlException("About Us information not found!");
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException(e.getLocalizedMessage());
        }
        {
            return Response.ok(Arrays.asList(aboutInfo), HttpStatus.OK.value(), HttpStatus.OK.name());
        }
    }

//    Adding about information to the database
    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addAboutInfo(@RequestBody AboutDTO aboutDTO){
        Integer aboutId = null;
        try{
            aboutId = aboutService.addAboutInfo(aboutDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomSqlException("Could not add about information to the database");
        }
        return Response.ok(aboutId, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

//  Updating about information to the database
  @RequestMapping(method = RequestMethod.PUT)
  public Response<?> updateAboutInfo(@RequestBody AboutDTO aboutDTO){
      Integer aboutId = null;
      try{
          aboutId = aboutService.updateAboutInfo(aboutDTO);
      }catch (Exception e){
          logger.error(e.getMessage());
          throw new CustomSqlException("Could not update about information to the database");
      }
      return Response.ok(aboutId, HttpStatus.OK.value(), HttpStatus.OK.name());
  }
    
//    Adding about Images to the database
    @RequestMapping(method = RequestMethod.POST, value = "/upload/{aboutId}")
    public Response<?> addAboutImages(@RequestParam("file") MultipartFile[] imageFile,
                                      @RequestParam("fileName") String[] fileNames,
                                      @PathVariable("aboutId") Integer aboutId){
        String bannerImage = "";
        String storyImage = "";
        if (imageFile != null && imageFile.length >0){
            try{
                for (int i = 0; i < imageFile.length; i++){
                    MultipartFile file = imageFile[i];
                    if (i ==0){
                        bannerImage = fileUploder.saveUploadedFiles(Arrays.asList(file), fileNames[i]);
                    }else {
                        storyImage = fileUploder.saveUploadedFiles(Arrays.asList(file), fileNames[i]);
                    }
                }
                aboutService.addAboutImages(aboutId, bannerImage, storyImage);
                return Response.ok("About Image are inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
            } catch (Exception e) {
                throw new CustomFileSystemException(e.getMessage());
            }
        } else {
            throw new BadRequestException("Some information are missing in your request object!",
                    HttpStatus.BAD_REQUEST);
        }
    }
//  Adding about Banner Image to the database
  @RequestMapping(method = RequestMethod.POST, value = "/upload/bannerImage")
  public Response<?> addAboutBannerImage(@RequestParam("file") MultipartFile imageFile,
                                    @RequestParam("fileName") String fileName){
      String bannerImage = "";
      if (imageFile != null){
          try{
                  MultipartFile file = imageFile;
       
                      bannerImage = fileUploder.saveUploadedFiles(Arrays.asList(file), fileName);
              aboutService.addAboutBannerImage(bannerImage);
              return Response.ok("About Banner Image is inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
          } catch (Exception e) {
              logger.error(e.getMessage());
              throw new CustomFileSystemException(e.getMessage());
          }
      } else {
          throw new BadRequestException("Some information are missing in your request object!",
                  HttpStatus.BAD_REQUEST);
      }
  }
  //About Banner Image addition ends
    
//Adding about Story Image to the database
@RequestMapping(method = RequestMethod.POST, value = "/upload/storyImage")
public Response<?> addAboutStoryImage(@RequestParam("file") MultipartFile imageFile,
                                  @RequestParam("fileName") String fileName){
    String storyImage = "";
    if (imageFile != null){
        try{
                MultipartFile file = imageFile;
     
                    storyImage = fileUploder.saveUploadedFiles(Arrays.asList(file), fileName);
            aboutService.addAboutStoryImage(storyImage);
            return Response.ok("About Story Image is inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomFileSystemException(e.getMessage());
        }
    } else {
        throw new BadRequestException("Some information are missing in your request object!",
                HttpStatus.BAD_REQUEST);
    }
}
//About Story Image addition ends
}
