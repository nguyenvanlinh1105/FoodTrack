package com.example.foodtrack.Model;

public class productModel {
    protected String Title, Price, description;
    protected int img;

    public productModel(String title, String price, String description, int img) {
        Title = title;
        Price = price;
        this.description = description;
        this.img = img;
    }

    public String getTitle() {
        return Title;
    }

    public String getPrice() {
        return Price;
    }

    public String getDescription() {
        return description;
    }

    public int getImg() {
        return img;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
