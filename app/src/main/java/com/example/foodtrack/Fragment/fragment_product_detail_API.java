package com.example.foodtrack.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Adapter.recyclerView_product_detail_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class fragment_product_detail_API extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_PRICE = "price";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE = "image";

    private static String title;
    private static String price;
    private static String description;
    private static String image; // Thay đổi kiểu dữ liệu thành String

    boolean isFavorite = false;

    private ImageView btn_back_product_detail;
    private ImageView btn_rating_product_details;
    private ImageView btn_favorite_check_product_detail;
    private ImageView btn_minus_product_detail;
    private ImageView btn_plus_product_detail;
    private TextView Text_quantity_product;
    private int quantity;
    private TextView btn_AddToCart_product_detail;

    private List<SanPhamAPIModel> listProduct;
    private RecyclerView rvProductDetail;

    public fragment_product_detail_API() {
        // Required empty public constructor
    }

    public static fragment_product_detail_API newInstance(String title, Double price, String description, String image) {
        fragment_product_detail_API fragment = new fragment_product_detail_API();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putDouble(ARG_PRICE, price);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            price = String.valueOf(getArguments().getDouble(ARG_PRICE));
            description = getArguments().getString(ARG_DESCRIPTION);
            image = getArguments().getString(ARG_IMAGE); // Lấy giá trị image từ URL
        }
    }

    private void InitializeData() {
        listProduct = new ArrayList<>();
        listProduct.add(new SanPhamAPIModel("Cơm tấm", 70000.0, "https://example.com/com_tam.png", "Cơm tấm bì nướng"));
        listProduct.add(new SanPhamAPIModel("Chuối tươi", 30000.0, "https://example.com/chuoi_tuoi.png", "Chuối sứ"));
        listProduct.add(new SanPhamAPIModel("Burger phô mai", 30000.0, "https://example.com/burger_phomai.png", "Burger phô mai cổ điển"));
        listProduct.add(new SanPhamAPIModel("Pizza hải sản", 120000.0, "https://example.com/pizza_seafood.png", "Pizza hải sản cao cấp"));
        listProduct.add(new SanPhamAPIModel("Sushi cá hồi", 50000.0, "https://example.com/sushi_ca_hoi.png", "Sushi cá hồi tươi"));
        listProduct.add(new SanPhamAPIModel("Mì Ý sốt bò bằm", 45000.0, "https://example.com/mi_y.png", "Mì Ý sốt bò bằm truyền thống"));
        listProduct.add(new SanPhamAPIModel("Nước cam ép", 20000.0, "https://example.com/nuoc_cam_ep.png", "Nước cam ép tươi"));
        listProduct.add(new SanPhamAPIModel("Cà phê sữa đá", 25000.0, "https://example.com/ca_phe_sua_da.png", "Cà phê sữa đá truyền thống"));
        listProduct.add(new SanPhamAPIModel("Trà sữa trân châu", 35000.0, "https://example.com/tra_sua.png", "Trà sữa trân châu đường đen"));
        listProduct.add(new SanPhamAPIModel("Salad rau củ", 30000.0, "https://example.com/salad_rau_cu.png", "Salad rau củ tươi mát"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        Mapping(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            Double price = bundle.getDouble("price");
            String description = bundle.getString("description");
            String imageUrl = bundle.getString("image");

            TextView titleView = view.findViewById(R.id.title_product_details);
            TextView priceView = view.findViewById(R.id.price_product_details);
            TextView descriptionView = view.findViewById(R.id.description_product_detail);
            LinearLayout imageView = view.findViewById(R.id.image_product_details);

            NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
            String formattedPrice = formatter.format(price) + "đ";

            titleView.setText(title);
            priceView.setText(formattedPrice);
            descriptionView.setText(description);
//            Glide.with(this).load(imageUrl).into(imageView);
            //nhớ chuyển http -> https
            Glide.with(getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setBackground(new BitmapDrawable(getContext().getResources(), resource));

                        }
                    });
        }

        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        btn_back_product_detail = view.findViewById(R.id.btn_back_product_detail);
        btn_rating_product_details = view.findViewById(R.id.button_rating_comment_product_details);
        btn_favorite_check_product_detail = view.findViewById(R.id.btn_favorite_check_product_detail);
        btn_plus_product_detail = view.findViewById(R.id.btn_plus_product_detail);
        btn_minus_product_detail = view.findViewById(R.id.btn_minus_product_detail);
        Text_quantity_product = view.findViewById(R.id.Text_quantity_product);
        btn_AddToCart_product_detail = view.findViewById(R.id.btn_AddToCart_product_detail);

        rvProductDetail = view.findViewById(R.id.recyclerView_product_detail);
        InitializeData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rvProductDetail.setLayoutManager(layoutManager);
//        recyclerView_product_detail_adapter dealAdapter = new recyclerView_product_detail_adapter(getContext(), listProduct);
//        rvProductDetail.setAdapter(dealAdapter);
    }

    private void ControlButton() {
        btn_back_product_detail.setOnClickListener(view -> requireActivity().getSupportFragmentManager().popBackStack());

        btn_rating_product_details.setOnClickListener(view -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.ReplaceFragment(new fragment_product_rating());
            }
        });

        btn_favorite_check_product_detail.setOnClickListener(view -> {
            if (!isFavorite) {
                btn_favorite_check_product_detail.setImageResource(R.drawable.icon_fill_heart_48);
                isFavorite = true;
            } else {
                btn_favorite_check_product_detail.setImageResource(R.drawable.icon_heart_48);
                isFavorite = false;
            }
        });

        btn_minus_product_detail.setOnClickListener(view -> {
            quantity = Integer.parseInt(Text_quantity_product.getText().toString());
            if (quantity > 0) quantity--;
            Text_quantity_product.setText(String.valueOf(quantity));
        });

        btn_plus_product_detail.setOnClickListener(view -> {
            quantity = Integer.parseInt(Text_quantity_product.getText().toString());
            quantity++;
            Text_quantity_product.setText(String.valueOf(quantity));
        });

        btn_AddToCart_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
                CreatePopup(view);
            }
        });

    }

    private void CreatePopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        View popupView = inflater.inflate(R.layout.popup_add_to_cart, null);

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        view.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
        int delay = 1100;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, delay);

    }

}