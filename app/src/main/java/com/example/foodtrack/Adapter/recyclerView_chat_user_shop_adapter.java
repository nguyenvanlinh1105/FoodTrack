package com.example.foodtrack.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Fragment.fragment_product_detail;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.Model.TestChat.TinNhanModel;
import com.example.foodtrack.R;
import com.example.foodtrack.SocketManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class recyclerView_chat_user_shop_adapter extends RecyclerView.Adapter<recyclerView_chat_user_shop_adapter.MyViewHolder> {
    Activity activity;
    List<TinNhanModel> list;
    Socket mSocket;
    String currentUser;

    public recyclerView_chat_user_shop_adapter(Activity activity, List<TinNhanModel> list) {
        this.activity = activity;
        this.list = list;

        this.mSocket = SocketManager.getInstance().getSocket();
        mSocket.on("server-send-current-user", onRetrieveCurrentUser);

    }

    @NonNull
    @Override
    public recyclerView_chat_user_shop_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_list_chat, parent, false);
        return new recyclerView_chat_user_shop_adapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TinNhanModel chat = list.get(position);

        if(Objects.equals(chat.getTenNguoiDung(), currentUser)){
            Toast.makeText(activity, currentUser, Toast.LENGTH_SHORT).show();
            holder.layout_left.setVisibility(View.GONE);
            holder.layout_right.setVisibility(View.VISIBLE);
            holder.chat_right.setText(chat.getNoiDungChat());
        }
        else {
            Toast.makeText(activity, "chat.getTenNguoiDung: " + chat.getTenNguoiDung(), Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, "currentUser: " + currentUser, Toast.LENGTH_SHORT).show();
            holder.layout_right.setVisibility(View.GONE);
            holder.layout_left.setVisibility(View.VISIBLE);
            holder.chat_left.setText(chat.getNoiDungChat());
        }
    }

    private final Emitter.Listener onRetrieveCurrentUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    try {
                        currentUser = obj.getString("username");
                        notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_left, layout_right;
        TextView chat_left, chat_right;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_left = (LinearLayout) itemView.findViewById(R.id.layout_left_list_chat);
            layout_right = (LinearLayout) itemView.findViewById(R.id.layout_right_list_chat);
            chat_left = (TextView) itemView.findViewById(R.id.tv_left_list_chat);
            chat_right = (TextView) itemView.findViewById(R.id.tv_right_list_chat);

        }
    }
}
