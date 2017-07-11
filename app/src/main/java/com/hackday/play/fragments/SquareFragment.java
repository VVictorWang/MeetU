package com.hackday.play.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackday.play.adapters.MyRecyAdapter;
import com.hackday.play.MyApplication;
import com.hackday.play.R;
import com.hackday.play.data.LocationInfor;
import com.hackday.play.utils.Utils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuhanson on 2017/6/3.
 */
public class SquareFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MyRecyAdapter myRecyAdapter;
    private List<LocationInfor> locationInforList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_square, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.square_RecyclerView);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.square_SwipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getContent();

            }
        });

        if (DataSupport.isExist(LocationInfor.class)) {
            locationInforList = DataSupport.findAll(LocationInfor.class);
        }

        myRecyAdapter = new MyRecyAdapter(locationInforList, getActivity());
        recyclerView.setAdapter(myRecyAdapter);
        refreshLayout.setRefreshing(true);
        getArguments();
        getContent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DataSupport.isExist(LocationInfor.class)) {
            locationInforList = DataSupport.findAll(LocationInfor.class);
        }
    }


    public void sortByDistance() {
        Utils.sortByDistance(locationInforList, myRecyAdapter, MyApplication.getLocationInfor());
    }

    public void sortByDate() {
        Utils.sortByDate(locationInforList, myRecyAdapter);
    }

    public void sortGirl() {
        Utils.sortGril(locationInforList, myRecyAdapter);
    }

    private void getContent() {
        if (DataSupport.isExist(LocationInfor.class)) {

            locationInforList = DataSupport.findAll(LocationInfor.class);
        }
        for (int i = 1; i < 5; i++) {
            LocationInfor locationInfor = new LocationInfor();
            locationInfor.setTime(Utils.formatDate(new Date(System.currentTimeMillis()
            )));
            locationInfor.setBuilding("启明学院");
            locationInfor.setLongtitude(2.343);
            locationInfor.setDetail("^-^求共伞");
            locationInfor.setLatitude(21.24);
            locationInfor.setSex(0);
            locationInfor.setStatus(LocationInfor.COMPLETED);
            locationInfor.save();
            locationInforList.add(locationInfor);

        }
        LocationInfor locationInfor = new LocationInfor();
        locationInfor.setLongtitude(42.21234);
        locationInfor.setLatitude(2.123);
        locationInfor.setBuilding("sw學院");
        locationInfor.setDetail("^-^求共伞");
        locationInfor.setTime(Utils.formatDate(new Date(System.currentTimeMillis() - 200)));
        locationInfor.setSex(1);
        locationInfor.setStatus(LocationInfor.IS_SHARING);
        locationInforList.add(locationInfor);
        LocationInfor locationInfor1 = new LocationInfor();
        locationInfor1.setLongtitude(22.21234);
        locationInfor1.setLatitude(24.123);
        locationInfor1.setBuilding("接口學院");
        locationInfor1.setDetail("^-^求共伞");
        locationInfor1.setTime(Utils.formatDate(new Date(System.currentTimeMillis() - 1000)));
        locationInfor1.setSex(-1);
        locationInfor1.setStatus(LocationInfor.IS_SHARING);
        locationInfor1.save();
        locationInforList.add(locationInfor1);
        myRecyAdapter.setLocationInforList(locationInforList);
        myRecyAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }
}