package com.example.foodtrack.Model;

import java.util.List;

public class PhuongModel {
    private String idPhuong;
    private String tenPhuong;
    private String idQuan;

    // Navigation properties
    private QuanModel quan;
    private List<ChiTietDiaChiModel> chiTietDiaChis;

    // Getters and Setters
    public String getIdPhuong() {
        return idPhuong;
    }

    public void setIdPhuong(String idPhuong) {
        this.idPhuong = idPhuong;
    }

    public String getTenPhuong() {
        return tenPhuong;
    }

    public void setTenPhuong(String tenPhuong) {
        this.tenPhuong = tenPhuong;
    }

    public String getIdQuan() {
        return idQuan;
    }

    public void setIdQuan(String idQuan) {
        this.idQuan = idQuan;
    }
}