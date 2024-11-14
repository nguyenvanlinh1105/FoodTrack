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
import com.example.foodtrack.Model.ChiTietDonHangModel;
import com.example.foodtrack.Model.DonHangModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    ImageView backBtn, imageViewTranslate;
    TextView toLichSu;
    TextView toDonHuy;
    ImageView chatIcon;
    ListView listview_myorders_ongoing;
    LinearLayout imageIfEmpty;


    ArrayList<String> orderId = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> img = new ArrayList<>();
    ArrayList<String> rate = new ArrayList<>();
    ArrayList<Integer> status = new ArrayList<>();
    ArrayList<String> orderStatus = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<Integer> qty = new ArrayList<>();

    ArrayList<DonHangModel> arrayListOrder = new ArrayList<>();

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
        List<SanPhamModel> sanPhamList = createSampleProducts();

        arrayListOrder = createSampleOrders(sanPhamList);
    }

    private List<SanPhamModel> createSampleProducts() {
        List<SanPhamModel> sanPhamList = new ArrayList<>();

        sanPhamList.add(new SanPhamModel("Cheesecake việt quất", 20000, R.drawable.dessert_ico, ""));
        sanPhamList.add(new SanPhamModel("Cơm tấm", 30000, R.drawable.com_tam, ""));

        return sanPhamList;
    }

    private ArrayList<DonHangModel> createSampleOrders(List<SanPhamModel> sanPhamList) {
        ArrayList<DonHangModel> donHangList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DonHangModel donHang = new DonHangModel();
            donHang.setIdDonHang("Mã đơn hàng: #000" + i);
            donHang.setNgayTao(new Date());
            donHang.setTinhTrang("Đang giao hàng");

            List<ChiTietDonHangModel> chiTietDonHangs = new ArrayList<>();
            ChiTietDonHangModel chiTietDonHang = new ChiTietDonHangModel();
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
        // Inflate the layout for this fragment
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
        toDonHuy = (TextView) view.findViewById(R.id.btn_donHuy_myOrders);
        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
        imageIfEmpty = (LinearLayout) view.findViewById(R.id.image_if_no_order_myOrders);
        imageViewTranslate = (ImageView) view.findViewById(R.id.imageViewTranslate);

        listview_myorders_ongoing = (ListView) view.findViewById(R.id.listview_myorders);
        myorders_ongoing_list_adapter listAdapter = new myorders_ongoing_list_adapter(getContext(), arrayListOrder);
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

        toDonHuy.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_donhuy());
                }
            }
        }));


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
                    mainActivity.ReplaceFragment(new fragment_myorders_ongoing_details());
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