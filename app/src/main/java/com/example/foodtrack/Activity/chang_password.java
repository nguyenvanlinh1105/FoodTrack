package com.example.foodtrack.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.foodtrack.Model.API.NguoiDungAPIModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class chang_password extends AppCompatActivity {
    ImageView btn_back_phone_verify;
    TextView btn_xacNhanDoi_MK;
    public  String email, emailBackup;
    SharedPreferences shareLogin ;

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
        shareLogin = getSharedPreferences("shareUserResponseLogin", MODE_PRIVATE);
        emailBackup = shareLogin.getString("email","vanlinh11052004nguyenvan@gmail.com");
        TextView edtmatkhau = findViewById(R.id.edt_confirm_chang_password);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             email = bundle.getString("email");

        }
        btn_back_phone_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_xacNhanDoi_MK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chang_password.this, "Bạn đã cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                Intent firstPage = new Intent(chang_password.this, first_page.class);
//                startActivity(firstPage);
                finish();

                String matkhau = edtmatkhau.getText().toString().trim();

                NguoiDungModel user = new NguoiDungModel();
                user.setMatKhau(matkhau);
                if(email==null){

                }else{
                    user.setEmail(email);
                }
                if(user.getEmail()==null){

                    if(emailBackup==null){

                    }else{
                        user.setEmail(emailBackup);
                    }
                }




                PostEmailAndPassToReset(user);

            }
        });
    }
    private void PostEmailAndPassToReset(NguoiDungModel user){
        APIService.API_SERVICE.PostToResetPass(user).enqueue(new Callback<NguoiDungModel>() {
            @Override
            public void onResponse(Call<NguoiDungModel> call, Response<NguoiDungModel> response) {
                if(response.isSuccessful()){
                    NguoiDungModel nguoiDungModel = response.body();
                    NguoiDungAPIModel userModel = new NguoiDungAPIModel();

                    userModel.setEmail(nguoiDungModel.getEmail());
                    userModel.setMatKhau(nguoiDungModel.getMatKhau());
                    GetUserToLogin(userModel);
                }

                // thông báo đổi mật khẩu thành công
            }
            @Override
            public void onFailure(Call<NguoiDungModel> call, Throwable t) {

            }
        });
    }
    public void GetUserToLogin(NguoiDungAPIModel userModel) {
        APIService.API_SERVICE.GetUserToLogin(userModel).enqueue(new Callback<NguoiDungAPIModel>() {
            @Override
            public void onResponse(Call<NguoiDungAPIModel> call, Response<NguoiDungAPIModel> response) {
                if (response.code() == 200) { // Kiểm tra status code
                    NguoiDungAPIModel responseUserModel = response.body();
                    if (responseUserModel != null && "Đăng nhập thành công".equals(responseUserModel.getMessage())) {
                        Intent home = new Intent(chang_password.this, MainActivity.class);
                        startActivity(home);
                        finish();
                    } else {
                        Toast.makeText(chang_password.this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                    }
                } else {
                  //  Toast.makeText(chang_password.this, "Đăng nhập thất bại với mã lỗi " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NguoiDungAPIModel> call, Throwable t) {
               // Toast.makeText(chang_password.this, "Đăng nhập thất bại, thử lại bằng email và password", Toast.LENGTH_LONG).show();
            }
        });
    }
}