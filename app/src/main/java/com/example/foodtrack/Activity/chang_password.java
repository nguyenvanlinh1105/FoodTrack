package com.example.foodtrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodtrack.R;

public class chang_password extends AppCompatActivity {
    ImageView btn_back_phone_verify;
    TextView btn_xacNhanDoi_MK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chang_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_back_phone_verify = findViewById(R.id.btn_back_phone_verify);
        btn_xacNhanDoi_MK = findViewById(R.id.btn_XacNhan_doiMK_changePassword);
        btn_back_phone_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phone_verify = new Intent(chang_password.this, forgot_password.class);
                startActivity(phone_verify);
            }
        });
        btn_xacNhanDoi_MK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chang_password.this, "Bạn đã cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}