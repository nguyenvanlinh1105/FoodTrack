package com.example.foodtrack.Fragment;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Adapter.NotificationHelper;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_choosing_payment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_choosing_payment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPreferencesDonHang;
    private ImageView backBtn;
    private LinearLayout momo, zaloPay;
    DonHangAPIModel donHang;

    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;
    private NotificationHelper notificationHelper;


    public fragment_choosing_payment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_choosing_payment.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_choosing_payment newInstance(String param1, String param2) {
        fragment_choosing_payment fragment = new fragment_choosing_payment();
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
        sharedPreferencesDonHang = getContext().getSharedPreferences("dataDonHangResponse", getContext().MODE_PRIVATE);

        if (getArguments() != null) {
             donHang = (DonHangAPIModel) getArguments().getSerializable("donHang");
        }

        notificationHelper = new NotificationHelper(getContext());

        // Kiểm tra và xin quyền
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission();
        } else {

        }

    }


    private void checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_NOTIFICATION_PERMISSION
            );
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Người dùng từ chối quyền
                Toast.makeText(getContext(), "Bạn đã từ chối quyền thông báo", Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choosing_payment, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    private void Mapping(View view){
        backBtn = (ImageView) view.findViewById(R.id.btn_back_choosing_payment);
        momo = (LinearLayout) view.findViewById(R.id.momo_choosing_payment);
        zaloPay = (LinearLayout) view.findViewById(R.id.zaloPay_choosing_payment);

    }

    private void ControlButton(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int delay = 2000;

                donHang.setTinhTrangThanhToan("Thanh toán online");
                donHang.setTinhTrang("Đã xác nhận");

                PostDataToOder(donHang, "momo");
            }
        });

        zaloPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                donHang.setTinhTrangThanhToan("Thanh toán online");
                donHang.setTinhTrang("Đã xác nhận");
                PostDataToOder(donHang, "zaloPay");


            }
        });
    }

    // TH thanh toán thành công :
    private void ReplaceFragConfirmPayment(){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.ReplaceFragment(new fragment_confirm_payment());
    }


    // TH thanh toán thất bại
    private void ReplaceCart(){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.ReplaceFragment(new checkout());
    }

    private void PostDataToOder(DonHangAPIModel donhang, String appPay){
        APIService.API_SERVICE.PostToOrder(donhang).enqueue(new Callback<DonHangAPIModel>() {
            @Override
            public void onResponse(Call<DonHangAPIModel> call, Response<DonHangAPIModel> response) {
                int delay = 2000;
                        SharedPreferences.Editor editorResponseDonHang = sharedPreferencesDonHang.edit();
                        editorResponseDonHang.putString("idDonHang", null);
                        editorResponseDonHang.apply();
                        if(appPay=="momo"){
                            notificationHelper.sendNotification("Thông báo đơn hàng","Đơn hàng " + sharedPreferencesDonHang.getString("idDonHang", "DH000") + " của bạn đã được đặt hàng thành công và trong quá trình xử lí!");
                            notificationHelper.sendNotification("Thông báo thanh toán đơn hàng","Đơn hàng " + sharedPreferencesDonHang.getString("idDonHang", "DH000") + " của bạn đã được thanh toán thành công bằng ví điện tử momo!");
                        }else{
                            notificationHelper.sendNotification("Thông báo đơn hàng","Đơn hàng " + sharedPreferencesDonHang.getString("idDonHang", "DH000") + " của bạn đã được đặt hàng thành công và trong quá trình xử lí!");
                            notificationHelper.sendNotification("Thông báo thanh toán đơn hàng","Đơn hàng " + sharedPreferencesDonHang.getString("idDonHang", "DH000") + " của bạn đã được thanh toán thành công bằng ví điện tử zaloPay!");

                        }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ReplaceFragConfirmPayment();
                    }
                }, delay);
            }

            @Override
            public void onFailure(Call<DonHangAPIModel> call, Throwable t) {
                Toast.makeText(getContext(), "Thanh toán không thành công, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                int delay = 2000;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ReplaceCart();
                    }
                }, delay);
            }
        });
    }
}