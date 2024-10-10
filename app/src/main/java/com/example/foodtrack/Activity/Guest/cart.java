package com.example.foodtrack.Activity.Guest;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.cart_adapter;
import java.util.ArrayList;

public class cart extends AppCompatActivity {

    private ArrayList<String> cartTitle = new ArrayList<>();
    private ArrayList<Integer> cartImg = new ArrayList<>();
    private ArrayList<String> cartSubTitle = new ArrayList<>();
    private ArrayList<String> cartPrice = new ArrayList<>();

    private ListView listView_cart;

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
    }
    public void Mapping() {

        listView_cart = (ListView) findViewById(R.id.listView_cart);
        cart_adapter listAdapter = new cart_adapter(cart.this, cartTitle, cartImg, cartSubTitle, cartPrice);
        listView_cart.setAdapter(listAdapter);
    }

    private void initializeData(){
        cartTitle.add("Burger phô mai");
        cartTitle.add("Burger bò");
        cartTitle.add("Burger trứng");
        cartTitle.add("Burger phô mai");
        cartTitle.add("Burger phô mai gà");
        cartTitle.add("Burger heo bằm");

        cartImg.add(R.drawable.double_cheese);
        cartImg.add(R.drawable.double_cheese);
        cartImg.add(R.drawable.double_cheese);
        cartImg.add(R.drawable.double_cheese);
        cartImg.add(R.drawable.double_cheese);
        cartImg.add(R.drawable.double_cheese);

        cartSubTitle.add("Classic cheesburger");
        cartSubTitle.add("Classic cheesburger");
        cartSubTitle.add("Classic cheesburger");
        cartSubTitle.add("Classic cheesburger");
        cartSubTitle.add("Classic cheesburger");
        cartSubTitle.add("Classic cheesburger");

        cartPrice.add("50.000đ");
        cartPrice.add("50.000đ");
        cartPrice.add("50.000đ");
        cartPrice.add("50.000đ");
        cartPrice.add("50.000đ");
        cartPrice.add("50.000đ");
    }
}