package com.example.foodtrack.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtrack.Model.ChiTietDonHangModel;
import com.example.foodtrack.Model.DonHangModel;
import com.example.foodtrack.Model.Order;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class myorders_ongoing_list_adapter extends ArrayAdapter<DonHangModel> {
    public myorders_ongoing_list_adapter(Context context, ArrayList<DonHangModel> arrayListDonHang) {
        super(context, R.layout.fragment_myorders_ongoing_list, arrayListDonHang);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        DonHangModel donHang = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_myorders_ongoing_list, parent, false);
        }

        TextView id = view.findViewById(R.id.item_id_myOrders);
        TextView time = view.findViewById(R.id.time_item_myOrders);
//        TextView name = view.findViewById(R.id.name_item_myOrders);
        ImageView img = view.findViewById(R.id.img_item_myOrders);
        TextView status = view.findViewById(R.id.tinhTrang_item_myOrders);
        TextView qty = view.findViewById(R.id.qty_myOrders);
        TextView price = view.findViewById(R.id.price_myOrders);

        if (donHang != null) {
            //Number format để định dạng giá tiền từ 20000 thành 20.000
            NumberFormat nf
                    = NumberFormat.getInstance(Locale.ITALY);

            id.setText(donHang.getIdDonHang());
            time.setText(donHang.getNgayTao().toString());
//            name.setText(donHang.getName());
            status.setText(donHang.getTinhTrang());
            qty.setText(String.valueOf(donHang.getChiTietDonHangs().size()));

            int totalQty = 0;
            int totalPrice = 0;

            List<ChiTietDonHangModel> chiTietDonHangs = donHang.getChiTietDonHangs();
            if (chiTietDonHangs != null && !chiTietDonHangs.isEmpty()) {

                ChiTietDonHangModel first  = chiTietDonHangs.get(0);
                SanPhamModel sanPhamFirst = first.getSanPham();
                img.setImageResource(sanPhamFirst.getImages());

                for (ChiTietDonHangModel chiTietDonHang : chiTietDonHangs) {
                    SanPhamModel sanPham = chiTietDonHang.getSanPham();
                    int soLuong = chiTietDonHang.getSoLuongDat();
                    totalQty += soLuong;

                    if (sanPham != null) {
                        totalPrice += sanPham.getGiaTien() * soLuong;
                    }
                }
                qty.setText(String.valueOf(totalQty));
                price.setText(nf.format(totalPrice) + "vnđ");

            }

        }
        return view;
    }
}

