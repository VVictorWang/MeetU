package com.hackday.play;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.hackday.play.api.Resource;
import com.hackday.play.api.UserApi;
import com.hackday.play.bean.UserInfo;
import com.hackday.play.db.UserDao;
import com.hackday.play.utils.AppExecutors;
import com.hackday.play.utils.Utils;

/**
 * Created by victor on 11/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserViewModel extends ViewModel {

    private final LiveData<Resource<UserInfo>> userInfo;

    public UserViewModel(UserDao userDao) {
        UserReposity userReposity = new UserReposity(userDao, UserApi.getInstance(), AppExecutors
                .getInstance());
        userInfo = userReposity.loadUser(Utils.getPhone(), Utils.getToken());
    }

    public LiveData<Resource<UserInfo>> getUserInfo() {
        return userInfo;
    }
}
