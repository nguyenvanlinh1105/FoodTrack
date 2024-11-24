package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.myorders_ongoing_list_adapter;
import com.example.foodtrack.Adapter.myorders_ongoing_list_adapter_api;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class fragment_myorders_ongoing_API extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ImageView backBtn, imageViewTranslate;
    TextView toLichSu;
    ImageView chatIcon;
    ListView listview_myorders_ongoing;
    LinearLayout imageIfEmpty;

    ArrayList<DonHangAPIModel> arrayListOrder = new ArrayList<>();

    public fragment_myorders_ongoing_API() {
        // Required empty public constructor
    }

    public static fragment_myorders_ongoing_API newInstance(String param1, String param2) {
        fragment_myorders_ongoing_API fragment = new fragment_myorders_ongoing_API();
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
        List<SanPhamAPIModel> sanPhamList = createSampleProducts();
        arrayListOrder = createSampleOrders(sanPhamList);
    }

    private List<SanPhamAPIModel> createSampleProducts() {
        List<SanPhamAPIModel> sanPhamList = new ArrayList<>();

        sanPhamList.add(new SanPhamAPIModel("Cheesecake việt quất", 20000, "R.drawable.dessert_ico", ""));
        sanPhamList.add(new SanPhamAPIModel("Cơm tấm", 30000, "R.drawable.com_tam", ""));

        return sanPhamList;
    }

    private ArrayList<DonHangAPIModel> createSampleOrders(List<SanPhamAPIModel> sanPhamList) {
        ArrayList<DonHangAPIModel> donHangList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DonHangAPIModel donHang = new DonHangAPIModel();
            donHang.setIdDonHang("Order: #000" + i);
            donHang.setNgayTao(new Date());
            donHang.setTinhTrang("Đang giao hàng");

            List<ChiTietDonHangAPIModel> chiTietDonHangs = new ArrayList<>();
            ChiTietDonHangAPIModel chiTietDonHang = new ChiTietDonHangAPIModel();
            chiTietDonHang.setSanPham(sanPhamList.get(i));
            chiTietDonHang.setSoLuongDat(i + 1);
            chiTietDonHangs.add(chiTietDonHang);

            donHang.setChiTietDonHangs(chiTietDonHangs);
            donHangList.add(donHang);
        }

        return donHangList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_ongoing, container, false);

        Mapping(view);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_shipper);
        imageViewTranslate.startAnimation(animation);
        checkIfListEmpty();
        ControlButton();
        return view;
    }

    public void Mapping(View view) {
        backBtn = (ImageView) view.findViewById(R.id.btn_back_myorders_ongoing);
        toLichSu = (TextView) view.findViewById(R.id.btn_lichSu_myOrders);
        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
        imageIfEmpty = (LinearLayout) view.findViewById(R.id.image_if_no_order_myOrders);
        imageViewTranslate = (ImageView) view.findViewById(R.id.imageViewTranslate);

        listview_myorders_ongoing = (ListView) view.findViewById(R.id.listview_myorders);
        myorders_ongoing_list_adapter_api listAdapter = new myorders_ongoing_list_adapter_api(getContext(), arrayListOrder);
        listview_myorders_ongoing.setAdapter(listAdapter);
    }

    public void ControlButton() {
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

        listview_myorders_ongoing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    DonHangAPIModel selectedOrder = arrayListOrder.get(i);
                    String id  = selectedOrder.getIdDonHang();
                    Bundle bundle = new Bundle();
                    bundle.putString("idDonHang",id);

                    // Tạo fragment mới và gán Bundle
                    fragment_myorders_ongoing_details detailsFragment = new fragment_myorders_ongoing_details();
                    detailsFragment.setArguments(bundle);

                    // Chuyển sang fragment mới
                    mainActivity.ReplaceFragment(detailsFragment);
                }
            }
        });



    }

    private void checkIfListEmpty() {
        if (arrayListOrder.isEmpty()) {
            listview_myorders_ongoing.setVisibility(View.GONE);
            imageIfEmpty.setVisibility(View.VISIBLE);
            imageViewTranslate.setVisibility(View.GONE);
        } else {
            listview_myorders_ongoing.setVisibility(View.VISIBLE);
            imageViewTranslate.setVisibility(View.VISIBLE);
            imageIfEmpty.setVisibility(View.GONE);
        }
    }
}
