package com.example.foodtrack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.example.foodtrack.Model.Order;
import com.example.foodtrack.Fragment.fragment_rating_comment;
import com.example.foodtrack.Model.OrderAPIModel;
import com.example.foodtrack.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class myorders_history_list_adapter_api extends ArrayAdapter<OrderAPIModel> {
    public myorders_history_list_adapter_api(Context context, ArrayList<OrderAPIModel> arrayListOrder) {
        super(context, R.layout.fragment_myorders_history_list, arrayListOrder);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        OrderAPIModel order = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_myorders_history_list, parent, false);
        }

        TextView id = view.findViewById(R.id.item_id_myOrders);
        TextView time = view.findViewById(R.id.time_item_myOrders);
        TextView name = view.findViewById(R.id.name_item_myOrders);
        ImageView img = view.findViewById(R.id.img_item_myOrders);
        TextView ratingBtn = view.findViewById(R.id.ratingBtn_item_myOrders);
        TextView status = view.findViewById(R.id.tinhTrang_item_myOrders);
        TextView qty = view.findViewById(R.id.qty_myOrders);
        TextView price = view.findViewById(R.id.price_myOrders);

        if (order != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            id.setText(order.getId());
            time.setText(dateFormat.format(order.getCreatedAt().getTime()));
            name.setText(order.getName());
           // img.setImageResource(order.getImg());
            String imageUrl = order.getImg();
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
                            img.setBackground(new BitmapDrawable(getContext().getResources(), resource));

                        }
                    });
            status.setText(order.getStatus());
            price.setText(order.getPrice());
            qty.setText(String.valueOf(order.getQty()));

            if (order.getRateStat() == 0) {
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
                    MainActivity mainActivity = (MainActivity)getContext();
                    if (mainActivity != null) {
                        mainActivity.ReplaceFragment(new fragment_rating_comment());
                    }
                }
            }
        });
        return view;


    }

}
