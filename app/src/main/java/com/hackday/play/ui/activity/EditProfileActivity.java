package com.hackday.play.ui.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hackday.play.R;
import com.hackday.play.api.UserApi;
import com.hackday.play.data.LocationInfor;
import com.hackday.play.data.StatusInfo;
import com.hackday.play.data.UserInfo;
import com.hackday.play.ui.base.BaseActivity;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.utils.Utils;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditProfileActivity extends BaseActivity {


    private EditText editname;
    private EditText editphone, editpassword;
    private EditText editqq;
    private EditText editlocation;
    private ImageView back;
    private Button confirm;
    private LocationInfor locationInfor = new LocationInfor();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                editlocation.setText(locationInfor.getSpecific_infor());
            }
        }
    };

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
        back = (ImageView) findViewById(R.id.edit_back);
        confirm = (Button) findViewById(R.id.confirm_edit);
        Utils.getLocation(locationInfor, handler);
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
                Observable<StatusInfo> observable = UserApi.getInstance().register(name, phone,
                        password, qq);
                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<StatusInfo>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(StatusInfo statusInfo) {
                                if (statusInfo.getStatus() == 1) {
                                    showToast("注册成功");
                                    Utils.updateUserInfo(userInfo);
                                    finish();
                                } else {
                                    showToast("手机号已注册");
                                }
                            }
                        });
            }
        });
    }


}
