package com.example.newslab.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;
    private String[] titles = { "每日", "国内", "国际", "互联网", "军事", "社会", "科技", "娱乐", "体育", "电竞", "旅游", "健康" };

    public NewsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
