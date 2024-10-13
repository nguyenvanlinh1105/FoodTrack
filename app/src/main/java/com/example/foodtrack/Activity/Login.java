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

public class Login extends AppCompatActivity {

    ImageView btn_back;
    TextView btnDangKiFormLogin;
    TextView btnQuenMatkhau;
    TextView btnLogin_TK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_back =  findViewById(R.id.btn_back);
        btnDangKiFormLogin =findViewById(R.id.btnDangKi_formLogin);
        btnQuenMatkhau = findViewById(R.id.btnQuenMatKhau);
        btnLogin_TK = findViewById(R.id.btn_Login_TK);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent firstpage = new Intent(Login.this, first_page.class);
                startActivity(firstpage);
                finish();
            }
        });
        btnDangKiFormLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formDangKi = new Intent(Login.this, Register.class);
                startActivity(formDangKi);
                finish();
            }
        });
        btnQuenMatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formquenMK = new Intent(Login.this, forgot_password.class);
                startActivity(formquenMK);
            }
        });
        btnLogin_TK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Login.this, MainActivity.class);
                startActivity(home);
                finish();
            }
        });





    }
}