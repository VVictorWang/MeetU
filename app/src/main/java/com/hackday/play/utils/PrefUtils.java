package com.hackday.play.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class PrefUtils {
    private final static String defaultString = "empty";

    /**
     * 移除SharedPreference
     */
    public static void RemoveValue(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(key);
        boolean result = editor.commit();
        if (!result) {
            Log.e("移除Shared", "save " + key + " failed");
        }
    }

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 获取SharedPreference 值
     */
    public static String getValue(Context context, String key) {
        return getSharedPreference(context).getString(key, null);
    }

    public static String getValue(Context context, String key, String defaultValu) {
        return getSharedPreference(context).getString(key, defaultValu);
    }

    public static boolean getBooleanValue(Context context, String key) {
        return getSharedPreference(context).getBoolean(key, false);
    }

    public static boolean getBooleanValue(Context context, String key, boolean defaultValue) {
        return getSharedPreference(context).getBoolean(key, defaultValue);
    }

    public static float getFloatValue(Context context, String key) {
        return getSharedPreference(context).getFloat(key, -99999);
    }

    public static void putBooleanValue(Context context, String key,
                                       boolean bl) {
        SharedPreferences.Editor edit = getSharedPreference(context).edit();
        edit.putBoolean(key, bl);
        edit.commit();
    }

    public static int getIntValue(Context context, String key) {
        return getSharedPreference(context).getInt(key, -1);
    }

    public static int getIntValue(Context context, String key, int defaultValue) {
        return getSharedPreference(context).getInt(key, defaultValue);
    }

    public static long getLongValue(Context context, String key,
                                    long default_data) {
        return getSharedPreference(context).getLong(key, default_data);
    }

    public static boolean putLongValue(Context context, String key,
                                       long value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean putFloatValue(Context context, String key,
                                        float value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static boolean hasValue(Context context, String key) {
        return getSharedPreference(context).contains(key);
    }

    /**
     * 设置SharedPreference 值
     */
    public static boolean putValue(Context context, String key,
                                   String value) {
        value = value == null ? "" : value;
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }

    /**
     * 设置SharedPreference 值
     */
    public static boolean putIntValue(Context context, String key,
                                      int value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }


}
