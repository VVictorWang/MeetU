package com.hackday.play.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hackday.play.R;

import java.util.Stack;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */
public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * Start activity for result.
     *
     * @param activity    the activity
     * @param cls         the cls
     * @param requestCode the request code
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    /**
     * Start activity for result.
     *
     * @param activity    the activity
     * @param intent      the intent
     * @param requestCode the request code
     */
    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    /**
     * Start activity.
     *
     * @param activity the activity
     * @param cls      the cls
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * Start activity.
     *
     * @param activity the activity
     * @param intent   the intent
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * Finish activity.
     *
     * @param activity the activity
     */
    public static void finishActivity(Activity activity) {
        instance.popActivity(activity);
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * Add fragment to activity.
     *
     * @param fragmentManager the fragment manager
     * @param fragment        the fragment
     * @param fragmeID        the fragme id
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull
            Fragment fragment, int fragmeID) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmeID, fragment);
        transaction.commit();

    }

    /**
     * Pop activity.
     *
     * @param activity the activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * Push activity.
     *
     * @param activity the activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * Gets current activity.
     *
     * @return the current activity
     */
    public Activity getCurrentActivity() {
        Activity activity = null;
        if (!activityStack.isEmpty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * Pop all activity.
     */
    public void popAllActivity() {
        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }
}
