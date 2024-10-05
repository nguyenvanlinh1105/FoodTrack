package com.example.foodtrack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import android.content.Intent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Drink#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Drink extends Fragment {

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

    ListView listView_drink;
    TextView btn_DoAn_food;

    ImageView chatIcon;
    public Drink() {
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
    public static Drink newInstance(String param1, String param2) {
        Drink fragment = new Drink();
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
    private void initializeData() {
        drinkTitle.add("Trà đào cam sả");
        drinkTitle.add("Cà phê capuccino");
        drinkTitle.add("Trà chanh");
        drinkTitle.add("Cà phê muối");

        drinkImg.add(R.drawable.drink2);
        drinkImg.add(R.drawable.drink1);
        drinkImg.add(R.drawable.drink2);
        drinkImg.add(R.drawable.drink1);
        drinkImg.add(R.drawable.drink2);

        drinkPrice.add("60.000đ");
        drinkPrice.add("30.000đ");
        drinkPrice.add("20.000đ");
        drinkPrice.add("50.000đ");
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
    private void Mapping(View view){
        listView_drink = (ListView) view.findViewById(R.id.listView_drink);
        btn_DoAn_food = view.findViewById(R.id.btn_DoAn_food );
        food_list_adapter listAdapter = new food_list_adapter(getContext(), drinkTitle, drinkPrice, drinkImg);
        listView_drink.setAdapter(listAdapter);

        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
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