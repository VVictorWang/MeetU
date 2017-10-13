package com.hackday.play.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.hackday.play.R;
import com.hackday.play.api.UserApi;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.ui.base.BaseActivity;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.ui.contract.EditUmbrellaContract;
import com.hackday.play.ui.presenter.EditUmbrellaPresenter;
import com.hackday.play.utils.ActivityManager;
import com.hackday.play.utils.Utils;
import com.hackday.play.view.PickerView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuhanson on 2017/6/3.
 */

public class EditUmbrellaActivity extends BaseActivity implements EditUmbrellaContract.View {
    private EditText editText;
    private TextView textView, time, where;
    private Button button, cancelButton, meetButton;
    private RelativeLayout relativeLayout;
    private ImageView addboy, addgirl, addsecret, clock, titleImg, back;
    private LinearLayout backgroung, helperLayout, buttonLayout;
    private ProgressDialog mProgressDialog;

    private EditUmbrellaContract.Presenter mPresenter;

    private String sex = "男";
    private int mode = 0;

    private NeedInfo loca = new NeedInfo();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                where.setText(loca.getLocation());
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new EditUmbrellaPresenter(this);
        if (getIntent() != null) {
            mode = getIntent().getIntExtra("Mode", 0);
        }
        super.onCreate(savedInstanceState);
    }


    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
    }

    @Override
    protected Activity getActivity() {
        return EditUmbrellaActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add;
    }

    @Override
    protected void initView() {
        editText = (EditText) findViewById(R.id.activity_add_EditText);
        textView = (TextView) findViewById(R.id.activity_add_TextView);
        button = (Button) findViewById(R.id.activity_add_Button);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_add_RelativeLayout);
        clock = (ImageView) findViewById(R.id.activity_add_time_ImageView);
        time = (TextView) findViewById(R.id.activity_add_time);
        addboy = (ImageView) findViewById(R.id.center_boy_img);
        addgirl = (ImageView) findViewById(R.id.activity_add_addgirl);
        where = (TextView) findViewById(R.id.where_are_you);
        addsecret = (ImageView) findViewById(R.id.activity_add_addsecret);
        titleImg = (ImageView) findViewById(R.id.activity_add_Title_ImageView);
        backgroung = (LinearLayout) findViewById(R.id.activity_add_background);
        back = (ImageView) findViewById(R.id.activity_add_Title_BackButton);
        helperLayout = (LinearLayout) findViewById(R.id.activity_add_helperlayout);
        buttonLayout = (LinearLayout) findViewById(R.id.activity_add_ButtonLayout);
        cancelButton = (Button) findViewById(R.id.activity_add_Button_Cancel);
        meetButton = (Button) findViewById(R.id.activity_add_Button_Meet);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("正在加载中...");
        Utils.getLocation(loca, handler, false);
//        getLocation();
    }

    @Override
    protected void initEvent() {
        View.OnClickListener timeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> data = new ArrayList<String>();
                data.add("5分钟");
                data.add("10分钟");
                data.add("15分钟");
                data.add("20分钟");
                data.add("30分钟");
                data.add("1小时");
                data.add("2小时");
                View view = getLayoutInflater().inflate(R.layout.picker_view, null);
                PickerView pickerView = (PickerView) view.findViewById(R.id.time_picker_view);
                pickerView.setData(data);

                final AlertDialog builder = new AlertDialog.Builder(EditUmbrellaActivity.this)
                        .create();
                builder.setView(view);
                builder.show();
                pickerView.setOnSelectListener(new PickerView.onSelectListener() {
                    @Override
                    public void onSelect(String text) {
                        time.setText(text);
                        builder.dismiss();
                    }
                });
            }
        };
        time.setOnClickListener(timeListener);
        clock.setOnClickListener(timeListener);

    }


    @Override
    public void onBackPressed() {
        if (mode == 0) showCacelDialog();
        else super.onBackPressed();
    }

    private void showCacelDialog() {
        View view = getLayoutInflater().inflate(R.layout.alert_dialog2, null);
        Button positive = (Button) view.findViewById(R.id.alert_dialog_positive), negative =
                (Button) view.findViewById(R.id.alert_dialog_negative);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).setCancelable
                (true).create();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDeleteDialog() {
        View view = getLayoutInflater().inflate(R.layout.alert_dialog2, null);
        Button positive = (Button) view.findViewById(R.id.alert_dialog_positive), negative =
                (Button) view.findViewById(R.id.alert_dialog_negative);
        TextView textView = (TextView) view.findViewById(R.id.alert_dialog_text);
        textView.setText("真的要删除吗？");
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).setCancelable
                (true).create();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void setPresenter(EditUmbrellaContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public int getMode() {
        return mode;
    }

    @Override
    public String getCreator() {
        return getIntent().getStringExtra("phone");
    }

    @Override
    public String getNeedID() {
        return getIntent().getStringExtra("id");
    }


    @Override
    public void showHelpView(NeedInfo needInfo) {
        showBrowseView();
        editText.setVisibility(View.INVISIBLE);
        buttonLayout.setVisibility(View.GONE);
        switch (needInfo.getSex()) {
            case "男":
                button.setText("帮助他");
                break;
            case "女":
                button.setText("帮助她");
                break;
            case "秘密":
                button.setText("帮助Ta");
                break;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.help();
            }
        });
        textView.setText(needInfo.getDesc());

    }

    @Override
    public void showFinishedView(NeedInfo needInfo) {
        showBrowseView();
        editText.setVisibility(View.GONE);
        button.setText("已完成");
        button.setVisibility(View.VISIBLE);
        button.setEnabled(false);
        buttonLayout.setVisibility(View.GONE);
        textView.setText(needInfo.getDesc());

    }

    @Override
    public void showRunningView(NeedInfo needInfo) {
        showBrowseView();
        editText.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.INVISIBLE);
        button.setVisibility(View.GONE);
        meetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FinishActivity.class);
                intent.putExtra("id", getNeedID());
                ActivityManager.startActivity(getActivity(), intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
        textView.setText(needInfo.getDesc());
    }

    @Override
    public void showWaitingView(NeedInfo needInfo) {
        showBrowseView();
        button.setVisibility(View.GONE);
        meetButton.setText("修改");
        meetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditView();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
        textView.setText(needInfo.getDesc());
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void setBackGround(int sex) {
        switch (sex) {
            case 0:
                backgroung.setBackground(getResources().getDrawable(R.drawable
                        .add_back));
                titleImg.setImageResource(R.drawable.add_banner_boy);
                break;
            case 1:
                backgroung.setBackground(getResources().getDrawable(R.drawable
                        .add_back_girl));
                titleImg.setImageResource(R.drawable.add_banner_girl);
                break;
            case 2:
                backgroung.setBackground(getResources().getDrawable(R.drawable
                        .add_back_secret));
                titleImg.setImageResource(R.drawable.add_banner_secret);
                break;
            default:
                break;
        }
    }

    @Override
    public void showEditView() {
        textView.setVisibility(View.GONE);
        helperLayout.setVisibility(View.GONE);
        button.setText("点击求帮助OvO");
        buttonLayout.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        time.setEnabled(true);
        clock.setClickable(true);
        editText.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCacelDialog();
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.center_boy_img: {
                        addboy.setImageResource(R.drawable.addboyc);
                        addgirl.setImageResource(R.drawable.addgirl);
                        addsecret.setImageResource(R.drawable.addsecret);
                        sex = "男";
                        setBackGround(0);
                        break;
                    }
                    case R.id.activity_add_addgirl: {
                        addboy.setImageResource(R.drawable.addboy);
                        addgirl.setImageResource(R.drawable.addgirlc);
                        addsecret.setImageResource(R.drawable.addsecret);
                        sex = "女";
                        setBackGround(1);
                        break;
                    }
                    case R.id.activity_add_addsecret: {
                        addboy.setImageResource(R.drawable.addboy);
                        addgirl.setImageResource(R.drawable.addgirl);
                        addsecret.setImageResource(R.drawable.addsecretc);
                        sex = "秘密";
                        setBackGround(2);
                        break;
                    }
                }
            }
        };
        addsecret.setOnClickListener(listener);
        addboy.setOnClickListener(listener);
        addgirl.setOnClickListener(listener);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                BDLocationListener listener2 = new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        String loca = "";
                        List<Poi> list = bdLocation.getPoiList();    // POI数据
                        if (list != null) {
                            loca = list.get(1).getName();
                        }
                        Observable<NeedInfo> observable = UserApi.getInstance().addNeed(Utils
                                        .getPhone(),
                                text, time.getText().toString(), sex, (float) bdLocation
                                        .getLongitude(), (float)
                                        bdLocation.getLatitude(), loca, "东一食堂", System
                                        .currentTimeMillis(), Utils.getToken());
                        observable.observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Observer<NeedInfo>() {
                                    @Override
                                    public void onCompleted() {
                                        showToast("添加成功");
                                        ActivityManager.finishActivity(EditUmbrellaActivity.this);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        showToast("添加失败");
                                    }

                                    @Override
                                    public void onNext(NeedInfo needInfo) {

                                    }
                                });
                    }

                    @Override
                    public void onConnectHotSpotMessage(String s, int i) {

                    }
                };
                Utils.LocationHelper(listener2);
            }
        });
    }

    public void showBrowseView() {
        clock.setClickable(false);
        time.setEnabled(false);
        editText.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

