package com.example.foodtrack.Fragment;

import android.graphics.Paint;
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

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.favorite_list_adapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favorite_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favorite_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView underlined;

    //List Item

    //khi truyền dữ liệu từ db vào thì cho dữ liệu sản phẩm trong favorite vào đây
    ArrayList<String> favoriteTitle = new ArrayList<>();
    ArrayList<Integer> favoriteImg = new ArrayList<>();
    ArrayList<String> favoriteSubTitle = new ArrayList<>();
    ArrayList<String> favoritePrice = new ArrayList<>();

    ListView listView_favorite;
    ImageView chatIcon;

    public favorite_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favorite_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static favorite_fragment newInstance(String param1, String param2) {
        favorite_fragment fragment = new favorite_fragment();
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
        favoriteTitle.add("Burger phô mai");
        favoriteTitle.add("Burger bò");
        favoriteTitle.add("Burger trứng");
        favoriteTitle.add("Burger phô mai");
        favoriteTitle.add("Burger phô mai gà");
        favoriteTitle.add("Burger mỳ ý");
        favoriteTitle.add("Burger heo quay");
        favoriteTitle.add("Burger thịt bằm");

        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.double_cheese);

        favoriteSubTitle.add("Classic cheesburger");
        favoriteSubTitle.add("Classic cheesburger");
        favoriteSubTitle.add("Classic cheesburger");
        favoriteSubTitle.add("Classic cheesburger");
        favoriteSubTitle.add("Classic cheesburger");
        favoriteSubTitle.add("Classic cheesburger");
        favoriteSubTitle.add("Classic cheesburger");
        favoriteSubTitle.add("Classic cheesburger");

        favoritePrice.add("50.000đ");
        favoritePrice.add("50.000đ");
        favoritePrice.add("50.000đ");
        favoritePrice.add("50.000đ");
        favoritePrice.add("50.000đ");
        favoritePrice.add("50.000đ");
        favoritePrice.add("50.000đ");
        favoritePrice.add("50.000đ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    public void Mapping(View view) {
        underlined = (TextView) view.findViewById(R.id.underlined_favorite);
        underlined.setPaintFlags(underlined.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        listView_favorite = (ListView) view.findViewById(R.id.listView_favorite);
        favorite_list_adapter listAdapter = new favorite_list_adapter(getContext(), favoriteTitle, favoriteImg, favoriteSubTitle, favoritePrice);
        listView_favorite.setAdapter(listAdapter);

        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
    }

    private void ControlButton() {
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(),  list_chat_user.class);
                startActivity(chat);
            }
        });
        listView_favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("title", favoriteTitle.get(position));
                bundle.putString("price", favoritePrice.get(position));
                bundle.putString("description", favoriteSubTitle.get(position));
                bundle.putInt("image", favoriteImg.get(position));

                fragment_product_detail productDetailsFragment = fragment_product_detail.newInstance(
                        favoriteTitle.get(position),
                        favoritePrice.get(position),
                        favoriteSubTitle.get(position),
                        favoriteImg.get(position)
                );
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(productDetailsFragment);
                }
            }
        });
    }


}