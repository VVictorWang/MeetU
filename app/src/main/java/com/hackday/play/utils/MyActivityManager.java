package com.hackday.play.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by victor on 17-6-4.
 */

public class MyActivityManager {
    private static Stack<Activity> activityStack;
    private static MyActivityManager instance;

    private MyActivityManager() {

    }

    public static MyActivityManager getInstance() {
        if (instance == null) {
            instance = new MyActivityManager();
        }
        return instance;
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }
    public Activity getCurrentActivity() {
        Activity activity = null;
        if (!activityStack.isEmpty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

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
