package com.hackday.play.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhanson on 2017/6/3.
 */

public class MyFragAdapter extends FragmentPagerAdapter {
    private List<Fragment> frags;


    public MyFragAdapter(FragmentManager fm) {
        super(fm);
        frags = new ArrayList<>();
    }

    public MyFragAdapter(FragmentManager fm, List<Fragment> frags) {
        super(fm);
        this.frags = frags;
    }

    public void addFragment(Fragment fragment) {
        frags.add(fragment);
    }

    @Override
    public Fragment getItem(int i) {
        return frags.get(i);
    }

    @Override
    public int getCount() {
        return frags.size();
    }

}