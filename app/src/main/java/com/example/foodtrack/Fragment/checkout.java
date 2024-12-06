package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.cart;
import com.example.foodtrack.Adapter.NotificationHelper;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.R;

import android.Manifest;

import java.text.NumberFormat;
import java.util.Locale;

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

    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;
    private NotificationHelper notificationHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);

    private ImageView backBtn;
    private Button payBtn;
    private LinearLayout ll_tich_diem_checkout;

    String idDonHang;

    private TextView tv_total_amount; // tổng tiền phải trả :
    private TextView tv_service_fee;
    private TextView tv_so_diem_tich_duoc;
    private TextView tv_ghi_chu;
    private TextView tv_total_price;// thành tiền
    private TextView tv_address;// thành tiền
    private TextView tv_tich_diem;// thành tiền
    String textGhiChu;
    Double tongTien;
    SharedPreferences sharedPreferencesDonHang, shareUserResponseLogin;

    Integer tichDiem;


    private LinearLayout tienMat, applePay, icon_check_tien_mat, icon_check_applePay, icon_check_tich_diem;

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
        sharedPreferencesDonHang = getContext().getSharedPreferences("dataDonHangResponse", getContext().MODE_PRIVATE);
        shareUserResponseLogin = getContext().getSharedPreferences("shareUserResponseLogin", getContext().MODE_PRIVATE);
        // Lấy ghiChu từ bundle
        if (getArguments() != null) {
            textGhiChu = getArguments().getString("ghiChu");
            tongTien = getArguments().getDouble("tongTien");
        }
        tichDiem = shareUserResponseLogin.getInt("tichDiem", 0);

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
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        Mapping(view);


        Log.d("tongTienCheckout", String.valueOf(tongTien));

        tv_so_diem_tich_duoc.setText(formatter.format(tichDiem));
        tv_tich_diem.setText(formatter.format(tichDiem) + "vnđ");
        tv_ghi_chu.setText(textGhiChu);
        tv_address.setText(shareUserResponseLogin.getString("diaChi", "Khách sạn Novotel, tầng 12 phòng 15, Đà Nẵng"));
        tv_total_price.setText(formatter.format(tongTien) + "vnđ");
        tv_total_amount.setText(formatter.format(tongTien + 15000) + "vnđ");

        payBtn.setText("Thanh toán " + tv_total_amount.getText());

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
        icon_check_tich_diem = (LinearLayout) view.findViewById(R.id.icon_check_tich_diem_checkout);

        ll_tich_diem_checkout = (LinearLayout) view.findViewById(R.id.ll_tich_diem_checkout);

        tv_ghi_chu = view.findViewById(R.id.tv_ghi_chu);
        tv_address = view.findViewById(R.id.tv_address);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        tv_total_amount = view.findViewById(R.id.tv_total_amount);
        tv_tich_diem = view.findViewById(R.id.tv_tich_diem);
        tv_so_diem_tich_duoc = view.findViewById(R.id.tv_so_diem_tich_duoc);
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

                DonHangAPIModel donHang = new DonHangAPIModel();

                SharedPreferences dataUserResponse = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);


                idDonHang = sharedPreferencesDonHang.getString("idDonHang", "");
                String diaChi = dataUserResponse.getString("diaChi", "");
                String phuongThucThanhToan = "Thanh toán trực tiếp";//0 la truc tiep


                donHang.setIdDonHang(idDonHang);
                donHang.setTinhTrang("Đã xác nhận");
                donHang.setDiaChi(diaChi);
                Drawable currentBackground = icon_check_tich_diem.getBackground();
                Drawable checkDrawable = ContextCompat.getDrawable(view.getContext(), R.drawable.icon_check_50);

                if (currentBackground != null && checkDrawable != null &&
                        currentBackground.getConstantState().equals(checkDrawable.getConstantState())) {
                    donHang.setTichDiem(tichDiem);
                }
                else{
                    donHang.setTichDiem(0);
                }


                donHang.setGhiChu(textGhiChu);
                if (payBtn.getText().toString().equals("Xác nhận đặt đơn")) {
                    // mainActivity.ReplaceFragment(new fragment_confirm_payment());
                    donHang.setTinhTrangThanhToan(phuongThucThanhToan);
                    PostDataToOder(donHang);
                } else {
                    fragment_choosing_payment fragment = new fragment_choosing_payment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("donHang", donHang);
                    fragment.setArguments(bundle);

                    mainActivity.ReplaceFragment(fragment);
                }

                if (currentBackground != null && checkDrawable != null &&
                        currentBackground.getConstantState().equals(checkDrawable.getConstantState())) {
                    SharedPreferences.Editor editorResponseLogin = shareUserResponseLogin.edit();
                    editorResponseLogin.putInt("tichDiem", 0);
                    editorResponseLogin.apply();
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
                payBtn.setText("Thanh toán " + tv_total_amount.getText());
            }
        });
        icon_check_tich_diem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable currentBackground = icon_check_tich_diem.getBackground();
                Drawable checkDrawable = ContextCompat.getDrawable(view.getContext(), R.drawable.icon_check_50);

                if (currentBackground != null && checkDrawable != null &&
                        currentBackground.getConstantState().equals(checkDrawable.getConstantState())) {
                    icon_check_tich_diem.setBackground(null);
                    ll_tich_diem_checkout.setVisibility(View.GONE);
                    tv_total_amount.setText(formatter.format(tongTien + 15000) + "vnđ");
                } else {
                    icon_check_tich_diem.setBackgroundResource(R.drawable.icon_check_50);
                    ll_tich_diem_checkout.setVisibility(View.VISIBLE);
                    tv_total_amount.setText(formatter.format(tongTien + 15000 - tichDiem) + "vnđ");
                    payBtn.setText("Thanh toán " + tv_total_amount.getText());

                }

            }
        });
    }


    private void PostDataToOder(DonHangAPIModel donhang) {
        APIService.API_SERVICE.PostToOrder(donhang).enqueue(new Callback<DonHangAPIModel>() {
            @Override
            public void onResponse(Call<DonHangAPIModel> call, Response<DonHangAPIModel> response) {
                idDonHang = sharedPreferencesDonHang.getString("idDonHang", "");
                notificationHelper.sendNotification("Thông báo đơn hàng", "Đơn hàng " + idDonHang + " bạn đã được đặt hàng thành công và trong quá trình xử lí!");

                SharedPreferences.Editor editorResponseDonHang = sharedPreferencesDonHang.edit();
                editorResponseDonHang.putString("idDonHang", null);
                editorResponseDonHang.apply();

                Log.d("idDonHang", idDonHang);
                MainActivity mainActivity = (MainActivity) getActivity();

                // Người dùng cấp quyền, gửi thông báo

                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_confirm_payment());
                    //mainActivity.ReplaceFragment(new fragment_myorders_ongoing_API());
                }
            }

            @Override
            public void onFailure(Call<DonHangAPIModel> call, Throwable t) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new Home_Page());
                }
            }
        });
    }
}