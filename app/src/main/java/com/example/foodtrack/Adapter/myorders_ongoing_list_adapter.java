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

import com.example.foodtrack.Model.Order;
import com.example.foodtrack.R;

import java.util.ArrayList;
import java.util.List;

public class myorders_ongoing_list_adapter extends ArrayAdapter<Order> {
    public myorders_ongoing_list_adapter(Context context, ArrayList<Order> arrayListOrder) {
        super(context, R.layout.fragment_myorders_ongoing_list, arrayListOrder);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Order order = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_myorders_ongoing_list, parent, false);
        }

        TextView id = view.findViewById(R.id.item_id_myOrders);
        TextView time = view.findViewById(R.id.time_item_myOrders);
        TextView name = view.findViewById(R.id.name_item_myOrders);
        ImageView img = view.findViewById(R.id.img_item_myOrders);
        TextView status = view.findViewById(R.id.tinhTrang_item_myOrders);
        TextView qty = view.findViewById(R.id.qty_myOrders);
        TextView price = view.findViewById(R.id.price_myOrders);

        if (order != null) {
            id.setText(order.getId());
            time.setText(order.getCreatedAt());
            name.setText(order.getName());
            img.setImageResource(order.getImg());
            status.setText(order.getStatus());
            price.setText(order.getPrice());
            qty.setText(String.valueOf(order.getQty()));


        }
        return view;
    }
}
