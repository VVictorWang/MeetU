    package com.hackday.play;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hackday.play.Utils.MyActivityManager;
import com.hackday.play.View.MainActivity;

    public class First extends AppCompatActivity {
    private int second = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        MyActivityManager.getInstance().pushActivity(First.this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(First.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }, second);
    }
}
