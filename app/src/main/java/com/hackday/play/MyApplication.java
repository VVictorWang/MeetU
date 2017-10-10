package com.hackday.play;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.hackday.play.data.LocationInfor;
import com.hackday.play.utils.AppUtils;

import org.litepal.LitePal;

/**
 * Created by victor on 17-6-3.
 */

public class MyApplication extends Application {
    private static Context context;
    private static LocationInfor locationInfor;

    public static void setContext(Context context) {
        MyApplication.context = context;
    }


    public static void setLocationInfor(LocationInfor locationInfor) {
        MyApplication.locationInfor = locationInfor;
    }


    public static LocationInfor getLocationInfor() {
        return locationInfor;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppUtils.init(this);
        SDKInitializer.initialize(this);
        locationInfor = new LocationInfor();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
