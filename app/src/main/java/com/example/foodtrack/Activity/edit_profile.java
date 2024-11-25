package com.example.foodtrack.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
import com.example.foodtrack.Model.API.NguoiDungAPIModel;
import com.example.foodtrack.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_profile extends AppCompatActivity {
    ImageView btn_back;
    TextView luuBtn_editProfile;

    TextView edt_HoTen, edt_sdt, edt_email, edt_gioiTinh, edt_ngaySinh, edt_diaChi;
    ImageView imgCalendar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        ControlButton();
    }

    public void Mapping() {
        btn_back = (ImageView) findViewById(R.id.btn_back_editProfile);
        edt_HoTen = findViewById(R.id.edt_hoTen);
        edt_sdt = findViewById(R.id.edt_sdt);
        edt_email = findViewById(R.id.edt_email);
        edt_ngaySinh = findViewById(R.id.edt_ngaySinh);
        edt_gioiTinh = findViewById(R.id.edt_gioiTinh);
        edt_diaChi = findViewById(R.id.edt_diaChi);
        imgCalendar = findViewById(R.id.imgCalendar);

        luuBtn_editProfile = (TextView) findViewById(R.id.luuBtn_editProfile);
    }

    public void ControlButton() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        luuBtn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(edit_profile.this, "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
            }
        });
        imgCalendar.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Cập nhật ngày vào EditText
                    String formattedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edt_ngaySinh.setText(formattedDate);
                },
                year,
                month,
                day
        );

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void ChangInfoUser(NguoiDungAPIModel nguoiDungAPIModel){
        APIService.API_SERVICE.ChangInfoUser(nguoiDungAPIModel).enqueue(new Callback<NguoiDungAPIModel>() {
            @Override
            public void onResponse(Call<NguoiDungAPIModel> call, Response<NguoiDungAPIModel> response) {

            }

            @Override
            public void onFailure(Call<NguoiDungAPIModel> call, Throwable t) {

            }
        });
    }
}

