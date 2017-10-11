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

        void showOtherView(NeedInfo needInfo);

        void showFinishedView();

        void showRunningView();

        void showWaitingView();


        void showEditView();

        void showBrowseView();
    }

    interface Presenter extends BasePresenter {
        void help();
    }

}
