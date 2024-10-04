package com.example.foodtrack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.service.controls.templates.ControlButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import android.content.Intent;

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
    ArrayList<String> foodPrice = new ArrayList<>();
    ArrayList<Integer> foodImg = new ArrayList<>();

    ListView listView_food;
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
        initializeData();
    }

    private void initializeData() {
        foodTitle.add("Pallavi Biryani");
        foodTitle.add("Cơm tấm");
        foodTitle.add("Burger phô mai");
        foodTitle.add("Burger trứng");

        foodImg.add(R.drawable.pallavi_biryani);
        foodImg.add(R.drawable.com_tam);
        foodImg.add(R.drawable.double_cheese);
        foodImg.add(R.drawable.double_cheese);

        foodPrice.add("50.000đ");
        foodPrice.add("30.000đ");
        foodPrice.add("20.000đ");
        foodPrice.add("50.000đ");
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
        food_list_adapter listAdapter = new food_list_adapter(getContext(), foodTitle, foodPrice, foodImg);
        listView_food.setAdapter(listAdapter);

        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
    }

    private void ControlButton(){
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(),  list_chat_user.class);
                startActivity(chat);
            }
        });
    }
}