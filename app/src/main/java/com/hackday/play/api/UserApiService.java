package com.hackday.play.api;

import com.hackday.play.data.LoginResponse;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.data.NeedList;
import com.hackday.play.data.StatusInfo;
import com.hackday.play.data.UserInfo;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
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

    @POST("user/<phone>")
    Observable<LoginResponse> login(@Path("phone") String phone, @Body RequestBody body);

    @GET("user/<phone>")
    Observable<Response<UserInfo>> getUserInfo(@Path("phone") String phone, @Header("token")
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

}
