package com.hackday.play.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hackday.play.R;
import com.hackday.play.utils.MyActivityManager;

public class FirstActivity extends AppCompatActivity {
    private int second = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        MyActivityManager.getInstance().pushActivity(FirstActivity.this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }, second);
    }

}
