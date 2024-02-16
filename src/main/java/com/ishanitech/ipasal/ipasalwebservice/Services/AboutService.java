package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.AboutDTO;

/**
 * Created by aevin on Feb 14, 2019
 **/
public interface AboutService {
    Integer addAboutInfo(AboutDTO aboutDTO);
    AboutDTO getAboutInfo();
    void addAboutImages(Integer aboutId, String bannerImage, String storyImage);
	Integer updateAboutInfo(AboutDTO aboutDTO);
	void addAboutBannerImage(String bannerImage);
	void addAboutStoryImage(String storyImage);
}
