package com.ishanitech.ipasal.ipasalwebservice.dto;


/**
 * Created by aevin on Feb, 2019
 **/
public class SliderDTO {
    private int sliderId;
    private String textMain;
    private String textSecondary;
    private SliderImageDTO sliderImage;
    private Integer categoryId;
    private String categoryName;
    private boolean showSlider;

    public int getSliderId() {
        return sliderId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setSliderId(int id) {
        this.sliderId = id;
    }

    public String getTextMain() {
        return textMain;
    }

    public void setTextMain(String textMain) {
        this.textMain = textMain;
    }

    public String getTextSecondary() {
        return textSecondary;
    }

    public void setTextSecondary(String textSecondary) {
        this.textSecondary = textSecondary;
    }

    public SliderImageDTO getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(SliderImageDTO sliderImage) {
        this.sliderImage = sliderImage;
    }

    public boolean isShowSlider() {
        return showSlider;
    }

    public void setShowSlider(boolean showSlide) {
        this.showSlider = showSlide;
    }
    
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

    @Override
    public String toString() {
        return "SliderDTO{" +
                "id=" + sliderId +
                ", textMain='" + textMain + '\'' +
                ", textSecondary='" + textSecondary + '\'' +
                ", image='" + sliderImage + '\'' +
                ", showSlide=" + showSlider +
                '}';
    }


}
