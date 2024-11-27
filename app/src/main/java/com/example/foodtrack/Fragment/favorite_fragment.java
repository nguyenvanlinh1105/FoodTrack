package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.FavoriteListAdapterAPI;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamYeuThichModel;
import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.favorite_list_adapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ArrayList<Double> favoritePrice = new ArrayList<>();

    GridView gv_favorite;
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

    }

    private void initializeData() {
        favoriteTitle.add("Burger phô mai");
        favoriteTitle.add("Capuccino");
        favoriteTitle.add("Burger trứng");
        favoriteTitle.add("Nước ép cam ");
        favoriteTitle.add("Burger phô mai gà");
        favoriteTitle.add("Sinh tố đu đủ");
        favoriteTitle.add("Burger heo quay");
        favoriteTitle.add("Burger thịt bằm");

        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.drink1);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.drink2);
        favoriteImg.add(R.drawable.double_cheese);
        favoriteImg.add(R.drawable.drink3);
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

        favoritePrice.add(50000.0);
        favoritePrice.add(50000.0);
        favoritePrice.add(50000.0);
        favoritePrice.add(50000.0);
        favoritePrice.add(50000.0);
        favoritePrice.add(50000.0);
        favoritePrice.add(50000.0);
        favoritePrice.add(50000.0);
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
        gv_favorite = (GridView) view.findViewById(R.id.gv_favorite);

        //       initializeData();
//        favorite_list_adapter listAdapter = new favorite_list_adapter(getContext(), favoriteTitle, favoriteImg, favoriteSubTitle, favoritePrice);
//        listView_favorite.setAdapter(listAdapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        getDsSanPhamYeuThich(idUser);

        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);


    }

    private void ControlButton() {
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), list_chat_user.class);
                startActivity(chat);
            }
        });
        gv_favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("title", favoriteTitle.get(position));
                bundle.putDouble("price", favoritePrice.get(position));
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


    private void getDsSanPhamYeuThich(String idNguoiDung) {
        APIService.API_SERVICE.getDsSanPhamYeuThich(idNguoiDung).enqueue(new Callback<List<SanPhamAPIModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamAPIModel>> call, Response<List<SanPhamAPIModel>> response) {
                List<SanPhamAPIModel> listSanPham = response.body();
                FavoriteListAdapterAPI listAdapter = new FavoriteListAdapterAPI(getContext(), listSanPham);
                gv_favorite.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<List<SanPhamAPIModel>> call, Throwable t) {

            }
        });
    }


}