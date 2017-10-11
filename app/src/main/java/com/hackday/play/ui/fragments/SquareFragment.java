package com.hackday.play.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackday.play.R;
import com.hackday.play.data.GlobaData;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.ui.adapters.MyRecyAdapter;
import com.hackday.play.ui.contract.SquareContract;
import com.hackday.play.ui.presenter.SquarePresenter;
import com.hackday.play.utils.PrefUtils;
import com.hackday.play.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by wuhanson on 2017/6/3.
 */
public class SquareFragment extends Fragment implements SquareContract.View{
    public static final int STATUS_ALL = 0;
    public static final int STATUS_SHARED = 1;
    public static final int STATUS_CREATED = 2;
    public static final int STATUS_FINISHED = 3;

    private static final String PARAM_STATUS = "status";
    private int status = 0;
    private SwipeRefreshLayout refreshLayout;
    private MyRecyAdapter myRecyAdapter;
    private List<NeedInfo> mNeedInfos = new ArrayList<>();
    private View rootView;

    private SquareContract.Presenter mPresenter;

    public static SquareFragment newInstance(int status) {
        Bundle args = new Bundle();
        args.putInt(PARAM_STATUS, status);
        SquareFragment fragment = new SquareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        status = getArguments().getInt(PARAM_STATUS);
        rootView = inflater.inflate(R.layout.frag_square, container, false);
        initView();
        mPresenter = new SquarePresenter(this);
        mPresenter.start();
        return rootView;
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.square_RecyclerView);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.square_SwipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
            }
        });
        myRecyAdapter = new MyRecyAdapter(getActivity());
        recyclerView.setAdapter(myRecyAdapter);
//        refreshLayout.setRefreshing(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByDistance() {
        List<NeedInfo> sorts = mNeedInfos.stream().sorted(new Comparator<NeedInfo>() {
            @Override
            public int compare(NeedInfo o1, NeedInfo o2) {
                float userLa = PrefUtils.getFloatValue(getActivity(), GlobaData.LATITUDE);
                float userLon = PrefUtils.getFloatValue(getActivity(), GlobaData.LONGTITUDE);
                if (Utils.GetDistance(o1.getLatitude(), o1.getLongitude(), userLa, userLon) <
                        Utils.GetDistance(o2.getLatitude(), o2.getLongitude(), userLa, userLon)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }).collect(Collectors.toList());
        myRecyAdapter.setLocationInforList(sorts);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByDate() {
        List<NeedInfo> sorts = mNeedInfos.stream().sorted(new Comparator<NeedInfo>() {
            @Override
            public int compare(NeedInfo o1, NeedInfo o2) {
                if (o1.getCreate_time() < o2.getCreate_time()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
        myRecyAdapter.setLocationInforList(sorts);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortGirl() {
        List<NeedInfo> sorts = mNeedInfos.stream().sorted(new Comparator<NeedInfo>() {
            @Override
            public int compare(NeedInfo o1, NeedInfo o2) {
                if (o2.getSex().equals("女")) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).collect(Collectors.toList());
        myRecyAdapter.setLocationInforList(sorts);
    }

    @Override
    public void setPresenter(SquareContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {

    }

    @Override
    public void showList(List<NeedInfo> needInfos) {
        myRecyAdapter.setLocationInforList(needInfos);
        mNeedInfos = needInfos;
    }

    @Override
    public int getStatus() {
        return status;
    }
}