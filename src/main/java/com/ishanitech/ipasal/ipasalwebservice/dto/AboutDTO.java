package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * Created by aevin on Feb 14, 2019
 **/
public class AboutDTO {
    private int id;
    private String storyMessage;
    private String quoteMessage;
    private AboutImageDTO aboutImages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoryMessage() {
        return storyMessage;
    }

    public void setStoryMessage(String storyMessage) {
        this.storyMessage = storyMessage;
    }

    public String getQuoteMessage() {
        return quoteMessage;
    }

    public void setQuoteMessage(String quoteMessage) {
        this.quoteMessage = quoteMessage;
    }

    public AboutImageDTO getAboutImages() {
        return aboutImages;
    }

    public void setAboutImages(AboutImageDTO aboutImages) {
        this.aboutImages = aboutImages;
    }

    @Override
    public String toString() {
        return "AboutDTO{" +
                "id=" + id +
                ", storyMessage='" + storyMessage + '\'' +
                ", quoteMessage='" + quoteMessage + '\'' +
                '}';
    }
}
