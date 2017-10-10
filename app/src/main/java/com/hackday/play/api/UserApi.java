package com.hackday.play.api;

import com.hackday.play.data.StatusInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserApi {
    private static UserApi instance;
    private UserApiService mUserApiService;

    public static UserApi getInstance() {
        if (instance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(200, TimeUnit.SECONDS)
                    .connectTimeout(200, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            instance = new UserApi(client);
        }
        return instance;
    }

    public UserApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.victorwang.science:8889/api/v1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mUserApiService = retrofit.create(UserApiService.class);
    }

    public Observable<StatusInfo> register(String myId, String name, String password, String phone,
                                           String qq, int age) {
        return mUserApiService.register(new JsonRequestBody().setMyId(myId).setUsername(name)
                .setPassword(password).setPhone(phone).setQq(qq).setAge(age).create());
    }
}
