//package com.example.foodtrack.Fragment;
//
//import android.media.Image;
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//
//import android.service.controls.templates.ControlButton;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.foodtrack.API.APIService;
//import com.example.foodtrack.Adapter.list_drink_API_adapter;
//import com.example.foodtrack.Adapter.list_drink_adapter;
//import com.example.foodtrack.Model.API.SanPhamAPIModel;
//import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
//import com.example.foodtrack.Model.ChiTietDonHangModel;
//import com.example.foodtrack.Model.SanPhamModel;
//import com.example.foodtrack.R;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class fragment_myorders_ongoing_details_API extends Fragment {
//
//    private ImageView btn_back_myorders_ongoing_detail;
//    private LinearLayout ll_list_myorders_details;
//    private View view_after_1, view_before_2, view_after_2, view_before_3;
//    private TextView tv_step1, tv_step2, tv_step3;
//    private ArrayList<ChiTietDonHangAPIModel> arrayChiTietDonHangAPI = new ArrayList<>();
//
//    public fragment_myorders_ongoing_details_API() {
//        // Required empty public constructor
//    }
//
//    private void initializeData() {
//        List<SanPhamModel> sanPhamList = createSampleProducts();
//        arrayChiTietDonHang = createSampleOrderDetails(sanPhamList);
//    }
//
//    private List<SanPhamModel> createSampleProducts() {
//        List<SanPhamModel> sanPhamList = new ArrayList<>();
//        sanPhamList.add(new SanPhamModel("Cheesecake việt quất", 20000, R.drawable.dessert_ico, ""));
//        sanPhamList.add(new SanPhamModel("Cơm tấm", 20000, R.drawable.com_tam, ""));
//        return sanPhamList;
//    }
//
//    private ArrayList<ChiTietDonHangModel> createSampleOrderDetails(List<SanPhamModel> sanPhamList) {
//        ArrayList<ChiTietDonHangModel> chiTietDonHangList = new ArrayList<>();
//        for (int i = 0; i < sanPhamList.size(); i++) {
//            ChiTietDonHangModel chiTietDonHang = new ChiTietDonHangModel();
//            chiTietDonHang.setIdChiTietDonHang("000" + i);
//            chiTietDonHang.setSanPham(sanPhamList.get(i));
//            chiTietDonHang.setSoLuongDat(1);
//            chiTietDonHangList.add(chiTietDonHang);
//        }
//        return chiTietDonHangList;
//    }
//
//    public static fragment_myorders_ongoing_details newInstance(String param1, String param2) {
//        fragment_myorders_ongoing_details fragment = new fragment_myorders_ongoing_details();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            String idDonHang = getArguments().getString("idDonHang");
//            Log.d("idDonHang", idDonHang);
//        }
//
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_myorders_ongoing_details, container, false);
//        Mapping(view);
////
//        GetOrderDetails();
//        ControlButton();
//        return view;
//    }
//
//    private void GetOrderDetails() {
//        APIService.API_SERVICE.getListDoUong_Explore().enqueue(new Callback<List<ChiTietDonHangAPIModel>>() {
//            @Override
//            public void onResponse(Call<List<ChiTietDonHangAPIModel>> call, Response<List<ChiTietDonHangAPIModel>> response) {
//                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
//                    List<ChiTietDonHangAPIModel> listChiTietDonHang = response.body();
//
//                    arrayChiTietDonHangAPI.clear();
//                    for (ChiTietDonHangAPIModel apiItem : listChiTietDonHang) {
//                        ChiTietDonHangAPIModel chiTietDonHang = new ChiTietDonHangAPIModel();
//                        SanPhamAPIModel sanPham = new SanPhamAPIModel(apiItem.getSanPham().getTenSanPham(), apiItem.getSanPham().getGiaTien(), apiItem.getSanPham().getImages(), apiItem.getSanPham().getMoTa());
//                        chiTietDonHang.setSanPham(sanPham);
//                        chiTietDonHang.setSoLuongDat(apiItem.getSoLuongDat());
//                        chiTietDonHang.setIdChiTietDonHang(apiItem.getIdChiTietDonHang());
//                        arrayChiTietDonHangAPI.add(chiTietDonHang);
//                    }
//
//                    // Hiển thị dữ liệu từ API
//                    displayOrderDetails();
//                } else {
//                    // Sử dụng dữ liệu tĩnh khi không có phản hồi API
//                    UseFallbackData();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ChiTietDonHangAPIModel>> call, Throwable t) {
//                // Sử dụng dữ liệu tĩnh khi API thất bại
//                UseFallbackData();
//            }
//        });
//    }
//
//    private void UseFallbackData() {
//        initializeData();
//        displayOrderDetails();
//    }
//
//    private void Mapping(View view) {
//        ll_list_myorders_details = view.findViewById(R.id.ll_list_myorders_details);
//        btn_back_myorders_ongoing_detail=(ImageView)view.findViewById(R.id. btn_back_myorders_ongoing_detail);
//        view_after_1 = (View) view.findViewById(R.id.view_after_step_1);
//        view_before_2 = (View) view.findViewById(R.id.view_before_step_2);
//        view_after_2 = (View) view.findViewById(R.id.view_after_step_2);
//        view_before_3 = (View) view.findViewById(R.id.view_before_step_3);
//    }
//
//    private void ControlButton(){
//        btn_back_myorders_ongoing_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requireActivity().getSupportFragmentManager().popBackStack();
//            }
//        });
//    }
//
//    private void displayOrderDetails() {
//
//        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
//
//        for (ChiTietDonHangModel item : arrayChiTietDonHang) {
//            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_my_orders_detail, ll_list_myorders_details, false);
//
//            // Mapping views in item layout
//            ImageView images = itemView.findViewById(R.id.img_item_myOrders_detail);
//            TextView soLuongDat = itemView.findViewById(R.id.tv_so_luong_myOrders_detail);
//            TextView tenSanPham = itemView.findViewById(R.id.tv_ten_mon_myOrders_detail);
//            TextView giaTien = itemView.findViewById(R.id.tv_gia_myOrders_detail);
//
//
//            // Set data to views
//            soLuongDat.setText(String.valueOf(item.getSoLuongDat()));
//            SanPhamModel sanPham = item.getSanPham();
//            if (sanPham != null) {
//                images.setImageResource(sanPham.getImages());
//                tenSanPham.setText(sanPham.getTenSanPham());
//                giaTien.setText(nf.format(sanPham.getGiaTien()) + " vnđ");
//            }
//
//            // Add itemView to LinearLayout
//            ll_list_myorders_details.addView(itemView);
//        }
//    }
//}
