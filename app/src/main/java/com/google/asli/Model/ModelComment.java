package com.google.asli.Model;

public class ModelComment {
    String image;
    String user;
    String comment;
    String posotive;
    String negative;
    float rating;

    public ModelComment(String image, String user, String comment, String posotive, String negative, float rating) {
        this.image = image;
        this.user = user;
        this.comment = comment;
        this.posotive = posotive;
        this.negative = negative;
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPosotive() {
        return posotive;
    }

    public void setPosotive(String posotive) {
        this.posotive = posotive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
