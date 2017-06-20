package com.hackday.play.View.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hackday.play.Adapters.MyFragAdapter;
import com.hackday.play.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhanson on 2017/6/3.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private ViewPager viewPager;
    private MyFragAdapter myFragAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private RelativeLayout tab1, tab2, tab3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myumbrellas, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.umbrella_ViewPager);
        tab1 = (RelativeLayout) view.findViewById(R.id.info_tab1);
        tab2 = (RelativeLayout) view.findViewById(R.id.info_tab2);
        tab3 = (RelativeLayout) view.findViewById(R.id.info_tab3);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        SquareFragment fragment = new SquareFragment(), fragment1 = new SquareFragment(), fragment2 = new SquareFragment();
        fragmentList.add(fragment);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        myFragAdapter = new MyFragAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                changeColor(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onClick(View v) {
        changeColor(v.getId());
    }

    private void changeColor(int i) {
        switch (i) {
            case 0:
            case R.id.info_tab1: {
                tab1.setBackgroundColor(getResources().getColor(R.color.TabSelected));
                tab2.setBackgroundColor(getResources().getColor(R.color.white));
                tab3.setBackgroundColor(getResources().getColor(R.color.white));
                viewPager.setCurrentItem(0);
                break;
            }
            case 1:
            case R.id.info_tab2: {
                tab2.setBackgroundColor(getResources().getColor(R.color.TabSelected));
                tab1.setBackgroundColor(getResources().getColor(R.color.white));
                tab3.setBackgroundColor(getResources().getColor(R.color.white));
                viewPager.setCurrentItem(1);
                break;
            }
            case 2:
            case R.id.info_tab3: {
                tab3.setBackgroundColor(getResources().getColor(R.color.TabSelected));
                tab2.setBackgroundColor(getResources().getColor(R.color.white));
                tab1.setBackgroundColor(getResources().getColor(R.color.white));
                viewPager.setCurrentItem(2);
                break;
            }
        }
    }
}