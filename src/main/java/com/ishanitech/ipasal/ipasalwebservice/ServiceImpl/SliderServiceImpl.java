package com.ishanitech.ipasal.ipasalwebservice.ServiceImpl;

import com.ishanitech.ipasal.ipasalwebservice.Services.DbService;
import com.ishanitech.ipasal.ipasalwebservice.Services.SliderService;
import com.ishanitech.ipasal.ipasalwebservice.dao.SliderDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.SliderDTO;
import com.ishanitech.ipasal.ipasalwebservice.utilities.SliderImageResourceUrlCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aevin on Feb, 2019
 **/
@Service
public class SliderServiceImpl implements SliderService {

    @Autowired
    SliderImageResourceUrlCreatorUtil imageResourceUrlCreatorUtil;

    private DbService dbService;
    @Autowired
    public SliderServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }


    @Override
    public Integer addSlider(SliderDTO sliderDTO) {
        if (dbService !=null) {
            SliderDAO sliderDAO = dbService.getDao(SliderDAO.class);
            return sliderDAO.addSlider(sliderDTO);
        }else {
            System.out.println("DbService is null");
            return null;
        }
    }

    @Override
    public List<SliderDTO> getAllActiveSliders() {
        SliderDAO sliderDAO = dbService.getDao(SliderDAO.class);
        List<SliderDTO> sliderData = sliderDAO.getAllActiveSlider();
        if (sliderData != null && sliderData.size() >0){
            List<SliderDTO> sliderWithImages = imageResourceUrlCreatorUtil.createSliderWithImages(sliderData, sliderDAO);
            return sliderWithImages;
        }
            return sliderData;

    }
    
	@Override
	public List<SliderDTO> getAllSliders() {
		SliderDAO sliderDAO = dbService.getDao(SliderDAO.class);
		List<SliderDTO> sliderData = sliderDAO.getAllSliders();
		return sliderData;
	}

    @Override
    public void updateSliderStatus(Integer id, Boolean sliderShow) {
        SliderDAO sliderDAO = dbService.getDao(SliderDAO.class);
        sliderDAO.updateSliderStatus(id, sliderShow);
    }

    @Override
    public void addSliderImage(Integer sliderId, String imageName) {
        SliderDAO sliderDAO = dbService.getDao(SliderDAO.class);
        sliderDAO.addSliderImage(sliderId, imageName);
    }
   
	@Override
	public void deleteSlider(Integer sliderId) {
		SliderDAO sliderDAO = dbService.getDao(SliderDAO.class);
		sliderDAO.deleteSlider(sliderId);		
	}

}
