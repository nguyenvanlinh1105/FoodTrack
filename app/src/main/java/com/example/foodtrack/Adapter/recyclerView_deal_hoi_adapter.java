package com.example.foodtrack.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtrack.Model.Product;
import com.example.foodtrack.R;

import java.util.List;

public class recyclerView_deal_hoi_adapter extends RecyclerView.Adapter<recyclerView_deal_hoi_adapter.MyViewHolder> {

    Context context;
    List<Product> list;

    public recyclerView_deal_hoi_adapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_hoi_homepage_item, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = list.get(position);
//        Log.d("Product", "Product Title: " + product.getTitle());
        holder.title.setText(product.getTitle());
        holder.price.setText(product.getPrice());
        Glide.with(context).load(product.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_deal_hoi_item);
            price = itemView.findViewById(R.id.price_deal_hoi_item);
            img = itemView.findViewById(R.id.img_deal_hoi_item);

        }
    }
}
