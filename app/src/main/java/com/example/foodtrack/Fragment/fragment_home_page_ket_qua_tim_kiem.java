package com.example.foodtrack.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Adapter.NotificationHelper;
import com.example.foodtrack.Adapter.food_list_API_adapter;
import com.example.foodtrack.Adapter.list_drink_API_adapter;
import com.example.foodtrack.Adapter.notification_list_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.R;
import com.example.foodtrack.SocketManager;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_home_page_ket_qua_tim_kiem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_home_page_ket_qua_tim_kiem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_SEARCH = "Seach";

    private TextView tv_so_luong;
    private ListView lv_ket_qua_tim_kiem;

    private LinearLayout if_not_empty, if_empty;



    private ImageView btn_back;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<SanPhamAPIModel> searchResult;

    public fragment_home_page_ket_qua_tim_kiem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_home_page_ket_qua_tim_kiem.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_home_page_ket_qua_tim_kiem newInstance(List<SanPhamAPIModel> searchResult) {
        fragment_home_page_ket_qua_tim_kiem fragment = new fragment_home_page_ket_qua_tim_kiem();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SEARCH, (Serializable) searchResult); // Chuyển đổi danh sách sang Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchResult = (List<SanPhamAPIModel>) getArguments().getSerializable(ARG_SEARCH);
        }

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page_ket_qua_tim_kiem, container, false);
        Mapping(view);
        if (!searchResult.isEmpty()) {
            if_empty.setVisibility(View.GONE);
            if_not_empty.setVisibility(View.VISIBLE);
            tv_so_luong.setText(String.valueOf(searchResult.size()));
            list_drink_API_adapter listAdapter = new list_drink_API_adapter(getContext(), searchResult);
            lv_ket_qua_tim_kiem.setAdapter(listAdapter);

            lv_ket_qua_tim_kiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SanPhamAPIModel selectedProduct = searchResult.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("soLuongDaBan", selectedProduct.getSoLuongDaBan());
                    fragment_product_detail_API productDetailsFragment = fragment_product_detail_API.newInstance(
                            selectedProduct.getIdSanPham(),
                            selectedProduct.getTenSanPham(),
                            selectedProduct.getGiaTien(),
                            selectedProduct.getMoTa(),
                            selectedProduct.getImages(),
                            selectedProduct.getSoLuongDaBan()
                    );
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.ReplaceFragment(productDetailsFragment);
                    }

                }
            });
        } else {
            if_empty.setVisibility(View.VISIBLE);
            if_not_empty.setVisibility(View.GONE);
        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void Mapping(View view) {
        tv_so_luong = (TextView) view.findViewById(R.id.tv_so_luong_ket_qua_tim_kiem);
        lv_ket_qua_tim_kiem = (ListView) view.findViewById(R.id.lv_ket_qua_tim_kiem);
        if_empty = (LinearLayout) view.findViewById(R.id.image_if_empty_ket_qua_tim_kiem);
        if_not_empty = (LinearLayout) view.findViewById(R.id.if_not_empty_ket_qua_tim_kiem);
        btn_back = (ImageView) view.findViewById(R.id.btn_back);
    }




}