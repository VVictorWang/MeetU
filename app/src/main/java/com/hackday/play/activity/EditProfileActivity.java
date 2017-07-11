package com.hackday.play.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hackday.play.MyApplication;
import com.hackday.play.R;
import com.hackday.play.data.GlobaData;
import com.hackday.play.data.LocationInfor;
import com.hackday.play.utils.MyActivityManager;
import com.hackday.play.utils.Utils;

public class EditProfileActivity extends AppCompatActivity {

    private android.widget.RelativeLayout editprofiletoolbar;
    private android.widget.RelativeLayout editidinfor;
    private android.widget.RelativeLayout editnameinfor;
    private android.widget.RelativeLayout editphoneinfor;
    private android.widget.RelativeLayout editqqinfor;
    private android.widget.RelativeLayout editlocationinfor;
    private EditText editname;
    private EditText editphone;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        MyActivityManager.getInstance().pushActivity(EditProfileActivity.this);
       InitView();
        InitEvent();
    }

    private void InitView() {
        this.editlocationinfor = (RelativeLayout) findViewById(R.id.edit_location_infor);
        this.editqqinfor = (RelativeLayout) findViewById(R.id.edit_qq_infor);
        this.editphoneinfor = (RelativeLayout) findViewById(R.id.edit_phone_infor);
        this.editnameinfor = (RelativeLayout) findViewById(R.id.edit_name_infor);
        this.editidinfor = (RelativeLayout) findViewById(R.id.edit_id_infor);
        this.editprofiletoolbar = (RelativeLayout) findViewById(R.id.edit_profile_toolbar);
        this.editlocation = (EditText) findViewById(R.id.edit_location);
        this.editqq = (EditText) findViewById(R.id.edit_qq);
        this.editphone = (EditText) findViewById(R.id.edit_phone);
        this.editname = (EditText) findViewById(R.id.edit_name);
        back = (ImageView) findViewById(R.id.edit_back);
        confirm = (Button) findViewById(R.id.confirm_edit);
        Utils.getLocation(locationInfor,handler);
    }

    private void InitEvent() {
        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editname.setCursorVisible(true);
            }
        });
        editlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editlocation.setCursorVisible(true);
            }
        });
        editqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editqq.setCursorVisible(true);
            }
        });
        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editphone.setCursorVisible(true);
            }
        });
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
                locationInfor.setName(name);
                locationInfor.setQq(qq);
                locationInfor.setPhone(phone);
                MyApplication.setLocationInfor(locationInfor);
                Utils.putIntValue(EditProfileActivity.this, GlobaData.ID, 3);
                Utils.putValue(EditProfileActivity.this, GlobaData.NAME, name);
                Utils.putValue(EditProfileActivity.this, GlobaData.QQ, qq);
                Utils.putValue(EditProfileActivity.this, GlobaData.PHONE, phone);
                Utils.putBooleanValue(EditProfileActivity.this,GlobaData.IS_EDITED,true);
                finish();
            }
        });
    }

}
