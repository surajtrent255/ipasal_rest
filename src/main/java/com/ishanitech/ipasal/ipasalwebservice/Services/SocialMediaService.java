package com.ishanitech.ipasal.ipasalwebservice.Services;

import java.util.List;

import com.ishanitech.ipasal.ipasalwebservice.dto.SocialMediaDTO;

/**
 * 
 * @author Tanchhowpa
 * Sep 4, 2019, 4:36:59 PM
 *
 */

public interface SocialMediaService {

	List<SocialMediaDTO> getAllSocialMedias();

	List<SocialMediaDTO> getAllActiveSocialMedias();

	Integer addSocialMedia(SocialMediaDTO socialMediaDTO);

	Integer updateSocialMedia(Integer socialMediaId, SocialMediaDTO socialMedia);

	SocialMediaDTO getSocialMediaById(Integer socialMediaId);

	int setSocialMediaActive(Integer socialMediaId);

	int setSocialMediaInactive(Integer socialMediaId);

}
