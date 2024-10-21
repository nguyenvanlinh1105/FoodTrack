package com.example.foodtrack.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Fragment.fragment_product_detail;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.util.List;

public class recyclerView_mon_moi_adapter extends RecyclerView.Adapter<recyclerView_mon_moi_adapter.MyViewHolder> {


    Context context;
    List<SanPhamModel> list;

    public recyclerView_mon_moi_adapter(Context context, List<SanPhamModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_home_page_mon_moi, parent, false);
        return new recyclerView_mon_moi_adapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamModel product = list.get(position);
//        Log.d("Product", "Product Title: " + product.getTitle());
        holder.title.setText(product.getTenSanPham());
        holder.price.setText(String.valueOf(product.getGiaTien())+"vnđ");

        Glide.with(context).load(product.getImages()).into(holder.img);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title",holder.title.getText().toString());
                bundle.putString("price",holder.price.getText().toString());
                bundle.putString("description", "Mô tả món ăn/đồ uống");
                bundle.putInt("image", product.getImages());
                fragment_product_detail productDetailsFragment = fragment_product_detail.newInstance(
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
                Toast.makeText(context.getApplicationContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
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
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title_product);
            price = itemView.findViewById(R.id.item_price_product);
            img = itemView.findViewById(R.id.item_image_product);
            container = itemView.findViewById(R.id.container_item_ban_chay_mon_moi);
            btn_AddToCart_banChay_monMoi = itemView.findViewById(R.id.btn_AddToCart_banChay_monMoi);
        }
    }

}
