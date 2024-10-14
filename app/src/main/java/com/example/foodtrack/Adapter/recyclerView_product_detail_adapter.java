package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtrack.Model.Product;
import com.example.foodtrack.R;

import java.util.List;

public class recyclerView_product_detail_adapter extends RecyclerView.Adapter<recyclerView_product_detail_adapter.MyViewHolder> {

    Context context;
    List<Product> list;

    public recyclerView_product_detail_adapter(Context context, List<Product> list) {
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
        Product product = list.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText(product.getPrice());
        Glide.with(context).load(product.getImg()).into(holder.img);
        //if (position % 2 == 0)
            holder.main.setBackgroundResource(R.drawable.input_conner);
       // else
       //     holder.main.setBackground(null);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout main;
        TextView title, price;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_deal_hoi_item);
            price = itemView.findViewById(R.id.price_deal_hoi_item);
            img = itemView.findViewById(R.id.img_deal_hoi_item);
            main = itemView.findViewById(R.id.main_card_view_product_detail_rv);

        }
    }

}
