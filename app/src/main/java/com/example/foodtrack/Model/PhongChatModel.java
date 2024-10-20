package com.example.foodtrack.Model;

import java.sql.Timestamp;
import java.util.List;

public class PhongChatModel {
    private String idPhongChat;
    private String loaiPhong;
    private Timestamp thoiGianTao;
    private Timestamp thoiGianCapNhat;

    // Navigation properties
    private List<ChiTietPhongChatModel> chiTietPhongChats;
    private List<TinNhanModel> tinNhans;

    // Getters and Setters
    public String getIdPhongChat() {
        return idPhongChat;
    }

    public void setIdPhongChat(String idPhongChat) {
        this.idPhongChat = idPhongChat;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public Timestamp getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(Timestamp thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }
}
