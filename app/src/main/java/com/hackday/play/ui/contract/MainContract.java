package com.hackday.play.ui.contract;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import com.hackday.play.UserViewModel;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.ui.base.BaseView;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showEditView();

        void setUserName(String name);

        void setUserLove_Level(int love_level);

        void setUserPhone(String phone);

        void initFragment();

        void initTab();

        UserViewModel getUserViewModel();


        LifecycleOwner getLiftcycle();
    }

    interface Presenter extends BasePresenter {

    }

}
