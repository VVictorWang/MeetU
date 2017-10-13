package com.hackday.play.ui.presenter;

import com.hackday.play.api.UserApi;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.data.NeedList;
import com.hackday.play.ui.contract.SquareContract;
import com.hackday.play.ui.fragments.SquareFragment;
import com.hackday.play.utils.CheckUtil;
import com.hackday.play.utils.RxUtil;
import com.hackday.play.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
                getInfo(0);
                break;
            case SquareFragment.STATUS_SHARED:
                getInfo(1);
                break;
            case SquareFragment.STATUS_CREATED:
                getInfo(2);
                break;
            case SquareFragment.STATUS_FINISHED:
                getInfo(3);
                break;
            default:
                break;
        }

    }


    private void getInfo(int mode) {
        if (!CheckUtil.isEmpty(Utils.getToken())) {
            String key = Utils.createAcacheKey("get_need_list", Utils.getPhone());
            Observable<NeedList> observable = UserApi.getInstance().getAllNeeds(Utils.getToken())
                    .compose(RxUtil.<NeedList>rxCacheListHelper(key));
            Observable.concat(observable, (Observable<NeedList>) RxUtil.rxCreateDiskObservable
                    (key, NeedList.class))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<NeedList>() {
                        @Override
                        public void onCompleted() {
                            mView.disableRefresh();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(NeedList needList) {
                            if (mode == 0) {
                                mView.showList(needList.getNeed());
                            } else {
                                List<NeedInfo> infos = new ArrayList<>();
                                for (NeedInfo info : needList.getNeed()) {
                                    if ((mode == 2 && info.getCreator_phone().equals(Utils
                                            .getPhone()
                                    )) || (mode == 1 && info.getHelper_phone().equals(Utils
                                            .getPhone())) || (mode == 3 && info.getStatus() == 2)) {
                                        infos.add(info);
                                    }
                                }
                                mView.showList(infos);
                            }
                        }
                    });
        }
    }


    @Override
    public void unscribe() {

    }


}
