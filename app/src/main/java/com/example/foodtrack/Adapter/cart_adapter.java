package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodtrack.R;

import java.util.ArrayList;

public class cart_adapter extends BaseAdapter {

    Context context;
    ArrayList<String> cartTitle;
    ArrayList<Integer> cartImg;
    ArrayList<String> cartSubTitle;
    ArrayList<String> cartPrice;
    LayoutInflater inflater;

    public cart_adapter(Context context, ArrayList<String> cartTitle, ArrayList<Integer> cartImg, ArrayList<String> cartSubTitle, ArrayList<String> cartPrice) {
        this.context = context;
        this.cartTitle = cartTitle;
        this.cartImg = cartImg;
        this.cartSubTitle = cartSubTitle;
        this.cartPrice = cartPrice;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartTitle.size();
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
            view = inflater.inflate(R.layout.fragment_cart_item, viewGroup, false);
        }

        TextView title = (TextView) view.findViewById(R.id.item_title_cart);
        TextView subTitle = (TextView) view.findViewById(R.id.item_subTitle_cart);
        ImageView img = (ImageView) view.findViewById(R.id.item_image_cart);
        TextView price = (TextView) view.findViewById(R.id.price_cart);

        TextView btn_plus_cart = (TextView)view.findViewById(R.id.btn_plus_cart);
        TextView btn_minus_cart = (TextView)view.findViewById(R.id.btn_minus_cart);
        TextView qty = (TextView)view.findViewById(R.id.qty_cart);

        title.setText(cartTitle.get(i));
        subTitle.setText(cartSubTitle.get(i));
        img.setImageResource(cartImg.get(i));
        price.setText(cartPrice.get(i));

        qty.setText("1");

        btn_plus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtyNum = Integer.parseInt(qty.getText().toString());
                qtyNum ++;
                qty.setText(String.valueOf(qtyNum));
            }
        });

        btn_minus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtyNum = Integer.parseInt(qty.getText().toString());
                qtyNum --;
                if (qtyNum <= 0){
                    removeProduct(i);
                    notifyDataSetChanged();
                }
                else {
                    qty.setText(String.valueOf(qtyNum));
                }

            }
        });
        return view;
    }

    private void removeProduct(int i) {
        cartTitle.remove(i);
        cartSubTitle.remove(i);
        cartImg.remove(i);
        cartPrice.remove(i);
    }
}
