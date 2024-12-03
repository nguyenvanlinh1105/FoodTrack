package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.myorders_donHuy_list_adapter_api;
import com.example.foodtrack.Adapter.myorders_history_list_adapter_api;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_myorders_history_API extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    List<ChiTietDonHangAPIModel> arrayListOrder;
    ListView listview_myorders_history;
    LinearLayout imageIfEmpty;
    ImageView chatIcon;
    ImageView backBtn;
    TextView toOngoing, rateBtn;


    public fragment_myorders_history_API() {
        // Required empty public constructor
    }

    public static fragment_myorders_history_API newInstance(String param1, String param2) {
        fragment_myorders_history_API fragment = new fragment_myorders_history_API();
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
     //   initializeData();
        SharedPreferences dataUserResponse = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
        String idUser = dataUserResponse.getString("idUser","");
        GetdsLichSuSanPhamDaMua(idUser);
    }

    private void initializeData() {
        // Sample data
        String[] orderIds = {"#0000", "#0001", "#0002", "#0003", "#0004", "#0005"};
        String[] times = {"22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM"};
        String[] names = {"Cheesecake việt quất", "Cơm tấm", "Trà chanh", "Cà phê capuccino", "Salad hoa quả", "Pallavi Biryani"};
        String[] imgLinks = {
                "https://example.com/dessert_ico.png",
                "https://example.com/com_tam.png",
                "https://example.com/drink2.png",
                "https://example.com/drink1.png",
                "https://example.com/icon_food1.png",
                "https://example.com/pallavi_biryani.png"
        };
        int[] statuses = {0, 0, 1, 1, 1, 1};
        String[] prices = {"20.000đ", "30.000đ", "50.000đ", "20.000đ", "40.000đ", "20.000đ"};
        int[] quantities = {1, 2, 1, 4, 1, 3};

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm a", Locale.getDefault());

        for (int i = 0; i < orderIds.length; i++) {
            try {
                Date createdAt = dateFormat.parse(times[i]);
               // arrayListOrder.add(new OrderAPIModel(orderIds[i], createdAt, names[i], statuses[i] == 0 ? "Pending" : "Delivered", imgLinks[i], statuses[i], prices[i], quantities[i]));

             //   arrayListOrder.add(new SanPhamAPIModel())
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_history, container, false);
        Mapping(view);
      //  checkIfListEmpty();
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        listview_myorders_history = view.findViewById(R.id.listview_myorders);
        if (arrayListOrder == null) {
            arrayListOrder = new ArrayList<>();
        }
        myorders_history_list_adapter_api listAdapter = new myorders_history_list_adapter_api(getContext(), arrayListOrder);
        listview_myorders_history.setAdapter(listAdapter);

        backBtn = view.findViewById(R.id.btn_back_myorders_history);
        chatIcon = view.findViewById(R.id.chatIcon);
        toOngoing = view.findViewById(R.id.btn_dangDen_myOrder);
        imageIfEmpty = view.findViewById(R.id.image_if_no_order_myOrders);
        rateBtn = view.findViewById(R.id.ratingBtn_item_myOrders);
    }


    private void checkIfListEmpty() {
        if (arrayListOrder.isEmpty()) {
            listview_myorders_history.setVisibility(View.GONE);
            imageIfEmpty.setVisibility(View.VISIBLE);
        } else {
            listview_myorders_history.setVisibility(View.VISIBLE);
            imageIfEmpty.setVisibility(View.GONE);
        }
    }

    private void ControlButton() {
        chatIcon.setOnClickListener(view -> {
            Intent chat = new Intent(getActivity(), list_chat_user.class);
            startActivity(chat);
        });

        listview_myorders_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChiTietDonHangAPIModel selectedProduct = arrayListOrder.get(i); // Lấy sản phẩm được chọn

                // Tạo bundle để truyền dữ liệu
                Bundle bundle = new Bundle();
                bundle.putString("idSanPham", selectedProduct.getIdSanPham());
                bundle.putString("title", selectedProduct.getProduct().getTenSanPham());
                bundle.putDouble("price", selectedProduct.getProduct().getGiaTien());
                bundle.putString("description", selectedProduct.getProduct().getMoTa());
                bundle.putString("image",selectedProduct.getProduct().getImages());

                // Tạo fragment mới và truyền bundle vào
                fragment_product_detail_API detailFragment = new fragment_product_detail_API();
                detailFragment.setArguments(bundle);

                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(detailFragment);
                }
            }
        });


        toOngoing.setOnClickListener(view -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.ReplaceFragment(new fragment_myorders_ongoing_API());
            }
        });

        backBtn.setOnClickListener(view -> requireActivity().getSupportFragmentManager().popBackStack());
    }


    private class  GetOrdersTask extends AsyncTask<String, Void, List<DonHangAPIModel>> {
        @Override
        protected List<DonHangAPIModel> doInBackground(String... params) {
            // Gọi API ở đây
            try {
                Response<List<DonHangAPIModel>> response = APIService.API_SERVICE.GetSanPhamDaMua(params[0]).execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DonHangAPIModel> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(result);
                Log.d("responseBody", "onPostExecute: " + json);
                List<DonHangAPIModel> listSP = result;
                List<ChiTietDonHangAPIModel> listChiTiet = new ArrayList<>();

                // Lọc dữ liệu chi tiết đơn hàng
                for (DonHangAPIModel dh : listSP) {
                    for (ChiTietDonHangAPIModel ctdh : dh.getChiTietDonHangs()) {
                        ChiTietDonHangAPIModel donhang = new ChiTietDonHangAPIModel();
                        donhang.setIdDonHang(dh.getIdDonHang());
                        donhang.setTrangThai(dh.getTinhTrang());
                        donhang.setNgayGiaoHang(dh.getThoiGianHoanThanh());
                        dh.setNgayTao(dh.getNgayTao());
                        donhang.setHasComment(ctdh.getHasComment());
                        donhang.setSoLuongDat(ctdh.getSoLuongDat());
                        donhang.setProduct(ctdh.getProduct());
                        listChiTiet.add(donhang);
                    }
                }
                myorders_history_list_adapter_api listAdapter = new myorders_history_list_adapter_api(getContext(), listChiTiet);
                listview_myorders_history.setAdapter(listAdapter);
            } else {
//                UseFallbackData();
                listview_myorders_history.setVisibility(View.GONE);
                imageIfEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    private void GetdsLichSuSanPhamDaMua(String idNguoiDung) {
        new GetOrdersTask().execute(idNguoiDung);
    }




}
