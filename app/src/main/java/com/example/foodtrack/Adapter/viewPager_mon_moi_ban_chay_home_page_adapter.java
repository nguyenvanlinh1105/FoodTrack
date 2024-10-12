package com.example.foodtrack.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodtrack.Fragment.fragment_viewpager_ban_chay_homepage;
import com.example.foodtrack.Fragment.fragment_viewpager_mon_moi_homepage;

public class viewPager_mon_moi_ban_chay_home_page_adapter extends FragmentStateAdapter  {

    public viewPager_mon_moi_ban_chay_home_page_adapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        System.out.println("Fragment created for position: " + position);
        switch (position) {
            case 0:
                return new fragment_viewpager_ban_chay_homepage();
            case 1:
                return new fragment_viewpager_mon_moi_homepage();
            default:
                return new fragment_viewpager_ban_chay_homepage();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
