package com.hackday.play;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.mapapi.SDKInitializer;
import com.hackday.play.Utils.LocationInfor;

import org.litepal.LitePal;
import org.litepal.parser.LitePalConfig;

/**
 * Created by victor on 17-6-3.
 */

public class MyApplication extends Application{
    private static Context context;
    private static  String name;
    private static int id;
    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static void setName(String name) {
        MyApplication.name = name;
    }

    public static void setId(int id) {
        MyApplication.id = id;
    }

    public static void setLocationInfor(LocationInfor locationInfor) {
        MyApplication.locationInfor = locationInfor;
    }

    public static String getName() {
        return name;
    }

    public static int getId() {
        return id;
    }

    public static LocationInfor getLocationInfor() {
        return locationInfor;
    }

    private static LocationInfor locationInfor;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SDKInitializer.initialize(this);
        locationInfor = new LocationInfor();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
