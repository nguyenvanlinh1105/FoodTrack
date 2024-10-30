package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.recyclerView_deal_hoi_API_adapter;
import com.example.foodtrack.Adapter.recyclerView_deal_hoi_adapter;
import com.example.foodtrack.Adapter.viewPager_mon_moi_ban_chay_home_page_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ImageView chatIcon, imageViewRotate1, imageViewRotate2, imageViewRotate_DealHoi;

    private List<SanPhamModel> listProduct;
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
        listProduct.add(new SanPhamModel("Salad trái cây", 70000, R.drawable.icon_food1, "Salad cổ điển cùng trái cây tươi trong ngày"));
        listProduct.add(new SanPhamModel("Mì Carbonara", 90000, R.drawable.carbonara, "Carbonara béo ngậy hòa cùng chút mặn đến từ thịt xông khói"));
        listProduct.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listProduct.add(new SanPhamModel("Gnocchi sốt cà chua", 80000, R.drawable.gnocchi_tomato, "Gnocchi tươi nấu cùng sốt cà chua nhà làm"));
        listProduct.add(new SanPhamModel("Cơm gà sốt chua ngọt", 80000, R.drawable.chicken, "Cơm nóng ăn kèm gà rán tẩm sốt chua ngọt bí truyền"));
        listProduct.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listProduct.add(new SanPhamModel("Mì Spaghetti", 90000, R.drawable.spaghetti, "Sợi mì spaghetti tươi ngon nấu cùng sốt cà chua nguyên chất"));
        listProduct.add(new SanPhamModel("Salad trái cây", 70000, R.drawable.icon_food1, "Salad cổ điển cùng trái cây tươi trong ngày"));
        listProduct.add(new SanPhamModel("Mì Carbonara", 90000, R.drawable.carbonara, "Carbonara béo ngậy hòa cùng chút mặn đến từ thịt xông khói"));
        listProduct.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listProduct.add(new SanPhamModel("Gnocchi sốt cà chua", 80000, R.drawable.gnocchi_tomato, "Gnocchi tươi nấu cùng sốt cà chua nhà làm"));
        listProduct.add(new SanPhamModel("Cơm gà sốt chua ngọt", 80000, R.drawable.chicken, "Cơm nóng ăn kèm gà rán tẩm sốt chua ngọt bí truyền"));
        listProduct.add(new SanPhamModel("Burger phô mai", 30000, R.drawable.double_cheese, "Burger phô mai cổ điển"));
        listProduct.add(new SanPhamModel("Mì Spaghetti", 90000, R.drawable.spaghetti, "Sợi mì spaghetti tươi ngon nấu cùng sốt cà chua nguyên chất"));
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
        rvDealHoi = view.findViewById(R.id.recyclerView_deal_hoi_home_page);
        imageViewRotate1 = view.findViewById(R.id.imageViewRotate1);
        imageViewRotate2 = view.findViewById(R.id.imageViewRotate2);
        imageViewRotate_DealHoi = view.findViewById(R.id.imageViewRotate_DealHoi);

        Animation anim_rotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        Animation anim_rotate_dealhoi = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_dealhoi);

        imageViewRotate_DealHoi.startAnimation(anim_rotate_dealhoi);
        imageViewRotate1.startAnimation(anim_rotate);
        imageViewRotate2.startAnimation(anim_rotate);



        listProduct = new ArrayList<>();

        InitializeData();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rvDealHoi.setLayoutManager(layoutManager);
        recyclerView_deal_hoi_adapter dealAdapter = new recyclerView_deal_hoi_adapter(getContext(), listProduct );
        rvDealHoi.setAdapter(dealAdapter);

    //    GetDealHoi();

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

    private void GetDealHoi(){
        APIService.API_SERVICE.getListSanphamHomePage_DealHoi().enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SanPhamAPIModel> listSanPhamDeaHoi = response.body();
                    Log.d("API_SUCCESS", "Data size: " + listSanPhamDeaHoi.size());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                    rvDealHoi.setLayoutManager(layoutManager);
                    recyclerView_deal_hoi_API_adapter dealAdapter = new recyclerView_deal_hoi_API_adapter(getContext(), listSanPhamDeaHoi);
                    rvDealHoi.setAdapter(dealAdapter);
//                    Log.d("Text.....", listSanPhamDeaHoi.get(0).getImages());

                } else {
                    UseFallbackData();
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                }
            }


            @Override
            public void onFailure(Call<List<SanPhamAPIModel>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                if (t instanceof JsonSyntaxException) {
                    JsonSyntaxException jsonError = (JsonSyntaxException) t;
                    Log.e("API_ERROR", "JSON Error: " + jsonError.getCause());
                }
                t.printStackTrace();
                Toast.makeText(getContext(), "Lỗi dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UseFallbackData() {
        // Khởi tạo dữ liệu nếu get fail api
        listProduct = new ArrayList<>();
        InitializeData(); // Hàm này sẽ thêm dữ liệu vào listProduct nếu không lấy được data từ api
        UpdateRecyclerView(listProduct);
    }

    private void UpdateRecyclerView(List<SanPhamModel> data) {
        // Cập nhật RecyclerView với dữ liệu (có thể từ API hoặc dữ liệu khởi tạo) rút gọn code cho dễ nhìn
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rvDealHoi.setLayoutManager(layoutManager);
        recyclerView_deal_hoi_adapter dealAdapter = new recyclerView_deal_hoi_adapter(getContext(), data);
        rvDealHoi.setAdapter(dealAdapter);
    }


}