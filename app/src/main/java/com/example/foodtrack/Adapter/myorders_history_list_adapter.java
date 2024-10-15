package com.example.foodtrack.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Model.Order;
import com.example.foodtrack.Fragment.fragment_rating_comment;
import com.example.foodtrack.R;

import java.util.ArrayList;

public class myorders_history_list_adapter extends ArrayAdapter<Order> {
    public myorders_history_list_adapter(Context context, ArrayList<Order> arrayListOrder) {
        super(context, R.layout.fragment_myorders_history_list, arrayListOrder);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Order order = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_myorders_history_list, parent, false);
        }

        TextView id = view.findViewById(R.id.item_id_myOrders);
        TextView time = view.findViewById(R.id.time_item_myOrders);
        TextView name = view.findViewById(R.id.name_item_myOrders);
        ImageView img = view.findViewById(R.id.img_item_myOrders);
        TextView ratingBtn = view.findViewById(R.id.ratingBtn_item_myOrders);
        TextView status = view.findViewById(R.id.tinhTrang_item_myOrders);

        if (order != null) {
            id.setText(order.getId());
            time.setText(order.getCreatedAt());
            name.setText(order.getName());
            img.setImageResource(order.getImg());
            status.setText(order.getStatus());

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
