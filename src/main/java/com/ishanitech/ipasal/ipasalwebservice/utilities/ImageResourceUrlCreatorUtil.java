package com.ishanitech.ipasal.ipasalwebservice.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ishanitech.ipasal.ipasalwebservice.dao.ProductDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ImageDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ImageType;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
@Component
public class ImageResourceUrlCreatorUtil {
	private final String PICTURE_DIR = "Pictures/";
	
	@Autowired
	private HostDetailsUtil resolveHostAddress;
	
	public List<ProductDTO> createProductWithImages(List<ProductDTO> productWithoutImg, ProductDAO productDAO, ImageType imageType) {
        List<ProductDTO> productsWithImages = new ArrayList<>();
        productWithoutImg.forEach(product -> {
            List<ImageDTO> images = productDAO.getProductImages(product.getProductId());
            product.setImages(createImgWithFullUrl(images, imageType));
            productsWithImages.add(product);
        });
        return productsWithImages;
    }


    public List<ImageDTO> createImgWithFullUrl(List<ImageDTO> images, ImageType imageType) {
        List<ImageDTO> fullImage = new ArrayList<>();
        images.forEach(imageOld -> {
            ImageDTO image = imageOld;
            if(imageType == ImageType.ORIGINAL) {
            	image.setImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + image.getImage());
            } else if(ImageType.THUMBNAIL == imageType) {
            	image.setImage(resolveHostAddress.getHostUrl() + PICTURE_DIR +"thumb/" + image.getImage());
            }
            
            fullImage.add(image);
        });
        return fullImage;
    }
}
