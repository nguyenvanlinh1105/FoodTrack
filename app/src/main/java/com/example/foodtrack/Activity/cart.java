package com.example.foodtrack.Activity;

import android.app.Activity;
import android.content.Intent;
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

import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.cart_adapter;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
        Mapping();
        initializeData();

        int totalPrice = 0;
        for (int i = 0; i < cartPrice.size(); i++) {
            String cleanedPrice = cartPrice.get(i).replace(".", "").replace("đ", "");
            totalPrice += Integer.parseInt(cleanedPrice) * cartQty.get(i);
        }

        NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
        String formattedNumber = formatter.format(totalPrice);
        formattedNumber = formattedNumber + "đ";


        total.setText(String.valueOf(formattedNumber));

        ControlButton();
    }

    public void Mapping() {
        backBtn = (ImageView) findViewById(R.id.btn_back_cart);
        listView_cart = (ListView) findViewById(R.id.listView_cart);
        cart_adapter listAdapter = new cart_adapter(cart.this, cartTitle, cartImg, cartSubTitle, cartPrice,cartQty ,this);
        listView_cart.setAdapter(listAdapter);

        ToFinishActivity = this;

        total = (TextView) findViewById(R.id.total_cart);

        datDonBtn = (TextView) findViewById(R.id.btn_dat_don_cart);

    }

    private void initializeData() {
        cartTitle.add("Burger phô mai");
        cartTitle.add("Burger bò");
        cartTitle.add("Spaghetti");
        cartTitle.add("Carbonara");
        cartTitle.add("Cơm tấm");
        cartTitle.add("Burger heo bằm");

        cartImg.add(R.drawable.double_cheese);
        cartImg.add(R.drawable.double_cheese);
        cartImg.add(R.drawable.spaghetti);
        cartImg.add(R.drawable.carbonara);
        cartImg.add(R.drawable.com_tam);
        cartImg.add(R.drawable.double_cheese);

        cartSubTitle.add("Classic cheesburger");
        cartSubTitle.add("Classic cheesburger");
        cartSubTitle.add("Classic spaghetti");
        cartSubTitle.add("Classic carbonara");
        cartSubTitle.add("Classic com tam");
        cartSubTitle.add("Classic cheesburger");

        cartPrice.add("50.000đ");
        cartPrice.add("50.000đ");
        cartPrice.add("80.000đ");
        cartPrice.add("90.000đ");
        cartPrice.add("60.000đ");
        cartPrice.add("50.000đ");

        cartQty.add(1);
        cartQty.add(2);
        cartQty.add(1);
        cartQty.add(5);
        cartQty.add(1);
        cartQty.add(1);
    }

    private void ControlButton() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        datDonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thanhToan = new Intent( cart.this, MainActivity.class);
                thanhToan.putExtra("fragmentToLoad", "cartFragment"); // Gửi dữ liệu fragment
                startActivity(thanhToan);

            }
        });
    }

    public void updateTotalPrice() {
        int totalPrice = 0;

        for (int i = 0; i < listView_cart.getCount(); i++) {
            View view = listView_cart.getChildAt(i);
            if (view != null) {
                TextView qtyTextView = view.findViewById(R.id.qty_cart);
                int qty = Integer.parseInt(qtyTextView.getText().toString());

                String cleanedPrice = cartPrice.get(i).replace(".", "").replace("đ", "");
                int pricePerItem = Integer.parseInt(cleanedPrice);

                totalPrice += qty * pricePerItem;
            }
        }

        NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
        String formattedNumber = formatter.format(totalPrice) + "đ";

        total.setText(formattedNumber);
    }

}