package com.ishanitech.ipasal.ipasalwebservice.Services;

import com.ishanitech.ipasal.ipasalwebservice.dto.SliderDTO;

import java.util.List;

/**
 * Created by aevin on Feb, 2019
 **/
public interface SliderService {
    Integer addSlider(SliderDTO sliderDTO);
    List<SliderDTO> getAllActiveSliders();
    List<SliderDTO> getAllSliders();
    void updateSliderStatus(Integer id, Boolean sliderShow);
    void addSliderImage(Integer sliderId, String imageName);
    void deleteSlider(Integer sliderId);
}
