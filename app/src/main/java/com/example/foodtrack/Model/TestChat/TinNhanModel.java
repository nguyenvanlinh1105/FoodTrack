package com.example.foodtrack.Model.TestChat;

public class TinNhanModel {
    private String idPhongChat;
    private String idUser;
    private String tenNguoiDung;
    private String noiDungChat;
    private String gioiTinh;
//    private

    public TinNhanModel(String idPhongChat,String idUser,String tenNguoiDung, String noiDungChat, String gioiTinh) {
        this.idPhongChat = idPhongChat;
        this.idUser = idUser;
        this.tenNguoiDung = tenNguoiDung;
        this.noiDungChat = noiDungChat;
        this.gioiTinh = gioiTinh;
    }

    public String getIdPhongChat() {
        return idPhongChat;
    }

    public void setIdPhongChat(String idPhongChat) {
        this.idPhongChat = idPhongChat;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    @Override
    public String toString() {
        return "TinNhan{" +
                "idUser='" + idUser + '\'' +
                ", tenNguoiDung='" + tenNguoiDung + '\'' +
                ", noiDung='" + noiDungChat + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                '}';
    }

    public String getNoiDungChat() {
        return noiDungChat;
    }

    public void setNoiDungChat(String noiDungChat) {
        this.noiDungChat = noiDungChat;
    }
}
