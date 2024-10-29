package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.recyclerView_deal_hoi_adapter;
import com.example.foodtrack.Model.SanPhamAPIModel; // Cập nhật import cho SanPhamAPIModel
import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.list_drink_adapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Drink_fragment_API#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Drink_fragment_API extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ArrayList<String> drinkTitle = new ArrayList<>();
    ArrayList<Double> drinkPrice = new ArrayList<Double>();
    ArrayList<String> drinkImg = new ArrayList<>(); // Thay đổi kiểu về String
    ArrayList<String> drinkDescription = new ArrayList<>();

    ListView listView_drink;
    TextView btn_DoAn_food;

    ImageView chatIcon;

    public Drink_fragment_API() {
        // Required empty public constructor
    }

    public static Drink_fragment_API newInstance(String param1, String param2) {
        Drink_fragment_API fragment = new Drink_fragment_API();
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

    ArrayList<SanPhamAPIModel> arrayListDrink = new ArrayList<>();

    private void InitializeData() {
        // Cập nhật lại dữ liệu với SanPhamAPIModel
        drinkTitle.add("Trà đào cam sả");
        drinkTitle.add("Cà phê capuccino");
        drinkTitle.add("Trà chanh");
        drinkTitle.add("Cà phê muối");
        drinkTitle.add("Cà phê sữa đá");

        drinkImg.add("drawable/drink2"); // Thay đổi kiểu thành String
        drinkImg.add("drawable/drink1");
        drinkImg.add("drawable/drink2");
        drinkImg.add("drawable/drink1");
        drinkImg.add("drawable/drink2");

        drinkPrice.add(60000.0);
        drinkPrice.add(30000.0);
        drinkPrice.add(20000.0);
        drinkPrice.add(50000.0);
        drinkPrice.add(50000.0);

        drinkDescription.add("Thức uống giải khát có mùi vị thơm ngon.");
        drinkDescription.add("Cà phê được pha chế theo phong cách Ý.");
        drinkDescription.add("Trà chanh mát lạnh, phù hợp cho ngày hè.");
        drinkDescription.add("Cà phê có vị muối đặc biệt.");
        drinkDescription.add("Cà phê sữa đá truyền thống.");

        // Thêm dữ liệu vào arrayListDrink
        for (int i = 0; i < drinkImg.size(); i++) {
            arrayListDrink.add(new SanPhamAPIModel(drinkTitle.get(i), drinkPrice.get(i), drinkImg.get(i), drinkDescription.get(i)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        listView_drink = (ListView) view.findViewById(R.id.listView_drink);
        btn_DoAn_food = view.findViewById(R.id.btn_DoAn_food);
        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);

        InitializeData();
        list_drink_adapter listAdapter = new list_drink_adapter(getContext(), arrayListDrink);
        listView_drink.setAdapter(listAdapter);

        // Thiết lập sự kiện cho từng item trong ListView
        listView_drink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi click vào item
                Bundle bundle = new Bundle();
                bundle.putString("title", drinkTitle.get(position));
                bundle.putDouble("price", drinkPrice.get(position));
                bundle.putString("description", drinkDescription.get(position));
                bundle.putString("image", drinkImg.get(position)); // Cập nhật kiểu về String

                fragment_product_detail_API productDetailsFragment = fragment_product_detail_API.newInstance(
                        drinkTitle.get(position),
                        drinkPrice.get(position),
                        drinkDescription.get(position),
                        drinkImg.get(position)
                );

                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(productDetailsFragment);
                }
            }
        });
    }

    public void ControlButton() {
        btn_DoAn_food.setOnClickListener(new View.OnClickListener() {
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

    private void GetDoUong() {
        APIService.API_SERVICE.getListDoUong_Explore().enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<SanPhamAPIModel> listUongDo_explore = response.body();
                    UpdateRecyclerView(listUongDo_explore);
                } else {
                    UseFallbackData();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamAPIModel>> call, Throwable t) {
                UseFallbackData();
            }
        });
    }

    private void UseFallbackData() {
        InitializeData(); // Hàm này sẽ thêm dữ liệu vào listProduct
        UpdateRecyclerView(arrayListDrink);
    }

    private void UpdateRecyclerView(List<SanPhamAPIModel> data) {
        list_drink_adapter listAdapter = new list_drink_adapter(getContext(), data);
        listView_drink.setAdapter(listAdapter);
    }
}
