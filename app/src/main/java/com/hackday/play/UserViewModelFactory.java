package com.hackday.play;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.hackday.play.db.UserDao;

/**
 * Created by victor on 11/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private UserDao mUserDao;

    public UserViewModelFactory(UserDao userDao) {
        mUserDao = userDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(mUserDao);
        }
        throw new IllegalArgumentException("error");
    }
}
