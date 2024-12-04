package com.example.foodtrack.Model.API;

import com.example.foodtrack.Model.BinhLuanSanPhamModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangModel;
import com.example.foodtrack.Model.DanhMucModel;
import com.example.foodtrack.Model.SanPhamYeuThichModel;

import java.sql.Timestamp;
import java.util.List;

public class SanPhamAPIModel {

    private String idSanPham;
    private String idDanhMuc;
    private String tenSanPham;
    private String trangThai;
    private double giaTien;
    private String images;
    private String moTa;
    private int soluongBH;
    private String donViTinh;
    private Timestamp ngayTao;
    private int trangThaiBinhLuan;
    private int soLuongDat;
    private String idDonHang ;

    private DanhMucModel danhMuc;
    private List<ChiTietDonHangAPIModel> chiTietDonHangs;
    private List<BinhLuanSanPhamModel> binhLuans;
    private List<SanPhamYeuThichModel> sanPhamYeuThichs;


    public SanPhamAPIModel(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public SanPhamAPIModel(String tenSanPham, double giaTien, String images, String moTa) {
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
        this.images = images;
        this.moTa = moTa;
    }


    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public int getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(int soLuongDat) {
        this.soLuongDat = soLuongDat;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoluongBH() {
        return soluongBH;
    }

    public void setSoluongBH(int soluongBH) {
        this.soluongBH = soluongBH;
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

    public List<ChiTietDonHangAPIModel> getChiTietDonHangs() {
        return chiTietDonHangs;
    }

    public void setChiTietDonHangs(List<ChiTietDonHangAPIModel> chiTietDonHangs) {
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

