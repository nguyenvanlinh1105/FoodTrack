package com.example.foodtrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodtrack.R;

public class phone_verify extends AppCompatActivity {
    TextView btnXacNhanCodeFromEmail ;
    ImageView btn_back_forgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone_verify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnXacNhanCodeFromEmail = findViewById(R.id.btn_XacNhan_Code_verify);
        btn_back_forgotPass = findViewById(R.id.btn_back_forgotPass);
        btnXacNhanCodeFromEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changPassword = new Intent(phone_verify.this, chang_password.class);
                startActivity(changPassword);
            }
        });
        btn_back_forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot_password = new Intent(phone_verify.this, forgot_password.class);
                startActivity(forgot_password);
            }
        });
    }
}