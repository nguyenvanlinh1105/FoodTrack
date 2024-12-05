package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Adapter.food_list_adapter;
import com.example.foodtrack.Adapter.list_drink_API_adapter;
import com.example.foodtrack.Adapter.recyclerView_deal_hoi_API_adapter;
import com.example.foodtrack.Adapter.recyclerView_product_detail_adapter;
import com.example.foodtrack.Adapter.recyclerView_product_detail_adapter_api;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.Model.SanPhamYeuThichModel;
import com.example.foodtrack.R;
import com.google.gson.JsonSyntaxException;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_product_detail_API extends Fragment {
    private static final String ARG_ID = "idSanPham";
    private static final String ARG_TITLE = "title";
    private static final String ARG_PRICE = "price";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE = "image";
    private static final String ARG_SOLUONGDABAN = "soluong";

    private static String idSanPham;
    private static String title;
    private static String price;
    private static String description;
    private static String image; // Thay đổi kiểu dữ liệu thành String
    private static Integer soLuongDaBan; // Thay đổi kiểu dữ liệu thành String

    String idUser;
    boolean isFavorite = false;

    private ImageView btn_back_product_detail;
    private ImageView btn_rating_product_details;
    private ImageView btn_favorite_check_product_detail;
    private ImageView btn_minus_product_detail;
    private ImageView btn_plus_product_detail;
    private TextView Text_quantity_product;
    private int quantity;
    private TextView btn_AddToCart_product_detail;

    private List<SanPhamAPIModel> listProduct;
    private RecyclerView rvProductDetail, rvDealHoi;

    SharedPreferences sharedPreferencesDonHang;


    public fragment_product_detail_API() {
        // Required empty public constructor
    }

    public static fragment_product_detail_API newInstance(String idSanPham, String title, Double price, String description, String image, int soLuongDaBan) {
        fragment_product_detail_API fragment = new fragment_product_detail_API();
        Bundle args = new Bundle();
        args.putString(ARG_ID, idSanPham);
        args.putString(ARG_TITLE, title);
        args.putDouble(ARG_PRICE, price);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_IMAGE, image);
        args.putInt(ARG_SOLUONGDABAN, soLuongDaBan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesDonHang = getContext().getSharedPreferences("dataDonHangResponse", getContext().MODE_PRIVATE);
        quantity = 1;
        if (getArguments() != null) {
            idSanPham = getArguments().getString(ARG_ID);
            title = getArguments().getString(ARG_TITLE);
            price = String.valueOf(getArguments().getDouble(ARG_PRICE));
            description = getArguments().getString(ARG_DESCRIPTION);
            image = getArguments().getString(ARG_IMAGE); // Lấy giá trị image từ URL
            soLuongDaBan = getArguments().getInt(ARG_SOLUONGDABAN);

        }


        // reset đơn hàng
//        SharedPreferences.Editor editorResponseDonHang = sharedPreferencesDonHang.edit();
//        editorResponseDonHang.putString("idDonHang", null);
//        editorResponseDonHang.apply();
    }

    private void InitializeData() {
        listProduct = new ArrayList<>();
        listProduct.add(new SanPhamAPIModel("Cơm tấm", 70000.0, "https://example.com/com_tam.png", "Cơm tấm bì nướng"));
        listProduct.add(new SanPhamAPIModel("Chuối tươi", 30000.0, "https://example.com/chuoi_tuoi.png", "Chuối sứ"));
        listProduct.add(new SanPhamAPIModel("Burger phô mai", 30000.0, "https://example.com/burger_phomai.png", "Burger phô mai cổ điển"));
        listProduct.add(new SanPhamAPIModel("Pizza hải sản", 120000.0, "https://example.com/pizza_seafood.png", "Pizza hải sản cao cấp"));
        listProduct.add(new SanPhamAPIModel("Sushi cá hồi", 50000.0, "https://example.com/sushi_ca_hoi.png", "Sushi cá hồi tươi"));
        listProduct.add(new SanPhamAPIModel("Mì Ý sốt bò bằm", 45000.0, "https://example.com/mi_y.png", "Mì Ý sốt bò bằm truyền thống"));
        listProduct.add(new SanPhamAPIModel("Nước cam ép", 20000.0, "https://example.com/nuoc_cam_ep.png", "Nước cam ép tươi"));
        listProduct.add(new SanPhamAPIModel("Cà phê sữa đá", 25000.0, "https://example.com/ca_phe_sua_da.png", "Cà phê sữa đá truyền thống"));
        listProduct.add(new SanPhamAPIModel("Trà sữa trân châu", 35000.0, "https://example.com/tra_sua.png", "Trà sữa trân châu đường đen"));
        listProduct.add(new SanPhamAPIModel("Salad rau củ", 30000.0, "https://example.com/salad_rau_cu.png", "Salad rau củ tươi mát"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        Mapping(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            Double price = bundle.getDouble("price");
            String description = bundle.getString("description");
            String imageUrl = bundle.getString("image");
//            Integer soLuongDaBan = bundle.getInt("soLuongDaBan");

            TextView titleView = view.findViewById(R.id.title_product_details);
            TextView priceView = view.findViewById(R.id.price_product_details);
            TextView descriptionView = view.findViewById(R.id.description_product_detail);
            ImageView imageView = view.findViewById(R.id.image_product_details);
            TextView soLuongView = view.findViewById(R.id.tv_soLuongDaBan_product_detail);

            NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
            String formattedPrice = formatter.format(price) + "vnđ";

            titleView.setText(title);
            priceView.setText(formattedPrice);
            descriptionView.setText(description);
            soLuongView.setText(String.valueOf(soLuongDaBan));
//            Glide.with(this).load(imageUrl).into(imageView);
            //nhớ chuyển http -> https
            Glide.with(getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageDrawable(new BitmapDrawable(getContext().getResources(), resource));

                        }
                    });
        }

        GetMonAnBenPhai();


       // deal hời
        GetDealHoi();
        GetTrangThaiYeuThich(idUser,idSanPham);
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        btn_back_product_detail = view.findViewById(R.id.btn_back_product_detail);
        btn_rating_product_details = view.findViewById(R.id.button_rating_comment_product_details);
        btn_favorite_check_product_detail = view.findViewById(R.id.btn_favorite_check_product_detail);
        btn_plus_product_detail = view.findViewById(R.id.btn_plus_product_detail);
        btn_minus_product_detail = view.findViewById(R.id.btn_minus_product_detail);
        Text_quantity_product = view.findViewById(R.id.Text_quantity_product);
        btn_AddToCart_product_detail = view.findViewById(R.id.btn_AddToCart_product_detail);


        // Lấy idUser từ SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("idUser", "-1"); // -1 là giá trị mặc định nếu không tìm thấy
        rvProductDetail = view.findViewById(R.id.recyclerView_product_detail);


//        InitializeData();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
//        rvProductDetail.setLayoutManager(layoutManager);
//        recyclerView_product_detail_adapter dealAdapter = new recyclerView_product_detail_adapter(getContext(), listProduct);
//        rvProductDetail.setAdapter(dealAdapter);

        rvDealHoi = view.findViewById(R.id.recyclerView_deal_hoi_product_detail);
    }

    private void ControlButton() {
        btn_back_product_detail.setOnClickListener(view -> requireActivity().getSupportFragmentManager().popBackStack());

        btn_rating_product_details.setOnClickListener(view -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                fragment_product_rating ratingFragment = new fragment_product_rating();

                Bundle bundle = new Bundle();
                bundle.putString("idSanPham", idSanPham);
                bundle.putString("tenSanPham", title);
                bundle.putString("image", image);

                // Đặt bundle vào fragment
                ratingFragment.setArguments(bundle);

                mainActivity.ReplaceFragment(ratingFragment);
            }
        });


        btn_favorite_check_product_detail.setOnClickListener(view -> {
            if (!isFavorite) {

                SanPhamYeuThichModel model = new SanPhamYeuThichModel();
                model.setIdNguoiDung(idUser);
                model.setIdSanPham(idSanPham);
                ThemSanPhamYeuThichModel(model);
                btn_favorite_check_product_detail.setImageResource(R.drawable.icon_fill_heart_48);
                isFavorite = true;

            } else {
                SanPhamYeuThichModel model = new SanPhamYeuThichModel();
                model.setIdNguoiDung(idUser);
                model.setIdSanPham(idSanPham);
                BoSanPhamYeuThichModel(model);
                btn_favorite_check_product_detail.setImageResource(R.drawable.icon_heart_48);
                isFavorite = false;
            }
        });
        btn_minus_product_detail.setOnClickListener(view -> {
            quantity = Integer.parseInt(Text_quantity_product.getText().toString());
            if (quantity > 0) quantity--;
            Text_quantity_product.setText(String.valueOf(quantity));
        });

        btn_plus_product_detail.setOnClickListener(view -> {
            quantity = Integer.parseInt(Text_quantity_product.getText().toString());
            quantity++;
            Text_quantity_product.setText(String.valueOf(quantity));
        });

        btn_AddToCart_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
                ChiTietDonHangAPIModel ctdh = new ChiTietDonHangAPIModel();


                ctdh.setIdSanPham(idSanPham);
                ctdh.setSoLuongDat(quantity);
                String idDonHang = sharedPreferencesDonHang.getString("idDonHang", "");
             //   Log.d("idDonHang",idDonHang);
                if (idDonHang != null) {
                    ctdh.setIdDonHang(idDonHang);
                }


                if (idUser != "-1") {
                    ctdh.setIdUser(idUser);
                    PostSanPhamToGioHang(ctdh);
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy ID người dùng", Toast.LENGTH_SHORT).show();
                }
                CreatePopup(view);
            }
        });

    }

    private void GetMonAnBenPhai(){
        APIService.API_SERVICE.getListMonAn_Explore().enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                if(response.isSuccessful()&& response.body()!=null &&!response.body().isEmpty()){
                    List<SanPhamAPIModel> listMonAn_explore = response.body();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    rvProductDetail.setLayoutManager(layoutManager);
                    recyclerView_product_detail_adapter_api listAdapter = new recyclerView_product_detail_adapter_api(getContext(), listMonAn_explore);
                    rvProductDetail.setAdapter(listAdapter);
                }else{
//                    UseFallbackData();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamAPIModel>> call, Throwable t) {
//                UseFallbackData();
            }
        });
    }

    private void GetDealHoi(){
        APIService.API_SERVICE.getListSanphamHomePage_DealHoi().enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SanPhamAPIModel> listSanPhamDeaHoi = response.body();
                    Log.d("API_SUCCESS", "Data size: " + listSanPhamDeaHoi.size());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                    rvDealHoi.setLayoutManager(layoutManager);
                    recyclerView_deal_hoi_API_adapter dealAdapter = new recyclerView_deal_hoi_API_adapter(getContext(), listSanPhamDeaHoi);
                    rvDealHoi.setAdapter(dealAdapter);

                } else {
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call<List<SanPhamAPIModel>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                if (t instanceof JsonSyntaxException) {
                    JsonSyntaxException jsonError = (JsonSyntaxException) t;
                    Log.e("API_ERROR", "JSON Error: " + jsonError.getCause());
                }
                t.printStackTrace();
                Toast.makeText(getContext(), "Lỗi dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CreatePopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        View popupView = inflater.inflate(R.layout.popup_add_to_cart, null);

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        view.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
        int delay = 1100;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, delay);

    }

    private void PostSanPhamToGioHang(ChiTietDonHangAPIModel ctdh) {

        APIService.API_SERVICE.PostToBuyProduct(ctdh).enqueue(new Callback<ChiTietDonHangAPIModel>() {
            @Override
            public void onResponse(Call<ChiTietDonHangAPIModel> call, Response<ChiTietDonHangAPIModel> response) {
                ChiTietDonHangAPIModel ctdh = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    SharedPreferences.Editor editorResponseDonHang = sharedPreferencesDonHang.edit();
                    if (ctdh.getIdDonHang()!=null) {
                        editorResponseDonHang.putString("idDonHang", ctdh.getIdDonHang());
                        editorResponseDonHang.apply();
                    } else {

                    }

                //    Toast.makeText(getContext(), ctdh.getIdDonHang() + "", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getContext(), "Thêm vào giỏ hàng thất bại, vui lòng thủ lại nhé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietDonHangAPIModel> call, Throwable t) {

            }
        });
    }

    private void ThemSanPhamYeuThichModel(SanPhamYeuThichModel model) {
        APIService.API_SERVICE.ThemSanPhamYeuThichModel(model).enqueue(new Callback<SanPhamYeuThichModel>() {
            @Override
            public void onResponse(Call<SanPhamYeuThichModel> call, Response<SanPhamYeuThichModel> response) {

            }

            @Override
            public void onFailure(Call<SanPhamYeuThichModel> call, Throwable t) {

            }
        });
    }

    private void BoSanPhamYeuThichModel(SanPhamYeuThichModel model) {
        APIService.API_SERVICE.BoSanPhamYeuThichModel(model).enqueue(new Callback<SanPhamYeuThichModel>() {
            @Override
            public void onResponse(Call<SanPhamYeuThichModel> call, Response<SanPhamYeuThichModel> response) {

            }

            @Override
            public void onFailure(Call<SanPhamYeuThichModel> call, Throwable t) {

            }
        });
    }

    private void GetTrangThaiYeuThich(String idNguoiDung , String idSanPham){
        APIService.API_SERVICE.GetTrangThaiYeuThich(idNguoiDung, idSanPham).enqueue(new Callback<SanPhamYeuThichModel>() {
            @Override
            public void onResponse(Call<SanPhamYeuThichModel> call, Response<SanPhamYeuThichModel> response) {
                if(response.isSuccessful()){

                    Log.d("isLove",response.body().isLove()+"");
                    if(response.body().isLove()==true){
                        btn_favorite_check_product_detail.setImageResource(R.drawable.icon_fill_heart_48);
                        isFavorite = true;
                    }else{
                        btn_favorite_check_product_detail.setImageResource(R.drawable.icon_heart_48);
                        isFavorite = false;
                    }


                }else{

                }
            }

            @Override
            public void onFailure(Call<SanPhamYeuThichModel> call, Throwable t) {

            }
        });
    }
}






