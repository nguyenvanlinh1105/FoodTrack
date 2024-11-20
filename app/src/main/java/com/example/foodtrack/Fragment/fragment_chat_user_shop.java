package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtrack.Adapter.recyclerView_ban_chay_adapter;
import com.example.foodtrack.Adapter.recyclerView_chat_user_shop_adapter;
import com.example.foodtrack.Model.TestChat.TinNhanModel;
import com.example.foodtrack.R;
import com.example.foodtrack.SocketManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_chat_user_shop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_chat_user_shop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView btn_back, btn_send, btn_add;
    //    private ArrayList listChat;
    private List<TinNhanModel> listChat;
    //    private ArrayAdapter adapterChat;
    private recyclerView_chat_user_shop_adapter adapterChat;
    private Socket mSocket;
    private TextView edt_chat;
    private RecyclerView rv_chat;


    public fragment_chat_user_shop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_chat_user_shop.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_chat_user_shop newInstance(String param1, String param2) {
        fragment_chat_user_shop fragment = new fragment_chat_user_shop();
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
        View view = inflater.inflate(R.layout.fragment_chat_user_shop, container, false);
        Mapping(view);


        listChat = new ArrayList();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_chat.setLayoutManager(layoutManager);
        SharedPreferences sharedUsername = requireContext().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        adapterChat = new recyclerView_chat_user_shop_adapter(requireActivity(), listChat, sharedUsername);
        rv_chat.setAdapter(adapterChat);

        ControlSocket();

        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        btn_back = (ImageView) view.findViewById(R.id.btn_back_chat_user);
        btn_send = (ImageView) view.findViewById(R.id.btn_send_chat_user_shop);
        btn_add = (ImageView) view.findViewById(R.id.btn_add_user_chat_user_shop);
        edt_chat = (TextView) view.findViewById(R.id.edt_content_chat_user_shop);
        rv_chat = (RecyclerView) view.findViewById(R.id.lv_chat_user_shop);
    }

    private void ControlSocket() {
        mSocket = SocketManager.getInstance().getSocket();
        mSocket.on("server-send-chat", onRetrieveListChat);
        mSocket.on("server-send-reply", onRetrieveResult);
    }

    private Emitter.Listener onRetrieveListChat = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            requireActivity().runOnUiThread(() -> {
                listChat.clear();
                try {
                    JSONObject obj = (JSONObject) args[0];
                    JSONArray arrayChat = obj.getJSONArray("chat");
                    Log.d("arrayChat", arrayChat.toString());
                    for (int i = 0; i < arrayChat.length(); i++) {
                        JSONObject objChat = arrayChat.getJSONObject(i);
                        String idChat = String.valueOf(objChat.getInt("idChat"));
                        String tenNguoiDung = objChat.getString("username");
                        String noiDung = objChat.getString("noiDungChat");
                        listChat.add(new TinNhanModel(idChat, tenNguoiDung, noiDung));
                    }
                    adapterChat.notifyDataSetChanged();
                    rv_chat.scrollToPosition(listChat.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    };


    private Emitter.Listener onRetrieveResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    try {
                        boolean exist = obj.getBoolean("ketqua");
                        if (exist) {
                            Toast.makeText(getContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    };

    private void ControlButton() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edt_chat.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập nội dung trước khi gửi", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences shared = getActivity().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
                    TinNhanModel tinNhan = new TinNhanModel(shared.getString("hoTenNguoiDung", ""), message, shared.getString("gioiTinh", ""));
                    Gson gson = new Gson();
                    String jsonTinNhan = gson.toJson(tinNhan);
                    mSocket.emit("client-send-chat", tinNhan.toString());
                    Log.d("tinNhanModel", tinNhan.toString());
                    edt_chat.setText("");
                }
            }
        });
    }
}