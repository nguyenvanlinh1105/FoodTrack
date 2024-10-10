package com.example.foodtrack.Fragment.Guest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodtrack.Activity.Guest.MainActivity;
import com.example.foodtrack.R;

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


    // TODO: Rename and change types of parameters
    private static String title;
    private static String price;
    private static String description;
    private static int image;

    // Khai báo một biến flag để kiểm tra trạng thái
    boolean isFavorite = false;

    private ImageView btn_back_product_detail;
    private ImageView btn_rating_product_details;
    private ImageView btn_favorite_check_product_detail;
    private ImageView btn_minus_product_detail;
    private ImageView btn_plus_product_detail;
    TextView Text_quantity_product;
    private int quantity;

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
    public static fragment_product_detail newInstance(String title, String price, String description, int image) {
        fragment_product_detail fragment = new fragment_product_detail();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_PRICE, price);
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
            price = getArguments().getString(ARG_PRICE);
            description = getArguments().getString(ARG_DESCRIPTION);
            image = getArguments().getInt(ARG_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        Mapping(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String price = bundle.getString("price");
            String description = bundle.getString("description");
            int image = bundle.getInt("image");

            TextView titleView = view.findViewById(R.id.title_product_details);
            TextView priceView = view.findViewById(R.id.price_product_details);
            TextView descriptionView = view.findViewById(R.id.description_product_detail);
            ImageView imageView = view.findViewById(R.id.image_product_details);

            titleView.setText(title);
            priceView.setText(price);
            descriptionView.setText(description);
            imageView.setImageResource(image);
        }

        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        btn_back_product_detail = (ImageView) view.findViewById(R.id.btn_back_product_detail);
        btn_rating_product_details = (ImageView) view.findViewById(R.id.button_rating_comment_product_details);
        btn_favorite_check_product_detail =(ImageView) view.findViewById(R.id.btn_favorite_check_product_detail);
        btn_plus_product_detail = (ImageView) view.findViewById(R.id.btn_plus_product_detail);
        btn_minus_product_detail = (ImageView) view.findViewById(R.id.btn_minus_product_detail);
        Text_quantity_product = (TextView) view.findViewById(R.id.Text_quantity_product);
        btn_rating_product_details = (ImageView) view.findViewById(R.id.button_rating_comment_product_details);
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
                if(!isFavorite){
                    btn_favorite_check_product_detail.setImageResource(R.drawable.icon_fill_heart_48);
                    isFavorite = true;
                }else{
                    btn_favorite_check_product_detail.setImageResource(R.drawable.icon_heart_48);
                    isFavorite = false;
                }
            }
        });
        btn_minus_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.valueOf(Text_quantity_product.getText().toString());
                if(quantity>0){
                    quantity--;
                }else{
                    quantity =0;
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

    }

}