package com.example.foodtrack.Model;

import com.example.foodtrack.Model.API.SanPhamAPIModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ChiTietDonHangAPIModel implements Serializable {
    private String idDonHang;
    private String idChiTietDonHang;
    private String idUser;
    private String idSanPham;
    private String nhanVienXuLy;
    private int soLuongDat;
    // Navigation properties
 //   private DonHangModel donHang;
    private SanPhamAPIModel Product;
    private Date ngayTao;
    private String ngayGiaoHang;
    private String trangThai;
    private int trangThaiBinhLuan;
    private int hasComment;

    private String tinhTrang;

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    private List<SanPhamAPIModel> products;

    public List<SanPhamAPIModel> getProducts() {
        return products;
    }

    public void setProducts(List<SanPhamAPIModel> products) {
        this.products = products;
    }

    public int getHasComment() {
        return hasComment;
    }

    public void setHasComment(int hasComment) {
        this.hasComment = hasComment;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayGiaoHang() {
        return ngayGiaoHang;
    }

    public void setNgayGiaoHang(String ngayGiaoHang) {
        this.ngayGiaoHang = ngayGiaoHang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getTrangThaiBinhLuan() {
        return trangThaiBinhLuan;
    }

    public void setTrangThaiBinhLuan(int trangThaiBinhLuan) {
        this.trangThaiBinhLuan = trangThaiBinhLuan;
    }

    public String getIdChiTietDonHang() {
        return idChiTietDonHang;
    }

    public void setIdChiTietDonHang(String idChiTietDonHang) {
        this.idChiTietDonHang = idChiTietDonHang;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public SanPhamAPIModel getProduct() {
        return Product;
    }

    public void setProduct(SanPhamAPIModel product) {
        Product = product;
    }
    // public DonHangModel getDonHang() {
//        return donHang;
//    }

    //public void setDonHang(DonHangModel donHang) {
//        this.donHang = donHang;
//    }

    public SanPhamAPIModel getSanPham() {
        return Product;
    }

    public void setSanPham(SanPhamAPIModel sanPham) {
        this.Product = sanPham;
    }

}
