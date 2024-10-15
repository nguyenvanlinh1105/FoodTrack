package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.foodtrack.Adapter.mycard_adapter;
import com.example.foodtrack.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_myCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_myCard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    LayoutInflater inflater;
    private ImageView backBtn;

    private mycard_adapter adapter;

    private int selectedPosition = -1; //biến lưu vị trí item được chọn

    public fragment_myCard() {
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
    public static fragment_myCard newInstance(String param1, String param2) {
        fragment_myCard fragment = new fragment_myCard();
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
        View view = inflater.inflate(R.layout.fragment_mycard, container, false);
        Mapping(view);
        ControlButton();
        return view;

    }

    private void Mapping(View view){
        listView = (ListView) view.findViewById(R.id.list_mycard_card_frag);
        backBtn = view.findViewById(R.id.btn_back_myCard);
        adapter = new mycard_adapter(getContext(),imgs);
        listView.setAdapter(adapter);
    }
    private void ControlButton(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectedPosition(i);
                adapter.notifyDataSetChanged();
            }
        });
    }
}