package com.ishanitech.ipasal.ipasalwebservice.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import com.ishanitech.ipasal.ipasalwebservice.dto.SocialMediaDTO;

/**
 * 
 * @author Tanchhowpa
 * Sep 4, 2019, 4:52:05 PM
 *
 */
public interface SocialMediaDAO {

	//Gets all the Social medias created
	@SqlQuery("SELECT * FROM social_tools")
	List<SocialMediaDTO> getAllSocialMedias();

	
	//Gets all active social medias
	@SqlQuery("SELECT * FROM social_tools WHERE active = 1")
	List<SocialMediaDTO> getAllActiveSocialMedias();


	//Adds new social media
	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO social_tools(social_name, social_icon, social_link) VALUES(:socialName, :socialIcon, :socialLink)")
	Integer addSocialMedia(@BindBean SocialMediaDTO socialMediaDTO);


	//Update the social media details
	@SqlUpdate("UPDATE social_tools SET social_name = :socialName, social_icon = :socialIcon, social_link = :socialLink WHERE social_media_id= :sId") 
	Integer updateSocialMedia(@Bind("sId") Integer socialMediaId ,@BindBean SocialMediaDTO socialMedia);

	//Get social media details from its id
	@SqlQuery("SELECT * FROM social_tools WHERE social_media_id=:socialMediaId")
	SocialMediaDTO getSocialMediaById(@Bind("socialMediaId")Integer socialMediaId);

	//Sets the social media to active
	@SqlUpdate("UPDATE social_tools SET active = 1 WHERE social_media_id=:socialMediaId")
	int setSocialMediaActive(@Bind("socialMediaId")Integer socialMediaId);

	//Sets the social media to inactive
	@SqlUpdate("UPDATE social_tools SET active = 0 WHERE social_media_id=:socialMediaId")
	int setSocialMediaInactive(@Bind("socialMediaId") Integer socialMediaId);
	
}
