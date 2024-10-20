package com.example.foodtrack.Model;

import java.sql.Date;
import java.util.List;

public class DanhMucModel {
    private String idDanhMuc;
    private String tenDanhMuc;
    private String moTa;
    private Date ngayTao;

    // Navigation properties
    private List<SanPhamModel> sanPhams;

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public List<SanPhamModel> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(List<SanPhamModel> sanPhams) {
        this.sanPhams = sanPhams;
    }
}