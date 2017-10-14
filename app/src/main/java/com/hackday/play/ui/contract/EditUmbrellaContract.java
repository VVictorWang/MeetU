package com.hackday.play.ui.contract;

import com.hackday.play.data.NeedInfo;
import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.ui.base.BaseView;

/**
 * Created by victor on 10/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface EditUmbrellaContract {

    interface View extends BaseView<Presenter> {
        int getMode();

        String getCreator();

        String getNeedID();

        void showHelpView(NeedInfo needInfo);

        void showFinishedView(NeedInfo needInfo,boolean flag);

        void showRunningView(NeedInfo needInfo);

        void showWaitingView(NeedInfo needInfo);

        void showProgressDialog();

        void dismissProgressDialog();

        void setBackGround(int sex);

        void showEditView();

    }

    interface Presenter extends BasePresenter {
        void help();
    }

}
