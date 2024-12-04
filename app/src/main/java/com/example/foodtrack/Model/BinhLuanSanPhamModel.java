package com.example.foodtrack.Model;

import com.example.foodtrack.Model.API.NguoiDungAPIModel;

import java.sql.Timestamp;

public class BinhLuanSanPhamModel {
    private String idBinhLuan;
    private String idNguoiDung;
    private String idSanPham;
    private String noiDung;
    private Timestamp ngayBinhLuan;
    private NguoiDungAPIModel user;

    public NguoiDungAPIModel getUser() {
        return user;
    }

    public void setUser(NguoiDungAPIModel user) {
        this.user = user;
    }

    // Navigation properties
    private NguoiDungModel nguoiDung;
    private SanPhamModel sanPham;

    public String getIdBinhLuan() {
        return idBinhLuan;
    }

    public void setIdBinhLuan(String idBinhLuan) {
        this.idBinhLuan = idBinhLuan;
    }

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(String idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Timestamp getNgayBinhLuan() {
        return ngayBinhLuan;
    }

    public void setNgayBinhLuan(Timestamp ngayBinhLuan) {
        this.ngayBinhLuan = ngayBinhLuan;
    }

    public NguoiDungModel getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDungModel nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public SanPhamModel getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPhamModel sanPham) {
        this.sanPham = sanPham;
    }


}