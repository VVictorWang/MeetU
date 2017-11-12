package com.hackday.play.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hackday.play.R;
import com.hackday.play.UserViewModel;
import com.hackday.play.UserViewModelFactory;
import com.hackday.play.bean.NeedInfo;
import com.hackday.play.db.MyDataBase;
import com.hackday.play.service.NotificitionService;
import com.hackday.play.ui.adapters.MyFragAdapter;
import com.hackday.play.ui.base.BaseActivity;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.ui.contract.MainContract;
import com.hackday.play.ui.fragments.MyFragment;
import com.hackday.play.ui.fragments.SquareFragment;
import com.hackday.play.ui.presenter.MainPresenter;
import com.hackday.play.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {

    private ImageView user_avatar, sort;
    private DrawerLayout drawerLayout;
    private RelativeLayout drawer;
    private TabLayout tabLayout;
    private TabLayout.Tab square, umbrella;
    private ViewPager viewPager;
    private MyFragAdapter myFragAdapter;
    private View view_square, view_umbrella;
    private FloatingActionButton add;
    private ImageView avatar;
    private List<Fragment> fragmentList = new ArrayList<>();
    private SquareFragment fragment;
    private MyFragment myFragment;
    private TextView user_name, user_love_level;
    private NeedInfo infor = new NeedInfo();
    private RelativeLayout location_info;
    private TextView user_location, user_location_detail, user_phone;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                user_location.setText(infor.getLocation());
                user_location_detail.setText(infor.getDesc());
            }
        }
    };
    private MainContract.Presenter mPresenter;

    private UserViewModel mUserViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new MainPresenter(this);
        UserViewModelFactory modelFactory = new UserViewModelFactory(MyDataBase.getInstance(this)
                .userDao
                ());
        mUserViewModel = ViewModelProviders.of(this, modelFactory).get(UserViewModel.class);
        super.onCreate(savedInstanceState);
        startService(new Intent(MainActivity.this, NotificitionService.class));
        Utils.getLocation(infor, handler, true);
    }

    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
    }

    @Override
    protected Activity getActivity() {
        return MainActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        user_avatar = (ImageView) findViewById(R.id.user_infor);
        add = (FloatingActionButton) findViewById(R.id.add_new);
        avatar = (ImageView) findViewById(R.id.avatar);
        sort = (ImageView) findViewById(R.id.sort_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        user_name = (TextView) findViewById(R.id.user_name);
        user_love_level = (TextView) findViewById(R.id.user_love_level_text_top);
        location_info = (RelativeLayout) findViewById(R.id.user_location_infor);
        user_location = (TextView) findViewById(R.id.user_location_infor_text_top);
        user_location_detail = (TextView) findViewById(R.id.user_location_detail);
        user_phone = (TextView) findViewById(R.id.user_phone);
        drawer = (RelativeLayout) findViewById(R.id.drawer);
        user_avatar.setOnClickListener(new View.OnClickListener() {
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
        viewPager = (ViewPager) findViewById(R.id.mViewPager);
        fragment = SquareFragment.newInstance(SquareFragment.STATUS_ALL);
        fragmentList.add(fragment);
        myFragment = new MyFragment();
        fragmentList.add(myFragment);
        myFragAdapter = new MyFragAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tables);
        tabLayout.setupWithViewPager(viewPager);
        view_square = getLayoutInflater().inflate(R.layout.view_square, null);
        view_umbrella = getLayoutInflater().inflate(R.layout.view_umbrella, null);
        view_square.setBackground(getResources().getDrawable(R.drawable.iconhall));
        view_umbrella.setBackground(getResources().getDrawable(R.drawable.icontaskc));
        initTab();
    }

    @Override
    public void initFragment() {
        fragment.loadData();
        myFragment.loadData();
    }

    @Override
    protected void initEvent() {
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


    public void initTab() {
        square = tabLayout.getTabAt(0);
        umbrella = tabLayout.getTabAt(1);

        square.setCustomView(view_square);
        umbrella.setCustomView(view_umbrella);

    }

    @Override
    public UserViewModel getUserViewModel() {
        return mUserViewModel;
    }

    @Override
    public LifecycleOwner getLiftcycle() {
        return this;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public void showEditView() {
        View view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        Button positive = (Button) view.findViewById(R.id.dialog_positive);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).setCancelable
                (false).create();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    @Override
    public void setUserName(String name) {
        user_name.setText(name);
    }

    @Override
    public void setUserLove_Level(int love_level) {
        user_love_level.setText(String.valueOf(love_level));
    }

    @Override
    public void setUserPhone(String phone) {
        user_phone.setText(phone);
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
