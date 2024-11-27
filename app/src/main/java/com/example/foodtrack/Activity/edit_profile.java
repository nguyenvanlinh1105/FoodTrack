package com.example.foodtrack.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Helper.RealPathUtil;
import com.example.foodtrack.Model.API.NguoiDungAPIModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_profile extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    ImageView btn_back;
    TextView luuBtn_editProfile;
    Uri mUri;

    TextView edt_HoTen, edt_sdt, edt_email, edt_gioiTinh, edt_ngaySinh, edt_diaChi,btn_doiAnh;
    ImageView imgCalendar , img_avt ;
    SharedPreferences shareUserResponse;
    String idUser;
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
        shareUserResponse = getApplicationContext().getSharedPreferences("shareUserResponseLogin", MODE_PRIVATE);
        idUser = shareUserResponse.getString("idUser","-1");
        Mapping();
        GetInfoUser(idUser);// null crash
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
        btn_doiAnh = findViewById(R.id.btn_doiAnh);
        img_avt = findViewById(R.id.img_avt);

        luuBtn_editProfile = (TextView) findViewById(R.id.luuBtn_editProfile);
    }

    public void ControlButton() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_doiAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });


        luuBtn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ các trường
                String hoTen = edt_HoTen.getText().toString().trim();
                String sdt = edt_sdt.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String gioiTinh = edt_gioiTinh.getText().toString().trim();
                String ngaySinh = edt_ngaySinh.getText().toString().trim();
                String diaChi = edt_diaChi.getText().toString().trim();

                // Kiểm tra và xử lý thông tin (tuỳ chọn)
                if (hoTen.isEmpty() || sdt.isEmpty() || email.isEmpty() || gioiTinh.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty()) {
                    Toast.makeText(edit_profile.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }





                RequestBody bodyIdNguoiDung = RequestBody.create(MediaType.parse("multipart/form-data"), idUser);

                RequestBody bodyHoTen = RequestBody.create(MediaType.parse("multipart/form-data"), hoTen);
                RequestBody bodySdt = RequestBody.create(MediaType.parse("multipart/form-data"), sdt);
                RequestBody bodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"), email);
                RequestBody bodyGioiTinh = RequestBody.create(MediaType.parse("multipart/form-data"), gioiTinh);
                RequestBody bodyNgaySinh = RequestBody.create(MediaType.parse("multipart/form-data"), ngaySinh);
                RequestBody bodyDiaChi = RequestBody.create(MediaType.parse("multipart/form-data"), diaChi);

                String realPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
                File file = new File(realPath);
                RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData("img", file.getName(), requestBodyAvt);


                APIService.API_SERVICE.ChangInfoUser(bodyIdNguoiDung,bodyHoTen, bodySdt, bodyEmail, bodyGioiTinh, bodyNgaySinh, bodyDiaChi,multipartBodyAvt).enqueue(new Callback<NguoiDungAPIModel>() {
                    @Override
                    public void onResponse(Call<NguoiDungAPIModel> call, Response<NguoiDungAPIModel> response) {
                        if(response.isSuccessful()){
                            NguoiDungAPIModel model = response.body();
                            edt_HoTen.setText(model.getHoTenNguoiDung());
                            edt_sdt.setText(model.getSdt());
                            edt_email.setText(model.getEmail());
                            edt_gioiTinh.setText(model.getGioiTinh());
                            edt_ngaySinh.setText(model.getNgaySinh());
                            edt_diaChi.setText(model.getDiaChi());

                            String imageUrl = model.getAvatar();
                            if (imageUrl.startsWith("http://")) {
                                imageUrl = imageUrl.replace("http://", "https://");
                            }

                            Glide.with(getApplicationContext())
                                    .asBitmap()
                                    .load(imageUrl)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }

                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            img_avt.setImageDrawable(new BitmapDrawable(getApplicationContext().getResources(), resource));

                                        }
                                    });


                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<NguoiDungAPIModel> call, Throwable t) {

                    }
                });



                //  ChangInfoUser(nguoiDung);
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
                   // String formattedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    String formattedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;

                    edt_ngaySinh.setText(formattedDate);
                },
                year,
                month,
                day
        );

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

//    private void ChangInfoUser(NguoiDungAPIModel model){
//        APIService.API_SERVICE.ChangInfoUser(model.getHoTen(), model.getSdt(), model.getEmail(), model.getGioiTinh(), model.getNgaySinh(), model.getDiaChi(),).enqueue(new Callback<NguoiDungAPIModel>() {
//            @Override
//            public void onResponse(Call<NguoiDungAPIModel> call, Response<NguoiDungAPIModel> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<NguoiDungAPIModel> call, Throwable t) {
//
//            }
//        });
//    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] requestPermission = {android.Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(requestPermission, MY_REQUEST_CODE);
            }
        } else { // Android 6 to 12
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] requestPermission = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(requestPermission, MY_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
    }

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();// kết quả nhận được khi chọn ảnh
                    mUri = uri;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        img_avt.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );


    private void GetInfoUser(String idUser){
        APIService.API_SERVICE.GetInfoUser(idUser).enqueue(new Callback<NguoiDungAPIModel>() {
            @Override
            public void onResponse(Call<NguoiDungAPIModel> call, Response<NguoiDungAPIModel> response) {
                if(response.isSuccessful()){
                    NguoiDungAPIModel model = response.body();
                    edt_HoTen.setText(model.getHoTenNguoiDung());
                    edt_sdt.setText(model.getSdt());
                    edt_email.setText(model.getEmail());
                    edt_gioiTinh.setText(model.getGioiTinh());
                    edt_ngaySinh.setText(model.getNgaySinh());
                    edt_diaChi.setText(model.getDiaChi());

                    String imageUrl = model.getAvatar();
                    if (imageUrl.startsWith("http://")) {
                        imageUrl = imageUrl.replace("http://", "https://");
                    }

                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(imageUrl)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }

                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    img_avt.setImageDrawable(new BitmapDrawable(getApplicationContext().getResources(), resource));

                                }
                            });


                }
            }

            @Override
            public void onFailure(Call<NguoiDungAPIModel> call, Throwable t) {

            }
        });
    }


}

