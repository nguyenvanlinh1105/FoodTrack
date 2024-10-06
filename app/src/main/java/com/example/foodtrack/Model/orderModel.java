package com.example.foodtrack.Model;

import java.util.Date;

public class orderModel {
    protected String id, createdAt, status;
    protected int img, rateStat;

    public orderModel(String id, String createdAt, String status, int img, int rateStat) {
        this.id = id;
        this.createdAt = createdAt;
        this.status = status;
        this.img = img;
        this.rateStat = rateStat;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getRateStat() {
        return rateStat;
    }

    public void setRateStat(int rateStat) {
        this.rateStat = rateStat;
    }
}
