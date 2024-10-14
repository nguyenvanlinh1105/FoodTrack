package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.first_page;
import com.example.foodtrack.Activity.forgot_password;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.R;
import com.example.foodtrack.Activity.edit_profile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ConstraintLayout toEditHoSo, toMyOrders, doiMatKhau_profile, dangXuat_profile,btn_card_profile;
    ImageView chatIcon;


    public profile_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile_fragment newInstance(String param1, String param2) {
        profile_fragment fragment = new profile_fragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    public void Mapping(View view){
        toEditHoSo = (ConstraintLayout)view.findViewById(R.id.capNhatHoSo_profile);
        chatIcon = (ImageView)view.findViewById(R.id.chatIcon);
        toMyOrders = (ConstraintLayout) view.findViewById(R.id.donHangCuaToi_profile);
        doiMatKhau_profile = view.findViewById(R.id.doiMatKhau_profile);
        btn_card_profile= view.findViewById(R.id.btn_card_profile);
        dangXuat_profile = view.findViewById(R.id.dangXuat_profile);
    }

    public void ControlButton(){
        toEditHoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editHoSo = new Intent(getActivity(),  edit_profile.class);
                startActivity(editHoSo);
            }
        });

        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(),  list_chat_user.class);
                startActivity(chat);
            }
        });

        toMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_ongoing());
                }
            }
        });
        doiMatKhau_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent changepass = new Intent(getActivity(), forgot_password.class);
                startActivity(changepass);

            }
        });
        dangXuat_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent firstPage = new Intent(getActivity(), first_page.class);
                startActivity(firstPage);
                getActivity().finish();
            }
        });

        btn_card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity!=null){
                    mainActivity.ReplaceFragment(new mycard_frag());
                }
            }
        });



    }

}