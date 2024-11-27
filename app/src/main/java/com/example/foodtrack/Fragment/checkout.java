package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.cart;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link checkout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class checkout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView backBtn;
    private Button payBtn;

    private TextView tv_total_amount; // tổng tiền phải trả :
    private TextView tv_service_fee;
    private TextView tv_shipping_fee;
    private TextView tv_ghi_chu;
    private TextView tv_total_price;// thành tiền
    String textGhiChu;


    private LinearLayout tienMat, applePay, icon_check_tien_mat, icon_check_applePay;

    public checkout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment checkout.
     */
    // TODO: Rename and change types and number of parameters
    public static checkout newInstance(String param1, String param2) {
        checkout fragment = new checkout();
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
        // Lấy ghiChu từ bundle
        if (getArguments() != null) {
            textGhiChu = getArguments().getString("ghiChu");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        Mapping(view);
        tv_ghi_chu.setText(textGhiChu);
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        backBtn = (ImageView) view.findViewById(R.id.btn_back_checkout);
        payBtn = (Button) view.findViewById(R.id.btn_pay);
        tienMat = (LinearLayout) view.findViewById(R.id.tien_mat_checkout);
        applePay = (LinearLayout) view.findViewById(R.id.apple_pay_checkout);
        icon_check_applePay = (LinearLayout) view.findViewById(R.id.icon_check_applePay_checkout);
        icon_check_tien_mat = (LinearLayout) view.findViewById(R.id.icon_check_tien_mat_checkout);

        tv_ghi_chu = view.findViewById(R.id.tv_ghi_chu);
    }

    private void ControlButton() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();

                if (payBtn.getText().toString().equals("Xác nhận đặt đơn")) {
                    mainActivity.ReplaceFragment(new fragment_confirm_payment());

                    DonHangAPIModel donHang = new DonHangAPIModel();


                    PostDataToOder(donHang);
                    cart.ToFinishActivity.finish();
                } else {
                    mainActivity.ReplaceFragment(new fragment_choosing_payment());
                    cart.ToFinishActivity.finish();

                }

            }

        });
        tienMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_check_tien_mat.setBackgroundResource(R.drawable.icon_check_50);
                icon_check_applePay.setBackground(null);
                payBtn.setText("Xác nhận đặt đơn");
            }
        });
        applePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_check_applePay.setBackgroundResource(R.drawable.icon_check_50);
                icon_check_tien_mat.setBackground(null);
                payBtn.setText("Thanh toán 248.000đ");
            }
        });
    }

    private void PostDataToOder(DonHangAPIModel donhang){
        APIService.API_SERVICE.PostToOrder(donhang).enqueue(new Callback<DonHangAPIModel>() {
            @Override
            public void onResponse(Call<DonHangAPIModel> call, Response<DonHangAPIModel> response) {

            }

            @Override
            public void onFailure(Call<DonHangAPIModel> call, Throwable t) {

            }
        });
    }
}