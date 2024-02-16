package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * Created by aevin on Feb 05, 2019
 **/
public class OfferProductDTO {
    private int offerId;
    private String offerTitle;
    private String shortDetail;
    private int categoryId;
    private Boolean offer;
    private OfferProductImageDTO offerProductImageDTO;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getShortDetail() {
        return shortDetail;
    }

    public void setShortDetail(String shortDetail) {
        this.shortDetail = shortDetail;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public Boolean getOffer() {
        return offer;
    }

    public void setOffer(Boolean offer) {
        this.offer = offer;
    }

    public OfferProductImageDTO getOfferProductImageDTO() {
        return offerProductImageDTO;
    }

    public void setOfferProductImageDTO(OfferProductImageDTO offerProductImageDTO) {
        this.offerProductImageDTO = offerProductImageDTO;
    }
}
