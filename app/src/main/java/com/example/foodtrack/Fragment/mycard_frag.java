package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.service.controls.Control;
import android.service.controls.templates.ControlButton;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.foodtrack.Adapter.card_adapter;
import com.example.foodtrack.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mycard_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mycard_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    LayoutInflater inflater;
    private ImageView imageView21;
    public mycard_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mycard_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static mycard_frag newInstance(String param1, String param2) {
        mycard_frag fragment = new mycard_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private  ArrayList<Integer> imgs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        imgs = new ArrayList<>();
        imgs.add(R.drawable.card1);
        imgs.add(R.drawable.card2);
        imgs.add(R.drawable.card3);
        imgs.add(R.drawable.card4);
        imgs.add(R.drawable.card5);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycard_frag, container, false);
        Mapping(view);
        ControlButton();
        return view;

    }

    private void Mapping(View view){
        listView = (ListView) view.findViewById(R.id.list_mycard_card_frag);
        imageView21 = view.findViewById(R.id.imageView21);
        card_adapter adapter = new card_adapter(getContext(),imgs);
        listView.setAdapter(adapter);
    }
    private  void ControlButton(){
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}