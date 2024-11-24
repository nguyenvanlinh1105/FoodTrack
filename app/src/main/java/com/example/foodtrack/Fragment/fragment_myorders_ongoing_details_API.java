package com.example.foodtrack.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel; // Import ChiTietDonHangAPIModel

 // Import SanPhamAPIModel
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class fragment_myorders_ongoing_details_API extends Fragment {

    private ImageView btn_back_myorders_ongoing_detail;
    private LinearLayout ll_list_myorders_details;
    private ArrayList<ChiTietDonHangAPIModel> arrayChiTietDonHangAPI = new ArrayList<>(); // Đổi tên danh sách
    private String idDonHang="DH001";

    public fragment_myorders_ongoing_details_API() {
        // Required empty public constructor
    }

    private void initializeData() {
        List<SanPhamAPIModel> sanPhamAPIList = createSampleAPIProducts();
        arrayChiTietDonHangAPI = createSampleOrderDetails(sanPhamAPIList); // Đổi tên danh sách
    }

    private List<SanPhamAPIModel> createSampleAPIProducts() {
        List<SanPhamAPIModel> sanPhamAPIList = new ArrayList<>();
        sanPhamAPIList.add(new SanPhamAPIModel("Cheesecake việt quất", 20000, "url_to_image", ""));
        sanPhamAPIList.add(new SanPhamAPIModel("Cơm tấm", 20000, "url_to_image", ""));
        return sanPhamAPIList;
    }

    private ArrayList<ChiTietDonHangAPIModel> createSampleOrderDetails(List<SanPhamAPIModel> sanPhamAPIList) {
        ArrayList<ChiTietDonHangAPIModel> chiTietDonHangList = new ArrayList<>();
        for (int i = 0; i < sanPhamAPIList.size(); i++) {
            ChiTietDonHangAPIModel chiTietDonHangAPI = new ChiTietDonHangAPIModel(); // Thay đổi từ ChiTietDonHangModel sang ChiTietDonHangAPIModel
            chiTietDonHangAPI.setIdChiTietDonHang("000" + i);
            chiTietDonHangAPI.setSanPham(sanPhamAPIList.get(i)); // Giữ nguyên
            chiTietDonHangAPI.setSoLuongDat(1);
            chiTietDonHangList.add(chiTietDonHangAPI);
        }
        return chiTietDonHangList;
    }

    public static fragment_myorders_ongoing_details_API newInstance(String param1, String param2) {
        fragment_myorders_ongoing_details_API fragment = new fragment_myorders_ongoing_details_API();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_ongoing_details, container, false);
        ll_list_myorders_details = view.findViewById(R.id.ll_list_myorders_details);
        btn_back_myorders_ongoing_detail = view.findViewById(R.id.btn_back_myorders_ongoing_detail);
        displayOrderDetails();
        ControlButton();

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            idDonHang = bundle.getString("idDonHang");
        }
        return view;
    }

    private void ControlButton() {
        btn_back_myorders_ongoing_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void displayOrderDetails() {
        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);

        for (ChiTietDonHangAPIModel item : arrayChiTietDonHangAPI) { // Thay đổi từ ChiTietDonHangModel sang ChiTietDonHangAPIModel
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_my_orders_detail, ll_list_myorders_details, false);

            // Mapping views in item layout
            ImageView images = itemView.findViewById(R.id.img_item_myOrders_detail);
            TextView soLuongDat = itemView.findViewById(R.id.tv_so_luong_myOrders_detail);
            TextView tenSanPham = itemView.findViewById(R.id.tv_ten_mon_myOrders_detail);
            TextView giaTien = itemView.findViewById(R.id.tv_gia_myOrders_detail);

            // Set data to views
            soLuongDat.setText(String.valueOf(item.getSoLuongDat()));
            SanPhamAPIModel sanPham = item.getSanPham(); // Giữ nguyên
            if (sanPham != null) {
                // Nếu hình ảnh là URL, bạn có thể sử dụng thư viện như Glide hoặc Picasso để tải hình ảnh
             //   images.setImageResource(R.drawable.placeholder_image); // Hình ảnh tạm thời
                tenSanPham.setText(sanPham.getTenSanPham());
                giaTien.setText(nf.format(sanPham.getGiaTien()) + " vnđ");
            }

            // Add itemView to LinearLayout
            ll_list_myorders_details.addView(itemView);
        }
    }
}
