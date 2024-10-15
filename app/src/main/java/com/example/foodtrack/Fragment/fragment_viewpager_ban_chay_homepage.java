package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtrack.Adapter.recyclerView_mon_moi_ban_chay_adapter;
import com.example.foodtrack.Model.Product;
import com.example.foodtrack.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_viewpager_ban_chay_homepage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_viewpager_ban_chay_homepage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Product> listProduct;
    private RecyclerView rvBanChay;


    public fragment_viewpager_ban_chay_homepage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_mon_ban_chay_homepage.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_viewpager_ban_chay_homepage newInstance(String param1, String param2) {
        fragment_viewpager_ban_chay_homepage fragment = new fragment_viewpager_ban_chay_homepage();
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
    }

    private void InitializeData() {
        listProduct = new ArrayList<>();
        listProduct.add(new Product("Cơm tấm", "50.000đ", "Cơm tấm bì nướng", R.drawable.com_tam));
        listProduct.add(new Product("Chuối tươi", "30.000đ", "Chuối sứ", R.drawable.icon_food2));
        listProduct.add(new Product("Pallavi Biryani", "50.000đ", "Burger phô mai cổ điển", R.drawable.pallavi_biryani));
        listProduct.add(new Product("Burger phô mai", "30.000đ", "Burger phô mai cổ điển", R.drawable.double_cheese));
        listProduct.add(new Product("Cơm gà sốt chua ngọt", "80.000đ", "Burger phô mai cổ điển", R.drawable.chicken));
        listProduct.add(new Product("Burger trứng", "30.000đ", "Burger phô mai cổ điển", R.drawable.double_cheese));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_ban_chay_homepage, container, false);
        Mapping(view);
        return view;
    }

    private void Mapping(View view) {
        listProduct = new ArrayList<>();

        rvBanChay = view.findViewById(R.id.recyclerView_ban_chay_homepage);
        InitializeData();
        GridLayoutManager layoutManager
                = new GridLayoutManager(requireContext(),1);
        rvBanChay.setLayoutManager(layoutManager);
        recyclerView_mon_moi_ban_chay_adapter dealAdapter = new recyclerView_mon_moi_ban_chay_adapter(getContext(), listProduct);
        rvBanChay.setAdapter(dealAdapter);

    }
}