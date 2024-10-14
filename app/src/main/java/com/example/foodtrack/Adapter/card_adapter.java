package com.example.foodtrack.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.foodtrack.R;

import java.util.ArrayList;

public class card_adapter extends BaseAdapter {

    Context context;
    private ArrayList<Integer> imgs;
    private LayoutInflater inflater;

    public card_adapter(Context context, ArrayList<Integer> imgs) {
        this.context = context;
        this.imgs = imgs;
        inflater = LayoutInflater.from(context);
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
            view = inflater.inflate(R.layout.fragment_card_item, viewGroup, false);

        }

        ImageView img = (ImageView) view.findViewById(R.id.card_img_item);

        img.setImageResource(imgs.get(i));

        return view;
    }
}
