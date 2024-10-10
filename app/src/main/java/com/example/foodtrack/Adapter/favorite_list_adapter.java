package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodtrack.R;

import java.util.ArrayList;

public class favorite_list_adapter extends BaseAdapter {


    Context context;
    ArrayList<String> favoriteTitle;
    ArrayList<Integer> favoriteImg;
    ArrayList<String> favoriteSubTitle;
    ArrayList<String> favoritePrice;
    LayoutInflater inflater;

    public favorite_list_adapter(Context context, ArrayList<String> title, ArrayList<Integer> img, ArrayList<String> subTitle, ArrayList<String> price) {
        this.context = context;
        this.favoriteTitle = title;
        this.favoriteImg = img;
        this.favoriteSubTitle = subTitle;
        this.favoritePrice = price;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return favoriteTitle.size();
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
            view = inflater.inflate(R.layout.fragment_favorite_list, viewGroup, false);
        }

        TextView title = (TextView) view.findViewById(R.id.item_title_favorite);
        TextView subTitle = (TextView) view.findViewById(R.id.item_subTitle_favorite);
        ImageView img = (ImageView) view.findViewById(R.id.item_image_favorite);
        TextView price = (TextView) view.findViewById(R.id.price_favorite);
        LinearLayout deleteBtn = (LinearLayout) view.findViewById(R.id.deleteBtn_favorite);

        title.setText(favoriteTitle.get(i));
        subTitle.setText(favoriteSubTitle.get(i));
        img.setImageResource(favoriteImg.get(i));
        price.setText(favoritePrice.get(i));

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeProduct(i);
                notifyDataSetChanged();
            }
        });



        return view;
    }

    private void removeProduct(int i) {
        favoriteTitle.remove(i);
        favoriteSubTitle.remove(i);
        favoriteImg.remove(i);
        favoritePrice.remove(i);
    }
}
