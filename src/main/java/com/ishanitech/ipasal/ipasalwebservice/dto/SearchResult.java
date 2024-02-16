package com.ishanitech.ipasal.ipasalwebservice.dto;

import java.util.List;

public class SearchResult {

    private List<ProductDTO> products;
    private List<CategoryDTO> categories;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
