package com.example.foodtrack.Model;

public class OrderAPIModel {
    protected String id, createdAt, status, name, price;
    protected int  rateStat, qty;
    protected String img;

    public OrderAPIModel(String id, String createdAt, String name, String status, String img, int rateStat, String price, int qty) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.status = status;
        this.img = img;
        this.rateStat = rateStat;
        this.price = price;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRateStat() {
        return rateStat;
    }

    public void setRateStat(int rateStat) {
        this.rateStat = rateStat;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
