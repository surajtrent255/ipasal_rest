package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.List;


public class ProductDTO implements Comparable{

    private int productId;
    private String productName;
    private String unit;
    private float rate;
    private int categoryId;
    private String categoryName;
    private int availableItems; //number of available items in the inventory
    private int parentId; // parentId of the category
    private List<ImageDTO> images;
    private String shortDesc;
    private String highlights;
    private String description;
    private String entryDate;
    private int quantity;
    private boolean featured;
    private int userId;
    private int relatedProducts[];
    private List<ProductDTO> relatedProductDtos;
    private boolean newProduct;
    private float discountPercent;
    private float actualRate;
    private int merchantId[];
    private List<MerchantDTO> merchant;
    private List<ReviewDTO> reviewDtos;
    private int nosReview;
    private float avgRating;
    private List<CategoryDTO> ancestorCategories;
    private int totalSoldQuantity;
    private String productTags[];
    

    
	public String[] getProductTags() {
		return productTags;
	}

	public void setProductTags(String[] productTags) {
		this.productTags = productTags;
	}

	public float getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

	public int getNosReview() {
		return nosReview;
	}

	public void setNosReview(int nosReview) {
		this.nosReview = nosReview;
	}

	public List<ReviewDTO> getReviewDtos() {
		return reviewDtos;
	}

	public void setReviewDtos(List<ReviewDTO> reviewDtos) {
		this.reviewDtos = reviewDtos;
	}

	public List<MerchantDTO> getMerchant() {
		return merchant;
	}

	public void setMerchant(List<MerchantDTO> merchant) {
		this.merchant = merchant;
	}

	public int[] getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int[] merchantId) {
		this.merchantId = merchantId;
	}

	public float getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public float getActualRate() {
		return actualRate;
	}

	public void setActualRate(float actualRate) {
		this.actualRate = actualRate;
	}

	public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public List<ProductDTO> getRelatedProductDtos() {
		return relatedProductDtos;
	}

	public void setRelatedProductDtos(List<ProductDTO> relatedProductDtos) {
		this.relatedProductDtos = relatedProductDtos;
	}

	public int[] getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(int[] relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    @Override
    public String toString(){
        return super.toString();
    }

	@Override
	public int compareTo(Object o) {
		return (this.getProductId() < ((ProductDTO) o).getProductId() ? -1: (this.getProductId() == ((ProductDTO) o).getProductId() ? 0 : 1) );
	}

	public List<CategoryDTO> getAncestorCategories() {
		return ancestorCategories;
	}

	public void setAncestorCategories(List<CategoryDTO> ancestorCategories) {
		this.ancestorCategories = ancestorCategories;
	}

	/**
	 * @return the totalSoldQuantity
	 */
	public int getTotalSoldQuantity() {
		return totalSoldQuantity;
	}

	/**
	 * @param totalSoldQuantity the totalSoldQuantity to set
	 */
	public void setTotalSoldQuantity(int totalSoldQuantity) {
		this.totalSoldQuantity = totalSoldQuantity;
	}

}
