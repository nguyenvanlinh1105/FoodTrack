package com.example.foodtrack.Model;

import java.util.List;

public class QuanModel {
    private String idQuan;
    private String tenQuan;
    private String idThanhPho;

    // Navigation properties
    private ThanhPhoModel thanhPho;
    private List<PhuongModel> phuongs;

    // Getters and Setters
    public String getIdQuan() {
        return idQuan;
    }

    public void setIdQuan(String idQuan) {
        this.idQuan = idQuan;
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    public String getIdThanhPho() {
        return idThanhPho;
    }

    public void setIdThanhPho(String idThanhPho) {
        this.idThanhPho = idThanhPho;
    }
}