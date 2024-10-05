package com.example.foodtrack;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class fragment_myorders_history_list_adapter extends BaseAdapter {

    Context context;
    ArrayList<String> orderId;
    ArrayList<String> time;
    ArrayList<Integer> img;
    ArrayList<String> rate;
    ArrayList<Integer> status;
    ArrayList<String> orderStatus;
    LayoutInflater inflater;

    public fragment_myorders_history_list_adapter(Context context, ArrayList<String> orderId,  ArrayList<String> time,  ArrayList<Integer> img, ArrayList<String> rate, ArrayList<Integer> status, ArrayList<String> orderStatus){
        this.context = context;
        this.orderId = orderId;
        this.time = time;
        this.img = img;
        this.rate = rate;
        this.status = status;
        this.orderStatus = orderStatus;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderId.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_myorders_history_list, viewGroup, false);
        }

        // Gán các view từ layout
        TextView idView = view.findViewById(R.id.order_id_myorders_history_list);
        TextView timeView = view.findViewById(R.id.time_myorders_ongoing_list);
        ImageView imgView = view.findViewById(R.id.img_myorders_history_list);
        TextView ratingBtn = view.findViewById(R.id.ratingBtn_myorders_history_list);
        TextView orderStatusView = view.findViewById(R.id.giao_hay_chua_myorders_history_list);

        idView.setText(orderId.get(i));
        timeView.setText(time.get(i));
        imgView.setImageResource(img.get(i));
        orderStatusView.setText(orderStatus.get(i));

        if (status.get(i) == 0) {
            ratingBtn.setText("Đánh giá ngay");
            ratingBtn.setTextColor(Color.parseColor("#FFFFFF"));
            ratingBtn.setBackgroundResource(R.drawable.less_radius_btn_bg_orange);
        } else {
            ratingBtn.setText("Đã đánh giá");
            ratingBtn.setTextColor(Color.parseColor("#ff5e00"));
            ratingBtn.setBackgroundResource(R.drawable.less_radius_btn_bg_white);
        }

        return view;
    }

}
