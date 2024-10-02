package com.example.foodtrack;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class food_list_adapter extends BaseAdapter {

    Context context;
    ArrayList<String> foodTitle;
    ArrayList<String> foodPrice;
    ArrayList<Integer> foodImg;
    LayoutInflater inflater;

    public food_list_adapter(Context context,  ArrayList<String> title, ArrayList<String> price, ArrayList<Integer> img){
        this.context = context;
        this.foodTitle = title;
        this.foodPrice = price;
        this.foodImg = img;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodTitle.size();
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
            view = inflater.inflate(R.layout.fragment_food_list, viewGroup, false);
        }
        TextView title = (TextView) view.findViewById(R.id.item_title_food);
        TextView price = (TextView) view.findViewById(R.id.item_price_food);
        ImageView img = (ImageView) view.findViewById(R.id.item_image_food);
        TextView addToCartBtn = (TextView) view.findViewById(R.id.add_to_cart_btn);

        title.setText(foodTitle.get(i));
        price.setText(foodPrice.get(i));
        img.setImageResource(foodImg.get(i));

        return view;
    }
}
