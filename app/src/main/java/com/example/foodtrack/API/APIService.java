package com.example.foodtrack.API;

import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

  //    linkAPI root:
    String url ="https://387b-2a09-bac5-d468-e6-00-17-34b.ngrok-free.app/";
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
    @GET("sanpham/dealhoi")
    Call<SanPhamModel> getListSanphamHomePage_DealHoi();
    // dùng để list sản phẩm banchay
    @GET("sanpham/banchay")
    Call<SanPhamModel> getListSanphamHomePage_BanChay();
  // dùng để list sản phẩm monmoi
    @GET("sanpham/monmoi")
    Call<SanPhamModel> getListSanphamHomePage_MonMoi();

    // Explore
  // dùng để lấy các món ăn
    @GET("sanpham/monan")
    Call<SanPhamModel> getListMonAn_Explore();
    // dùng đẻ lấy các thức uống
    @GET("sanpham/douong")
    Call<SanPhamModel>getListDoUong_Explore();

    // Tìm sản phẩm chi tiết
    @GET("sanpham/{id}")
    Call<SanPhamModel>getChiTietSanPham(@Query("idSanPham")String idSanPham);


}


