package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Adapter.myorders_donHuy_list_adapter;
import com.example.foodtrack.Adapter.myorders_donHuy_list_adapter_api;
import com.example.foodtrack.Adapter.notification_list_adapter;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.Model.ThongBaoModel;
import com.example.foodtrack.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_thong_bao_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_thong_bao_user extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lv_thong_bao_user;
    List<ThongBaoModel> listThongBao = new ArrayList<>();

    String idNguoiDung;

    private notification_list_adapter listAdapter;

    public fragment_thong_bao_user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thong_bao_user.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_thong_bao_user newInstance(String param1, String param2) {
        fragment_thong_bao_user fragment = new fragment_thong_bao_user();
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
        SharedPreferences sharedPreferencesUser = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
        idNguoiDung = sharedPreferencesUser.getString("idUser", "-1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_bao_user, container, false);

        lv_thong_bao_user = (ListView) view.findViewById(R.id.lv_thong_bao_user);
        GetNoti(idNguoiDung);

        return view;
    }

    private class GetNotiTask extends AsyncTask<String, Void, List<ThongBaoModel>> {
        @Override
        protected List<ThongBaoModel> doInBackground(String... params) {
            // Gọi API ở đây
            try {
                Response<List<ThongBaoModel>> response = APIService.API_SERVICE.GetListNoti(params[0]).execute();
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
        protected void onPostExecute(List<ThongBaoModel> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                String json = gson.toJson(result);
//                Log.d("responseBody", "onPostExecute: " + json);
                listThongBao = result;
                listAdapter = new notification_list_adapter(getContext(), result);
                lv_thong_bao_user.setAdapter(listAdapter);
            } else {
                lv_thong_bao_user.setVisibility(View.GONE);
//                imageIfEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    private void GetNoti(String idNguoiDung) {
        new GetNotiTask().execute(idNguoiDung);
    }
}