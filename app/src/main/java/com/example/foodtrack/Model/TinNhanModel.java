package com.example.foodtrack.Model;

import java.sql.Timestamp;

public class TinNhanModel {
    private String idTinNhan;
    private String idPhongChat;
    private String noiDung;
    private String images;
    private Timestamp thoiGianCapNhat;

    // Navigation property
    private PhongChatModel phongChat;

    // Getters and Setters
    public String getIdTinNhan() {
        return idTinNhan;
    }

    public void setIdTinNhan(String idTinNhan) {
        this.idTinNhan = idTinNhan;
    }

    public String getIdPhongChat() {
        return idPhongChat;
    }

    public void setIdPhongChat(String idPhongChat) {
        this.idPhongChat = idPhongChat;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Timestamp getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(Timestamp thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }
}
