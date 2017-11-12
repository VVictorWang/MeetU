package com.hackday.play.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hackday.play.R;
import com.hackday.play.bean.GlobaData;
import com.hackday.play.bean.NeedInfo;
import com.hackday.play.ui.activity.MainActivity;
import com.hackday.play.utils.PrefUtils;
import com.hackday.play.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhanson on 2017/6/4.
 */

public class NotificitionService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private int Mode, Status;
    private double StepPerSecond;
    private final int INTERVAL = 5, OUTSIDE = 1, AT_HOME = 0;
    private NotificationManager notificationManager;
    private List<Long> timeList = new ArrayList<>();
    private List<Long> stepList = new ArrayList<>();
    private NeedInfo mNeedInfo = new NeedInfo();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                double previousx = Double.parseDouble(PrefUtils.getValue(getApplicationContext(),
                        GlobaData.LATITUDE));
                double previousy = Double.parseDouble(PrefUtils.getValue(getApplicationContext(),
                        GlobaData.LONGTITUDE));
                if (Utils.GetDistance(mNeedInfo.getLatitude(), mNeedInfo.getLongitude(),
                        previousx, previousy) < 10) {

                    if (Status == AT_HOME) {
                        Status = OUTSIDE;
                    } else {
                        Status = AT_HOME;
                    }
                    Notification.Builder builder = new Notification.Builder(getApplicationContext())
                            .setContentTitle("！")
                            .setContentText("注意带伞哦！")
                            .setSmallIcon(R.drawable.icont1)
                            .setWhen(System.currentTimeMillis());
                    Notification notification = builder.build();
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(101, notification);
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Start");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)).setContentText
                ("Meet U").setSmallIcon(R.mipmap.ic_launcher_round).setWhen(System
                .currentTimeMillis());
        Notification notification = builder.build();
        getSensor();
        startForeground(100, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service", "Destory");
    }

    private void getSensor() {
        if (sensorManager != null) {
            sensorManager = null;
        }
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 19) addListener();
    }

    private void addListener() {
        Sensor counter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (counter != null) {
            sensorManager.registerListener(NotificitionService.this, counter, SensorManager
                    .SENSOR_DELAY_NORMAL);
            Mode = 0;
        } else {
            sensorManager.registerListener(NotificitionService.this, detector, SensorManager
                    .SENSOR_DELAY_NORMAL);
            Mode = 1;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long time = System.currentTimeMillis();
        if (Mode == 0) {
            long tempStep = (long) event.values[0];
            stepList.add(tempStep);
            timeList.add(time);
        } else {
            if (event.values[0] == 1.0) {
                stepList.add(stepList.get(stepList.size() - 1) + 1);
                timeList.add(time);
            }
        }
        if (stepList.size() > INTERVAL && timeList.size() > INTERVAL) {
            StepPerSecond = (double) (stepList.get(stepList.size() - 1) - stepList.get(stepList
                    .size() - 1 - INTERVAL)) / (timeList.get(timeList.size() - 1) - timeList.get
                    (timeList.size() - 1 - INTERVAL)) * 1000;
            Log.d("Step/s", StepPerSecond + "");
            StepPerSecond = 0;
            int i = INTERVAL;
            while (i < timeList.size()) {
                if ((timeList.get(timeList.size() - 1) - timeList.get(timeList.size() - 1 - i)) <
                        10000)
                    i++;
                else {
                    StepPerSecond = (double) (stepList.get(stepList.size() - 1) - stepList.get
                            (stepList.size() - 1 - i)) / (timeList.get(timeList.size() - 1) -
                            timeList.get(timeList.size() - 1 - i)) * 1000;
                    break;
                }
            }
            if (StepPerSecond >= 0.6) {
                Log.d("Status", "Walking" + StepPerSecond);
                Message message = new Message();
                message.what = 0x123;
                handler.sendMessage(message);
                Utils.getLocation(mNeedInfo, handler, false);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}