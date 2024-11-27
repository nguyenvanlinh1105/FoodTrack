package com.example.foodtrack.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtrack.Fragment.Home_Page;
import com.example.foodtrack.Fragment.fragment_product_detail;
import com.example.foodtrack.R;
import com.example.foodtrack.Fragment.checkout;
import com.example.foodtrack.databinding.ActivityMainBinding;
import com.example.foodtrack.Fragment.favorite_fragment;
import com.example.foodtrack.Fragment.food_fragment;
import com.example.foodtrack.Fragment.fragment_profile;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String fragmentToLoad = intent.getStringExtra("fragmentToLoad");
        String ghiChu = intent.getStringExtra("ghiChu");

        if (fragmentToLoad != null) {
            if (fragmentToLoad.equals("cartFragment")) {
                checkout checkoutFragment = new checkout();

                Bundle bundle = new Bundle();
                bundle.putString("ghiChu", ghiChu);

                checkoutFragment.setArguments(bundle);

                ReplaceFragment(checkoutFragment);
            }
        } else {
            // Nếu không có fragmentToLoad, thay thế với Home_Page
            ReplaceFragment(new Home_Page());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                ReplaceFragment(new Home_Page());
            } else if (item.getItemId() == R.id.explore) {
                ReplaceFragment(new food_fragment());
            } else if (item.getItemId() == R.id.cart) {
                Intent cart = new Intent(MainActivity.this, cart.class);
                startActivity(cart);
            } else if (item.getItemId() == R.id.favorite) {
                ReplaceFragment(new favorite_fragment());

            } else if (item.getItemId() == R.id.account) {
                ReplaceFragment(new fragment_profile());
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    // Nếu có fragment trong back stack, pop fragment
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);


    }

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment instanceof fragment_product_detail) {
            ft.setCustomAnimations(
                    R.anim.anim_slide_up_product_detail,
                    R.anim.anim_fade_out_product_detail,
                    R.anim.anim_fade_in_product_detail,
                    R.anim.anim_slide_down_product_detail
            );
        }
        ft.add(R.id.frameLayout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}