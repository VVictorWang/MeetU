package com.hackday.play.ui.base;

import android.app.Activity;
import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hackday.play.utils.ActivityManager;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public abstract class BaseActivity extends LifecycleActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityManager.getInstance().pushActivity(getActivity());
        initView();
        initEvent();
        if (getPresnter() != null) {
            getPresnter().start();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresnter() != null) {
            getPresnter().unscribe();
        }
    }

    protected abstract BasePresenter getPresnter();

    protected abstract Activity getActivity();

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initEvent();

    protected void showToast(String description) {
        Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
    }
}
