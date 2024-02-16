package com.ishanitech.ipasal.ipasalwebservice.dto;

/**
 * Created by aevin on Feb 05, 2019
 **/
public class HotDealsDTO {
    private Integer hotDealId;
    private Integer productId;
    private String productName;
    private Float oldRate;
    private Float newRate;
    private String finishDate;
    private Boolean hotDeal;
    private HotDealImageDTO hotDealImageDTO;


    public Integer getHotDealId() {
        return hotDealId;
    }

    public void setHotDealId(Integer hotDealId) {
        this.hotDealId = hotDealId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getOldRate() {
        return oldRate;
    }

    public void setOldRate(Float oldRate) {
        this.oldRate = oldRate;
    }

    public Float getNewRate() {
        return newRate;
    }

    public void setNewRate(Float newRate) {
        this.newRate = newRate;
    }


    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Boolean getHotDeal() {
        return hotDeal;
    }

    public void setHotDeal(Boolean hotDeal) {
        this.hotDeal = hotDeal;
    }

    public HotDealImageDTO getHotDealImageDTO() {
        return hotDealImageDTO;
    }

    public void setHotDealImageDTO(HotDealImageDTO hotDealImageDTO) {
        this.hotDealImageDTO = hotDealImageDTO;
    }

    @Override
    public String toString() {
        return "HotDealsDTO{" +
                "id=" + hotDealId +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", oldRate=" + oldRate +
                ", newRate=" + newRate +
                ", finishDate=" + finishDate +
                ", isHotDeal=" + hotDeal +
                '}';
    }

    
}
