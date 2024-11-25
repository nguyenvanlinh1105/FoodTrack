package com.example.foodtrack.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Fragment.fragment_product_detail;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.util.List;

public class recyclerView_product_detail_adapter extends RecyclerView.Adapter<recyclerView_product_detail_adapter.MyViewHolder> {

    Context context;
    List<SanPhamModel> list;

    public recyclerView_product_detail_adapter(Context context, List<SanPhamAPIModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_product_detail, parent, false);
        return new recyclerView_product_detail_adapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamModel product = list.get(position);
        holder.title.setText(product.getTenSanPham());
        holder.price.setText(String.valueOf(product.getGiaTien()) + "vnđ");
        holder.description.setText(product.getMoTa());
        if (product.getImages() != 0) {
            Glide.with(context).load(product.getImages()).into(holder.img);
        }
        else {
            holder.img.setImageResource(R.drawable.drink1);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", product.getTenSanPham());
                bundle.putDouble("price", product.getGiaTien());
                bundle.putString("description", product.getMoTa());
                bundle.putInt("image", product.getImages());
                fragment_product_detail productDetailsFragment = fragment_product_detail.newInstance(
                        product.getTenSanPham(),
                        product.getGiaTien(),
                        product.getMoTa(),
                        product.getImages()
                );
                productDetailsFragment.setArguments(bundle);

                if (context instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.ReplaceFragment(productDetailsFragment);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
