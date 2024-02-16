package com.ishanitech.ipasal.ipasalwebservice.dao;

import com.ishanitech.ipasal.ipasalwebservice.dto.AboutDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.AboutImageDTO;
import org.skife.jdbi.v2.sqlobject.*;

/**
 * Created by aevin on Feb 14, 2019
 **/
public interface AboutDAO{
//    Inserting about data in the database
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO about_info(story_message, quote_message) VALUES(:storyMessage, :quoteMessage)")
    public Integer addAboutInfo(@BindBean AboutDTO aboutDTO);

//    Retrieve about info for the view
    @SqlQuery("SELECT * FROM about_info")
    public AboutDTO getAboutData();

//    Update About Info
//    @SqlUpdate("Update about_info SET story_message = :storyMessage, quote_message = :quoteMessage")
//    public void updateAboutInfo(@Bind("storyMessage") String storyMessage, @Bind("quoteMessage") String quoteMessage);

//    Adding about image to the database
    @SqlUpdate("INSERT INTO about_image(about_id, banner_image, story_image) VALUES(:aboutId, :bannerImage, :storyImage)")
    public void addAboutImages(@Bind("aboutId") Integer aboutId, @Bind("bannerImage") String bannerImage, @Bind("storyImage") String storyImage);

//    Retrieve about images for display
    @SqlQuery("SELECT * FROM about_image")
    public AboutImageDTO getAboutImages();

    @SqlUpdate("Update about_info SET story_message = :storyMessage, quote_message = :quoteMessage where id = 1")
	public Integer updateAboutInfo(@BindBean AboutDTO aboutDTO);

    @SqlUpdate("UPDATE about_image SET banner_image = :bannerImage where id = 1")
	public void addAboutBannerImage(@Bind("bannerImage") String bannerImage);

    @SqlUpdate("UPDATE about_image SET story_image = :storyImage where id = 1")
	public void addAboutStoryImage(@Bind("storyImage") String storyImage);


}
