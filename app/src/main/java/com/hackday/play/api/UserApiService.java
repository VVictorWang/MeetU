package com.hackday.play.api;

import com.hackday.play.data.StatusInfo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by victor on 10/9/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface UserApiService {

    @POST("user")
    Observable<StatusInfo> register(@Body RequestBody body);

}
