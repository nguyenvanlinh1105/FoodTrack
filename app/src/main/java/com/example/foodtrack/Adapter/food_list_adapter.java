package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtrack.Model.productModel;
import com.example.foodtrack.R;

import java.util.ArrayList;

public class food_list_adapter extends ArrayAdapter<productModel> {
    public food_list_adapter(Context context, ArrayList<productModel> arraylistFood) {
        super(context, R.layout.fragment_food_drink_item, arraylistFood);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        productModel food = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_food_drink_item, parent, false);
        }

        TextView title = view.findViewById(R.id.item_title_product);
        TextView price = view.findViewById(R.id.item_price_product);
        ImageView img = view.findViewById(R.id.item_image_product);
        TextView description = view.findViewById(R.id.description_product_item);
        TextView addToCartBtn = view.findViewById(R.id.add_to_cart_btn);

        if (food != null) {
            img.setImageResource(food.getImg());
            title.setText(food.getTitle());
            description.setText(food.getDescription());
            price.setText(food.getPrice());
        }
        return view;
    }
}
