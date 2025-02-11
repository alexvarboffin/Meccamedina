package com.walhalla.ytlib.mvp.view;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface Home_F_View extends MvpView {
    void showData(List<Object> arr);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(int error);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(String s);

//    @StateStrategyType(SkipStrategy.class)
//    void getVideoRequest(String[] yt_entry, int position);
}
