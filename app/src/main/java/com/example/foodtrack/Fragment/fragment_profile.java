package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.chang_password;
import com.example.foodtrack.Activity.first_page;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Model.TestChat.TinNhanModel;
import com.example.foodtrack.R;
import com.example.foodtrack.Activity.edit_profile;
import com.example.foodtrack.SocketManager;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class fragment_profile extends Fragment {

    ConstraintLayout toEditHoSo, toMyOrders, doiMatKhau_profile, dangXuat_profile, btn_card_profile, chiTietNhom_profile, toThongBao;
    ImageView chatIcon;
    TextView txt_tenKH_profile, tv_tich_diem;

    Socket mSocket;

    Integer tichDiem;

    SharedPreferences shareUserResponseLogin;

    public fragment_profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Mapping(view);
        LoadUserInfo();
        ControlSocket();
        ControlButton();
        return view;
    }

    public void Mapping(View view) {
        toEditHoSo = view.findViewById(R.id.capNhatHoSo_profile);
        chatIcon = view.findViewById(R.id.chatIcon);
        toMyOrders = view.findViewById(R.id.donHangCuaToi_profile);
        doiMatKhau_profile = view.findViewById(R.id.doiMatKhau_profile);
        btn_card_profile = view.findViewById(R.id.btn_card_profile);
        dangXuat_profile = view.findViewById(R.id.dangXuat_profile);
        txt_tenKH_profile = view.findViewById(R.id.txt_tenKH_profile);
        chiTietNhom_profile = view.findViewById(R.id.chiTietNhom_profile);
        toThongBao = view.findViewById(R.id.thong_bao_profile);
        tv_tich_diem = view.findViewById(R.id.tv_tich_diem);
    }

    public void LoadUserInfo() {
        // Lấy dữ liệu từ SharedPreferences
        shareUserResponseLogin = getActivity().getSharedPreferences("shareUserResponseLogin", getActivity().MODE_PRIVATE);
        String userName = shareUserResponseLogin.getString("hoTenNguoiDung", "Guest"); // Mặc định là "Guest" nếu không tìm thấy
        tichDiem = shareUserResponseLogin.getInt("tichDiem", 0);

        txt_tenKH_profile.setText("Xin chào!," + userName);
        tv_tich_diem.setText(String.valueOf(tichDiem) + " điểm");
    }

    private void ControlSocket() {
        mSocket = SocketManager.getInstance().getSocket();

    }



    public void ControlButton() {
        toEditHoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editHoSo = new Intent(getActivity(), edit_profile.class);
                startActivity(editHoSo);
            }
        });

        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), list_chat_user.class);
                startActivity(chat);
            }
        });

        toMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_ongoing_API());
                }
            }
        });

        doiMatKhau_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changepass = new Intent(getActivity(), chang_password.class);
                startActivity(changepass);
            }
        });

        dangXuat_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent firstPage = new Intent(getActivity(), first_page.class);
                startActivity(firstPage);
                getActivity().finish();
            }
        });

        btn_card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myCard());
                }
            }
        });

        chiTietNhom_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_chi_tiet_nhom_thuc_hien());
                }
            }
        });

        toThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_thong_bao_user());
                }
            }
        });
    }
}
