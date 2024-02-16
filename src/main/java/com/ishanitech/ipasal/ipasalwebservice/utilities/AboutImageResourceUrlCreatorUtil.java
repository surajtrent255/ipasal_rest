package com.ishanitech.ipasal.ipasalwebservice.utilities;

import com.ishanitech.ipasal.ipasalwebservice.dao.AboutDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.AboutDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.AboutImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by aevin on Feb 14, 2019
 **/
@Component
public class AboutImageResourceUrlCreatorUtil {
    private final String PICTURE_DIR = "Pictures/";

    @Autowired
    private HostDetailsUtil resolveHostAddress;

    public AboutDTO createAboutInfoWithImages(AboutDTO aboutDTO, AboutDAO aboutDAO){
        AboutImageDTO images = aboutDAO.getAboutImages();
        aboutDTO.setAboutImages(createAboutImageWithFullUrl(images));
        return aboutDTO;
    }

    public AboutImageDTO createAboutImageWithFullUrl(AboutImageDTO aboutImageDTO){
        AboutImageDTO aboutImage = new AboutImageDTO();
        aboutImage.setAbout_id(aboutImageDTO.getAbout_id());
        aboutImage.setBannerImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + aboutImageDTO.getBannerImage());
        aboutImage.setStoryImage(resolveHostAddress.getHostUrl() + PICTURE_DIR + aboutImageDTO.getStoryImage());

        return aboutImage;
    }
}
