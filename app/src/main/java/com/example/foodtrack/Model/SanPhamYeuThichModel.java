package com.example.foodtrack.Model;

import java.sql.Timestamp;

public class SanPhamYeuThichModel {
    private String idYeuThich;
    private String idNguoiDung;
    private String idSanPham;
    private Timestamp ngayCapNhat;

    // Navigation properties
    private NguoiDungModel nguoiDung;
    private SanPhamModel sanPham;

    public String getIdYeuThich() {
        return idYeuThich;
    }

    public void setIdYeuThich(String idYeuThich) {
        this.idYeuThich = idYeuThich;
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

    public Timestamp getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(Timestamp ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
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