package com.hackday.play.utils;

import android.os.Handler;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.hackday.play.MyApplication;
import com.hackday.play.data.GlobaData;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.data.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by victor on 17-6-3.
 */

public class Utils {
    public static void getLocation(final NeedInfo needInfo, final Handler handler, boolean
            isDetail) {
        LocationClient locationClient = new LocationClient(MyApplication.getContext());
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");

        int span = 1000;
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(span);

        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);

        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);

        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);

        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);

        locationClient.setLocOption(option);

        BDLocationListener listener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                //获取定位结果
                needInfo.setLatitude(location.getLatitude());
                needInfo.setLongitude(location.getLongitude());
//                sb.append(location.getLongitude());    //获取经度信息


                // 网络定位结果
//                needInfo.setLocation(location.getAddrStr());


                List<Poi> list = location.getPoiList();    // POI数据
                needInfo.setLocation(list.get(1).getName());
                //如果需要获取更加详细的地址(主要在MainActivity中调用
                if (isDetail) {
                    needInfo.setDesc(list.get(2).getName());
                }
                if (list != null) {
                    for (Poi p : list) {
                        String name = p.getName();
                        Log.d("@victor", name);
                    }
//                    locationInfor.setSpecific_infor(list.get(1).getName());
                }
                handler.sendEmptyMessage(0x123);
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        };
        locationClient.registerLocationListener(listener);
        locationClient.start();
        locationClient.requestLocation();
    }

    public static void LocationHelper(BDLocationListener listener) {
        LocationClient locationClient = new LocationClient(MyApplication.getContext());
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");

        int span = 1000;
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(span);

        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);

        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);

        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);

        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);

        locationClient.setLocOption(option);
        locationClient.registerLocationListener(listener);
        locationClient.start();
        locationClient.requestLocation();
    }

    public static String createAcacheKey(Object... param) {
        String key = "";
        for (Object o : param) {
            key += "-" + o;
        }
        return key.replaceFirst("-", "");
    }


    public static String getPhone() {
        return PrefUtils.getValue(AppUtils.getAppContext(), GlobaData.PHONE);
    }

    public static String getToken() {
        return PrefUtils.getValue(AppUtils.getAppContext(), GlobaData.TOKEN);
    }

    public static void updateUserInfo(UserInfo userInfo) {
        PrefUtils.putIntValue(AppUtils.getAppContext(), GlobaData.LOVE_LEVEL, userInfo
                .getLove_level());
        PrefUtils.putValue(AppUtils.getAppContext(), GlobaData.NICKNAME, userInfo.getNickname());
        PrefUtils.putValue(AppUtils.getAppContext(), GlobaData.PHONE, userInfo.getPhone());
        PrefUtils.putValue(AppUtils.getAppContext(), GlobaData.QQ, userInfo.getQq());
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     */
    public static double GetDistance(double lati1, double long1, double lati2, double long2) {
        double EARTH_RADIUS = 6378.137;
        double radLat1 = rad(lati1); //计算弧度
        double radLat2 = rad(lati2);
        double a = radLat1 - radLat2;
        double b = rad(long1) - rad(long2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    public static String formatChineseDate(long timeMillise) {
        long diff = System.currentTimeMillis() - timeMillise;
        Date date = new Date(timeMillise);
        int munite = (int) diff / (1000 * 60);
        if (munite == 0) {
            int seconds = (int) diff / 1000;
            return String.format("%02d秒前", seconds);
        } else if (munite >= 100) {
            int hour = (int) diff / (1000 * 60 * 60);
            if (hour >= 23) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
                return dateFormat.format(date);
            }
            return String.format("%02d小时前", hour);
        }
        return String.format("%02d分钟前", munite);
    }

    public static boolean isOutdated(String continue_time, long created_time) {
        Date date = new Date(created_time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (continue_time.contains("分钟")) {
            int minute = Integer.valueOf(getNumber(continue_time));
            calendar.add(Calendar.MINUTE, minute);
        } else if (continue_time.contains("小时")) {
            int hour = Integer.valueOf(getNumber(continue_time));
            calendar.add(Calendar.HOUR, hour);
        }
        return System.currentTimeMillis() < calendar.getTime().getTime();
    }

    private static  String getNumber(String strs) {
        String reg = "[^0-9]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(strs);
        return m.replaceAll("").trim();
    }


}
