package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodtrack.Activity.cart;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.R;

import java.util.List;

public class cart_adapter_api extends BaseAdapter {

    Context context;
    List<SanPhamAPIModel> arrayListSanPham;
    LayoutInflater inflater;
    cart activityCart;

    public cart_adapter_api(Context context, List<SanPhamAPIModel> arrayListSanPham, cart activityCart) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
        this.activityCart = activityCart;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayListSanPham.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListSanPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_cart_item, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.item_title_cart);
            holder.subTitle = convertView.findViewById(R.id.item_subTitle_cart);
            holder.img = convertView.findViewById(R.id.item_image_cart);
            holder.price = convertView.findViewById(R.id.price_cart);
            holder.qty = convertView.findViewById(R.id.qty_cart);
            holder.btn_plus_cart = convertView.findViewById(R.id.btn_plus_cart);
            holder.btn_minus_cart = convertView.findViewById(R.id.btn_minus_cart);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SanPhamAPIModel product = arrayListSanPham.get(i);

        // Set data to views
        holder.title.setText(product.getTenSanPham());
        holder.subTitle.setText(product.getMoTa());
        holder.price.setText(product.getGiaTien() + "đ");
        holder.qty.setText(String.valueOf(product.getSoluongBH()));

        Glide.with(context)
                .load(product.getImages())
                .into(holder.img);

        // Handle button actions
        holder.btn_plus_cart.setOnClickListener(view -> updateQuantity(i, holder.qty, 1));
        holder.btn_minus_cart.setOnClickListener(view -> updateQuantity(i, holder.qty, -1));

        return convertView;
    }

    private void updateQuantity(int position, TextView qtyView, int change) {
        SanPhamAPIModel product = arrayListSanPham.get(position);
        int qtyNum = product.getSoluongBH() + change;

        if (qtyNum <= 0) {
            removeProduct(position);
        } else {
            product.setSoluongBH(qtyNum);
            qtyView.setText(String.valueOf(qtyNum));
        }
        activityCart.updateTotalPrice();
        notifyDataSetChanged();
    }

    private void removeProduct(int position) {
        arrayListSanPham.remove(position);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView img;
        TextView price;
        TextView qty;
        TextView btn_plus_cart;
        TextView btn_minus_cart;
    }
}
