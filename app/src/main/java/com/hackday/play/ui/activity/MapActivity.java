package com.hackday.play.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.hackday.play.R;
import com.hackday.play.bean.NeedInfo;
import com.hackday.play.utils.MyActivityManager;
import com.hackday.play.utils.Utils;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap baiduMap;
    private NeedInfo infor;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                LatLng latLng = new LatLng(infor.getLatitude(), infor.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(update);
                mapView.invalidate();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MyActivityManager.getInstance().pushActivity(MapActivity.this);
        mapView = (MapView) findViewById(R.id.baidu_map);
        baiduMap = mapView.getMap();
        infor = new NeedInfo();
        Utils.getLocation(infor, handler, false);
    }
}
