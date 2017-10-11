package com.hackday.play.ui.presenter;

import com.hackday.play.api.UserApi;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.data.NeedList;
import com.hackday.play.ui.contract.SquareContract;
import com.hackday.play.ui.fragments.SquareFragment;
import com.hackday.play.utils.CheckUtil;
import com.hackday.play.utils.Utils;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 10/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class SquarePresenter implements SquareContract.Presenter {
    private SquareContract.View mView;

    public SquarePresenter(SquareContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        int status = mView.getStatus();
        switch (status) {
            case SquareFragment.STATUS_ALL:
                getInfo(noFilter);
                break;
            case SquareFragment.STATUS_SHARED:
                getInfo(shared_Filter);
                break;
            default:
                break;
        }

    }

    private void getInfo(Func1<NeedList, Boolean> myFilter) {
        if (!CheckUtil.isEmpty(Utils.getToken())) {
            Observable<NeedList> observable = UserApi.getInstance().getAllNeeds(Utils.getToken());
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .filter(myFilter)
                    .subscribe(new Observer<NeedList>() {
                        @Override
                        public void onCompleted() {
//                        refreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(NeedList needList) {
                            mView.showList(needList.getNeed());
                        }
                    });
        }

    }

    private Func1<NeedList, Boolean> noFilter = new Func1<NeedList, Boolean>() {
        @Override
        public Boolean call(NeedList needlist) {
            return true;
        }
    };

    private Func1<NeedList, Boolean> shared_Filter = new Func1<NeedList, Boolean>() {
        @Override
        public Boolean call(NeedList needlist) {
            for (NeedInfo needinfo : needlist.getNeed()) {
                if (needinfo.getHelper_phone().equals(Utils.getPhone())) {
                    return true;
                }
            }
            return false;
        }
    };


    @Override
    public void unscribe() {

    }


}
