package com.example.foodtrack.API;

import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

  //    linkAPI root:
    String url ="https://3aa1-2a09-bac5-d46a-16d2-00-246-bf.ngrok-free.app/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:sss").create();
    APIService API_SERVICE = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    // hàm này dùng để login , gửi email và pass word , api trả về code và message.
    @POST("user/login")
    Call<NguoiDungModel> GetUserToLogin(@Body NguoiDungModel userModel);

    // hàm này dùng để singin đăng kí tài khoản , api trả về ....
    @POST("user/register")
    Call<NguoiDungModel> PostUserToSingin(@Body NguoiDungModel userModel);


    // Home_Page
    // dùng để list sản phẩm deal hời
    @GET("food/bargain")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_DealHoi();
    // dùng để list sản phẩm banchay
    @GET("/food/bestseller")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_BanChay();
  // dùng để list sản phẩm monmoi
    @GET("sanpham/monmoi")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_MonMoi();

    // Explore
  // dùng để lấy các món ăn
    @GET("food/new")
    Call<List<SanPhamAPIModel>> getListMonAn_Explore();
    // dùng đẻ lấy các thức uống
    @GET("sanpham/douong")
    Call<List<SanPhamAPIModel>>getListDoUong_Explore();

    // Tìm sản phẩm chi tiết
    @GET("sanpham/{id}")
    Call<SanPhamAPIModel>getChiTietSanPham(@Query("idSanPham")String idSanPham);

    @POST("/user/password/forgot")
    Call<NguoiDungModel>PostEmailToLogin(@Body NguoiDungModel userModel);

    @POST("/user/password/otp")
    Call<NguoiDungModel>PostEmailandOPTLogin(@Body NguoiDungModel userModel);

    @POST("/user/password/reset")
    Call<NguoiDungModel>PostToResetPass(@Body NguoiDungModel userModel);




}


