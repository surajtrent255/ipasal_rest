package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    private int categoryId;
    private String categoryName;
    private int parentId;
    private CategoryImageDTO image;
    private boolean featured;
    private boolean offered;
    private List<CategoryDTO> childCategories;
    
    public CategoryDTO() {
    	this.childCategories = new ArrayList<>();
    }
    
    public CategoryDTO(int categoryId, String categoryName, int parentId, CategoryImageDTO categoryImageDTO) {
    	this.categoryId = categoryId;
    	this.categoryName = categoryName;
    	this.parentId = parentId;
    	this.image = categoryImageDTO;
    	this.childCategories = new ArrayList<>();
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

    public void setFeatured(boolean featured){
        this.featured = featured;
    }

    public boolean isFeatured() {
        return featured;
    }

    public CategoryImageDTO getImage() {
        return image;
    }

    public void setImage(CategoryImageDTO image) {
        this.image = image;
    }
    
    public void addChildCategory(CategoryDTO childCategory) {
    	if(!this.childCategories.contains(childCategory)) {
    		this.childCategories.add(childCategory);
    	}
    }

	public List<CategoryDTO> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(List<CategoryDTO> childCategories) {
		this.childCategories = childCategories;
	}

	public boolean isOffered() {
		return offered;
	}

	public void setOffered(boolean offered) {
		this.offered = offered;
	}
	@Override
    public String toString() {
        return "{" +
            " categoryId='" + getCategoryId() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", featured='" + isFeatured() + "'" +
            ", offered='" + isOffered() + "'"+
            "}";
    }
}
