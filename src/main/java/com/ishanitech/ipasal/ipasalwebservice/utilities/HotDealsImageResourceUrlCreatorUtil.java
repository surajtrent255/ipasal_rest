package com.ishanitech.ipasal.ipasalwebservice.utilities;

import com.ishanitech.ipasal.ipasalwebservice.dao.HotDealsDAO;
import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealImageDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.HotDealsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HotDealsImageResourceUrlCreatorUtil {
	private final String PICTURE_DIR = "Pictures/";
	
	@Autowired
	private HostDetailsUtil resolveHostAddress;
	
	public List<HotDealsDTO> createHotDealsWithImages(List<HotDealsDTO> hotDealsWithoutImages, HotDealsDAO hotDealsDAO) {
        List<HotDealsDTO> hotDealsWithImages = new ArrayList<>();
        hotDealsWithoutImages.forEach(hotDeal -> {
            HotDealImageDTO image = hotDealsDAO.getHotDealsImage(hotDeal.getHotDealId());
            hotDeal.setHotDealImageDTO(createSliderImgWithFullUrl(image));
            hotDealsWithImages.add(hotDeal);
        });
        return hotDealsWithImages;

    }

    private HotDealImageDTO createSliderImgWithFullUrl(HotDealImageDTO images) {
	    HotDealImageDTO hotDealImageDTO = new HotDealImageDTO();
	    hotDealImageDTO.setId(images.getId());
        hotDealImageDTO.setImageName(resolveHostAddress.getHostUrl() + PICTURE_DIR + images.getImageName());

        return hotDealImageDTO;
    }
}
