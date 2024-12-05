package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodtrack.Adapter.food_list_API_adapter;
import com.example.foodtrack.Adapter.list_drink_API_adapter;
import com.example.foodtrack.Adapter.notification_list_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.R;

import java.io.Serializable;
import java.util.List;

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
        } else {
            if_empty.setVisibility(View.VISIBLE);
            if_not_empty.setVisibility(View.GONE);
        }
        return view;
    }

    private void Mapping(View view) {
        tv_so_luong = (TextView) view.findViewById(R.id.tv_so_luong_ket_qua_tim_kiem);
        lv_ket_qua_tim_kiem = (ListView) view.findViewById(R.id.lv_ket_qua_tim_kiem);
        if_empty = (LinearLayout) view.findViewById(R.id.image_if_empty_ket_qua_tim_kiem);
        if_not_empty = (LinearLayout) view.findViewById(R.id.if_not_empty_ket_qua_tim_kiem);
    }
}