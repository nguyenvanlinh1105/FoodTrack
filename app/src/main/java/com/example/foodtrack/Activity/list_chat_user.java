package com.example.foodtrack.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtrack.Fragment.fragment_chat_user_shop;
import com.example.foodtrack.R;
import com.example.foodtrack.Adapter.list_chat_user_adapter;
import com.example.foodtrack.SocketManager;
import com.example.foodtrack.databinding.ActivityListChatUserBinding;
import com.example.foodtrack.databinding.ActivityMainBinding;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;

public class list_chat_user extends AppCompatActivity {
    Socket mSocket;
    ActivityListChatUserBinding binding;

    ArrayList<String> tenNguoiGui_list_chat = new ArrayList<>();
    ArrayList<Integer> ava_nguoiGui_list_chat = new ArrayList<>();
    ArrayList<String> noiDung_list_chat = new ArrayList<>();
    ArrayList<String> number_list_chat = new ArrayList<>();

    ListView listview_chat_user;
    ImageView btn_back_list_chat_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_list_chat_user);
        binding = ActivityListChatUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeData();
        Mapping();

        mSocket = SocketManager.getInstance().getSocket();

        list_chat_user_adapter listAdapter = new list_chat_user_adapter(getApplicationContext(), tenNguoiGui_list_chat, ava_nguoiGui_list_chat, noiDung_list_chat, number_list_chat);
        listview_chat_user.setAdapter(listAdapter);
        ControlButton();

    }



    private void initializeData() {
        tenNguoiGui_list_chat.add("FoodTrack");
        tenNguoiGui_list_chat.add("Shipper Ngọc Duyên");
        tenNguoiGui_list_chat.add("Shipper Văn Tùng");
        tenNguoiGui_list_chat.add("Shipper Thị Phương");
        tenNguoiGui_list_chat.add("Shipper Công Quốc");
        tenNguoiGui_list_chat.add("Shipper Huỳnh Anh");
        tenNguoiGui_list_chat.add("Shipper Công Phượng");

        ava_nguoiGui_list_chat.add(R.drawable.foodtrack_ava_chat);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);
        ava_nguoiGui_list_chat.add(R.drawable.avatar_chat_sender_1);

        noiDung_list_chat.add("Cảm ơn bạn đã đặt hàng ở...");
        noiDung_list_chat.add("Cảm ơn bạn đã chờ giúp mình...");
        noiDung_list_chat.add("Cảm ơn bạn đã chờ giúp mình...");
        noiDung_list_chat.add("Cảm ơn bạn đã chờ giúp mình...");
        noiDung_list_chat.add("Cảm ơn bạn đã chờ giúp mình...");
        noiDung_list_chat.add("Cảm ơn bạn đã chờ giúp mình...");
        noiDung_list_chat.add("Cảm ơn bạn đã chờ giúp mình...");

        number_list_chat.add("0");
        number_list_chat.add("1");
        number_list_chat.add("2");
        number_list_chat.add("1");
        number_list_chat.add("2");
        number_list_chat.add("0");
        number_list_chat.add("0");
    }

    private void Mapping(){
        btn_back_list_chat_user = (ImageView)findViewById(R.id.btn_back_list_chat_user);
        listview_chat_user = (ListView) findViewById(R.id.listview_chat_user);
    }

    private void ControlButton(){
        btn_back_list_chat_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listview_chat_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReplaceFragment(new fragment_chat_user_shop());
            }
        });

    }

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}