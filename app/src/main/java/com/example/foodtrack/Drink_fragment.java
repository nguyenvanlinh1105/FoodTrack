package com.example.foodtrack;

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
import android.content.Intent;
import android.widget.Toast;

import com.example.foodtrack.Model.productModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Drink_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Drink_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> drinkTitle = new ArrayList<>();
    ArrayList<String> drinkPrice = new ArrayList<>();
    ArrayList<Integer> drinkImg = new ArrayList<>();
    ArrayList<String> drinkDescription = new ArrayList<>();

    ListView listView_drink;
    TextView btn_DoAn_food;

    ImageView chatIcon;
    public Drink_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Drink.
     */
    // TODO: Rename and change types and number of parameters
    public static Drink_fragment newInstance(String param1, String param2) {
        Drink_fragment fragment = new Drink_fragment();
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
        initializeData();

    }
    ArrayList<productModel> arrayListDrink = new ArrayList<>();
    private void initializeData() {
        drinkTitle.add("Trà đào cam sả");
        drinkTitle.add("Cà phê capuccino");
        drinkTitle.add("Trà chanh");
        drinkTitle.add("Cà phê muối");
        drinkTitle.add("Cà phê sữa đá");

        drinkImg.add(R.drawable.drink2);
        drinkImg.add(R.drawable.drink1);
        drinkImg.add(R.drawable.drink2);
        drinkImg.add(R.drawable.drink1);
        drinkImg.add(R.drawable.drink2);

        drinkPrice.add("60.000đ");
        drinkPrice.add("30.000đ");
        drinkPrice.add("20.000đ");
        drinkPrice.add("50.000đ");
        drinkPrice.add("50.000đ");

        // Thêm dữ liệu vào drinkDescription
        drinkDescription.add("Thức uống giải khát có mùi vị thơm ngon.");
        drinkDescription.add("Cà phê được pha chế theo phong cách Ý.");
        drinkDescription.add("Trà chanh mát lạnh, phù hợp cho ngày hè.");
        drinkDescription.add("Cà phê có vị muối đặc biệt.");
        drinkDescription.add("Cà phê sữa đá truyền thống.");

        // Thêm dữ liệu vào arrayListDrink
        for (int i = 0; i < drinkImg.size(); i++) {
            arrayListDrink.add(new productModel(drinkTitle.get(i), drinkPrice.get(i), drinkDescription.get(i), drinkImg.get(i)));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_drink, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        listView_drink = (ListView) view.findViewById(R.id.listView_drink);
        btn_DoAn_food = view.findViewById(R.id.btn_DoAn_food);

        list_drink_adapter listAdapter = new list_drink_adapter(getContext(), arrayListDrink);
        listView_drink.setAdapter(listAdapter);

        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);

        // Thiết lập sự kiện cho từng item trong ListView
        listView_drink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi click vào item
                //String selectedDrinkTitle = drinkTitle.get(position);// đang trỏ vào item tương ứng position

//                Intent i = new Intent(getActivity(), product_detail.class);
//                i.putExtra("title",drinkTitle.get(position));
//                i.putExtra("price",drinkPrice.get(position));
//                i.putExtra("description",drinkDescription.get(position));
//                i.putExtra("image",drinkImg.get(position));
//                startActivity(i);

                Bundle bundle = new Bundle();
                bundle.putString("title", drinkTitle.get(position));
                bundle.putString("price", drinkPrice.get(position));
                bundle.putString("description", drinkDescription.get(position));
                bundle.putInt("image", drinkImg.get(position));

                fragment_product_detail productDetailsFragment = fragment_product_detail.newInstance(
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


    public void ControlButton(){
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
                Intent chat = new Intent(getActivity(),  list_chat_user.class);
                startActivity(chat);
            }
        });
    }




}