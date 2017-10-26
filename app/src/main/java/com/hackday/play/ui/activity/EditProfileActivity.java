package com.hackday.play.ui.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hackday.play.R;
import com.hackday.play.api.UserApi;
import com.hackday.play.data.GlobaData;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.data.StatusInfo;
import com.hackday.play.data.UserInfo;
import com.hackday.play.ui.base.BaseActivity;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.utils.ActivityManager;
import com.hackday.play.utils.CheckUtil;
import com.hackday.play.utils.PrefUtils;
import com.hackday.play.utils.Utils;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditProfileActivity extends BaseActivity {

    public static final String TAG = "@victor EditProfileActi";

    private static NotifyLogin sNotifyLogin;
    private Subscription mSubscription;
    private EditText editname;
    private EditText editphone, editpassword;
    private EditText editqq;
    private EditText editlocation;
    private ImageView back;
    private Button confirm;
    private RelativeLayout password_layout;
    private NeedInfo mNeedInfo = new NeedInfo();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                editlocation.setText(mNeedInfo.getLocation());
            }
        }
    };
    private boolean isEdit = false;
    private String old_phone = "";

    public static void setNotifyLogin(NotifyLogin notifyLogin) {
        sNotifyLogin = notifyLogin;
    }

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return EditProfileActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initView() {
        this.editlocation = (EditText) findViewById(R.id.edit_location);
        this.editqq = (EditText) findViewById(R.id.edit_qq);
        this.editphone = (EditText) findViewById(R.id.edit_phone);
        this.editname = (EditText) findViewById(R.id.edit_name);
        this.editpassword = (EditText) findViewById(R.id.password);
        password_layout = (RelativeLayout) findViewById(R.id.edit_password_infor);
        back = (ImageView) findViewById(R.id.edit_back);
        confirm = (Button) findViewById(R.id.confirm_edit);
        Utils.getLocation(mNeedInfo, handler, false);
        init();
    }

    private void init() {
        if (!CheckUtil.isEmpty(Utils.getToken())) {
            editqq.setText(PrefUtils.getValue(getActivity(), GlobaData.QQ));
            editphone.setText(PrefUtils.getValue(getActivity(), GlobaData.PHONE));
            editname.setText(PrefUtils.getValue(getActivity(), GlobaData.NICKNAME));
            password_layout.setVisibility(View.GONE);
            isEdit = true;
            old_phone = editphone.getText().toString();
        }
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editname.getText().toString();
                String qq = editqq.getText().toString();
                String phone = editphone.getText().toString();
                String password = editpassword.getText().toString();
                final UserInfo userInfo = new UserInfo();
                userInfo.setLove_level(0);
                userInfo.setPhone(phone);
                userInfo.setQq(qq);
                userInfo.setNickname(name);

                UserApi api = UserApi.getInstance();
                Observable<StatusInfo> observable;
                if (!isEdit) {
                    observable = api.register(name, phone,
                            password, qq);
                } else {
                    observable = api.editUser(old_phone, Utils.getToken(),
                            name, phone, qq);
                }
                mSubscription = observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<StatusInfo>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, e.getMessage());
                            }

                            @Override
                            public void onNext(StatusInfo statusInfo) {
                                if (statusInfo.getStatus() == 1) {
                                    if (!isEdit)
                                        showToast("注册成功");
                                    else
                                        showToast("修改成功");
                                    Utils.updateUserInfo(userInfo);
                                    if (!isEdit) {
                                        PrefUtils.putValue(getActivity(), "password", password);
                                        sNotifyLogin.notify(phone, password);
                                    } else {
                                        sNotifyLogin.notify(phone, PrefUtils.getValue(getActivity
                                                (), "password"));
                                    }
                                    ActivityManager.finishActivity(getActivity());
                                } else {
                                    showToast("失败");
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public interface NotifyLogin {
        void notify(String phone, String password);

    }
}
