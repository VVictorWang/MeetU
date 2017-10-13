package com.hackday.play.ui.adapters;

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

import com.hackday.play.R;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.ui.activity.EditUmbrellaActivity;
import com.hackday.play.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhanson on 2017/6/3.
 */

public class MyRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NeedInfo> mNeedInfos;

    private static int CONTENT_TYPE = 0;
    private static int FOOT_TYPE = 1;
    private static int EMPTY_TYPE = 2;
    private ProgressBar pbLoading;
    private TextView tvLoadMore;
    private Context context;

    public MyRecyAdapter(Context context) {
        mNeedInfos = new ArrayList<>();
        this.context = context;
    }

    public void setLocationInforList(List<NeedInfo> needInfos) {
        mNeedInfos.clear();
        mNeedInfos.addAll(needInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == CONTENT_TYPE)
            return new MyViewHolder(LayoutInflater.from(context).inflate(R
                    .layout.frag_square_item, null), i);
        else if (i == EMPTY_TYPE) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R
                    .layout.item_empty, null), i);
        } else if (i == FOOT_TYPE) {
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R
                    .layout.list_footer, null));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mViewHolder, int i) {
        Log.d(" ", "" + i);
        int type = getItemViewType(i);
        if (type == CONTENT_TYPE) {
            if (mNeedInfos.size() != 0) {
                NeedInfo need = mNeedInfos.get(i);
                ((MyViewHolder) mViewHolder).location.setText(need.getLocation());
                ((MyViewHolder) mViewHolder).remain_time.setText(need.getContinue_time()+"内");
                ((MyViewHolder) mViewHolder).title.setText(need.getDesc());
                ((MyViewHolder) mViewHolder).create_time.setText(Utils.formatChineseDate(need
                        .getCreate_time()));
                ((MyViewHolder) mViewHolder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(context, EditUmbrellaActivity.class);
                        intent.putExtra("phone", need.getCreator_phone());
                        intent.putExtra("id", need.get_id());
                        intent.putExtra("Mode", 1);
                        context.startActivity(intent);
                    }
                });
                switch (need.getSex()) {
                    case "秘密":
                        ((MyViewHolder) mViewHolder).cardView.setBackgroundColor(context
                                .getResources().getColor(R.color.MessageSecret));
                        break;
                    case "男":
                        ((MyViewHolder) mViewHolder).cardView.setBackgroundColor(context
                                .getResources().getColor(R.color.MessageBoy));
                        break;
                    case "女":
                        ((MyViewHolder) mViewHolder).cardView.setBackgroundColor(context
                                .getResources().getColor(R.color.MessageGirl));
                        break;
                    default:
                        break;
                }

            }

        } else if (type == EMPTY_TYPE) {
            ((MyViewHolder) mViewHolder).title.setText("暂时没有内容哦");
        } else if (type == FOOT_TYPE) {
            pbLoading = ((FooterViewHolder) mViewHolder).pbLoading;
            tvLoadMore = ((FooterViewHolder) mViewHolder).tvLoadMore;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mNeedInfos.size() == 0)
            return EMPTY_TYPE;
        else if (position == getItemCount()) {
            return FOOT_TYPE;
        } else
            return CONTENT_TYPE;
    }

    @Override
    public int getItemCount() {
        if (mNeedInfos.size() != 0)
            return mNeedInfos.size();
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView remain_time, location, title;
        private ImageView avatar;
        private CardView cardView;
        private TextView create_time;

        public MyViewHolder(View itemView, int type) {
            super(itemView);
            if (type != EMPTY_TYPE) {
                avatar = (ImageView) itemView.findViewById(R.id.item_avatar);
                remain_time = (TextView) itemView.findViewById(R.id.item_remain_time);
                create_time = (TextView) itemView.findViewById(R.id.item_create_time);
                location = (TextView) itemView.findViewById(R.id.item_location);
                title = (TextView) itemView.findViewById(R.id.item_title);
                cardView = (CardView) itemView.findViewById(R.id.item_CardView);
            } else
                title = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}