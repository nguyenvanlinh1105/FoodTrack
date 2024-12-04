package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.cart;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_myorders_mualai_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_myorders_mualai_details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "idDonHang";
    private static final String ARG_NOTE = "ghiChu";

    // TODO: Rename and change types of parameters
    private String idDonHang, tinhTrang, ghiChu;

    private Button btn_mua_lai;
    private ImageView btn_back_myorders_ongoing_detail;
    private LinearLayout ll_list_myorders_details;

    private ArrayList<ChiTietDonHangModel> arrayChiTietDonHang = new ArrayList<>();
    private ArrayList<ChiTietDonHangAPIModel> arrayChiTietDonHangAPI = new ArrayList<>();

    private DonHangAPIModel donHangAPIModel = new DonHangAPIModel();

    private TextView tv_ghiChu, tv_thoiGianDat, tv_thanhToan,
            tv_tongTien, tv_tongCong, tv_idDonHang, tv_tinhTrang, tv_tongSoLuongMon;
    String idUser;
    SharedPreferences shareUserResponseLogin,shareDonHangResponseData ;


    List<String> listIdSanPham  = new ArrayList<>();

    public fragment_myorders_mualai_details() {
        // Required empty public constructor
    }


    private void initializeData() {
        List<SanPhamModel> sanPhamList = createSampleProducts();
        arrayChiTietDonHang = createSampleOrderDetails(sanPhamList);
    }

    private List<SanPhamModel> createSampleProducts() {
        List<SanPhamModel> sanPhamList = new ArrayList<>();
        sanPhamList.add(new SanPhamModel("Cheesecake việt quất", 20000, R.drawable.dessert_ico, ""));
        sanPhamList.add(new SanPhamModel("Cơm tấm", 20000, R.drawable.com_tam, ""));
        return sanPhamList;
    }

    private ArrayList<ChiTietDonHangModel> createSampleOrderDetails(List<SanPhamModel> sanPhamList) {
        ArrayList<ChiTietDonHangModel> chiTietDonHangList = new ArrayList<>();
        for (int i = 0; i < sanPhamList.size(); i++) {
            ChiTietDonHangModel chiTietDonHang = new ChiTietDonHangModel();
            chiTietDonHang.setIdChiTietDonHang("000" + i);
            chiTietDonHang.setSanPham(sanPhamList.get(i));
            chiTietDonHang.setSoLuongDat(1);
            chiTietDonHangList.add(chiTietDonHang);
        }
        return chiTietDonHangList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_myorders_mualai_details.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_myorders_mualai_details newInstance() {
        fragment_myorders_mualai_details fragment = new fragment_myorders_mualai_details();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            donHangAPIModel = (DonHangAPIModel) getArguments().getSerializable("selectedOrder");
        }
          shareUserResponseLogin = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
        idUser = shareUserResponseLogin.getString("idUser","-1");

        shareDonHangResponseData = getContext().getSharedPreferences("dataDonHangResponse", Context.MODE_PRIVATE);
        idDonHang = shareDonHangResponseData.getString("idDonHang","");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_mualai_details, container, false);
        Mapping(view);
//        displayOrderDetails();
        GetOrderDetails();
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        ll_list_myorders_details = view.findViewById(R.id.ll_list_myorders_details);
        btn_back_myorders_ongoing_detail = (ImageView) view.findViewById(R.id.btn_back_myorders_ongoing_detail);
        btn_mua_lai = (Button) view.findViewById(R.id.btn_mua_lai_myOrders_detail);

        tv_tongTien = (TextView) view.findViewById(R.id.tv_tongTien_myOrders_details);
        tv_tongCong = (TextView) view.findViewById(R.id.tv_total_amount_myOrders_detail);
        tv_ghiChu = (TextView) view.findViewById(R.id.tv_ghi_chu_myOrders_detail);
        tv_idDonHang = (TextView) view.findViewById(R.id.tv_ma_don_hang_myOrders_detail);
        tv_thoiGianDat = (TextView) view.findViewById(R.id.tv_thoi_gian_dat_myOrders_detail);
        tv_thanhToan = (TextView) view.findViewById(R.id.tv_thanh_toan_myOrders_detail);
        tv_tinhTrang = (TextView) view.findViewById(R.id.tinhTrang_myOrders_detail);
        tv_tongSoLuongMon = (TextView) view.findViewById(R.id.so_luong_myOrders_detail);
    }

    private void GetOrderDetails() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(donHangAPIModel);
        Log.d("selectedOrder", json);

        displayOrderDetailsAPI();
    }

    private void UseFallbackData() {
        initializeData();
        displayOrderDetails();
    }

    private void displayOrderDetailsAPI() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);

        tv_idDonHang.setText(donHangAPIModel.getIdDonHang());
        tv_thoiGianDat.setText(dateFormat.format(donHangAPIModel.getNgayTao().getTime()));
        tv_tinhTrang.setText(donHangAPIModel.getTinhTrang());
        tv_thanhToan.setText(donHangAPIModel.getTinhTrangThanhToan());
        tv_ghiChu.setText(donHangAPIModel.getGhiChu());
        tv_tongSoLuongMon.setText(String.valueOf(donHangAPIModel.getChiTietDonHangs().size()));
//        Log.d("idDonHang", donHangAPIModel.getIdDonHang());

        double tongTien = 0;

        for (ChiTietDonHangAPIModel chiTiet : donHangAPIModel.getChiTietDonHangs()) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_my_orders_detail, ll_list_myorders_details, false);

            ImageView images = itemView.findViewById(R.id.img_item_myOrders_detail);
            TextView soLuongDat = itemView.findViewById(R.id.tv_so_luong_myOrders_detail);
            TextView tenSanPham = itemView.findViewById(R.id.tv_ten_mon_myOrders_detail);
            TextView giaTien = itemView.findViewById(R.id.tv_gia_myOrders_detail);

            soLuongDat.setText(String.valueOf(chiTiet.getSoLuongDat()));
            SanPhamAPIModel sanPham = chiTiet.getProduct();
            listIdSanPham.add(sanPham.getIdSanPham());
            if (sanPham != null) {
                String imageUrl = sanPham.getImages();
                if (imageUrl.startsWith("http://")) {
                    imageUrl = imageUrl.replace("http://", "https://");
                }

                Glide.with(getContext())
                        .asBitmap()
                        .load(imageUrl)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }

                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                images.setImageDrawable(new BitmapDrawable(getContext().getResources(), resource));

                            }
                        });
                tenSanPham.setText(sanPham.getTenSanPham());
                giaTien.setText(nf.format(sanPham.getGiaTien()) + " vnđ");

                tongTien += Double.valueOf(sanPham.getGiaTien()) * Integer.valueOf(chiTiet.getSoLuongDat());
            }

            ll_list_myorders_details.addView(itemView);
        }
        tv_tongTien.setText(nf.format(tongTien) + " vnđ");
        tongTien += 15000;
        tv_tongCong.setText(nf.format(tongTien) + " vnđ");
    }


    private void ControlButton() {
        btn_back_myorders_ongoing_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        btn_mua_lai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTietDonHangAPIModel model = new ChiTietDonHangAPIModel();
                model.setIdDonHang(idDonHang);
                model.setIdUser(idUser);
                model.setIdDonHangHuy(donHangAPIModel.getIdDonHang());
                List<SanPhamAPIModel> listproduct = new ArrayList<>();
                List<ChiTietDonHangAPIModel> listCTDH = donHangAPIModel.getChiTietDonHangs();

                for (int i = 0; i < listIdSanPham.size(); i++) {

                    String idSanPham = listIdSanPham.get(i);

                    ChiTietDonHangAPIModel chiTiet = listCTDH.get(i);

                    SanPhamAPIModel sanPham = new SanPhamAPIModel();
                    sanPham.setIdSanPham(idSanPham);
                    sanPham.setSoLuongDat(chiTiet.getSoLuongDat());

                    listproduct.add(sanPham);
                }

                model.setProducts(listproduct);

                MuaLai(model);


               // showPopup(view);
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        Intent cart = new Intent(getContext(), cart.class);
                        startActivity(cart);
                    }, 1000);// đổi lại nếu nhanh quá nha

                }

            }
        });
    }

    private void showPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_cancel_order, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView btnOk = popupView.findViewById(R.id.btn_ok_popup_submit_cmt);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_history_API());
                }
            }
        });
    }

    private void displayOrderDetails() {
        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);

        for (ChiTietDonHangModel item : arrayChiTietDonHang) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_my_orders_detail, ll_list_myorders_details, false);

            // Mapping views in item layout
            ImageView images = itemView.findViewById(R.id.img_item_myOrders_detail);
            TextView soLuongDat = itemView.findViewById(R.id.tv_so_luong_myOrders_detail);
            TextView tenSanPham = itemView.findViewById(R.id.tv_ten_mon_myOrders_detail);
            TextView giaTien = itemView.findViewById(R.id.tv_gia_myOrders_detail);


            // Set data to views
            soLuongDat.setText(String.valueOf(item.getSoLuongDat()));
            SanPhamModel sanPham = item.getSanPham();
            if (sanPham != null) {
                images.setImageResource(sanPham.getImages());
                tenSanPham.setText(sanPham.getTenSanPham());
                giaTien.setText(nf.format(sanPham.getGiaTien()) + " vnđ");
            }

            // Add itemView to LinearLayout
            ll_list_myorders_details.addView(itemView);
        }
    }

    private void MuaLai(ChiTietDonHangAPIModel model) {
        APIService.API_SERVICE.MuaLaiDonHang(model).enqueue(new Callback<ChiTietDonHangAPIModel>() {
            @Override
            public void onResponse(Call<ChiTietDonHangAPIModel> call, Response<ChiTietDonHangAPIModel> response) {
                ChiTietDonHangAPIModel ctdh = response.body();
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editorResponseDonHang = shareDonHangResponseData.edit();
                    if (ctdh.getIdDonHang()!=null) {
                        editorResponseDonHang.putString("idDonHang", ctdh.getIdDonHang());
                        editorResponseDonHang.apply();


                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ChiTietDonHangAPIModel> call, Throwable t) {

            }
        });
    }
}