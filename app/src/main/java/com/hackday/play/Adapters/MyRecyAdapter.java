package com.hackday.play.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hackday.play.View.EditUmbrellaActivity;
import com.hackday.play.MyApplication;
import com.hackday.play.R;
import com.hackday.play.Utils.LocationInfor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhanson on 2017/6/3.
 */

public class MyRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LocationInfor> locationInforList = new ArrayList<>();

    private int CONTENT_TYPE = 0;
    private int FOOT_TYPE = 1;
    private int EMPTY_TYPE = 2;
    private ProgressBar pbLoading;
    private TextView tvLoadMore;
    private Context context;

    public MyRecyAdapter(List<LocationInfor> locationInforList, Context context) {
        if (locationInforList != null)
            this.locationInforList = locationInforList;
        this.context = context;
    }

    public void setLocationInforList(List<LocationInfor> locationInforList) {
        this.locationInforList = locationInforList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mViewHolder holder;
        if (i == CONTENT_TYPE)
            return new mViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.frag_square_item, viewGroup, false), i);
        else if (i == EMPTY_TYPE) {
            return new mViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_empty, viewGroup, false), i);
        } else if (i == FOOT_TYPE) {
            return new FooterViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.list_footer, viewGroup, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mViewHolder, int i) {
        Log.d(" ", "" + i);
        int type = getItemViewType(i);
        if (type == CONTENT_TYPE) {
            if (locationInforList.size() != 0) {
                LocationInfor locationInfor = locationInforList.get(i);
                ((mViewHolder) mViewHolder).location.setText(locationInfor.getBuilding());
                ((mViewHolder) mViewHolder).remain_time.setText(locationInfor.getTime());
                ((mViewHolder)mViewHolder).title.setText(locationInfor.getDetail());
                ((mViewHolder) mViewHolder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(context, EditUmbrellaActivity.class);
                        intent.putExtra("Mode", 1);
                        context.startActivity(intent);
                    }
                });
                switch (locationInfor.getSex()) {
                    case 0:
                        ((mViewHolder) mViewHolder).cardView.setBackgroundColor(context.getResources().getColor(R.color.MessageSecret));
                        break;
                    case 1:
                        ((mViewHolder) mViewHolder).cardView.setBackgroundColor(context.getResources().getColor(R.color.MessageBoy));
                        break;
                    case -1:
                        ((mViewHolder) mViewHolder).cardView.setBackgroundColor(context.getResources().getColor(R.color.MessageGirl));
                        break;
                }

            }

        } else if (type == EMPTY_TYPE) {
            ((mViewHolder) mViewHolder).title.setText("暂时没有内容哦");
        } else if (type == FOOT_TYPE) {
            pbLoading = ((FooterViewHolder) mViewHolder).pbLoading;
            tvLoadMore = ((FooterViewHolder) mViewHolder).tvLoadMore;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (locationInforList.size() == 0)
            return EMPTY_TYPE;
        else if (position  == getItemCount()) {
            return FOOT_TYPE;
        } else
            return CONTENT_TYPE;
    }

    @Override
    public int getItemCount() {
        if (locationInforList.size() != 0)
            return locationInforList.size();
        else {
            return 1;
        }

    }

    public void showLoading() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.VISIBLE);
            tvLoadMore.setVisibility(View.GONE);
        }
    }

    /**
     * 显示上拉加载的文字，当数据加载完毕，调用该方法，隐藏进度条，显示“上拉加载更多”
     */
    public void showLoadMore() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.GONE);
            tvLoadMore.setVisibility(View.VISIBLE);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLoadMore;
        private ProgressBar pbLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvLoadMore = (TextView) itemView.findViewById(R.id.tv_item_footer_load_more);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_item_footer_loading);
        }
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        protected TextView remain_time, location, title;
        protected ImageView avatar;

        protected CardView cardView;

        public mViewHolder(View itemView, int type) {
            super(itemView);
            if (type != 1) {
                avatar = (ImageView) itemView.findViewById(R.id.item_avatar);
                remain_time = (TextView) itemView.findViewById(R.id.item_remain_time);
                location = (TextView) itemView.findViewById(R.id.item_location);
                title = (TextView) itemView.findViewById(R.id.item_title);
                cardView = (CardView) itemView.findViewById(R.id.item_CardView);
            } else
                title = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}