package com.example.foodtrack.Model;

import java.sql.Timestamp;
import java.util.List;

public class SanPhamModel {
    private String idSanPham;
    private String idDanhMuc;
    private String tenSanPham;
    private String trangThai;
    private double giaTien;
    private int images;
    private String moTa;
    private int soLuongDaBan;
    private String donViTinh;
    private Timestamp ngayTao;
    private int trangThaiBinhLuan;

    private DanhMucModel danhMuc;
    private List<ChiTietDonHangModel> chiTietDonHangs;
    private List<BinhLuanSanPhamModel> binhLuans;
    private List<SanPhamYeuThichModel> sanPhamYeuThichs;


    public SanPhamModel(String tenSanPham, double giaTien, int images, String moTa) {
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
        this.images = images;
        this.moTa = moTa;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }

    public DanhMucModel getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMucModel danhMuc) {
        this.danhMuc = danhMuc;
    }

    public List<ChiTietDonHangModel> getChiTietDonHangs() {
        return chiTietDonHangs;
    }

    public void setChiTietDonHangs(List<ChiTietDonHangModel> chiTietDonHangs) {
        this.chiTietDonHangs = chiTietDonHangs;
    }

    public List<BinhLuanSanPhamModel> getBinhLuans() {
        return binhLuans;
    }

    public void setBinhLuans(List<BinhLuanSanPhamModel> binhLuans) {
        this.binhLuans = binhLuans;
    }

    public List<SanPhamYeuThichModel> getSanPhamYeuThichs() {
        return sanPhamYeuThichs;
    }

    public void setSanPhamYeuThichs(List<SanPhamYeuThichModel> sanPhamYeuThichs) {
        this.sanPhamYeuThichs = sanPhamYeuThichs;
    }

    public int getTrangThaiBinhLuan() {
        return trangThaiBinhLuan;
    }

    public void setTrangThaiBinhLuan(int trangThaiBinhLuan) {
        this.trangThaiBinhLuan = trangThaiBinhLuan;
    }
}
