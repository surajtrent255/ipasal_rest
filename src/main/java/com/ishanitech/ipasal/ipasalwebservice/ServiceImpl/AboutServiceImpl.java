package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.AboutService;
import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.dao.AboutDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.AboutDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.AboutImageResourceUrlCreatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aevin on Feb 14, 2019
 **/
@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    AboutImageResourceUrlCreatorUtil aboutImageResourceUrlCreatorUtil;

    private DbService dbService;
    public AboutServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    private Logger logger = LoggerFactory.getLogger(AboutServiceImpl.class);

    @Override
    public Integer addAboutInfo(AboutDTO aboutDTO) {
        if (dbService != null){
            AboutDAO aboutDAO = dbService.getDao(AboutDAO.class);
            return aboutDAO.addAboutInfo(aboutDTO);
        } else {
            logger.error("DbService is null");
            return null;
        }
    }

    @Override
    public AboutDTO getAboutInfo() {
        AboutDAO aboutDAO = dbService.getDao(AboutDAO.class);
        AboutDTO aboutDetails = aboutDAO.getAboutData();
        AboutDTO aboutInfoWithImage = aboutImageResourceUrlCreatorUtil.createAboutInfoWithImages(aboutDetails, aboutDAO);
        return aboutInfoWithImage;
    }

    @Override
    public void addAboutImages(Integer aboutId, String bannerImage, String storyImage) {
        AboutDAO aboutDAO = dbService.getDao(AboutDAO.class);
        aboutDAO.addAboutImages(aboutId, bannerImage, storyImage);
    }

	@Override
	public Integer updateAboutInfo(AboutDTO aboutDTO) {
		if (dbService != null){
            AboutDAO aboutDAO = dbService.getDao(AboutDAO.class);
            return aboutDAO.updateAboutInfo(aboutDTO);
        }else {
            logger.error("DbService is null");
            return null;
        }
	}

	@Override
	public void addAboutBannerImage(String bannerImage) {
		AboutDAO aboutDAO = dbService.getDao(AboutDAO.class);
		aboutDAO.addAboutBannerImage(bannerImage);
		
	}

	@Override
	public void addAboutStoryImage(String storyImage) {
		AboutDAO aboutDAO = dbService.getDao(AboutDAO.class);
		aboutDAO.addAboutStoryImage(storyImage);
		
	}
}
