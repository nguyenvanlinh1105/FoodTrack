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

public class phone_verify extends AppCompatActivity {
    TextView btnXacNhanCodeFromEmail ;
    ImageView btn_back_forgotPass;
    public String email="";
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

        TextView edt_otp = findViewById(R.id.edt_VerifyCode_phoneVerify);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            email = bundle.getString("email").toString();

        }


        btnXacNhanCodeFromEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent changPassword = new Intent(phone_verify.this, chang_password.class);
//                startActivity(changPassword);
                String otp = edt_otp.getText().toString().trim();

                NguoiDungModel user = new NguoiDungModel();
                user.setEmail(email);
                user.setOtp(otp);
                if(otp.isEmpty()){
                    Toast.makeText(phone_verify.this, "Vui lòng nhập mã OTP từ email",Toast.LENGTH_SHORT).show();
                }else{
                    postEmailAndOTP(user);
                }


            }
        });


        btn_back_forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void postEmailAndOTP(NguoiDungModel user){
        APIService.API_SERVICE.PostEmailandOPTLogin(user).enqueue(new Callback<NguoiDungModel>() {
            @Override
            public void onResponse(Call<NguoiDungModel> call, Response<NguoiDungModel> response) {
                Intent home = new Intent(phone_verify.this, chang_password.class);
                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                home.putExtras(bundle);
                startActivity(home);
                finish();
            }

            @Override
            public void onFailure(Call<NguoiDungModel> call, Throwable t) {

            }
        });
    }
}