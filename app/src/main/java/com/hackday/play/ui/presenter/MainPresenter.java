package com.hackday.play.ui.presenter;

import android.util.Log;

import com.hackday.play.api.UserApi;
import com.hackday.play.data.GlobaData;
import com.hackday.play.data.LoginResponse;
import com.hackday.play.data.UserInfo;
import com.hackday.play.ui.activity.EditProfileActivity;
import com.hackday.play.ui.contract.MainContract;
import com.hackday.play.utils.AppUtils;
import com.hackday.play.utils.CheckUtil;
import com.hackday.play.utils.PrefUtils;
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

public class MainPresenter implements MainContract.Presenter, EditProfileActivity.NotifyLogin {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
        EditProfileActivity.setNotifyLogin(this);
    }

    @Override
    public void start() {
        String phone = Utils.getPhone();
        String token = Utils.getToken();
        if (!CheckUtil.isEmpty(phone) && !CheckUtil.isEmpty(token)) {
            mView.setUserName(PrefUtils.getValue(AppUtils.getAppContext(), GlobaData.NICKNAME));
            mView.setUserLove_Level(PrefUtils.getIntValue(AppUtils.getAppContext(), GlobaData
                    .LOVE_LEVEL));
            mView.setUserPhone(PrefUtils.getValue(AppUtils.getAppContext(), GlobaData.PHONE));
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
                                mView.setUserPhone(userInfoResponse.body().getPhone());
                                mView.setUserLove_Level(userInfoResponse.body().getLove_level());
                                mView.setUserName(userInfoResponse.body().getNickname());
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

    @Override
    public void notify(String phone, String password) {
        Observable<LoginResponse> observable1 = UserApi.getInstance().login(phone,
                password);
        observable1.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showMyToast("登录成功");
//                            ActivityManager.finishActivity(getActivity());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        if (loginResponse.getStatus() == 1) {

                            PrefUtils.putValue(AppUtils.getAppContext
                                            (), GlobaData.TOKEN,
                                    loginResponse.getToken());
                        }
                        Log.d("@vic", loginResponse.getStatus() + " ");
                    }
                });
    }
}
