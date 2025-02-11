package com.walhalla.ytlib.mvp.view;

import com.walhalla.ytlib.domen.ListEntryUI;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ChannelVideosView extends MvpView
{

    @StateStrategyType(AddToEndSingleStrategy.class)
    void disableLoadMore();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(String msg);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(int msg);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void haveResultView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void retryView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void noResultView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onVideoItemSelected(String videoId);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setCustomLoadMoreView(Integer view_id);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void swapRecyclerView(List<ListEntryUI> arr, int position);
}
