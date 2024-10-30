package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtrack.Model.BinhLuanSanPhamModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.util.List;

public class recyclerView_product_rating_adapter extends RecyclerView.Adapter<recyclerView_product_rating_adapter.MyViewHolder>{

    Context context;
    List<BinhLuanSanPhamModel> list;

    public recyclerView_product_rating_adapter(Context context, List<BinhLuanSanPhamModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public recyclerView_product_rating_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_product_rating, parent, false);
        return new recyclerView_product_rating_adapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerView_product_rating_adapter.MyViewHolder holder, int position) {
        BinhLuanSanPhamModel cmt = list.get(position);

        NguoiDungModel user = cmt.getNguoiDung();

        holder.name.setText(user.getHoTenNguoiDung());
        holder.date .setText(cmt.getNgayBinhLuan().toString());
        Glide.with(context).load(user.getAvatar()).into(holder.img);
        holder.content.setText(cmt.getNoiDung());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_listview_sanpham);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout main;
        TextView name, date, content;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_ten_nguoiDung_product_rating);
            date = itemView.findViewById(R.id.tv_dateComment_productRating);
            content = itemView.findViewById(R.id.tv_contentComment_product_rating);
            img = itemView.findViewById(R.id.img_ava_User_product_rating);
            main = itemView.findViewById(R.id.main_comment_item_product_detail);

        }
    }
}