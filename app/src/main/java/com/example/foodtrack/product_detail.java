package com.example.foodtrack;

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

public class product_detail extends AppCompatActivity {

    ImageView btn_back_product_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();

        // Nhận dữ liệu từ Intent
        String title = getIntent().getStringExtra("title");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");
        int imageResourceId = getIntent().getIntExtra("image", 0);

        TextView titleView = findViewById(R.id.title_product_details);
        TextView priceView = findViewById(R.id.price_product_details);
        TextView descriptionView = findViewById(R.id.description_product_detail);
        ImageView imageView = findViewById(R.id.image_product_details);

        // Hiển thị dữ liệu trong giao diện
        titleView.setText(title);
        priceView.setText(price);
        descriptionView.setText(description);
        imageView.setImageResource(imageResourceId);

        ControlButton();

    }

    private void Mapping(){
        btn_back_product_detail = (ImageView)findViewById(R.id.btn_back_product_detail);

    }

    private  void ControlButton(){
        btn_back_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}