package com.example.foodtrack.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private TextView total;
    private TextView datDonBtn;

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

        String idDonHang = sharedPreferencesDonHang.getString("idDonHang", "");


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

        if (listView_cart != null) {
            cart_adapter listAdapter = new cart_adapter(this, cartTitle, cartImg, cartSubTitle, cartPrice, cartQty, this);
            listView_cart.setAdapter(listAdapter);
        }
    }

    private void initializeData() {
        cartTitle.add("Burger phô mai");
        cartImg.add(R.drawable.double_cheese);
        cartSubTitle.add("Classic cheesburger");
        cartPrice.add("50.000vnđ");
        cartQty.add(1);

        // Thêm dữ liệu mẫu khác tại đây
    }

    private void ControlButton() {
        if (backBtn != null) {
            backBtn.setOnClickListener(view -> finish());
        }
        if (datDonBtn != null) {
            datDonBtn.setOnClickListener(view -> {
                Intent thanhToan = new Intent(cart.this, MainActivity.class);
                thanhToan.putExtra("fragmentToLoad", "cartFragment");
                startActivity(thanhToan);
            });
        }
    }

    public void updateTotalPrice() {
        if (listView_cart != null) {
            int totalPrice = 0;
            for (int i = 0; i < listView_cart.getCount(); i++) {
                View view = listView_cart.getChildAt(i);
                if (view != null) {
                    TextView qtyTextView = view.findViewById(R.id.qty_cart);
                    int qty = Integer.parseInt(qtyTextView.getText().toString());

                    String cleanedPrice = cartPrice.get(i).replace(".", "").replace("vnđ", "");
                    int pricePerItem = Integer.parseInt(cleanedPrice);

                    totalPrice += qty * pricePerItem;
                }
            }

            NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
            total.setText(formatter.format(totalPrice) + "vnđ");
        }
    }

    private void GetDsSanPhamOrder(String idDonHang) {
        APIService.API_SERVICE.GetSanPhamGioHang(idDonHang).enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SanPhamAPIModel> listSanPham = response.body();


                    UpdateRecyclerView(listSanPham);
                } else {
                    UseFallbackData();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamAPIModel>> call, Throwable t) {
                UseFallbackData();
            }
        });
    }

    private void UseFallbackData() {
        initializeData();
        UpdateRecyclerView(new ArrayList<>());
    }

    private void UpdateRecyclerView(List<SanPhamAPIModel> data) {
        if (listView_cart != null) {
            cart_adapter_api listAdapter = new cart_adapter_api(this, data, this);
            listView_cart.setAdapter(listAdapter);
        }
    }
}
