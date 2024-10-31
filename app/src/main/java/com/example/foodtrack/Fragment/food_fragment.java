package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.foodtrack.Adapter.list_drink_adapter;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.food_list_adapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link food_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class food_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> foodTitle = new ArrayList<>();
    ArrayList<Double> foodPrice = new ArrayList<>();
    ArrayList<Integer> foodImg = new ArrayList<>();
    ArrayList<String> foodDescription = new ArrayList<>();

    ListView listView_food;
    TextView btn_DoUong_food;
    ImageView chatIcon;
    public food_fragment() {
        // Required empty public constructor
        ImageView chatIcon;    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment food_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static food_fragment newInstance(String param1, String param2) {
        food_fragment fragment = new food_fragment();
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

    ArrayList<SanPhamModel> arraylistFood = new ArrayList<>();
    private void InitializeData() {
        foodTitle.add("Pallavi Biryani");
        foodTitle.add("Cơm tấm");
        foodTitle.add("Burger phô mai");
        foodTitle.add("Burger trứng");
        foodTitle.add("Mì Spaghetti");
        foodTitle.add("Cơm gà sốt chua ngọt");
        foodTitle.add("Mì Carbonara");
        foodTitle.add("Gnocchi sốt cà chua");

        foodImg.add(R.drawable.pallavi_biryani);
        foodImg.add(R.drawable.com_tam);
        foodImg.add(R.drawable.double_cheese);
        foodImg.add(R.drawable.double_cheese);
        foodImg.add(R.drawable.spaghetti);
        foodImg.add(R.drawable.chicken);
        foodImg.add(R.drawable.carbonara);
        foodImg.add(R.drawable.gnocchi_tomato);

        foodPrice.add(50000.0);
        foodPrice.add(30000.0);
        foodPrice.add(20000.0);
        foodPrice.add(50000.0);
        foodPrice.add(80000.0);
        foodPrice.add(90000.0);
        foodPrice.add(80000.0);
        foodPrice.add(90000.0);


        foodDescription.add("Pallavi Biryani là một món cơm trộn đặc biệt từ Ấn Độ, được nấu với gạo thơm, thịt và gia vị độc đáo.");
        foodDescription.add("Cơm tấm là món ăn truyền thống của Việt Nam, gồm cơm tấm, sườn nướng, bì và chả.");
        foodDescription.add("Burger phô mai là món burger với nhân thịt bò và phô mai tan chảy thơm ngon.");
        foodDescription.add("Burger trứng là sự kết hợp giữa nhân thịt bò và trứng ốp la, mang đến hương vị béo ngậy.");
        foodDescription.add("Sợi mì spaghetti tươi ngon nấu cùng sốt cà chua nguyên chất");
        foodDescription.add("Cơm nóng ăn kèm gà rán tẩm sốt chua ngọt bí truyền");
        foodDescription.add("Carbonara béo ngậy hòa cùng chút mặn đến từ thịt xông khói.");
        foodDescription.add(".Gnocchi tươi nấu cùng sốt cà chua nhà làm.");

        for (int i =0 ;i<foodTitle.size(); i++){
            arraylistFood.add(new SanPhamModel(foodTitle.get(i), foodPrice.get(i),  foodImg.get(i),foodDescription.get(i)));
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }


    private void Mapping(View view){
        listView_food = (ListView) view.findViewById(R.id.listView_food);
        btn_DoUong_food = view.findViewById(R.id.btn_DoUong_food );

        InitializeData();
        food_list_adapter listAdapter = new food_list_adapter(getContext(), arraylistFood);
        listView_food.setAdapter(listAdapter);

        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);

        listView_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("title", foodTitle.get(position));
                bundle.putDouble("price", foodPrice.get(position));
                bundle.putString("description", foodDescription.get(position));
                bundle.putInt("image", foodImg.get(position));

                fragment_product_detail productDetailsFragment = fragment_product_detail.newInstance(
                        foodTitle.get(position),
                        foodPrice.get(position),
                        foodDescription.get(position),
                        foodImg.get(position)
                );
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(productDetailsFragment);
                }


            }
        });
    }

    private void ControlButton(){

        btn_DoUong_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new Drink_fragment());
                }
            }
        });

        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(),list_chat_user.class);
                startActivity(chat);
            }
        });

    }

    private void GetMonAn(){
        APIService.API_SERVICE.getListMonAn_Explore().enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                if(response.isSuccessful()&& response.body()!=null &&!response.body().isEmpty()){
                        List<SanPhamAPIModel> listMonAn_explore = response.body();
//                    list_drink_adapter listAdapter = new list_drink_adapter_(getContext(), arrayListDrink);
//                    listView_drink.setAdapter(listAdapter);
                }else{
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
        UpdateRecyclerView(arraylistFood);
    }

    private void UpdateRecyclerView(List<SanPhamModel> data) {
        food_list_adapter listAdapter = new food_list_adapter(getContext(), arraylistFood);
        listView_food.setAdapter(listAdapter);
    }


}