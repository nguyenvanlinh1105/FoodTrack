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


public class list_drink_adapter extends ArrayAdapter<productModel> {

    public list_drink_adapter(Context context, ArrayList<productModel> arrayListDrink) {
        super(context, R.layout.fragment_food_drink_item, arrayListDrink);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        productModel drink = getItem(position); // Lấy đối tượng Drink ở vị trí hiện tại

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_food_drink_item, parent, false);
        }

        TextView title = view.findViewById(R.id.item_title_product);
        TextView price = view.findViewById(R.id.item_price_product);
        ImageView img = view.findViewById(R.id.item_image_product);
        TextView description = view.findViewById(R.id.description_product_item);
        TextView addToCartBtn = view.findViewById(R.id.add_to_cart_btn);

        if (drink != null) {
            img.setImageResource(drink.getImg());
            title.setText(drink.getTitle());
            description.setText(drink.getDescription());
            price.setText(drink.getPrice());
        }

        return view;
    }
}
