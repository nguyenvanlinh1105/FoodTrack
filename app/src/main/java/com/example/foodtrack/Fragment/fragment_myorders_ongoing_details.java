package com.example.foodtrack.Fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.service.controls.templates.ControlButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Adapter.list_drink_API_adapter;
import com.example.foodtrack.Adapter.list_drink_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_myorders_ongoing_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_myorders_ongoing_details extends Fragment {

    private static final String ARG_ID = "idDonHang";
    private static final String ARG_STATUS = "tinhTrang";
    private static final String ARG_NOTE = "ghiChu";

    private String idDonHang, tinhTrang, ghiChu;

    private ImageView btn_back_myorders_ongoing_detail;
    private LinearLayout ll_list_myorders_details;
    private ArrayList<ChiTietDonHangModel> arrayChiTietDonHang = new ArrayList<>();
    private Button btn_huy;

    private View view_after_1, view_before_2, view_after_2, view_before_3;
    private TextView tv_step1, tv_step2, tv_step3;
    private ArrayList<ChiTietDonHangAPIModel> arrayChiTietDonHangAPI = new ArrayList<>();

    private TextView tv_ghiChu, tv_thoiGianDat, tv_thanhToan,
            tv_tongTien, tv_tongCong, tv_idDonHang;


    public fragment_myorders_ongoing_details() {
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

    public static fragment_myorders_ongoing_details newInstance(String id, String tinhTrang, String ghiChu) {
        fragment_myorders_ongoing_details fragment = new fragment_myorders_ongoing_details();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putString(ARG_STATUS, tinhTrang);
        args.putString(ARG_NOTE, ghiChu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idDonHang = getArguments().getString(ARG_ID);
            Log.d("idDonHang", idDonHang);
            tinhTrang = getArguments().getString(ARG_STATUS);
            Log.d("tinhTrang", tinhTrang);
            ghiChu = getArguments().getString(ARG_NOTE);
            Log.d("ghichu", ghiChu);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_ongoing_details, container, false);
        Mapping(view);
//        displayOrderDetails();

        GetOrderDetails();
        ControlButton();
        return view;
    }

    private void GetOrderDetails() {
        APIService.API_SERVICE.GetChiTietDonHangDangGiao().enqueue(new Callback<List<ChiTietDonHangAPIModel>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonHangAPIModel>> call, Response<List<ChiTietDonHangAPIModel>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<ChiTietDonHangAPIModel> listChiTietDonHang = response.body();

                    arrayChiTietDonHangAPI.clear();
                    for (ChiTietDonHangAPIModel apiItem : listChiTietDonHang) {
                        ChiTietDonHangAPIModel chiTietDonHang = new ChiTietDonHangAPIModel();
                        SanPhamAPIModel sanPham = new SanPhamAPIModel(apiItem.getProduct().getTenSanPham(), apiItem.getProduct().getGiaTien(), apiItem.getProduct().getImages(), apiItem.getProduct().getMoTa());
                        chiTietDonHang.setProduct(sanPham);
                        chiTietDonHang.setSoLuongDat(apiItem.getSoLuongDat());
                        chiTietDonHang.setIdChiTietDonHang(apiItem.getIdChiTietDonHang());
                        arrayChiTietDonHangAPI.add(chiTietDonHang);
                    }
                    displayOrderDetailsAPI();
                    btn_huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                } else {
                    UseFallbackData();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietDonHangAPIModel>> call, Throwable t) {
                UseFallbackData();
            }
        });
    }

    private void UseFallbackData() {
        initializeData();
        displayOrderDetails();

    }


    private void Mapping(View view) {
        ll_list_myorders_details = view.findViewById(R.id.ll_list_myorders_details);
        btn_back_myorders_ongoing_detail = (ImageView) view.findViewById(R.id.btn_back_myorders_ongoing_detail);
        view_after_1 = (View) view.findViewById(R.id.view_after_step_1);
        view_before_2 = (View) view.findViewById(R.id.view_before_step_2);
        view_after_2 = (View) view.findViewById(R.id.view_after_step_2);
        view_before_3 = (View) view.findViewById(R.id.view_before_step_3);
        btn_huy = (Button) view.findViewById(R.id.btn_huy_myOrders_detail);

        tv_step2 = (TextView)view.findViewById(R.id.tv_step_2_tinhTrang_myOrders_details);
        tv_step3 = (TextView)view.findViewById(R.id.tv_step_3_tinhTrang_myOrders_details);

        tv_tongTien = (TextView) view.findViewById(R.id.tv_tongTien_myOrders_details);
        tv_tongCong = (TextView) view.findViewById(R.id.tv_total_amount_myOrders_detail);
        tv_ghiChu = (TextView) view.findViewById(R.id.tv_ghi_chu_myOrders_detail);
        tv_idDonHang = (TextView) view.findViewById(R.id.tv_ma_don_hang_myOrders_detail);
        tv_thoiGianDat = (TextView) view.findViewById(R.id.tv_thoi_gian_dat_myOrders_detail);
        tv_thanhToan = (TextView) view.findViewById(R.id.tv_thanh_toan_myOrders_detail);

    }

    private void ControlButton() {
        btn_back_myorders_ongoing_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DonHangAPIModel model = new DonHangAPIModel();
                model.setIdDonHang(idDonHang);
                PostToCancleOrder(model);
            }
        });


    }

    private void displayOrderDetailsAPI() {

        //step 2: đang giao
        if (tinhTrang == "đang giao") {
            view_after_1.setBackgroundResource(R.drawable.green_btn_bg);
            view_before_2.setBackgroundResource(R.drawable.green_btn_bg);
            tv_step2.setBackgroundResource(R.drawable.green_btn_bg);

            btn_huy.setVisibility(View.GONE);
        }
        //step 3: hoàn thành
        else if (tinhTrang == "hoàn thành") {
            view_after_1.setBackgroundResource(R.drawable.green_btn_bg);
            view_before_2.setBackgroundResource(R.drawable.green_btn_bg);
            tv_step2.setBackgroundResource(R.drawable.green_btn_bg);

            view_after_2.setBackgroundResource(R.drawable.green_btn_bg);
            view_before_3.setBackgroundResource(R.drawable.green_btn_bg);
            tv_step3.setBackgroundResource(R.drawable.green_btn_bg);

            btn_huy.setVisibility(View.GONE);
        }

        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
        double tongTien = 0;

        for (ChiTietDonHangAPIModel chiTiet : arrayChiTietDonHangAPI) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_my_orders_detail, ll_list_myorders_details, false);

            ImageView images = itemView.findViewById(R.id.img_item_myOrders_detail);
            TextView soLuongDat = itemView.findViewById(R.id.tv_so_luong_myOrders_detail);
            TextView tenSanPham = itemView.findViewById(R.id.tv_ten_mon_myOrders_detail);
            TextView giaTien = itemView.findViewById(R.id.tv_gia_myOrders_detail);

            soLuongDat.setText(String.valueOf(chiTiet.getSoLuongDat()));
            SanPhamAPIModel sanPham = chiTiet.getProduct();
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

                tongTien += Double.valueOf(sanPham.getGiaTien())*Integer.valueOf(chiTiet.getSoLuongDat());
            }

            ll_list_myorders_details.addView(itemView);
        }
        tv_ghiChu.setText(ghiChu);
        tv_idDonHang.setText(idDonHang);
        tv_tongTien.setText(tongTien + " vnđ");
    }

    private void displayOrderDetails() {

        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);

        for (ChiTietDonHangModel item : arrayChiTietDonHang) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_my_orders_detail, ll_list_myorders_details, false);

            ImageView images = itemView.findViewById(R.id.img_item_myOrders_detail);
            TextView soLuongDat = itemView.findViewById(R.id.tv_so_luong_myOrders_detail);
            TextView tenSanPham = itemView.findViewById(R.id.tv_ten_mon_myOrders_detail);
            TextView giaTien = itemView.findViewById(R.id.tv_gia_myOrders_detail);

            soLuongDat.setText(String.valueOf(item.getSoLuongDat()));
            SanPhamModel sanPham = item.getSanPham();
            if (sanPham != null) {
                images.setImageResource(sanPham.getImages());
                tenSanPham.setText(sanPham.getTenSanPham());
                giaTien.setText(nf.format(sanPham.getGiaTien()) + " vnđ");
            }

            ll_list_myorders_details.addView(itemView);
        }
    }


    private void PostToCancleOrder(DonHangAPIModel model){
        APIService.API_SERVICE.PostToCancleOrder(model).enqueue(new Callback<DonHangAPIModel>() {
            @Override
            public void onResponse(Call<DonHangAPIModel> call, Response<DonHangAPIModel> response) {

            }

            @Override
            public void onFailure(Call<DonHangAPIModel> call, Throwable t) {

            }
        });
    }

}
