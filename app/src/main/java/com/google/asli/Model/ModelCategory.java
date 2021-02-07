package com.google.asli.Model;

public class ModelCategory {

    int id;
    String titlecategory;
    String image;

    public ModelCategory(int id, String titlecategory, String image) {
        this.id = id;
        this.titlecategory = titlecategory;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlecategory() {
        return titlecategory;
    }

    public void setTitlecategory(String titlecategory) {
        this.titlecategory = titlecategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
