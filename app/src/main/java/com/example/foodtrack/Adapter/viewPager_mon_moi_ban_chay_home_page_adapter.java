package com.example.foodtrack.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodtrack.Fragment.fragment_viewpager_ban_chay_homepage;
import com.example.foodtrack.Fragment.fragment_viewpager_mon_moi_homepage;

public class view_pager_mon_moi_ban_chay_home_page_adapter extends FragmentPagerAdapter {

    public view_pager_mon_moi_ban_chay_home_page_adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
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
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Bán chạy";
            case 1:
                return "Món mới";
            default:
                return "Bán chạy";
        }
    }
}
