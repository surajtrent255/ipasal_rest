package com.ishanitech.ipasal.ipasalwebservice.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FilenameUtils;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 
 * @author Yoomes <yoomesbhujel@gmail.com>
 *
 */

@Component
public class FileUploder {
	private final Logger LOGGER = LoggerFactory.getLogger(FileUploder.class);
    @Value("${com.ipasal.fileUploadingDirectory}")
    private String UPLOADED_FOLDER;
    
    private static final int THUMBNAIL_MAX_WIDTH = 720;
    public String saveUploadedFiles(List<MultipartFile> files, String fileName) throws IOException {
        String returnFileName = null;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            //for unique filename
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileNameWithoutExt = FilenameUtils.removeExtension(file.getOriginalFilename());
            String newGeneratedFileName = fileNameWithoutExt + new SimpleDateFormat("yyyyMMMddHHmmss'." + extension + "'").format(new Date());
            
            //for unique filename ends
            
            Path path = Paths.get(UPLOADED_FOLDER +"/"+ newGeneratedFileName);
            LOGGER.info("COMPLETE PATH : " + path);
            LOGGER.info("FILE NAME : " + newGeneratedFileName);
            Files.write(path, bytes);
            returnFileName =  String.valueOf(path.getFileName());
            generateThumbnail(returnFileName);
        }
        return returnFileName;
    }

    /*
    * Resizing the category image for the index page view
    * */
    public String resizeUploadedFiles(String imageFileName, String type) throws IOException {
        /*
        * 1. Getting the original image from the disk
        * */
        BufferedImage originalImage = ImageIO.read(new File(UPLOADED_FOLDER+"/"+ imageFileName));

        /*
        * 2. Create temporary Image as per the type being provided
        * */
        BufferedImage tempImage;
        Image newResizedImage;
        if (type == "odd"){
            tempImage = new BufferedImage(720, 932, BufferedImage.TYPE_INT_RGB);
            newResizedImage = originalImage.getScaledInstance(720,932, Image.SCALE_SMOOTH);
        }else {
            tempImage = new BufferedImage(720, 660, BufferedImage.TYPE_INT_RGB);
            newResizedImage = originalImage.getScaledInstance(720,660, Image.SCALE_SMOOTH);
        }
        Graphics2D g2D = tempImage.createGraphics();
        g2D.drawImage(newResizedImage, 0, 0, null);
        g2D.dispose();
        String newImageName = type+"_"+imageFileName;
        File resizedImageFile = new File(UPLOADED_FOLDER+"/"+newImageName);
        ImageIO.write(tempImage, "JPG", resizedImageFile);
        LOGGER.info("The retrieved image file name is -> resized: "+resizedImageFile.getName());
        return newImageName;
    }

   public void generateThumbnail(String fileName) {
	   	try {
	   		/*
	   		 * Get Image From Disk And Get it's original width and height
	   		 */
	   		BufferedImage originalImage = ImageIO.read(new File(UPLOADED_FOLDER +"/"+ fileName));
	   		float originalWidth, originalHeight, calculatedThumbnailHeight;
	   		float expWidth, expHeight, newWidth, newHeight;
	   		originalWidth = originalImage.getWidth();
	   		originalHeight = originalImage.getHeight();
	   		expWidth = 284;
	   		expHeight = 379;
	   		newWidth = originalWidth;
	   		newHeight = originalHeight;

	   		// Checking if we need to scale the width
	   		if (originalWidth > expWidth){
	   		    // Scale width to fit
                newWidth = expWidth;

                // Scale height to maintain aspect ratio
                newHeight = (newWidth*originalHeight)/originalWidth;
            }
            // Check if we need to scale even with new height
            if (newHeight > expHeight){
                // Scale height to fit instead
                newHeight = expHeight;

                // Scale width to maintain aspect ratio
                newWidth = (newHeight*originalWidth)/originalHeight;
            }


	   		/*
	   		 * Calculate the thumbnail height based on original aspect ratio of image.
	   		 */
	   		calculatedThumbnailHeight = (originalHeight/originalWidth) * THUMBNAIL_MAX_WIDTH;
	   		
	   		
	   		/*
	   		 * Create Temporary Image
	   		 */
	   		BufferedImage temporaryImage = new BufferedImage((int)expWidth, (int) expHeight, BufferedImage.TYPE_INT_RGB);
	   		Image image = originalImage.getScaledInstance((int)expWidth, (int) expHeight, Image.SCALE_SMOOTH);
	   		temporaryImage.createGraphics().drawImage(image, 0, 0, null);
	   		
	   		/*
	   		 * Write temporary thumbnail image to disk
	   		 */
	   		ImageIO.write(temporaryImage, "JPG", new File(UPLOADED_FOLDER +"/thumb/" + fileName));
	   		LOGGER.info("HEIGHT->"+temporaryImage.getHeight()+" Width->"+temporaryImage.getWidth());
	   	} catch(IOException ioException) {
	   		
	   	}
   }

}
