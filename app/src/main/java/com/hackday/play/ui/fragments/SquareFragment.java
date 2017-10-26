package com.hackday.play.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;


/**
 * Created by wuhanson on 2017/6/3.
 */
public class SquareFragment extends Fragment implements SquareContract.View {
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


    public void loadData() {
        mPresenter.start();
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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
        refreshLayout.setRefreshing(true);
    }

    public void sortByDistance() {
        Observable.from(mNeedInfos).observeOn(AndroidSchedulers.mainThread()).subscribeOn
                (Schedulers.io())
                .toSortedList(new Func2<NeedInfo, NeedInfo, Integer>() {
                    @Override
                    public Integer call(NeedInfo needInfo, NeedInfo needInfo2) {
                        float userLa = PrefUtils.getFloatValue(getActivity(), GlobaData.LATITUDE);
                        float userLon = PrefUtils.getFloatValue(getActivity(), GlobaData
                                .LONGTITUDE);
                        if (Utils.GetDistance(needInfo.getLatitude(), needInfo.getLongitude(),
                                userLa, userLon) <
                                Utils.GetDistance(needInfo2.getLatitude(), needInfo2.getLongitude
                                        (), userLa, userLon)) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }).subscribe(new Observer<List<NeedInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<NeedInfo> needInfos) {
                myRecyAdapter.setLocationInforList(needInfos, false);
            }
        });

    }

    public void sortByDate() {
        Observable.from(mNeedInfos).toSortedList(new Func2<NeedInfo, NeedInfo, Integer>() {
            @Override
            public Integer call(NeedInfo needInfo, NeedInfo needInfo2) {
                if (needInfo.getCreate_time() < needInfo2.getCreate_time()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<NeedInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<NeedInfo> needInfos) {
                        myRecyAdapter.setLocationInforList(needInfos, false);
                    }
                });


    }

    public void sortGirl() {
        Observable.from(mNeedInfos).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).toSortedList(new Func2<NeedInfo, NeedInfo, Integer>() {
            @Override
            public Integer call(NeedInfo needInfo, NeedInfo needInfo2) {
                if (needInfo2.getSex().equals("女")) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }).subscribe(new Observer<List<NeedInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<NeedInfo> needInfos) {
                myRecyAdapter.setLocationInforList(needInfos, false);
            }
        });

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
        myRecyAdapter.setLocationInforList(needInfos, true);
        mNeedInfos = needInfos;
    }

    @Override
    public void disableRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public int getStatus() {
        return status;
    }
}