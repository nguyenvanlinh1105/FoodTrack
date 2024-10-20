package com.example.foodtrack.Model;

import java.util.List;

public class ThanhPhoModel {
    private String idThanhPho;
    private String tenThanhPho;

    // Navigation properties
    private List<QuanModel> quans;

    // Getters and Setters
    public String getIdThanhPho() {
        return idThanhPho;
    }

    public void setIdThanhPho(String idThanhPho) {
        this.idThanhPho = idThanhPho;
    }

    public String getTenThanhPho() {
        return tenThanhPho;
    }

    public void setTenThanhPho(String tenThanhPho) {
        this.tenThanhPho = tenThanhPho;
    }
}