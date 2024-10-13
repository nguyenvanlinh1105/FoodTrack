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
import com.example.foodtrack.Model.Product;
import com.example.foodtrack.R;

import java.util.List;

public class recyclerView_mon_moi_ban_chay_adapter extends RecyclerView.Adapter<recyclerView_mon_moi_ban_chay_adapter.MyViewHolder> {


    Context context;
    List<Product> list;

    public recyclerView_mon_moi_ban_chay_adapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_home_page_ban_chay_mon_moi, parent, false);
        return new recyclerView_mon_moi_ban_chay_adapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = list.get(position);
//        Log.d("Product", "Product Title: " + product.getTitle());
        holder.title.setText(product.getTitle());
        holder.price.setText(product.getPrice());

        Glide.with(context).load(product.getImg()).into(holder.img);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title",holder.title.getText().toString());
                bundle.putString("price",holder.price.getText().toString());
                bundle.putString("description", "Mô tả món ăn/đồ uống");
                bundle.putInt("image", product.getImg());
                fragment_product_detail productDetailsFragment = fragment_product_detail.newInstance(
                        holder.title.getText().toString(),
                        holder.price.getText().toString(),
                        "Mô tả món ăn/đồ uống",
                        product.getImg()
                );
                MainActivity mainActivity = (MainActivity) context;
                if (mainActivity != null)
                    mainActivity.ReplaceFragment(productDetailsFragment);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout container;
        TextView title, price;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title_product);
            price = itemView.findViewById(R.id.item_price_product);
            img = itemView.findViewById(R.id.item_image_product);
            container = itemView.findViewById(R.id.container_item_ban_chay_mon_moi);
        }
    }

}
