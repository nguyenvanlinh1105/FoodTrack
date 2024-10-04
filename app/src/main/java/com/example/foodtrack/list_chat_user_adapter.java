package com.example.foodtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class list_chat_user_adapter extends BaseAdapter {

    Context context;
    ArrayList<String> tenNguoiGui_list_chat;
    ArrayList<Integer> ava_nguoiGui_list_chat;
    ArrayList<String> noiDung_list_chat;
    ArrayList<String> number_list_chat;
    LayoutInflater inflater;

    public list_chat_user_adapter(Context context, ArrayList<String> sender, ArrayList<Integer> avatar, ArrayList<String> content, ArrayList<String> num) {
        this.context = context;
        this.tenNguoiGui_list_chat = sender;
        this.ava_nguoiGui_list_chat = avatar;
        this.noiDung_list_chat = content;
        this.number_list_chat = num;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tenNguoiGui_list_chat.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.fragment_list_chat_user_item, viewGroup, false);

        TextView sender = (TextView) view.findViewById(R.id.tenNguoiGui_list_chat);
        ImageView avatar = (ImageView) view.findViewById(R.id.ava_nguoiGui_list_chat);
        TextView content = (TextView) view.findViewById(R.id.noiDung_list_chat);
        TextView num = (TextView) view.findViewById(R.id.number_list_chat);

        sender.setText(tenNguoiGui_list_chat.get(i));
        avatar.setImageResource(ava_nguoiGui_list_chat.get(i));
        content.setText(noiDung_list_chat.get(i));
        num.setText(number_list_chat.get(i));

        return view;
    }
}
