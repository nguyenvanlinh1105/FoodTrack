package com.example.foodtrack.Model;

public class ChiTietPhongChatModel {
    private String idNguoiDung;
    private String idPhongChat;

    // Navigation property
    private PhongChatModel phongChat;

    // Getters and Setters
    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(String idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getIdPhongChat() {
        return idPhongChat;
    }

    public void setIdPhongChat(String idPhongChat) {
        this.idPhongChat = idPhongChat;
    }
}

