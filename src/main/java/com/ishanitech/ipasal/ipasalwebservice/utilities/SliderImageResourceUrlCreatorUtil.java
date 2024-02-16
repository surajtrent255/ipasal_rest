package com.ishanitech.ipasal.ipasalwebservice.utilities;

import com.ishanitech.ipasal.ipasalwebservice.dao.SliderDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.SliderDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.SliderImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SliderImageResourceUrlCreatorUtil {
	private final String PICTURE_DIR = "Pictures/";
	
	@Autowired
	private HostDetailsUtil resolveHostAddress;
	
	public List<SliderDTO> createSliderWithImages(List<SliderDTO> sliderWithoutImage, SliderDAO sliderDAO) {
        List<SliderDTO> sliderWithImages = new ArrayList<>();
        sliderWithoutImage.forEach(slider -> {
            SliderImageDTO image = sliderDAO.getSliderImage(slider.getSliderId());
            slider.setSliderImage(createSliderImgWithFullUrl(image));
            sliderWithImages.add(slider);
        });
        return sliderWithImages;

    }

    private SliderImageDTO createSliderImgWithFullUrl(SliderImageDTO images) {
	    SliderImageDTO sliderImageDTO = new SliderImageDTO();
        sliderImageDTO.setImageName(resolveHostAddress.getHostUrl() + PICTURE_DIR + images.getImageName());

        return sliderImageDTO;
    }
}
