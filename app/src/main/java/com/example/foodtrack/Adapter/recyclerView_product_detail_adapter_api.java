package com.example.foodtrack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Fragment.fragment_product_detail_API;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.R;

import java.util.List;

public class recyclerView_product_detail_adapter_api extends RecyclerView.Adapter<recyclerView_product_detail_adapter_api.MyViewHolder> {

    private final Context context;
    private final List<SanPhamAPIModel> productList;

    public recyclerView_product_detail_adapter_api(Context context, List<SanPhamAPIModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_product_detail, parent, false);


        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamAPIModel product = productList.get(position);

        // Set product details
        holder.title.setText(product.getTenSanPham());
        Log.d("tenLinhLINh",product.getTenSanPham());
        holder.price.setText(String.format("%,.0f vnđ", product.getGiaTien()));
        holder.description.setText(product.getMoTa());

        // Load image with Glide
        String imageUrl = product.getImages().replace("http://", "https://");
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.img.setImageDrawable(new BitmapDrawable(context.getResources(), resource));

                    }
                });

        // Set onClickListener for product container
        holder.container.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("soLuongDaBan", product.getSoLuongDaBan());
            fragment_product_detail_API productDetailsFragment = fragment_product_detail_API.newInstance(
                    product.getIdSanPham(),
                    product.getTenSanPham(),
                    product.getGiaTien(),
                    product.getMoTa(),
                    product.getImages(),
                    product.getSoLuongDaBan()
            );

            if (context instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.ReplaceFragment(productDetailsFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout container;
        TextView title, price, description;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_ten_mon_product_detail);
            price = itemView.findViewById(R.id.tv_item_gia_mon_product_detail);
            description = itemView.findViewById(R.id.tv_item_mo_ta_product_detail);
            img = itemView.findViewById(R.id.image_item_product_detail);
            container = itemView.findViewById(R.id.main_card_view_product_detail_rv);
        }
    }
}
