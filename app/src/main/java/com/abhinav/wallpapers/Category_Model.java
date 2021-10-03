package com.abhinav.wallpapers;

public class Category_Model {

    private String category;
    private String category_image_url;

    public Category_Model(String category, String category_image_url) {
        this.category = category;
        this.category_image_url = category_image_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }
}
