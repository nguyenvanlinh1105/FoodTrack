package com.example.foodtrack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;



import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Fragment.fragment_confirm_payment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_choosing_payment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_choosing_payment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView backBtn;
    private LinearLayout momo, zaloPay;


    public fragment_choosing_payment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_choosing_payment.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_choosing_payment newInstance(String param1, String param2) {
        fragment_choosing_payment fragment = new fragment_choosing_payment();
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
        View view = inflater.inflate(R.layout.fragment_choosing_payment, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    private void Mapping(View view){
        backBtn = (ImageView) view.findViewById(R.id.btn_back_choosing_payment);
        momo = (LinearLayout) view.findViewById(R.id.momo_choosing_payment);
        zaloPay = (LinearLayout) view.findViewById(R.id.zaloPay_choosing_payment);

    }

    private void ControlButton(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int delay = 2000;
                Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ReplaceFragConfirmPayment();
                    }
                }, delay);

            }
        });

        zaloPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int delay = 2000;
                Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ReplaceFragConfirmPayment();
                    }
                }, delay);
            }
        });
    }

    private void ReplaceFragConfirmPayment(){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.ReplaceFragment(new fragment_confirm_payment());
    }
}