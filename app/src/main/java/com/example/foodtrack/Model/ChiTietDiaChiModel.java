package com.example.foodtrack.Model;

public class ChiTietDiaChiModel {
    private String idDiaChi;
    private String ngoiSiDung;
    private String idPhuong;
    private String moTaDiaChi;

    // Navigation property
    private PhuongModel phuong;

    // Getters and Setters
    public String getIdDiaChi() {
        return idDiaChi;
    }

    public void setIdDiaChi(String idDiaChi) {
        this.idDiaChi = idDiaChi;
    }

    public String getNgoiSiDung() {
        return ngoiSiDung;
    }

    public void setNgoiSiDung(String ngoiSiDung) {
        this.ngoiSiDung = ngoiSiDung;
    }

    public String getIdPhuong() {
        return idPhuong;
    }

    public void setIdPhuong(String idPhuong) {
        this.idPhuong = idPhuong;
    }

    public String getMoTaDiaChi() {
        return moTaDiaChi;
    }

    public void setMoTaDiaChi(String moTaDiaChi) {
        this.moTaDiaChi = moTaDiaChi;
    }
}