package com.example.foodtrack.Fragment.Admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.foodtrack.R;

public class AD_Detail_Customer extends Fragment {

    private Spinner genderSpinner;

    public AD_Detail_Customer() {
        // Required empty public constructor
    }

    public static AD_Detail_Customer newInstance() {
        return new AD_Detail_Customer();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ad__detail__customer, container, false);
        Mapping(view);

        // Initialize gender spinner
        String[] genders = new String[]{"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        return view;
    }

    private void Mapping(View view) {
        genderSpinner = view.findViewById(R.id.idGioiTinh);
    }
}
