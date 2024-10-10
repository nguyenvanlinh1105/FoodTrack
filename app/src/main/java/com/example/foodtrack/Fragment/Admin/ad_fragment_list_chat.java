package com.example.foodtrack.Fragment.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.list_chat_user_adapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ad_fragment_list_chat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ad_fragment_list_chat extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> tenNguoiGui_list_chat = new ArrayList<>();
    private ArrayList<Integer> ava_nguoiGui_list_chat = new ArrayList<>();
    private ArrayList<String> noiDung_list_chat = new ArrayList<>();
    private ArrayList<String> number_list_chat = new ArrayList<>();

    private ListView listview_chat_ad;

    public ad_fragment_list_chat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listchat_ad.
     */
    // TODO: Rename and change types and number of parameters
    public static ad_fragment_list_chat newInstance(String param1, String param2) {
        ad_fragment_list_chat fragment = new ad_fragment_list_chat();
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
        tenNguoiGui_list_chat.add("Nguyễn Văn Linh");
        tenNguoiGui_list_chat.add("Nguyễn Ngọc Tú Anh");
        tenNguoiGui_list_chat.add("Phạm Lê Văn Huy");
        tenNguoiGui_list_chat.add("Nguyễn Thị Hiếu");
        tenNguoiGui_list_chat.add("Trần Văn Bình");
        tenNguoiGui_list_chat.add("Lê Ngọc Hải");


        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_user);
        ava_nguoiGui_list_chat.add(R.drawable.avatar1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_2);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);

        noiDung_list_chat.add("Cảm ơn app nhiều nha <3");
        noiDung_list_chat.add("Giao nhầm hàng rồi...");
        noiDung_list_chat.add("Làm ăn chán quá vậy...");
        noiDung_list_chat.add("Em mới chuyển tiền đấy...");
        noiDung_list_chat.add(" Bạn kiểm tra lại tin nhăn với");
        noiDung_list_chat.add("Đồ ăn ở đây ngon lắm á...");

        number_list_chat.add("3");
        number_list_chat.add("1");
        number_list_chat.add("0");
        number_list_chat.add("4");
        number_list_chat.add("2");
        number_list_chat.add("9");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad_fragment_listchat, container, false);

        Mapping(view);
        list_chat_user_adapter listAdapter = new list_chat_user_adapter(getContext(), tenNguoiGui_list_chat, ava_nguoiGui_list_chat, noiDung_list_chat, number_list_chat);
        listview_chat_ad.setAdapter(listAdapter);
        ControlButton();
        return view;
    }

    private void Mapping(View view){
        listview_chat_ad = (ListView)view.findViewById(R.id.listview_chat_ad);

    }

    private void ControlButton(){

    }
}