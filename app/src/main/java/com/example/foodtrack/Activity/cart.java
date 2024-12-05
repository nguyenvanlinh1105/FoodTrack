package com.example.foodtrack.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Adapter.cart_adapter_api;
import com.example.foodtrack.Adapter.cart_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cart extends AppCompatActivity {

    private ArrayList<String> cartTitle = new ArrayList<>();
    private ArrayList<Integer> cartImg = new ArrayList<>();
    private ArrayList<String> cartSubTitle = new ArrayList<>();
    private ArrayList<String> cartPrice = new ArrayList<>();
    private ArrayList<Integer> cartQty = new ArrayList<>();

    private ListView listView_cart;
    private ImageView backBtn;
    public TextView total;
    private TextView datDonBtn;
    public double tongTien = 0;
    private TextView edt_ghiChu;
    public String textGhiChu;
    String idDonHang;

    public static Activity ToFinishActivity;

    private SharedPreferences sharedPreferencesDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferencesDonHang = getSharedPreferences("dataDonHangResponse", Context.MODE_PRIVATE);

        Mapping();

        idDonHang = sharedPreferencesDonHang.getString("idDonHang", "");


        if (idDonHang != null && !idDonHang.isEmpty()) {
            GetDsSanPhamOrder(idDonHang);
        } else {
            UseFallbackData();
        }


        ControlButton();
    }

    public void Mapping() {
        backBtn = findViewById(R.id.btn_back_cart);
        listView_cart = findViewById(R.id.listView_cart);
        total = findViewById(R.id.total_cart);
        datDonBtn = findViewById(R.id.btn_dat_don_cart);
        edt_ghiChu = findViewById(R.id.edt_ghiChu);

        if (listView_cart != null) {
            cart_adapter listAdapter = new cart_adapter(this, cartTitle, cartImg, cartSubTitle, cartPrice, cartQty, this);
            listView_cart.setAdapter(listAdapter);
        }
        total.setText(tongTien + " vnđ");

    }

    private void initializeData() {
        cartTitle.add("Burger phô mai");
        cartImg.add(R.drawable.double_cheese);
        cartSubTitle.add("Classic cheesburger");
        cartPrice.add("50.000 vnđ");
        cartQty.add(1);

        // Thêm dữ liệu mẫu khác tại đây
    }

    private void ControlButton() {
        if (backBtn != null) {
            backBtn.setOnClickListener(view -> finish());
        }
        if (datDonBtn != null) {
            if (idDonHang != null) {
                datDonBtn.setOnClickListener(view -> {
                    textGhiChu = edt_ghiChu.getText().toString();
                    Intent thanhToan = new Intent(cart.this, MainActivity.class);
                    thanhToan.putExtra("fragmentToLoad", "cartFragment");
                    thanhToan.putExtra("ghiChu", textGhiChu);
//                    Log.d("tongTien", String.valueOf(tongTien));
                    thanhToan.putExtra("tongTien", tongTien);
                    startActivity(thanhToan);
                });
            } else {

            }
        }
    }

    public void updateTotalPrice() {
        if (listView_cart != null) {
            int totalPrice = 0;

            cart_adapter_api adapter = (cart_adapter_api) listView_cart.getAdapter();
            if (adapter != null) {
                List<SanPhamAPIModel> sanPhamList = adapter.arrayListSanPham;

                for (SanPhamAPIModel product : sanPhamList) {
                    totalPrice += product.getSoLuongDat() * product.getGiaTien();
                }
            }
            NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
            total.setText(formatter.format(totalPrice) + " vnđ");
        }
    }

    private class GetDsSanPhamOrderTask extends AsyncTask<String, Void, List<SanPhamAPIModel>> {

        @Override
        protected List<SanPhamAPIModel> doInBackground(String... params) {
            try {
                Response<List<SanPhamAPIModel>> response = APIService.API_SERVICE.GetSanPhamGioHang(params[0]).execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<SanPhamAPIModel> listSanPham) {
            super.onPostExecute(listSanPham);
            if (listSanPham != null && !listSanPham.isEmpty()) {
                for (SanPhamAPIModel sp : listSanPham) {
                    tongTien += Double.parseDouble(String.valueOf(sp.getGiaTien())) * Integer.parseInt(String.valueOf(sp.getSoLuongDat()));
                }
                Log.d("TongTien", "Giá trị tổng tiền: " + tongTien);


                // Format tổng tiền
                NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
                String formattedPrice = numberFormat.format(tongTien);
                total.setText(formattedPrice + " vnđ");

                // Cập nhật RecyclerView
                UpdateRecyclerView(listSanPham);
            } else {
                UseFallbackData();
            }
        }
    }

    public void GetDsSanPhamOrder(String idDonHang) {
        new GetDsSanPhamOrderTask().execute(idDonHang);
    }

    public void updateButtonVisibility() {
        if (tongTien == 0.0) {
            datDonBtn.setVisibility(View.GONE);
        } else {
            datDonBtn.setVisibility(View.VISIBLE);
        }
    }


    private void UseFallbackData() {
        // initializeData();
        UpdateRecyclerView(new ArrayList<>());
    }

    private void UpdateRecyclerView(List<SanPhamAPIModel> data) {
        updateButtonVisibility();
        if (listView_cart != null) {
            cart_adapter_api listAdapter = new cart_adapter_api(this, data, this);
            listView_cart.setAdapter(listAdapter);
        }
    }
}
