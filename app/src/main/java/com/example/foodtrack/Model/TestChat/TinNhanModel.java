package com.example.foodtrack.Model.TestChat;

public class TinNhanModel {
    private String tenNguoiDung;
    private String noiDungChat;
    private String gioiTinh;
//    private

    public TinNhanModel(String tenNguoiDung, String noiDungChat, String gioiTinh) {
        this.tenNguoiDung = tenNguoiDung;
        this.noiDungChat = noiDungChat;
        this.gioiTinh = gioiTinh;
    }


    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getNoiDungChat() {
        return noiDungChat;
    }

    public void setNoiDungChat(String noiDungChat) {
        this.noiDungChat = noiDungChat;
    }
}
