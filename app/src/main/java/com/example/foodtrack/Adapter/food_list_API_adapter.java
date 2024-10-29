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

import com.bumptech.glide.Glide;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class food_list_API_adapter extends ArrayAdapter<SanPhamAPIModel> {
    private Context context;
    public food_list_API_adapter(Context context, ArrayList<SanPhamAPIModel> arraylistFood) {
        super(context, R.layout.fragment_food_drink_item, arraylistFood);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        SanPhamAPIModel food = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_food_drink_item, parent, false);
        }

        TextView title = view.findViewById(R.id.item_title_product);
        TextView price = view.findViewById(R.id.item_price_product);
        ImageView img = view.findViewById(R.id.item_image_product);
//        TextView description = view.findViewById(R.id.description_product_item);
        TextView addToCartBtn = view.findViewById(R.id.btn_AddToCart_food_drink);

        if (food != null) {

            String imageUrl = food.getImages();
            if (imageUrl.startsWith("http://")) {
                imageUrl = imageUrl.replace("http://", "https://");
            }

            Glide.with(getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.icon_food2)
                    .error(R.drawable.icon_food1)
                    .into(img);
//            img.setImageResource(food.getImages());
            title.setText(food.getTenSanPham());

            NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
            String formattedPrice= formatter.format(food.getGiaTien());
            formattedPrice = formattedPrice + "vnđ";
            price.setText(formattedPrice);
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_listview_sanpham);
        view.startAnimation(animation);

        return view;
    }

}
