package com.hackday.play.ui.presenter;

import com.hackday.play.api.UserApi;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.ui.contract.EditUmbrellaContract;
import com.hackday.play.utils.RxUtil;
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
            mView.showProgressDialog();
            String key = Utils.createAcacheKey("get_need_info", mView.getNeedID());
            Observable<NeedInfo> observable = UserApi.getInstance().getNeedInfo(mView
                    .getNeedID(), Utils.getToken()).compose(RxUtil.<NeedInfo>rxCacheBeanHelper
                    (key));
            Observable.concat(observable, (Observable<NeedInfo>) RxUtil.rxCreateDiskObservable(key,
                    NeedInfo.class)).observeOn(AndroidSchedulers.mainThread())
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
                            switch (needInfo.getSex()) {
                                case "男":
                                    mView.setBackGround(0);
                                    break;
                                case "女":
                                    mView.setBackGround(1);
                                    break;
                                case "秘密":
                                    mView.setBackGround(2);
                                    break;
                                default:
                                    break;
                            }
                            if (Utils.getPhone().equals(mView.getCreator())) {
                                switch (needInfo.getStatus()) {
                                    case 0:
                                        mView.showWaitingView(needInfo);
                                        break;
                                    case 1:
                                        mView.showRunningView(needInfo);
                                        break;
                                    case 2:
                                        mView.showFinishedView(needInfo, true);
                                        break;
                                    default:
                                        break;
                                }
                                mView.dismissProgressDialog();
                            } else {
                                if (Utils.getPhone().equals(needInfo
                                        .getHelper_phone())) {
                                    if (needInfo.getStatus() == 1) {
                                        mView.showRunningView(needInfo);
                                    } else if (needInfo.getStatus() == 2) {
                                        mView.showFinishedView(needInfo, true);
                                    }
                                } else if (needInfo.getStatus() == 0) {
                                    if (Utils.isOutdated(needInfo.getContinue_time(), needInfo
                                            .getCreate_time())) {
                                        mView.showHelpView(needInfo);
                                    } else {
                                        mView.showFinishedView(needInfo, false);
                                    }
                                } else if (needInfo.getStatus() == 2) {
                                    mView.showFinishedView(needInfo, false);
                                }
                                mView.dismissProgressDialog();
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
                        mView.showRunningView(needInfo);
                    }
                });
    }
}
