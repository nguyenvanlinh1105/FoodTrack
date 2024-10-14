package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.recyclerView_deal_hoi_adapter;
import com.example.foodtrack.Adapter.viewPager_mon_moi_ban_chay_home_page_adapter;
import com.example.foodtrack.Model.Product;
import com.example.foodtrack.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView btn_DoUong_homepage, btn_DoAn_homepage;
    private ImageView chatIcon;

    private List<Product> listProduct;
    private RecyclerView rvDealHoi;

    private TabLayout tlMonMoiBanChay;
    private ViewPager2 vpMonMoiBanChay;



    public Home_Page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_Page.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_Page newInstance(String param1, String param2) {
        Home_Page fragment = new Home_Page();
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
        listProduct.add(new Product("Salad trái cây", "70.000đ", "Salad cổ điển cùng trái cây tươi trong ngày", R.drawable.icon_food1));
        listProduct.add(new Product("Mì Carbonara", "90.000đ", "Carbonara béo ngậy hòa cùng chút mặn đến từ thịt xông khói", R.drawable.carbonara));
        listProduct.add(new Product("Burger phô mai", "30.000đ", "Burger phô mai cổ điển", R.drawable.double_cheese));
        listProduct.add(new Product("Gnocchi sốt cà chua", "80.000đ", "Gnocchi tươi nấu cùng sốt cà chua nhà làm", R.drawable.gnocchi_tomato));
        listProduct.add(new Product("Cơm gà sốt chua ngọt", "80.000đ", "Cơm nóng ăn kèm gà rán tẩm sốt chua ngọt bí truyền", R.drawable.chicken));
        listProduct.add(new Product("Burger phô mai", "30.000đ", "Burger phô mai cổ điển", R.drawable.double_cheese));
        listProduct.add(new Product("Mì Spaghetti", "90.000đ", "Sợi mì spaghetti tươi ngon nấu cùng sốt cà chua nguyên chất", R.drawable.spaghetti));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__page, container, false);
        Mapping(view);
        ControlButton();



        return view;

    }

    private void Mapping(View view) {
        btn_DoUong_homepage = view.findViewById(R.id.btn_DoUong_homepage);
        btn_DoAn_homepage = view.findViewById(R.id.btn_DoAn_homepage);
        chatIcon = view.findViewById(R.id.chatIcon);

        listProduct = new ArrayList<>();

        rvDealHoi = view.findViewById(R.id.recyclerView_deal_hoi_home_page);
        InitializeData();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rvDealHoi.setLayoutManager(layoutManager);
        recyclerView_deal_hoi_adapter dealAdapter = new recyclerView_deal_hoi_adapter(getContext(), listProduct );
        rvDealHoi.setAdapter(dealAdapter);

        tlMonMoiBanChay = view.findViewById(R.id.tabLayout_banChay_monMoi_home_page);
        vpMonMoiBanChay = view.findViewById(R.id.view_pager_mon_moi_ban_chay_home_page);
        viewPager_mon_moi_ban_chay_home_page_adapter adapter = new viewPager_mon_moi_ban_chay_home_page_adapter(this);
        vpMonMoiBanChay.setAdapter(adapter);


        new TabLayoutMediator(tlMonMoiBanChay, vpMonMoiBanChay,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Món bán Chạy");
                            break;
                        case 1:
                            tab.setText("Món Mới");
                            break;
                    }
                }).attach();



    }

    private void ControlButton() {
        btn_DoUong_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new Drink_fragment());
                    mainActivity.getSupportFragmentManager().beginTransaction().addToBackStack("Drink_fragment").commit();
                }
            }
        });

        btn_DoAn_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new food_fragment());
                }
            }
        });

        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), list_chat_user.class);
                startActivity(chat);
            }
        });


    }
}