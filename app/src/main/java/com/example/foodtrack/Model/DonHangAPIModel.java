package com.example.foodtrack.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DonHangAPIModel implements Serializable {
    private String idDonHang;
    private String idNguoiDung;
    private String tinhTrang; //Trạng thái
    private Date ngayTao;
    private Timestamp thoiGianCapNhat;
    private String diaChi;
    private String phuongThucThanhToan;
    private String ghiChu ;


    // Navigation properties
    private NguoiDungModel nguoiDung;
    private List<ChiTietDonHangAPIModel> chiTietDonHangs;

    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setIdNguoiDung(String idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Timestamp getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(Timestamp thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }

    public NguoiDungModel getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDungModel nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public List<ChiTietDonHangAPIModel> getChiTietDonHangs() {
        return chiTietDonHangs;
    }

    public void setChiTietDonHangs(List<ChiTietDonHangAPIModel> chiTietDonHangs) {
        this.chiTietDonHangs = chiTietDonHangs;
    }
}
