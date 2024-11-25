package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Adapter.recyclerView_ban_chay_API_adapter;
import com.example.foodtrack.Adapter.recyclerView_mon_moi_API_adapter;
import com.example.foodtrack.Adapter.recyclerView_mon_moi_adapter;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_viewpager_mon_moi_homepage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_viewpager_mon_moi_homepage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<SanPhamModel> listProduct;
    private RecyclerView rvMonMoi;

    public fragment_viewpager_mon_moi_homepage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_viewpager_mon_moi_homepage.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_viewpager_mon_moi_homepage newInstance(String param1, String param2) {
        fragment_viewpager_mon_moi_homepage fragment = new fragment_viewpager_mon_moi_homepage();
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
        View view = inflater.inflate(R.layout.fragment_viewpager_mon_moi_homepage, container, false);
        Mapping(view);
        return view;
    }

    private void Mapping(View view) {
        listProduct = new ArrayList<>();

        rvMonMoi = view.findViewById(R.id.recyclerView_mon_moi_homepage);
//        InitializeData();
//        GridLayoutManager layoutManager
//                = new GridLayoutManager(requireContext(),1);
//        rvMonMoi.setLayoutManager(layoutManager);
//        recyclerView_mon_moi_adapter dealAdapter = new recyclerView_mon_moi_adapter(getContext(), listProduct);
//        rvMonMoi.setAdapter(dealAdapter);


        GetMonMoi();

    }
    private void GetMonMoi() {
        APIService.API_SERVICE.getListSanphamHomePage_MonMoi().enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                if(response.isSuccessful()&&response.body()!=null && !response.body().isEmpty()){
                    List<SanPhamAPIModel> listMonBanChay = response.body();
//                    UpdateRecyclerView(listMonBanChay);
                    UpdateRecyclerViewAPI(listMonBanChay);

                }else{
                   // UseFallbackData();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamAPIModel>> call, Throwable t) {
              //  UseFallbackData();
            }
        });
    }

    private void UseFallbackData() {
        // Khởi tạo dữ liệu giả lập
        listProduct = new ArrayList<>();
       // InitializeData(); // Hàm này sẽ thêm dữ liệu vào listProduct
        UpdateRecyclerView(listProduct);
    }

    private void UpdateRecyclerView(List<SanPhamModel> data) {
        // Cập nhật RecyclerView với dữ liệu (có thể từ API hoặc dữ liệu khởi tạo)
        GridLayoutManager layoutManager
                = new GridLayoutManager(requireContext(),1);
        rvMonMoi.setLayoutManager(layoutManager);
        recyclerView_mon_moi_adapter dealAdapter = new recyclerView_mon_moi_adapter(getContext(), listProduct);
        rvMonMoi.setAdapter(dealAdapter);
    }

    private void UpdateRecyclerViewAPI(List<SanPhamAPIModel> ApiData){
        GridLayoutManager layoutManager
                = new GridLayoutManager(requireContext(), 1);
        rvMonMoi.setLayoutManager(layoutManager);
        recyclerView_mon_moi_API_adapter dealAdapter = new recyclerView_mon_moi_API_adapter(getContext(), ApiData);
        rvMonMoi.setAdapter(dealAdapter);
    }
}