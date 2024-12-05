package com.example.foodtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.ThongBaoModel;
import com.example.foodtrack.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class notification_list_adapter extends ArrayAdapter<ThongBaoModel> {
    public notification_list_adapter(Context context, List<ThongBaoModel> arrayNotification) {
        super(context, R.layout.fragment_thong_bao_item, arrayNotification);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ThongBaoModel noti = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_thong_bao_item, parent, false);
        }

        TextView tv_loai = (TextView) view.findViewById(R.id.tv_loai_thong_bao_item);
        TextView tv_tieuDe = (TextView) view.findViewById(R.id.tv_tieuDe_thong_bao_item);
        TextView tv_noiDung = (TextView) view.findViewById(R.id.tv_noiDung_thong_bao_item);
        TextView tv_ngay = (TextView) view.findViewById(R.id.tv_ngay_thong_bao_item);

        if (noti != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

            tv_loai.setText(noti.getLoaiThongBao());
            tv_tieuDe.setText(noti.getTieuDe());
            tv_noiDung.setText(noti.getNoiDung());
            tv_ngay.setText(dateFormat.format(noti.getNgayThongBao().getTime()));

        }

        return view;
    }

}
