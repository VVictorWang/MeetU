package com.hackday.play.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackday.play.R;
import com.hackday.play.api.UserApi;
import com.hackday.play.data.NeedInfo;
import com.hackday.play.data.StatusInfo;
import com.hackday.play.ui.activity.EditUmbrellaActivity;
import com.hackday.play.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

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

    public void setLocationInforList(List<NeedInfo> needInfos, boolean sort) {
        mNeedInfos.clear();
        if (sort) {
            Observable.from(needInfos).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toSortedList(new Func2<NeedInfo, NeedInfo, Integer>() {
                        @Override
                        public Integer call(NeedInfo needInfo, NeedInfo needInfo2) {
                            if (needInfo.getCreate_time() < needInfo2.getCreate_time()) {
                                return 1;
                            }
                            return -1;
                        }
                    }).subscribe(new Observer<List<NeedInfo>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<NeedInfo> needInfos) {
                    mNeedInfos.addAll(needInfos);
                    notifyDataSetChanged();
                }
            });
        } else {
            mNeedInfos.addAll(needInfos);
            notifyDataSetChanged();
        }
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
                ((MyViewHolder) mViewHolder).remain_time.setText(need.getContinue_time() + "内");
                ((MyViewHolder) mViewHolder).title.setText(need.getDesc());
                ((MyViewHolder) mViewHolder).create_time.setText(Utils.formatChineseDate(need
                        .getCreate_time()));
                ((MyViewHolder) mViewHolder).cardView.setOnClickListener(new View.OnClickListener
                        () {
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
                ((MyViewHolder) mViewHolder).cardView.setOnLongClickListener(new View
                        .OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog2,
                                null);
                        ((TextView) view.findViewById(R.id.alert_dialog_text)).setText("真的要删除吗?");
                        Button positive = (Button) view.findViewById(R.id.alert_dialog_positive);
                        Button cancel = (Button) view.findViewById(R.id.alert_dialog_negative);
                        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view)
                                .create();
                        dialog.show();
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Observable<StatusInfo> observable = UserApi.getInstance()
                                        .deleteNeed(need.get_id(), Utils.getToken());
                                observable.observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new Observer<StatusInfo>() {
                                            @Override
                                            public void onCompleted() {
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(StatusInfo statusInfo) {
                                                if (statusInfo.getStatus() == 1) {
                                                    Toast.makeText(context, "删除成功", Toast
                                                            .LENGTH_SHORT);
                                                    mNeedInfos.remove(need);
                                                    notifyDataSetChanged();
                                                }
                                            }
                                        });
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        return true;
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
        else if (position == getItemCount() - 1) {
            return FOOT_TYPE;
        } else
            return CONTENT_TYPE;
    }

    @Override
    public int getItemCount() {
        if (mNeedInfos.size() != 0)
            return mNeedInfos.size() + 1;
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