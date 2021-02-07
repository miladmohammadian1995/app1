package com.google.asli.Model;

public class ModelSearch {
    int id;
    String image;
    String title;
    String price;
    String cat;
    String label;
    String freeprice;

    public ModelSearch(int id, String image, String title, String price, String cat, String label, String freeprice) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.cat = cat;
        this.label = label;
        this.freeprice = freeprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFreeprice() {
        return freeprice;
    }

    public void setFreeprice(String freeprice) {
        this.freeprice = freeprice;
    }
}
