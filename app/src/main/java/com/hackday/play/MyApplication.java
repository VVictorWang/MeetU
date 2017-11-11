package com.hackday.play;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.hackday.play.bean.GlobaData;
import com.hackday.play.utils.AppUtils;
import com.hackday.play.utils.PrefUtils;
import com.hackday.play.utils.Utils;

import org.litepal.LitePal;

/**
 * Created by victor on 17-6-3.
 */

public class MyApplication extends Application {
    private static Context context;

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppUtils.init(this);
        SDKInitializer.initialize(this);
        LitePal.initialize(context);
        getUserLocation();
    }

    private void getUserLocation() {
        BDLocationListener listener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                PrefUtils.putFloatValue(context, GlobaData.LATITUDE, (float) bdLocation
                        .getLatitude());
                PrefUtils.putFloatValue(context, GlobaData.LONGTITUDE, (float) bdLocation
                        .getLongitude());
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {
            }
        };
        Utils.LocationHelper(listener);
    }

    public static Context getContext() {
        return context;
    }
}
