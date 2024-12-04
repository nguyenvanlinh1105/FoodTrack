package com.example.foodtrack.Fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Adapter.recyclerView_product_rating_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.BinhLuanSanPhamModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.R;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_product_rating#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_product_rating extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tv_soLuongBinhLuan;
    private ImageView btn_back;
    private RecyclerView rv_product_rating;
    private List<BinhLuanSanPhamModel> binhLuanList = new ArrayList<>();
    private LinearLayout if_no_Comment_productRating;

    private String idSanPham;
    private String tenSanPham;
    private String image;

    public fragment_product_rating() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_product_ratings.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_product_rating newInstance(String param1, String param2) {
        fragment_product_rating fragment = new fragment_product_rating();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (getArguments() != null) {
            // Lấy dữ liệu từ Bundle
            idSanPham = getArguments().getString("idSanPham");
            tenSanPham = getArguments().getString("tenSanPham");
            image = getArguments().getString("image");


        }
        //      Log.d("fragment_product_rating", "idSanPham: " + idSanPham);
        //   initializeData();


    }

    private void initializeData() {
        // Replace this with real data fetching logic
        for (int i = 1; i <= 10; i++) {
            NguoiDungModel user = new NguoiDungModel();
            user.setHoTenNguoiDung("Người dùng " + i);
            user.setAvatar(String.valueOf(R.drawable.icon_account_light_orange));

            BinhLuanSanPhamModel comment = new BinhLuanSanPhamModel();
            comment.setNguoiDung(user);
            comment.setNoiDung("Lorem ipsum dolor sit amet, consectetur adipiscing elit. ");

            try {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = dateFormat.parse("22/9/2024");
                long time = date.getTime();
                // comment.setNgayBinhLuan(new Timestamp(time));
            } catch (ParseException e) {
                e.printStackTrace(); // In ra lỗi nếu có
            }

            binhLuanList.add(comment);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_rating, container, false);

        Mapping(view);
        LayCommentSanPham(idSanPham);

//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
//        rv_product_rating.setLayoutManager(layoutManager);
//        recyclerView_product_rating_adapter adapter = new recyclerView_product_rating_adapter(getContext(), binhLuanList);
//        rv_product_rating.setAdapter(adapter);
//        tv_soLuongBinhLuan.setText(String.valueOf(binhLuanList.size()));

        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        btn_back = (ImageView) view.findViewById(R.id.btn_back_product_rating);
        rv_product_rating = (RecyclerView) view.findViewById(R.id.recyclerView_product_rating);
        tv_soLuongBinhLuan = (TextView) view.findViewById(R.id.tv_so_luong_binh_luan_productRating);
        if_no_Comment_productRating = (LinearLayout) view.findViewById(R.id.if_no_Comment_productRating);

        ImageView image_sanpham = view.findViewById(R.id.image_sanpham);
        TextView ten = view.findViewById(R.id.tenSanPham);
        ten.setText("Tên món: " + tenSanPham);


        String imageUrl = image;
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
                        image_sanpham.setImageDrawable(new BitmapDrawable(getContext().getResources(), resource));

                    }
                });


    }

    private void ControlButton() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    private void LayCommentSanPham(String idSanPham) {
        new LayCommentSanPhamTask().execute(idSanPham);
    }

    private class LayCommentSanPhamTask extends AsyncTask<String, Void, List<BinhLuanSanPhamModel>> {
        @Override
        protected List<BinhLuanSanPhamModel> doInBackground(String... params) {
            String idSanPham = params[0];
            try {
                // Gọi API đồng bộ
                Response<List<BinhLuanSanPhamModel>> response = APIService.API_SERVICE.LayCommentSanPham(idSanPham).execute();
                if (response.isSuccessful() && response.body() != null) {

                    return response.body();

                } else {
                    Log.d("BinhLuan", "Lỗi không lấy bình luận sản phẩm");

                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<BinhLuanSanPhamModel> binhLuanList) {
            if (binhLuanList != null) {
                rv_product_rating.setVisibility(View.VISIBLE);
                if_no_Comment_productRating.setVisibility(View.GONE);

                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                rv_product_rating.setLayoutManager(layoutManager);

                recyclerView_product_rating_adapter adapter = new recyclerView_product_rating_adapter(getContext(), binhLuanList);
                rv_product_rating.setAdapter(adapter);

                tv_soLuongBinhLuan.setText(String.valueOf(binhLuanList.size()));
            } else {
                rv_product_rating.setVisibility(View.GONE);
                if_no_Comment_productRating.setVisibility(View.VISIBLE);
                Log.d("BinhLuan", "Không có bình luận nào được trả về.");
            }
        }
    }


}