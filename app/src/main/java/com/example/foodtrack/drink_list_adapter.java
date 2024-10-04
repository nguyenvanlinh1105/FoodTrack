package com.example.foodtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class drink_list_adapter extends BaseAdapter {

    Context context;
    ArrayList<String> drinkTitle;
    ArrayList<String> drinkPrice;
    ArrayList<Integer> drinkImg;
    LayoutInflater inflater;
    public drink_list_adapter (ArrayList<String > drinkTitle , ArrayList<String> drinkPrice, ArrayList<Integer> img){
        drinkPrice = drinkPrice;
        drinkTitle = drinkTitle;
        drinkImg = img;
    }

    @Override
    public int getCount() {
        return drinkTitle.size();
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
            view = inflater.inflate(R.layout.fragment_drink_list, viewGroup, false);
        }
        TextView title = (TextView) view.findViewById(R.id.item_title_drink);
        TextView price = (TextView) view.findViewById(R.id.item_price_drink);
        ImageView img = (ImageView) view.findViewById(R.id.item_image_drink);
        TextView addToCartBtn = (TextView) view.findViewById(R.id.add_to_cart_btn);

        title.setText(drinkTitle.get(i));
        price.setText(drinkPrice.get(i));
        img.setImageResource(drinkImg.get(i));

        return view;
    }
}
