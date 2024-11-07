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

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView btn_back_login = findViewById(R.id.btn_back_login);
        TextView  btn_guiMa_Verify = findViewById(R.id.btn_GuiMa_verifyTK);

        TextView edt_email =findViewById(R.id.edtMail_forgotPassword);


        btn_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent loginform = new Intent(forgot_password.this, Login.class);
//                startActivity(loginform);
                finish();
            }
        });
        btn_guiMa_Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent loginform = new Intent(forgot_password.this, phone_verify.class);
//                startActivity(loginform);

                String email = edt_email.getText().toString().trim();

                NguoiDungModel user = new NguoiDungModel();
                user.setEmail(email);
                postEmailToLogin(user);
            }
        });

    }

    private void postEmailToLogin(NguoiDungModel user){

        APIService.API_SERVICE.PostEmailToLogin(user).enqueue(new Callback<NguoiDungModel>() {
            @Override
            public void onResponse(Call<NguoiDungModel> call, Response<NguoiDungModel> response) {
                if (response.code() == 200) {
                        Intent home = new Intent(forgot_password.this, phone_verify.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("email", user.getEmail());
                        home.putExtras(bundle);
                        startActivity(home);
                        finish();
            }
            }

            @Override
            public void onFailure(Call<NguoiDungModel> call, Throwable t) {

            }
        });
    }
}