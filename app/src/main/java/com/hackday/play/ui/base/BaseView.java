package com.hackday.play.ui.base;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showMyToast(String description);
}
