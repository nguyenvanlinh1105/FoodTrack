package com.example.foodtrack;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtrack.databinding.ActivityMainBinding;
import com.example.foodtrack.databinding.AdActivityMainBinding;

public class ad_main_activity extends AppCompatActivity {

    AdActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = AdActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ReplaceFragment(new ad_fragment_list_chat());

        binding.bottomNavigationViewAd.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.message) {
                ReplaceFragment(new ad_fragment_list_chat());
            } else if (item.getItemId() == R.id.menu) {
                ReplaceFragment(new AD_Detail_Customer());
            } else if (item.getItemId() == R.id.customers) {
               ReplaceFragment(new ad_Customer());
            } else if (item.getItemId() == R.id.staffs) {
             //   ReplaceFragment(new favorite_fragment());
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void ReplaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout_ad, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}