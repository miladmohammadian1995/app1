package com.google.asli.Model;

public class ModelFree {
    int id;
    String image;
    String title;
    String visit;
    String price;
    String label;
    String freeprice;
    float finalrating;

    public ModelFree(int id, String image, String title, String visit, String price, String label, String freeprice, float finalrating) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.visit = visit;
        this.price = price;
        this.label = label;
        this.freeprice = freeprice;
        this.finalrating = finalrating;
    }

    public float getFinalrating() {
        return finalrating;
    }

    public void setFinalrating(float finalrating) {
        this.finalrating = finalrating;
    }

    public String getFreeprice() {
        return freeprice;
    }

    public void setFreeprice(String freeprice) {
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

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
