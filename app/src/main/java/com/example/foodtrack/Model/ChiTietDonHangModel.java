package com.example.foodtrack.Model;

public class ChiTietDonHangModel {
    private String idChiTietDonHang;
    private String idSanPham;
    private String idDonHang;
    private String nhanVienXuLy;
    private int soLuongDat;

    // Navigation properties
    private DonHangModel donHang;
    private SanPhamModel sanPham;

    public String getIdChiTietDonHang() {
        return idChiTietDonHang;
    }

    public void setIdChiTietDonHang(String idChiTietDonHang) {
        this.idChiTietDonHang = idChiTietDonHang;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getNhanVienXuLy() {
        return nhanVienXuLy;
    }

    public void setNhanVienXuLy(String nhanVienXuLy) {
        this.nhanVienXuLy = nhanVienXuLy;
    }

    public int getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(int soLuongDat) {
        this.soLuongDat = soLuongDat;
    }

    public DonHangModel getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHangModel donHang) {
        this.donHang = donHang;
    }

    public SanPhamModel getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPhamModel sanPham) {
        this.sanPham = sanPham;
    }
}