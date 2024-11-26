package com.example.foodtrack.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamYeuThichModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteListAdapterAPI extends BaseAdapter {

    private Context context;
    private List<SanPhamAPIModel> dsSanPham;
    private LayoutInflater inflater;

    public FavoriteListAdapterAPI(Context context, List<SanPhamAPIModel> dsSanPham) {
        this.context = context;
        this.dsSanPham = dsSanPham;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dsSanPham.size(); // Sử dụng danh sách sản phẩm để xác định số lượng item.
    }

    @Override
    public Object getItem(int i) {
        return dsSanPham.get(i); // Trả về sản phẩm tại vị trí i.
    }

    @Override
    public long getItemId(int i) {
        return i; // Trả về vị trí của sản phẩm như ID.
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_favorite_list, viewGroup, false);
            holder = new ViewHolder();
            holder.title = view.findViewById(R.id.item_title_favorite);
            holder.subTitle = view.findViewById(R.id.item_subTitle_favorite);
            holder.img = view.findViewById(R.id.item_image_favorite);
            holder.price = view.findViewById(R.id.price_favorite);
            holder.deleteBtn = view.findViewById(R.id.deleteBtn_favorite);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Lấy sản phẩm hiện tại
        SanPhamAPIModel product = dsSanPham.get(i);

        // Gán dữ liệu cho View
        holder.title.setText(product.getTenSanPham());
        holder.subTitle.setText(product.getMoTa());

        // Load image with Glide
        String imageUrl = product.getImages().replace("http://", "https://");
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.img.setImageDrawable(new BitmapDrawable(context.getResources(), resource));

                    }
                });


        // Format giá tiền
        NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
        String formattedPrice = formatter.format(product.getGiaTien()) + " vnđ";
        holder.price.setText(formattedPrice);

        // Xử lý nút xóa
        holder.deleteBtn.setOnClickListener(view1 -> {
            SanPhamYeuThichModel model = new SanPhamYeuThichModel();
            SharedPreferences sharedPreferences = context.getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
            String idUser = sharedPreferences.getString("idUser","");
            model.setIdNguoiDung(idUser);
            model.setIdSanPham(product.getIdSanPham());
            BoSanPhamYeuThichModel(model);

            removeProduct(i);
            notifyDataSetChanged();
        });

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_listview_sanpham);
        view.startAnimation(animation);


        return view;
    }

    private void removeProduct(int i) {
        dsSanPham.remove(i); // Xóa sản phẩm khỏi danh sách
    }

    // Lớp ViewHolder để tối ưu hóa
    private static class ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView img;
        TextView price;
        LinearLayout deleteBtn;
    }

    private void BoSanPhamYeuThichModel(SanPhamYeuThichModel model) {
        APIService.API_SERVICE.BoSanPhamYeuThichModel(model).enqueue(new Callback<SanPhamYeuThichModel>() {
            @Override
            public void onResponse(Call<SanPhamYeuThichModel> call, Response<SanPhamYeuThichModel> response) {

            }

            @Override
            public void onFailure(Call<SanPhamYeuThichModel> call, Throwable t) {

            }
        });
    }
}
