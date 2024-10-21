package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.util.ArrayList;

public class food_list_adapter extends ArrayAdapter<SanPhamModel> {
    public food_list_adapter(Context context, ArrayList<SanPhamModel> arraylistFood) {
        super(context, R.layout.fragment_food_drink_item, arraylistFood);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        SanPhamModel food = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_food_drink_item, parent, false);
        }

        TextView title = view.findViewById(R.id.item_title_product);
        TextView price = view.findViewById(R.id.item_price_product);
        ImageView img = view.findViewById(R.id.item_image_product);
//        TextView description = view.findViewById(R.id.description_product_item);
        TextView addToCartBtn = view.findViewById(R.id.btn_AddToCart_food_drink);

        if (food != null) {
            img.setImageResource(food.getImages());
            title.setText(food.getTenSanPham());
//            description.setText(food.getDescription());
            price.setText(String.valueOf(food.getGiaTien())+"vnđ");
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
            }
        });

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_listview_sanpham);
        view.startAnimation(animation);

        return view;
    }

}
