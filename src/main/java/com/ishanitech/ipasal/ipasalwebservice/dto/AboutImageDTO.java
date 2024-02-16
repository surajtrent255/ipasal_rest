package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * Created by aevin on Feb 14, 2019
 **/
public class AboutImageDTO {
    private int id;
    private int about_id;
    private String bannerImage;
    private String storyImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAbout_id() {
        return about_id;
    }

    public void setAbout_id(int about_id) {
        this.about_id = about_id;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getStoryImage() {
        return storyImage;
    }

    public void setStoryImage(String storyImage) {
        this.storyImage = storyImage;
    }

    @Override
    public String toString() {
        return "AboutImageDTO{" +
                "id=" + id +
                ", about_id=" + about_id +
                ", bannerImage='" + bannerImage + '\'' +
                ", storyImage='" + storyImage + '\'' +
                '}';
    }
}
