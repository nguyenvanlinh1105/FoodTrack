package com.example.foodtrack.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.cart;
import com.example.foodtrack.Fragment.fragment_product_detail_API;
import com.example.foodtrack.Fragment.product_detail_change_info;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cart_adapter_api extends BaseAdapter {

    Context context;
    public List<SanPhamAPIModel> arrayListSanPham;
    LayoutInflater inflater;
    cart activityCart;

    SharedPreferences userResponse;
    SharedPreferences donHangResponse;

    public cart_adapter_api(Context context, List<SanPhamAPIModel> arrayListSanPham, cart activityCart) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
     //   this.arrayListSanPham = activityCart.list
        this.activityCart = activityCart;
        inflater = LayoutInflater.from(context);

        // Khởi tạo SharedPreferences sau khi context đã được truyền vào
        userResponse = context.getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
        donHangResponse = context.getSharedPreferences("dataDonHangResponse", Context.MODE_PRIVATE);
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
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_cart_item, parent, false);
        }

        TextView title = view.findViewById(R.id.item_title_cart);
        ImageView img = view.findViewById(R.id.item_image_cart);
        TextView price = view.findViewById(R.id.price_cart);
        TextView qty = view.findViewById(R.id.qty_cart);
        TextView btn_plus_cart = view.findViewById(R.id.btn_plus_cart);
        TextView btn_minus_cart = view.findViewById(R.id.btn_minus_cart);
        ImageView btn_XoaCTDH = view.findViewById(R.id.btn_XoaCTDH);


        SanPhamAPIModel product = arrayListSanPham.get(i);

        // Set data to views
        title.setText(product.getTenSanPham());
        double giaTien = product.getGiaTien();
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault()); // Sử dụng Locale mặc định
        String formattedPrice = numberFormat.format(giaTien);

        price.setText(formattedPrice + " vnđ");
        qty.setText(String.valueOf(product.getSoLuongDat()));

        Glide.with(context)
                .load(product.getImages())
                .into(img);


        String idUser = userResponse.getString("idUser", "-1");
        String idDonHang = donHangResponse.getString("idDonHang", "");
        ChiTietDonHangAPIModel model = new ChiTietDonHangAPIModel();
        model.setIdDonHang(idDonHang);
        model.setIdSanPham(product.getIdSanPham());

        // Handle button actions
        btn_plus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int qtyNum = product.getSoLuongDat() + 1;
//                product.setSoLuongDat(qtyNum);
//                qty.setText(String.valueOf(qtyNum));
//                activityCart.updateTotalPrice();
//                notifyDataSetChanged();


                Bundle bundle = new Bundle();
                bundle.putString("idSanPham", product.getIdSanPham());
                bundle.putString("title", product.getTenSanPham());
                bundle.putDouble("price", product.getGiaTien());
                bundle.putString("description", "Mô tả món ăn/đồ uống");
                bundle.putString("image", product.getImages());
                bundle.putInt("qty", product.getSoLuongDat());

                Intent detail = new Intent(context, MainActivity.class);
                detail.putExtra("fragmentToLoad", "product_detail_change_info");
                detail.putExtra("productBundle", bundle);
                context.startActivity(detail);

            }
        });

        btn_minus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int qtyNum = product.getSoLuongDat() - 1;
//                if(qtyNum == 0){
//                    removeProductByModel(model);
//                    XoaSanPhamGioHang(model);
//                    activityCart.updateTotalPrice();
//                }
//                else{
//                    product.setSoLuongDat(qtyNum);
//                    qty.setText(String.valueOf(qtyNum));
//                    activityCart.updateTotalPrice();
//                    notifyDataSetChanged();
//                }

//                product_detail_change_info productDetailsFragment = product_detail_change_info.newInstance(
//                        product.getIdSanPham(),
//                        product.getTenSanPham(),
//                        product.getGiaTien(),
//                        "Mô tả món ăn/đồ uống",
//                        product.getImages(),
//                        Double.valueOf(product.getSoLuongDat())
//                );

                Bundle bundle = new Bundle();
                bundle.putString("idSanPham", product.getIdSanPham());
                bundle.putString("title", product.getTenSanPham());
                bundle.putDouble("price", product.getGiaTien());
                bundle.putString("description", "Mô tả món ăn/đồ uống");
                bundle.putString("image", product.getImages());
                bundle.putInt("qty", product.getSoLuongDat());

                Intent detail = new Intent(context, MainActivity.class);
                detail.putExtra("fragmentToLoad", "product_detail_change_info");
                detail.putExtra("productBundle", bundle);
                context.startActivity(detail);

            }
        });

        btn_XoaCTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeProductByModel(model);
                activityCart.updateButtonVisibility();
                XoaSanPhamGioHang(model);
            }
        });

        return view;
    }

    private void removeProduct(int position) {
        arrayListSanPham.remove(position);
        notifyDataSetChanged();
    }

    private void XoaSanPhamGioHang(ChiTietDonHangAPIModel model) {
        APIService.API_SERVICE.XoaSanPhamGioHang(model).enqueue(new Callback<ChiTietDonHangAPIModel>() {
            @Override
            public void onResponse(Call<ChiTietDonHangAPIModel> call, Response<ChiTietDonHangAPIModel> response) {
                if (response.isSuccessful()) {

                } else {
                    // Xử lý lỗi nếu xóa không thành công
                }
            }

            @Override
            public void onFailure(Call<ChiTietDonHangAPIModel> call, Throwable t) {
                // Xử lý lỗi khi không kết nối được với API
            }
        });
    }

    private void removeProductByModel(ChiTietDonHangAPIModel model) {
        // Loại bỏ sản phẩm từ danh sách nếu cần
        for (int i = 0; i < arrayListSanPham.size(); i++) {
            if (arrayListSanPham.get(i).getIdSanPham().equals(model.getIdSanPham())) {
                activityCart.tongTien -= Double.valueOf(arrayListSanPham.get(i).getGiaTien()) * Integer.valueOf(arrayListSanPham.get(i).getSoLuongDat());
                NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault()); // Sử dụng Locale mặc định
                String formattedPrice = numberFormat.format(activityCart.tongTien);
                activityCart.total.setText(formattedPrice + " vnđ");
                arrayListSanPham.remove(i);
                break;
            }
        }


        notifyDataSetChanged();  // Cập nhật lại Adapter sau khi xóa

    }

}
