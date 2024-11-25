package com.example.foodtrack.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Fragment.fragment_product_detail;
import com.example.foodtrack.Fragment.fragment_product_detail_API;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recyclerView_mon_moi_API_adapter extends RecyclerView.Adapter<recyclerView_mon_moi_API_adapter.MyViewHolder> {


    Context context;
    List<SanPhamAPIModel> list;
    SharedPreferences sharedPreferencesDonHang;

    public recyclerView_mon_moi_API_adapter(Context context, List<SanPhamAPIModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_home_page_mon_moi, parent, false);

        sharedPreferencesDonHang = context.getSharedPreferences("dataDonHangResponse", context.MODE_PRIVATE);
        return new recyclerView_mon_moi_API_adapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamAPIModel product = list.get(position);
//        Log.d("Product", "Product Title: " + product.getTitle());
        holder.title.setText(product.getTenSanPham());

        NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
        String formattedPrice = formatter.format(product.getGiaTien());
        formattedPrice = formattedPrice + "vnđ";
        holder.price.setText(formattedPrice);

        String imageUrl = product.getImages();
        if (imageUrl.startsWith("http://")) {
            imageUrl = imageUrl.replace("http://", "https://");
        }

//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.icon_food2)
//                .error(R.drawable.icon_food1)
//                .into(holder.img);
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.img.setBackground(new BitmapDrawable(context.getResources(), resource));

                    }
                });

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("idSanPham",product.getIdSanPham());
                bundle.putString("title", holder.title.getText().toString());
                bundle.putString("price", holder.price.getText().toString());
                bundle.putString("description", "Mô tả món ăn/đồ uống");
                bundle.putString("image", product.getImages());
                fragment_product_detail_API productDetailsFragment = fragment_product_detail_API.newInstance(
                        product.getIdSanPham(),
                        holder.title.getText().toString(),
                        product.getGiaTien(),
                        "Mô tả món ăn/đồ uống",
                        product.getImages()
                );
                MainActivity mainActivity = (MainActivity) context;
                if (mainActivity != null)
                    mainActivity.ReplaceFragment(productDetailsFragment);

            }
        });

        holder.btn_AddToCart_banChay_monMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();

                ChiTietDonHangAPIModel ctdh = new ChiTietDonHangAPIModel();

                ctdh.setIdSanPham(product.getIdSanPham());
                ctdh.setSoLuongDat(1);
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

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_listview_sanpham);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout container;
        TextView title, price, btn_AddToCart_banChay_monMoi;
        ConstraintLayout img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title_product);
            price = itemView.findViewById(R.id.item_price_product);
            img = itemView.findViewById(R.id.item_image_product);
            container = itemView.findViewById(R.id.container_item_ban_chay_mon_moi);
            btn_AddToCart_banChay_monMoi = itemView.findViewById(R.id.btn_AddToCart_banChay_monMoi);
        }
    }

    private void CreatePopup(View view) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

