package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * Created by aevin on Feb 07, 2019
 **/
public class CategoryImageDTO {
    private Integer id;
    private Integer categoryId;
    private String imageName;
    private String oddImage;
    private String evenImage;
    private String bannerImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOddImage() {
        return oddImage;
    }

    public void setOddImage(String oddImage) {
        this.oddImage = oddImage;
    }

    public String getEvenImage() {
        return evenImage;
    }

    public void setEvenImage(String evenImage) {
        this.evenImage = evenImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }
}
