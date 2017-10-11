package com.hackday.play.ui.contract;

import com.hackday.play.ui.base.BasePresenter;
import com.hackday.play.ui.base.BaseView;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showEditView();
    }

    interface Presenter extends BasePresenter {

    }

}
