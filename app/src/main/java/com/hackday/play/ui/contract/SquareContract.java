package com.hackday.play.ui.contract;

import com.hackday.play.data.NeedInfo;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.ui.base.BaseView;

import java.util.List;

/**
 * Created by victor on 10/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface SquareContract {

    interface View extends BaseView<Presenter> {
        void showList(List<NeedInfo> needInfos);

        int getStatus();
    }

    interface Presenter extends BasePresenter {
    }

}
