package com.example.foodtrack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class myorders_ongoing_list_adapter_api extends ArrayAdapter<DonHangAPIModel> {
    public myorders_ongoing_list_adapter_api(Context context, List<DonHangAPIModel> arrayListDonHang) {
        super(context, R.layout.fragment_myorders_ongoing_list, arrayListDonHang);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        DonHangAPIModel donHang = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_myorders_ongoing_list, parent, false);
        }

        TextView id = view.findViewById(R.id.item_id_myOrders);
        TextView time = view.findViewById(R.id.time_item_myOrders);
        TextView name = view.findViewById(R.id.ten_mon_myOrders);
        ImageView img = view.findViewById(R.id.img_item_myOrders);
        TextView status = view.findViewById(R.id.tinhTrang_item_myOrders);
        TextView qty = view.findViewById(R.id.qty_myOrders);
        TextView price = view.findViewById(R.id.price_myOrders);

        if (donHang != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

            NumberFormat nf
                    = NumberFormat.getInstance(Locale.ITALY);

            id.setText(donHang.getIdDonHang());

            time.setText(dateFormat.format(donHang.getNgayTao().getTime()));

            status.setText(donHang.getTinhTrang());
            qty.setText(String.valueOf(donHang.getChiTietDonHangs().size()));

            int totalQty = 0;
            int totalPrice = 0;

            List<ChiTietDonHangAPIModel> chiTietDonHangs = donHang.getChiTietDonHangs();
            if (chiTietDonHangs != null && !chiTietDonHangs.isEmpty()) {

                ChiTietDonHangAPIModel first  = chiTietDonHangs.get(0);
                SanPhamAPIModel sanPhamFirst = first.getProduct();
                String imageUrl = sanPhamFirst.getImages();
                if (imageUrl.startsWith("http://")) {
                    imageUrl = imageUrl.replace("http://", "https://");
                }

                Glide.with(getContext())
                        .asBitmap()
                        .load(imageUrl)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }

                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                img.setImageDrawable(new BitmapDrawable(getContext().getResources(), resource));
                            }
                        });

                for (ChiTietDonHangAPIModel chiTietDonHang : chiTietDonHangs) {
                    SanPhamAPIModel sanPham = chiTietDonHang.getProduct();
                    int soLuong = chiTietDonHang.getSoLuongDat();
                    totalQty += soLuong;

                    if (sanPham != null) {
                        totalPrice += sanPham.getGiaTien() * soLuong;
                    }
                }
                qty.setText(String.valueOf(totalQty));
                price.setText(nf.format(totalPrice) + "vnđ");
                name.setText(sanPhamFirst.getTenSanPham());
            }

        }
        return view;
    }
}

