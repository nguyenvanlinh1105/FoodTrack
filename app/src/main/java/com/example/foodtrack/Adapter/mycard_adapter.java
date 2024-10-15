package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.foodtrack.R;

import java.util.ArrayList;

public class mycard_adapter extends BaseAdapter {

    Context context;
    private ArrayList<Integer> imgs;
    private LayoutInflater inflater;
    private int selectedPosition = -1;

    public mycard_adapter(Context context, ArrayList<Integer> imgs) {
        this.context = context;
        this.imgs = imgs;
        inflater = LayoutInflater.from(context);
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int i) {
        return imgs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mycard_item, viewGroup, false);

        }
        LinearLayout container = (LinearLayout)view.findViewById(R.id.card_item_container);
        LinearLayout imgBg = (LinearLayout) view.findViewById(R.id.card_img_item);

        imgBg.setBackgroundResource(imgs.get(i));

        if (i == selectedPosition) {
            container.setBackgroundResource(R.drawable.selected_card_bg);
        } else {
            container.setBackgroundResource(0);
        }



        return view;
    }
}
