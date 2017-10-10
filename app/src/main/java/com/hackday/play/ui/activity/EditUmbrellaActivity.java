package com.hackday.play.ui.activity;

import android.app.Activity;
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

import com.hackday.play.MyApplication;
import com.hackday.play.R;
import com.hackday.play.data.GlobaData;
import com.hackday.play.data.LocationInfor;
import com.hackday.play.ui.base.BaseActivity;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.utils.ActivityManager;
import com.hackday.play.utils.PrefUtils;
import com.hackday.play.utils.Utils;
import com.hackday.play.view.PickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhanson on 2017/6/3.
 */

public class EditUmbrellaActivity extends BaseActivity {
    private LocationInfor locationInfor;
    private EditText editText;
    private TextView textView, time, where;
    private Button button, cancelButton, meetButton;
    private RelativeLayout relativeLayout;
    private ImageView addboy, addgirl, addsecret, clock, titleImg, back;
    private LinearLayout backgroung, helperLayout, buttonLayout;
    private String selectedTime;
    private int sex = 0, mode = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                where.setText(locationInfor.getSpecific_infor());
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter getPresnter() {
        return null;
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
        init();
    }

    @Override
    protected void initEvent() {
        time.setOnClickListener(new View.OnClickListener() {
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
        });
        meetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUmbrellaActivity.this, FinishActivity.class);
                startActivity(intent);
            }
        });
    }


    private void init() {
        locationInfor = MyApplication.getLocationInfor();
        Utils.getLocation(locationInfor, handler);
        mode = getIntent().getIntExtra("Mode", 0);

        if (mode == 0) {//编辑、发布模式
            textView.setVisibility(View.GONE);
            helperLayout.setVisibility(View.GONE);
            button.setText("点击求帮助OvO");
            buttonLayout.setVisibility(View.GONE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
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
                            sex = 1;
                            backgroung.setBackground(getResources().getDrawable(R.drawable
                                    .add_back));
                            titleImg.setImageResource(R.drawable.add_banner_boy);
                            break;
                        }
                        case R.id.activity_add_addgirl: {
                            addboy.setImageResource(R.drawable.addboy);
                            addgirl.setImageResource(R.drawable.addgirlc);
                            addsecret.setImageResource(R.drawable.addsecret);
                            sex = -1;
                            backgroung.setBackground(getResources().getDrawable(R.drawable
                                    .add_back_girl));
                            titleImg.setImageResource(R.drawable.add_banner_girl);
                            break;
                        }
                        case R.id.activity_add_addsecret: {
                            addboy.setImageResource(R.drawable.addboy);
                            addgirl.setImageResource(R.drawable.addgirl);
                            addsecret.setImageResource(R.drawable.addsecretc);
                            sex = 0;
                            backgroung.setBackground(getResources().getDrawable(R.drawable
                                    .add_back_secret));
                            titleImg.setImageResource(R.drawable.add_banner_secret);
                            break;
                        }
                    }
                }
            };
            addsecret.setOnClickListener(listener);
            addboy.setOnClickListener(listener);
            addgirl.setOnClickListener(listener);
            View.OnClickListener listener1 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedTime = "";
                }
            };
            time.setOnClickListener(listener1);
            clock.setOnClickListener(listener1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = editText.getText().toString();
                    LocationInfor newLocationInfor = new LocationInfor();
                    newLocationInfor.setDetail(text);
                    newLocationInfor.setName(PrefUtils.getValue(EditUmbrellaActivity.this, GlobaData
                            .NAME));
                    newLocationInfor.setTime(time.getText().toString());
                    newLocationInfor.setSex(sex);
                    newLocationInfor.setLatitude(locationInfor.getLatitude());
                    newLocationInfor.setLongtitude(locationInfor.getLongtitude());
                    newLocationInfor.setStatus(LocationInfor.NEED_ED);
                    newLocationInfor.save();
                    ActivityManager.finishActivity(EditUmbrellaActivity.this);
                }
            });
        } else {//浏览模式
            if (locationInfor == null) finish();
            clock.setClickable(false);
//            time.setClickable(false);
            time.setEnabled(false);
            editText.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
//            if (locationInfor.getId() == MyApplication.getId()) {
            if (locationInfor.getId() == 1) {
                button.setVisibility(View.GONE);
                switch (locationInfor.getStatus()) {
                    case 1:
                        buttonLayout.setVisibility(View.GONE);
                        break;
                    case 2:
                        meetButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                            }
                        });
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAlertDialog2();
                            }
                        });
                        break;

                    case 3:
                        meetButton.setText("修改");
                        meetButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAlertDialog2();
                            }
                        });
                        break;

                }
            } else {
                switch (locationInfor.getSex()) {
                    case 1:
                        button.setText("帮助他");
                        break;
                    case -1:
                        button.setText("帮助她");
                        break;
                    case 0:
                        button.setText("帮助他/她");
                        break;
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (mode == 0) showAlertDialog();
        else super.onBackPressed();
    }

    private void showAlertDialog() {
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

    private void showAlertDialog2() {
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

}

