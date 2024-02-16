package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.SocialMediaService;
import com.ishanitech.ipasal.ipasalwebservice.dao.SocialMediaDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.SocialMediaDTO;

/**
 * 
 * @author Tanchhowpa
 * Sep 4, 2019, 4:40:03 PM
 *
 */

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

	Logger logger = LoggerFactory.getLogger(SocialMediaServiceImpl.class);
	
	
	private DbService dbService;
	
	@Autowired
	public SocialMediaServiceImpl(DbService dbService) {
		super();
		this.dbService = dbService;
	}

	@Override
	public List<SocialMediaDTO> getAllSocialMedias() {
		SocialMediaDAO socialMediaDAO = dbService.getDao(SocialMediaDAO.class);
		return socialMediaDAO.getAllSocialMedias();
	}

	@Override
	public List<SocialMediaDTO> getAllActiveSocialMedias() {
		SocialMediaDAO socialMediaDAO = dbService.getDao(SocialMediaDAO.class);
		return socialMediaDAO.getAllActiveSocialMedias();
	}

	@Override
	public Integer addSocialMedia(SocialMediaDTO socialMediaDTO) {
		SocialMediaDAO socialMediaDAO = dbService.getDao(SocialMediaDAO.class);
		return socialMediaDAO.addSocialMedia(socialMediaDTO);
	}

	@Override
	public Integer updateSocialMedia(Integer socialMediaId, SocialMediaDTO socialMedia) {
		SocialMediaDAO socialMediaDAO = dbService.getDao(SocialMediaDAO.class);
		return socialMediaDAO.updateSocialMedia(socialMediaId, socialMedia);
	}

	@Override
	public SocialMediaDTO getSocialMediaById(Integer socialMediaId) {
		SocialMediaDAO socialMediaDAO = dbService.getDao(SocialMediaDAO.class);
		return socialMediaDAO.getSocialMediaById(socialMediaId);
	}

	@Override
	public int setSocialMediaActive(Integer socialMediaId) {
		SocialMediaDAO socialMediaDAO = dbService.getDao(SocialMediaDAO.class);
		return socialMediaDAO.setSocialMediaActive(socialMediaId);
	}

	@Override
	public int setSocialMediaInactive(Integer socialMediaId) {
		SocialMediaDAO socialMediaDAO = dbService.getDao(SocialMediaDAO.class);
		return socialMediaDAO.setSocialMediaInactive(socialMediaId);
	}

}
