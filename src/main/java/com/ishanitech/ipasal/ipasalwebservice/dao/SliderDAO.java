package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.SliderDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.SliderImageDTO;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

/**
 * Created by aevin on Feb, 2019
 **/
public interface SliderDAO {

    //Inserting the slider data to the database
    @GetGeneratedKeys
    @SqlUpdate("Insert into slider(text_main, text_secondary, category_id, show_slider) values(:textMain, :textSecondary, :categoryId, :showSlider)")
    public Integer addSlider(@BindBean SliderDTO sliderDTO);

    //Retrieving all the active sliders from the database
    @SqlQuery("Select * from slider WHERE show_slider = true ORDER BY rand() LIMIT 3")
    public List<SliderDTO> getAllActiveSlider();
    
    @SqlQuery("Select s.* , c.category_name from slider s inner join category c on s.category_id = c.category_id")
    public List<SliderDTO> getAllSliders();

    //Updating the slider image show/hide status
    @SqlUpdate("Update slider set show_slider = :sliderStatus where slider_id = :sliderId")
    public void updateSliderStatus(@Bind("sliderId") Integer sliderId, @Bind("sliderStatus") Boolean showSlide);


    //Saving the slider image to the database
    @SqlUpdate("Insert into slider_image(slider_id, image_name) VALUES(:sliderId, :imageName)")
    void addSliderImage(@Bind("sliderId") Integer sliderId, @Bind("imageName") String imageName);

    //Retrieving the slider image from the database using the sliderId
    @SqlQuery("SELECT * FROM slider_image WHERE slider_id = :sliderId")
    SliderImageDTO getSliderImage(@Bind("sliderId") Integer sliderId);
    
    @SqlUpdate("DELETE FROM slider where slider_id = :sliderId")
    void deleteSlider(@Bind("sliderId") Integer sliderId);
    

}
