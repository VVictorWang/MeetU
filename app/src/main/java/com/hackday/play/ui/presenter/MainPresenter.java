package com.hackday.play.ui.presenter;

import com.hackday.play.api.UserApi;
import com.hackday.play.data.UserInfo;
import com.hackday.play.ui.contract.MainContract;
import com.hackday.play.utils.CheckUtil;
import com.hackday.play.utils.Utils;

import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        String phone = Utils.getPhone();
        String token = Utils.getToken();
        if (!CheckUtil.isEmpty(phone) && !CheckUtil.isEmpty(token)) {
            Observable<Response<UserInfo>> observable = UserApi.getInstance().getUserInfo(phone,
                    token);
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<Response<UserInfo>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Response<UserInfo> userInfoResponse) {
                            if (userInfoResponse.code() != 200) {
                                mView.showEditView();
                            } else {
                                Utils.updateUserInfo(userInfoResponse.body());
                            }
                        }
                    });
        } else {
            mView.showEditView();
        }

    }


    @Override
    public void unscribe() {

    }
}
