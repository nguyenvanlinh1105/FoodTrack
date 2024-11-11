package com.example.foodtrack.Model.TestChat;

public class TinNhanModel {
    private String idTinNhan;
    private String tenNguoiDung;
    private String noiDungChat;

    public TinNhanModel(String idTinNhan, String tenNguoiDung, String noiDungChat) {
        this.idTinNhan = idTinNhan;
        this.tenNguoiDung = tenNguoiDung;
        this.noiDungChat = noiDungChat;
    }

    public String getIdTinNhan() {
        return idTinNhan;
    }

    public void setIdTinNhan(String idTinNhan) {
        this.idTinNhan = idTinNhan;
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
