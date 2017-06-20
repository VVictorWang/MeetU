package com.hackday.play.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hackday.play.Adapters.MyFragAdapter;
import com.hackday.play.MyApplication;
import com.hackday.play.NotificitionService;
import com.hackday.play.R;
import com.hackday.play.Utils.MyActivityManager;
import com.hackday.play.View.Fragments.MyFragment;
import com.hackday.play.View.Fragments.SquareFragment;
import com.hackday.play.Utils.GlobaData;
import com.hackday.play.Utils.LocationInfor;
import com.hackday.play.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //    private MapView mapView;
    private boolean isEdited;
    private ImageView user_infor,sort;
    private DrawerLayout drawerLayout;
    private RelativeLayout drawer;
    private TabLayout tabLayout;
    private TabLayout.Tab square, umbrella;
    private static final String TAG = "MainActivity";
    private boolean sortmenu = false;
    private ViewPager viewPager;
    private MyFragAdapter myFragAdapter;
    private View view_square, view_umbrella;
    private FloatingActionButton add;
    private ImageView avatar;
    private List<Fragment> fragmentList=new ArrayList<>();
    private SquareFragment fragment;
    private TextView user_name, user_love_level;
    private LocationInfor infor = new LocationInfor();
    private RelativeLayout location_info;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Utils.putValue(MainActivity.this, GlobaData.LATITUDE, "" + infor.getLatitude());
                Utils.putValue(MainActivity.this, GlobaData.LONGTITUDE, "" + infor.getLongtitude());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyActivityManager.getInstance().pushActivity(MainActivity.this);
        isEdited = Utils.getBooleanValue(MainActivity.this, GlobaData.IS_EDITED);
        if (!isEdited) {
            View view=getLayoutInflater().inflate(R.layout.alert_dialog, null);
            Button positive=(Button) view.findViewById(R.id.dialog_positive);
            final AlertDialog dialog=new AlertDialog.Builder(this).setView(view).setCancelable(false).create();
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,EditProfileActivity.class);
                    MyApplication.setLocationInfor(new LocationInfor());
                    startActivity(intent);
                }
            });
            dialog.show();
        }
        InitView();
        InitTab();
        InitEvent();
        InitData();
        startService(new Intent(MainActivity.this,NotificitionService.class));
        Utils.getLocation(infor,handler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitData();
    }

    private void InitData() {
        user_name.setText(Utils.getValue(MainActivity.this, GlobaData.NAME));
        user_love_level.setText("" + Utils.getIntValue(MainActivity.this, GlobaData.LOVE_LEVEL));
    }

    private void InitView(){
        user_infor = (ImageView) findViewById(R.id.user_infor);
        add = (FloatingActionButton) findViewById(R.id.add_new);
        avatar = (ImageView) findViewById(R.id.avatar);
        sort = (ImageView) findViewById(R.id.sort_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        user_name = (TextView) findViewById(R.id.user_name);
        user_love_level = (TextView) findViewById(R.id.user_love_level_text_top);
        location_info = (RelativeLayout) findViewById(R.id.user_location_infor);
        drawer = (RelativeLayout) findViewById(R.id.drawer);
        user_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawer);
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindow popWindow = new popwindow(MainActivity.this);
                popWindow.showPopupWindow(sort);
            }
        });
        viewPager=(ViewPager) findViewById(R.id.mViewPager);
        fragment=new SquareFragment();
        fragmentList.add(fragment);
        MyFragment myFragment=new MyFragment();
        fragmentList.add(myFragment);
        myFragAdapter = new MyFragAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tables);
        tabLayout.setupWithViewPager(viewPager);
        view_square = getLayoutInflater().inflate(R.layout.view_square, null);
        view_umbrella = getLayoutInflater().inflate(R.layout.view_umbrella, null);
        view_square.setBackground(getResources().getDrawable(R.drawable.iconhall));
        view_umbrella.setBackground(getResources().getDrawable(R.drawable.icontaskc));
    }

    private void InitTab() {
        square = tabLayout.getTabAt(0);
        umbrella = tabLayout.getTabAt(1);
        square.setCustomView(view_square);
        umbrella.setCustomView(view_umbrella);
    }

    private void InitEvent() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0)) {
                    view_square.setBackground(getResources().getDrawable(R.drawable.iconhall));
                    square.setCustomView(view_square);
                    add.setVisibility(View.VISIBLE);
                } else if (tab == tabLayout.getTabAt(1)) {
                    view_umbrella.setBackground(getResources().getDrawable(R.drawable.icontask));
                    umbrella.setCustomView(view_umbrella);
                    add.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0)) {
                    view_square.setBackground(getResources().getDrawable(R.drawable.iconhallc));
                    square.setCustomView(view_square);
                } else if (tab == tabLayout.getTabAt(1)) {
                    view_umbrella.setBackground(getResources().getDrawable(R.drawable.icontaskc));
                    umbrella.setCustomView(view_umbrella);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditUmbrellaActivity.class);
                intent.putExtra("Mode", 0);
                startActivity(intent);
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        location_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }




    private class popwindow extends PopupWindow {
        private View conentView;

        public popwindow(Activity context) {
            super(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            conentView = inflater.inflate(R.layout.sort_view, null);
            int h = context.getWindowManager().getDefaultDisplay().getHeight();
            int w = context.getWindowManager().getDefaultDisplay().getWidth();
            // 设置SelectPicPopupWindow的View
            this.setContentView(conentView);
            // 设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(w / 2 + 10);
            // 设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(h / 2 - 100);
            // 设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            this.setOutsideTouchable(true);
            // 刷新状态
            this.update();
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0000000000);
            // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
            this.setBackgroundDrawable(dw);
            // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            // 设置SelectPicPopupWindow弹出窗体动画效果
//            this.setAnimationStyle(R.style.AnimationPreview);
            RelativeLayout map = (RelativeLayout) conentView.findViewById(R.id.show_map_layout);
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });
            RelativeLayout near = (RelativeLayout) conentView.findViewById(R.id.near_me);
            near.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.sortByDistance();
                    popwindow.this.dismiss();
                }
            });
            RelativeLayout time = (RelativeLayout) conentView.findViewById(R.id.sort_time);
            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.sortByDate();
                    popwindow.this.dismiss();
                }
            });
            RelativeLayout girl = (RelativeLayout) conentView.findViewById(R.id.sort_girl);
            girl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.sortGirl();
                    popwindow.this.dismiss();
                }
            });

        }
        public void showPopupWindow(View parent) {
            if (!this.isShowing()) {
                // 以下拉方式显示popupwindow
                this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 5);
            } else {
                this.dismiss();
            }
        }
    }
}
