package com.nutcunofficial.nutcapps.Home.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.ListFragment;

import com.nutcunofficial.nutcapps.Base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class viewPager extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public viewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.fragments = new ArrayList<>();
    }

    public void add(BaseFragment fragment){
        fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
