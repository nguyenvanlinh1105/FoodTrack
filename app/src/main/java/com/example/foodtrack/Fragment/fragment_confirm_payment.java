package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_confirm_payment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_confirm_payment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView chat_icon;
    private TextView btn_my_orders_ongoing, btn_home;

    public fragment_confirm_payment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_confirm_payment.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_confirm_payment newInstance(String param1, String param2) {
        fragment_confirm_payment fragment = new fragment_confirm_payment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_payment, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    private void Mapping(View view){
        chat_icon = (ImageView) view.findViewById(R.id.chatIcon);
        btn_my_orders_ongoing = (TextView) view.findViewById(R.id.to_my_orders_confirm_payment);
        btn_home = (TextView) view.findViewById(R.id.to_home_confirm_payment);
    }

    private void ControlButton(){

        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), list_chat_user.class);
                startActivity(chat);
            }
        });
        btn_my_orders_ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_ongoing());
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new Home_Page());
                }
            }
        });
    }
}