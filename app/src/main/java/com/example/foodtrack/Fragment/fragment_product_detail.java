package com.example.foodtrack.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Adapter.recyclerView_deal_hoi_adapter;
import com.example.foodtrack.Adapter.recyclerView_product_detail_adapter;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.Model.SanPhamYeuThichModel;
import com.example.foodtrack.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_product_detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_product_detail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";
    private static final String ARG_PRICE = "price";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE = "image";
    private static final String ARG_SOLUONGDABAN = "soLuong";


    // TODO: Rename and change types of parameters
    private static String title;
    private static String price;
    private static String description;
    private static int image, soLuongDaBan;

    // Khai báo một biến flag để kiểm tra trạng thái
    boolean isFavorite = false;

    private ImageView btn_back_product_detail;
    private ImageView btn_rating_product_details;
    private ImageView btn_favorite_check_product_detail;
    private ImageView btn_minus_product_detail;
    private ImageView btn_plus_product_detail;
    private TextView Text_quantity_product;
    private TextView tv_soLuongDaBan;
    private int quantity;
    private TextView btn_AddToCart_product_detail;

    private List<SanPhamModel> listProduct, listDealHoi;
    private RecyclerView rvProductDetail, rvDealHoi;

    public fragment_product_detail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_product_detail.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_product_detail newInstance(String title, Double price, String description, int image) {
        fragment_product_detail fragment = new fragment_product_detail();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putDouble(ARG_PRICE, price);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_IMAGE, image);
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
            image = getArguments().getInt(ARG_IMAGE);
            soLuongDaBan = getArguments().getInt(ARG_SOLUONGDABAN);
        }
    }

    private void InitializeData() {
        listProduct = new ArrayList<>();
        listProduct.add(new SanPhamModel("Cơm tấm sườn", 70000.0, R.drawable.com_tam, "Hạt cơm tấm dẻo thơm ăn kèm sườn nướng tươi ngon vừa ra lò"));
        listProduct.add(new SanPhamModel("Chuối sứ", 30000.0, R.drawable.icon_food2, "Chuối sứ vàng ươm tươi ngon đây bà con ơi"));
        listProduct.add(new SanPhamModel("Gnocchi sốt cà chua", 30000.0, R.drawable.gnocchi_tomato, "Gnocchi hòa cùng sốt cà chua nhà làm mang chút vị chua thanh mát, kết hợp với mùi thơm từ húng tây và tiêu đen xay mịn"));
        listProduct.add(new SanPhamModel("Cơm gà sốt chua ngọt", 30000.0, R.drawable.chicken, "Cơm nóng ăn kèm gà chiên tẩm sốt chua ngọt ngoài giòn trong mềm, thấm đẫm gia vị"));
        listProduct.add(new SanPhamModel("Burger phô mai", 30000.0, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listProduct.add(new SanPhamModel("Cheesecake việt quất", 30000.0, R.drawable.dessert_ico, "Cheesecake béo ngậy cùng mứt việt quất chua ngọt vừa phải"));
    }

    private void InitializeDataDealHoi() {
        listDealHoi = new ArrayList<>();
        listDealHoi.add(new SanPhamModel("Salad trái cây", 70000, R.drawable.icon_food1, "Salad cổ điển cùng trái cây tươi trong ngày"));
        listDealHoi.add(new SanPhamModel("Mì Carbonara", 90000, R.drawable.carbonara, "Carbonara béo ngậy hòa cùng chút mặn đến từ thịt xông khói"));
        listDealHoi.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listDealHoi.add(new SanPhamModel("Gnocchi sốt cà chua", 80000, R.drawable.gnocchi_tomato, "Gnocchi tươi nấu cùng sốt cà chua nhà làm"));
        listDealHoi.add(new SanPhamModel("Cơm gà sốt chua ngọt", 80000, R.drawable.chicken, "Cơm nóng ăn kèm gà rán tẩm sốt chua ngọt bí truyền"));
        listDealHoi.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listDealHoi.add(new SanPhamModel("Mì Spaghetti", 90000, R.drawable.spaghetti, "Sợi mì spaghetti tươi ngon nấu cùng sốt cà chua nguyên chất"));
        listDealHoi.add(new SanPhamModel("Salad trái cây", 70000, R.drawable.icon_food1, "Salad cổ điển cùng trái cây tươi trong ngày"));
        listDealHoi.add(new SanPhamModel("Mì Carbonara", 90000, R.drawable.carbonara, "Carbonara béo ngậy hòa cùng chút mặn đến từ thịt xông khói"));
        listDealHoi.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listDealHoi.add(new SanPhamModel("Gnocchi sốt cà chua", 80000, R.drawable.gnocchi_tomato, "Gnocchi tươi nấu cùng sốt cà chua nhà làm"));
        listDealHoi.add(new SanPhamModel("Cơm gà sốt chua ngọt", 80000, R.drawable.chicken, "Cơm nóng ăn kèm gà rán tẩm sốt chua ngọt bí truyền"));
        listDealHoi.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listDealHoi.add(new SanPhamModel("Mì Spaghetti", 90000, R.drawable.spaghetti, "Sợi mì spaghetti tươi ngon nấu cùng sốt cà chua nguyên chất"));
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
            int image = bundle.getInt("image");
            soLuongDaBan = bundle.getInt("soLuongDaBan");

            TextView titleView = view.findViewById(R.id.title_product_details);
            TextView priceView = view.findViewById(R.id.price_product_details);
            TextView descriptionView = view.findViewById(R.id.description_product_detail);
            ImageView imageView = view.findViewById(R.id.image_product_details);
            TextView soLuongDaBanView = view.findViewById(R.id.tv_soLuongDaBan_product_detail);

            NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
            String formattedPrice = formatter.format(price);
            formattedPrice = formattedPrice + "vnđ";

            titleView.setText(title);
            priceView.setText(formattedPrice);
            descriptionView.setText(description);
            imageView.setImageResource(image);
//            Glide.with(getContext())
//                    .asBitmap()
//                    .load(image)
//                    .into(new CustomTarget<Bitmap>() {
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//                        }
//
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            imageView.setBackground(new BitmapDrawable(getContext().getResources(), resource));
//                        }
//                    });
        }
        requireActivity().getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            // Kiểm tra nếu fragment này không còn trong back stack
            if (!isAdded()) {
                // Thực hiện các thao tác cần thiết khi back
                animateExitTransition();
            }
        });
        ControlButton();
        return view;
    }

    private void animateExitTransition() {
        View rootView = getView();
        if (rootView != null) {
            rootView.setAlpha(1f);
            rootView.animate().alpha(0f).setDuration(300).start();
        }
    }

    private void Mapping(View view) {
        btn_back_product_detail = (ImageView) view.findViewById(R.id.btn_back_product_detail);
        btn_rating_product_details = (ImageView) view.findViewById(R.id.button_rating_comment_product_details);
        btn_favorite_check_product_detail = (ImageView) view.findViewById(R.id.btn_favorite_check_product_detail);
        btn_plus_product_detail = (ImageView) view.findViewById(R.id.btn_plus_product_detail);
        btn_minus_product_detail = (ImageView) view.findViewById(R.id.btn_minus_product_detail);
        Text_quantity_product = (TextView) view.findViewById(R.id.Text_quantity_product);
        btn_rating_product_details = (ImageView) view.findViewById(R.id.button_rating_comment_product_details);
        btn_AddToCart_product_detail = (TextView) view.findViewById(R.id.btn_AddToCart_product_detail);

        rvProductDetail = (RecyclerView) view.findViewById(R.id.recyclerView_product_detail);
        InitializeData();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        rvProductDetail.setLayoutManager(layoutManager);
        recyclerView_product_detail_adapter recAdapter = new recyclerView_product_detail_adapter(getContext(), listProduct);
        rvProductDetail.setAdapter(recAdapter);

        rvDealHoi = view.findViewById(R.id.recyclerView_deal_hoi_product_detail);
        InitializeDataDealHoi();
        LinearLayoutManager layoutDealManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rvDealHoi.setLayoutManager(layoutDealManager);
        recyclerView_deal_hoi_adapter dealAdapter = new recyclerView_deal_hoi_adapter(getContext(), listDealHoi);
        rvDealHoi.setAdapter(dealAdapter);
    }

    private void ControlButton() {
        btn_back_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_rating_product_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_product_rating());
                }
            }
        });
        btn_favorite_check_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFavorite) {
                    btn_favorite_check_product_detail.setImageResource(R.drawable.icon_fill_heart_48);
                    isFavorite = true;
//                    CreatePopupAddToFavorite(view);
                } else {
                    btn_favorite_check_product_detail.setImageResource(R.drawable.icon_heart_48);
                    isFavorite = false;
                }
            }
        });
        btn_minus_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.valueOf(Text_quantity_product.getText().toString());
                if (quantity > 1) {
                    quantity--;
                } else {
                    quantity = 1;
                }
                Text_quantity_product.setText(String.valueOf(quantity));
            }
        });
        btn_plus_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.valueOf(Text_quantity_product.getText().toString());
                quantity++;
                Text_quantity_product.setText(String.valueOf(quantity));
            }
        });
        btn_AddToCart_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
                CreatePopupAddToCart(view);
            }
        });


    }

    private void CreatePopupAddToCart(View view) {
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
        int delay = 1300;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, delay);

    }

    private void CreatePopupAddToFavorite(View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        View popupView = inflater.inflate(R.layout.popup_add_to_favorite, null);

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