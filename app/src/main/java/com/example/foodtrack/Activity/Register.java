package com.example.foodtrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.foodtrack.SocketManager;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private Socket mSocket;
    private EditText edt_hoTen_signin,edt_Mail_signin,edt_SDT_signin,edt_Password_signin,edt_confirmPassword_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InitView();

//        mSocket = SocketManager.getInstance().getSocket();

        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent firstpage = new Intent(Register.this, first_page.class);
                startActivity(firstpage);
                finish();
            }
        });
        TextView btnDangNhap_formRegister = findViewById(R.id.btnDangNhap_formRegister);
        btnDangNhap_formRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
                finish();
            }
        });
        TextView  btnDangKi = findViewById(R.id.btn_DangKi_TK);
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen = edt_hoTen_signin.getText().toString().trim();
              //  String username = edt_UserName_signin.getText().toString();
                String email = edt_Mail_signin.getText().toString().trim();
                String sdt = edt_SDT_signin.getText().toString().trim();
                String password = edt_Password_signin.getText().toString().trim();

                String gioiTinh = "Nam";

//                Log.d("MatKhau",password);

                NguoiDungModel userModel = new NguoiDungModel();
                userModel.setHoTenNguoiDung(hoTen);
                userModel.setEmail(email);
                userModel.setSdt(sdt);
                userModel.setMatKhau(password);
                userModel.setGioiTinh(gioiTinh);
                PostUserToSingin(userModel);

                if(edt_hoTen_signin.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Vui lòng nhập nội dung trước khi gửi", Toast.LENGTH_SHORT).show();
                }
                else{
                    mSocket.emit("client-register-user", edt_hoTen_signin.getText().toString().trim());
//                    Toast.makeText(getContext(), "Nội dung: " + edt_chat.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void InitView(){
        edt_hoTen_signin = findViewById(R.id.edt_hoTen_signin);
        edt_Mail_signin = findViewById(R.id.edt_Mail_signin);
        edt_SDT_signin = findViewById(R.id.edt_SDT_signin);
        edt_Password_signin = findViewById(R.id.edt_Password_signin);
        edt_confirmPassword_signin = findViewById(R.id.edt_confirmPassword_signin);


    }

    private void PostUserToSingin (NguoiDungModel userModel){
        APIService.API_SERVICE.PostUserToSingin(userModel).enqueue(new Callback<NguoiDungModel>() {
            @Override
            public void onResponse(Call<NguoiDungModel> call, Response<NguoiDungModel> response) {
                Intent login = new Intent(Register.this, Login.class);
                login.putExtra("currentUsername",userModel.getHoTenNguoiDung());
//                Toast.makeText(Register.this, userModel.getHoTenNguoiDung(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Register.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                startActivity(login);
                finish();
            }

            @Override
            public void onFailure(Call<NguoiDungModel> call, Throwable t) {
                Toast.makeText(Register.this, "Đăng kí thất bại, thử lại.", Toast.LENGTH_LONG).show();
            }
        });
    }
}