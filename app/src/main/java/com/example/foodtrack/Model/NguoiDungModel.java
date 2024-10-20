package com.example.foodtrack.Model;

import java.time.LocalDateTime;

public class NguoiDungModel {
    private int code;
    private  String message ;
    private int idUser;
    private String hoTenNguoiDung;
    private String email;
    private String diaChi;
    private String sdt;
    private LocalDateTime ngaySinh;
    private String matKhau;
    private String gioiTinh;
    private String avatar;
    private String trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private String nguoiCapNhat;
    private String thongTinUser;
    private vaiTroModel vaitro;
    // Default constructor

    public NguoiDungModel() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getHoTenNguoiDung() {
        return hoTenNguoiDung;
    }

    public void setHoTenNguoiDung(String hoTenNguoiDung) {
        this.hoTenNguoiDung = hoTenNguoiDung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    
    public LocalDateTime getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDateTime ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public String getNguoiCapNhat() {
        return nguoiCapNhat;
    }

    public void setNguoiCapNhat(String nguoiCapNhat) {
        this.nguoiCapNhat = nguoiCapNhat;
    }

    public String getThongTinUser() {
        return thongTinUser;
    }

    public void setThongTinUser(String thongTinUser) {
        this.thongTinUser = thongTinUser;
    }

    public vaiTroModel getVaitro() {
        return vaitro;
    }

    public void setVaitro(vaiTroModel vaitro) {
        this.vaitro = vaitro;
    }
}