package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.myorders_history_list_adapter;
import com.example.foodtrack.Adapter.myorders_ongoing_list_adapter;
import com.example.foodtrack.Model.Order;
import com.example.foodtrack.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_myorders_ongoing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_myorders_ongoing extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView backBtn;
    TextView toLichSu;
    ImageView chatIcon;
    ListView listview_myorders_ongoing;
    LinearLayout imageIfEmpty;

    ArrayList<String> orderId = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> img = new ArrayList<>();
    ArrayList<String> rate = new ArrayList<>();    ArrayList<Integer> status = new ArrayList<>();
    ArrayList<String> orderStatus = new ArrayList<>();
    ArrayList<String>  price = new ArrayList<>();
    ArrayList<Integer> qty = new ArrayList<>();

    ArrayList<Order> arrayListOrder = new ArrayList<>();

    public fragment_myorders_ongoing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_myorders_ongoing.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_myorders_ongoing newInstance(String param1, String param2) {
        fragment_myorders_ongoing fragment = new fragment_myorders_ongoing();
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
        for (int i = 0; i < 2; i++)
            orderId.add("Order: #000" + i);
        for (int i = 0; i < 2; i++)
            time.add("22-9-2024, 12:00 p.m");

        name.add("Cheesecake việt quất");
        name.add("Cơm tấm");


        img.add(R.drawable.dessert_ico);
        img.add(R.drawable.com_tam);

        status.add(0);
        status.add(0);

        price.add("20.000đ");
        price.add("30.000đ");

        qty.add(1);
        qty.add(2);

        for (int i = 0; i < 6; i++)
            orderStatus.add("Đang giao hàng");

        for (int i = 0; i < orderId.size(); i++) {
            arrayListOrder.add(new Order(orderId.get(i), time.get(i), name.get(i), orderStatus.get(i), img.get(i), status.get(i),price.get(i), qty.get(i)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myorders_ongoing, container, false);
        Mapping(view);
        checkIfListEmpty();
        ControlButton();
        return view;
    }

    public void Mapping(View view){
        backBtn = (ImageView) view.findViewById(R.id.btn_back_myorders_ongoing);
        toLichSu = (TextView) view.findViewById(R.id.btn_lichSu_myOrders);
        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
        imageIfEmpty = (LinearLayout) view.findViewById(R.id.image_if_no_order_myOrders);

        listview_myorders_ongoing = (ListView) view.findViewById(R.id.listview_myorders);
        myorders_ongoing_list_adapter listAdapter = new myorders_ongoing_list_adapter(getContext(), arrayListOrder);
        listview_myorders_ongoing.setAdapter(listAdapter);
    }
    public void ControlButton(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();

            }
        });

        toLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_history());
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

    private void checkIfListEmpty() {
        if (arrayListOrder.isEmpty()) {
            listview_myorders_ongoing.setVisibility(View.GONE);
            imageIfEmpty.setVisibility(View.VISIBLE);
        } else {
            listview_myorders_ongoing.setVisibility(View.VISIBLE);
            imageIfEmpty.setVisibility(View.GONE);
        }
    }
}