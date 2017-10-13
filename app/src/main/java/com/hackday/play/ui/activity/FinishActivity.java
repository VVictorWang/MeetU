package com.hackday.play.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hackday.play.R;
import com.hackday.play.api.UserApi;
import com.hackday.play.data.GlobaData;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.data.UserInfo;
import com.hackday.play.ui.base.BaseActivity;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.utils.ActivityManager;
import com.hackday.play.utils.MyActivityManager;
import com.hackday.play.utils.PrefUtils;
import com.hackday.play.utils.Utils;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FinishActivity extends BaseActivity {
    private View view;
    private Button getsafely;
    private TextView accident;
    private TextView dia_text;

    private Button positive;

    private String needId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            needId = getIntent().getStringExtra("id");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return FinishActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finish;
    }

    @Override
    protected void initView() {
        getsafely = (Button) findViewById(R.id.safe_get);
        view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        accident = (TextView) findViewById(R.id.happen_something);
        dia_text = (TextView) view.findViewById(R.id.dialog_text);
        positive = (Button) view.findViewById(R.id.dialog_positive);
        dia_text.setText("哇～,你成功和ta分享了一次雨伞\n     你的爱心值增加了1");


    }

    @Override
    protected void initEvent() {
        final AlertDialog dialog = new AlertDialog.Builder(FinishActivity.this).create();
        getsafely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setView(view);
                dialog.show();
            }
        });
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Observable<NeedInfo> observable = UserApi.getInstance().finishedNeed(needId,
                        Utils.getToken());
                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<NeedInfo>() {
                            @Override
                            public void onCompleted() {
                                Observable<UserInfo> observable1 = UserApi.getInstance()
                                        .changeLoveLevel(Utils.getPhone(), Utils.getToken());
                                observable1.observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new Observer<UserInfo>() {
                                            @Override
                                            public void onCompleted() {
                                                dialog.dismiss();
                                                MyActivityManager.getInstance().popAllActivity();
                                                ActivityManager.startActivity(FinishActivity
                                                        .this, MainActivity
                                                        .class);
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(UserInfo userInfo) {
                                                PrefUtils.putIntValue(getActivity(), GlobaData
                                                        .LOVE_LEVEL, userInfo.getLove_level());
                                            }
                                        });
//                                int level = PrefUtils.getIntValue(FinishActivity.this, GlobaData
//                                        .LOVE_LEVEL);
//                                level++;
//                                PrefUtils.putIntValue(FinishActivity.this, GlobaData.LOVE_LEVEL,
//                                        level);

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(NeedInfo needInfo) {

                            }
                        });

            }
        });

    }


}
