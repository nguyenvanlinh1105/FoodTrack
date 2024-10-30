package com.example.foodtrack.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.foodtrack.Adapter.recyclerView_product_rating_adapter;
import com.example.foodtrack.Model.BinhLuanSanPhamModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.R;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.http.HEAD;

public class fragment_product_rating extends Fragment {

    private ImageView btn_back;
    private RecyclerView rv_product_rating;
    private List<BinhLuanSanPhamModel> binhLuanList = new ArrayList<>();

    public fragment_product_rating() {
        // Required empty public constructor
    }

    public static fragment_product_rating newInstance(String param1, String param2) {
        fragment_product_rating fragment = new fragment_product_rating();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    private void initializeData(){
        // Generate sample data for RecyclerView
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 1; i <= 10; i++) {
            NguoiDungModel user = new NguoiDungModel();
            user.setHoTenNguoiDung("Người dùng " + i);
            user.setAvatar(String.valueOf(R.drawable.icon_account_light_orange));

            BinhLuanSanPhamModel comment = new BinhLuanSanPhamModel();
            comment.setNguoiDung(user);
            comment.setNoiDung("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

            try {
                Date date = dateFormat.parse("22/09/2024");
                if (date != null) {
                    comment.setNgayBinhLuan(new Timestamp(date.getTime()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            binhLuanList.add(comment);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_rating, container, false);
        Mapping(view);

        // Set up RecyclerView with LinearLayoutManager and Adapter
        rv_product_rating.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView_product_rating_adapter adapter = new recyclerView_product_rating_adapter(requireContext(), binhLuanList);
        rv_product_rating.setAdapter(adapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        rv_product_rating.setLayoutManager(layoutManager);
//        recyclerView_product_rating_adapter adapter = new recyclerView_product_rating_adapter(getContext(), binhLuanList);
//        rv_product_rating.setAdapter(adapter);
        ControlButton();
        return view;
    }

    private void Mapping(View view){
        btn_back = view.findViewById(R.id.btn_back_product_rating);
        rv_product_rating = view.findViewById(R.id.recyclerView_product_rating);
    }

    private void ControlButton(){
        btn_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }
}
