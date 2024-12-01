package com.example.foodtrack.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.foodtrack.Activity.cart;
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
    List<SanPhamAPIModel> arrayListSanPham;
    LayoutInflater inflater;
    cart activityCart;

    SharedPreferences userResponse ;
    SharedPreferences donHangResponse ;

    public cart_adapter_api(Context context, List<SanPhamAPIModel> arrayListSanPham, cart activityCart) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
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
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_cart_item, parent, false);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.item_title_cart);
            holder.img = convertView.findViewById(R.id.item_image_cart);
            holder.price = convertView.findViewById(R.id.price_cart);
            holder.qty = convertView.findViewById(R.id.qty_cart);
            holder.btn_plus_cart = convertView.findViewById(R.id.btn_plus_cart);
            holder.btn_minus_cart = convertView.findViewById(R.id.btn_minus_cart);
            holder.btn_XoaCTDH = convertView.findViewById(R.id.btn_XoaCTDH);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SanPhamAPIModel product = arrayListSanPham.get(i);

        // Set data to views
        holder.title.setText(product.getTenSanPham());
        double giaTien = product.getGiaTien();
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault()); // Sử dụng Locale mặc định
        String formattedPrice = numberFormat.format(giaTien);

        holder.price.setText(formattedPrice + " vnđ");
        holder.qty.setText(String.valueOf(product.getSoLuongDat()));

        Glide.with(context)
                .load(product.getImages())
                .into(holder.img);

        // Handle button actions
        holder.btn_plus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtyNum = product.getSoLuongDat() + 1;

                product.setSoLuongDat(qtyNum);
                product.setSoluongBH(qtyNum);
                holder.qty.setText(String.valueOf(qtyNum));
                activityCart.updateTotalPrice();
                notifyDataSetChanged();
            }
        });
        holder.btn_minus_cart.setOnClickListener(view -> updateQuantity(i, holder.qty, -1));

        String idUser = userResponse.getString("idUser","-1");
        String idDonHang = donHangResponse.getString("idDonHang","");
        ChiTietDonHangAPIModel model = new ChiTietDonHangAPIModel();
        model.setIdDonHang(idDonHang);
        model.setIdSanPham(product.getIdSanPham());

        holder.btn_XoaCTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeProductByModel(model);
                XoaSanPhamGioHang(model);
            }
        });

        return convertView;
    }

    private void updateQuantity(int position, TextView qtyView, int change) {
        // Kiểm tra vị trí hợp lệ
        if (position < 0 || position >= arrayListSanPham.size()) {
            // Log lỗi để dễ kiểm tra
            Log.e("updateQuantity", "Invalid position: " + position);
            return;
        }

    }


    private void removeProduct(int position) {
        arrayListSanPham.remove(position);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView title;
        ImageView img;
        TextView price;
        TextView qty;
        TextView btn_plus_cart;
        TextView btn_minus_cart;
        ImageView btn_XoaCTDH;
    }

    private void XoaSanPhamGioHang(ChiTietDonHangAPIModel model) {
        APIService.API_SERVICE.XoaSanPhamGioHang(model).enqueue(new Callback<ChiTietDonHangAPIModel>() {
            @Override
            public void onResponse(Call<ChiTietDonHangAPIModel> call, Response<ChiTietDonHangAPIModel> response) {
                if (response.isSuccessful()) {
//
//                    activityCart.updateTotalPrice();
//                    notifyDataSetChanged();
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
                activityCart.total.setText(formattedPrice+" vnđ");
                arrayListSanPham.remove(i);
                break;
            }
        }


        notifyDataSetChanged();  // Cập nhật lại Adapter sau khi xóa

    }

}
