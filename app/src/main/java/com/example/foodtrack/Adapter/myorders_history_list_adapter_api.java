package com.example.foodtrack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Fragment.fragment_rating_comment;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class myorders_history_list_adapter_api extends ArrayAdapter<ChiTietDonHangAPIModel> {
    public myorders_history_list_adapter_api(Context context, List<ChiTietDonHangAPIModel> arrayListOrder) {
        super(context, R.layout.fragment_myorders_history_list, arrayListOrder);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ChiTietDonHangAPIModel order = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_myorders_history_list, parent, false);
        }

        TextView id = view.findViewById(R.id.item_id_myOrders);
        TextView time = view.findViewById(R.id.time_item_myOrders);
        TextView name = view.findViewById(R.id.name_item_myOrders);
        ImageView img = view.findViewById(R.id.img_item_myOrders);
        TextView ratingBtn = view.findViewById(R.id.ratingBtn_item_myOrders);
//        TextView status = view.findViewById(R.id.tinhTrang_item_myOrders);
        TextView qty = view.findViewById(R.id.qty_myOrders);
        TextView price = view.findViewById(R.id.price_myOrders);
        TextView donViTinh = view.findViewById(R.id.donViTinh);

        if (order != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            id.setText(order.getIdDonHang());


            NumberFormat nf
                    = NumberFormat.getInstance(Locale.ITALY);


            time.setText(dateFormat.format(order.getNgayTao().getTime()));

            name.setText(order.getSanPham().getTenSanPham());
            String imageUrl = order.getProduct().getImages();
            if (imageUrl.startsWith("http://")) {
                imageUrl = imageUrl.replace("http://", "https://");
            }

            Glide.with(getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            img.setImageDrawable(new BitmapDrawable(getContext().getResources(), resource));
                        }
                    });




            double tongTien = order.getProduct().getGiaTien() * order.getSoLuongDat();
            price.setText( nf.format(tongTien) + "vnđ");
            qty.setText(String.valueOf(order.getSoLuongDat()));
            donViTinh.setText("Đơn vị tính:" + order.getProduct().getDonViTinh());

            if (order.getHasComment() == 0) {
                ratingBtn.setText("Đánh giá ngay");
                ratingBtn.setTextColor(Color.parseColor("#FFFFFF"));
                ratingBtn.setBackgroundResource(R.drawable.less_radius_btn_bg_orange);
            } else {
                ratingBtn.setText("Đã đánh giá");
                ratingBtn.setTextColor(Color.parseColor("#ff5e00"));
                ratingBtn.setBackgroundResource(R.drawable.less_radius_btn_bg_white);
            }

        }

        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBtn.getText() == "Đánh giá ngay") {
                    MainActivity mainActivity = (MainActivity) getContext();
                    if (mainActivity != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("idSanPham", order.getProduct().getIdSanPham());
                        bundle.putString("idDonHang", order.getIdDonHang());
                        bundle.putString("image", order.getProduct().getImages());
                        fragment_rating_comment fragment = new fragment_rating_comment();
                        fragment.setArguments(bundle);

                        mainActivity.ReplaceFragment(fragment);
                    }
                }
            }
        });

        return view;
    }




}
