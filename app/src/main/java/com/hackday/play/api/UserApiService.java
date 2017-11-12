package com.hackday.play.api;

import android.arch.lifecycle.LiveData;

import com.hackday.play.bean.LoginResponse;
import com.hackday.play.bean.NeedInfo;
import com.hackday.play.bean.NeedList;
import com.hackday.play.bean.StatusInfo;
import com.hackday.play.bean.UserInfo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by victor on 10/9/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface UserApiService {

    @POST("user")
    Observable<StatusInfo> register(@Body RequestBody body);

    @POST("user/{phone}")
    Observable<LoginResponse> login(@Path("phone") String phone, @Body RequestBody body);

    @PUT("user/{phone}")
    Observable<StatusInfo> editUser(@Path("phone") String phone, @Header("token") String token,
                                    @Body RequestBody body);

    @GET("user/{phone}")
    LiveData<ApiResponse<UserInfo>> getUserInfo(@Path("phone") String phone, @Header("token")
            String token);

    @GET("needs")
    Observable<NeedList> getAllNeeds(@Header("token") String header);

    @POST("needs")
    Observable<NeedInfo> addNeed(@Header("token") String token, @Body RequestBody body);

    @GET("needs/{id}")
    Observable<NeedInfo> getNeedInfo(@Header("token") String token, @Path("id") String id);

    @PUT("needs/{id}")
    Observable<NeedInfo> changeNeedInfo(@Header("token") String token, @Path("id") String id,
                                        @Body RequestBody body);

    @GET("user/edit/{phone}")
    Observable<UserInfo> changeLoveLevel(@Header("token") String token, @Path("phone") String
            phone);

    @DELETE("needs/{id}")
    Observable<StatusInfo> deleteNeed(@Header("token") String token, @Path("id") String id);

}
