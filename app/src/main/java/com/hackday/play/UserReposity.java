package com.hackday.play;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hackday.play.api.ApiResponse;
import com.hackday.play.api.Resource;
import com.hackday.play.api.UserApi;
import com.hackday.play.db.UserDao;
import com.hackday.play.bean.UserInfo;
import com.hackday.play.utils.AppExecutors;
import com.hackday.play.utils.NetworkBoundResource;

/**
 * Created by victor on 11/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserReposity {

    private final UserDao mUserDao;
    private final UserApi mUserApi;
    private final AppExecutors mAppExecutors;

    public UserReposity(UserDao userDao, UserApi userApi, AppExecutors appExecutors) {
        mUserDao = userDao;
        mUserApi = userApi;
        mAppExecutors = appExecutors;
    }

    public LiveData<Resource<UserInfo>> loadUser(String phone, String token) {
        return new NetworkBoundResource<UserInfo, UserInfo>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull UserInfo item) {
                mUserDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserInfo data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<UserInfo> loadFromDb() {
                return mUserDao.loadUser(phone);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserInfo>> createCall() {
                return mUserApi.getUserInfo(phone, token);
            }
        }.asLiveData();
    }
}
