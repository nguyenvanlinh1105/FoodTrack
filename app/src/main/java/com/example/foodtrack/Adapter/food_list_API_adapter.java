package com.example.foodtrack.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class food_list_API_adapter extends ArrayAdapter<SanPhamAPIModel> {
    private Context context;

    SharedPreferences sharedPreferencesDonHang ;
    public food_list_API_adapter(Context context, ArrayList<SanPhamAPIModel> arraylistFood) {
        super(context, R.layout.fragment_food_drink_item, arraylistFood);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        SanPhamAPIModel food = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_food_drink_item, parent, false);
        }
        sharedPreferencesDonHang = context.getSharedPreferences("sharedPreferencesDonHang",Context.MODE_PRIVATE);
        TextView title = view.findViewById(R.id.item_title_product);
        TextView price = view.findViewById(R.id.item_price_product);
        ConstraintLayout img = view.findViewById(R.id.item_image_product);
//        TextView description = view.findViewById(R.id.description_product_item);
        TextView addToCartBtn = view.findViewById(R.id.btn_AddToCart_food_drink);

        if (food != null) {

            String imageUrl = food.getImages();
            if (imageUrl.startsWith("http://")) {
                imageUrl = imageUrl.replace("http://", "https://");
            }

//            Glide.with(getContext())
//                    .load(imageUrl)
//                    .placeholder(R.drawable.icon_food2)
//                    .error(R.drawable.icon_food1)
//                    .into(img);
            Glide.with(getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            img.setBackground(new BitmapDrawable(context.getResources(), resource));

                        }
                    });
            title.setText(food.getTenSanPham());

            NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
            String formattedPrice= formatter.format(food.getGiaTien());
            formattedPrice = formattedPrice + "vnđ";
            price.setText(formattedPrice);
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
                ChiTietDonHangAPIModel ctdh = new ChiTietDonHangAPIModel();


                ctdh.setIdSanPham(food.getIdSanPham());
                ctdh.setSoLuongDat(5);
                String idDonHang= sharedPreferencesDonHang.getString("idDonHang","");
                if(idDonHang !=null){
                    ctdh.setIdDonHang(idDonHang);
                }
                // Lấy idUser từ SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
                String idUser = sharedPreferences.getString("idUser", "-1"); // -1 là giá trị mặc định nếu không tìm thấy
                if (idUser != "-1") {
                    ctdh.setIdUser(idUser);
                    PostSanPhamToGioHang(ctdh);
                } else {
                    Toast.makeText(context, "Không tìm thấy ID người dùng", Toast.LENGTH_SHORT).show();
                }



                CreatePopup(view);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_listview_sanpham);
        view.startAnimation(animation);

        return view;
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


    private void PostSanPhamToGioHang(ChiTietDonHangAPIModel ctdh){
        APIService.API_SERVICE.PostToBuyProduct(ctdh).enqueue(new Callback<ChiTietDonHangAPIModel>() {
            @Override
            public void onResponse(Call<ChiTietDonHangAPIModel> call, Response<ChiTietDonHangAPIModel> response) {
                ChiTietDonHangAPIModel ctdh = response.body();
                if (response.isSuccessful() && response.body() != null ) {
                    SharedPreferences.Editor editorResponseDonHang = sharedPreferencesDonHang.edit();
                    if(ctdh.getIdDonHang()==null){
                        Toast.makeText(context, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }else{
                        editorResponseDonHang.putString("idDonHang",ctdh.getIdDonHang());
                    }

                    Toast.makeText(context,ctdh.getIdDonHang()+"",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietDonHangAPIModel> call, Throwable t) {

            }
        });
    }

}
