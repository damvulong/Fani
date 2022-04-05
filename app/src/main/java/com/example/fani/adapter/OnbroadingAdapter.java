package com.example.fani.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fani.fragment.onbroading.Onbroading1Fragment;
import com.example.fani.fragment.onbroading.Onbroading2Fragment;
import com.example.fani.fragment.onbroading.Onbroading3Fragment;

public class OnbroadingAdapter extends FragmentStatePagerAdapter {

    public OnbroadingAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Onbroading1Fragment();
            case 1:
                return new Onbroading2Fragment();
            case 2:
                return new Onbroading3Fragment();

            default:
                return new Onbroading1Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
