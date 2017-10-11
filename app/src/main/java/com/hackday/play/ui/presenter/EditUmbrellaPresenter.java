package com.hackday.play.ui.presenter;

import com.hackday.play.api.UserApi;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.ui.contract.EditUmbrellaContract;
import com.hackday.play.utils.Utils;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 10/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class EditUmbrellaPresenter implements EditUmbrellaContract.Presenter {
    private EditUmbrellaContract.View mView;

    public EditUmbrellaPresenter(EditUmbrellaContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mView.getMode() == 0) {
            mView.showEditView();
        } else {

            mView.showBrowseView();
            Observable<NeedInfo> observable = UserApi.getInstance().getNeedInfo(mView
                    .getNeedID(), Utils.getToken());
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<NeedInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(NeedInfo needInfo) {
                            if (Utils.getPhone().equals(mView.getCreator())) {
                                switch (needInfo.getStatus()) {
                                    case 0:
                                        mView.showWaitingView();
                                        break;
                                    case 1:
                                        mView.showRunningView();
                                        break;
                                    case 2:
                                        mView.showFinishedView();
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                mView.showOtherView(needInfo);
                            }

                        }
                    });
        }
    }


    @Override
    public void unscribe() {

    }

    @Override
    public void help() {
        Observable<NeedInfo> observable = UserApi.getInstance().helpNeed(mView.getNeedID(), Utils
                .getPhone(), Utils.getToken());
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NeedInfo>() {
                    @Override
                    public void onCompleted() {
                        mView.showMyToast("成功");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NeedInfo needInfo) {

                    }
                });
    }
}
