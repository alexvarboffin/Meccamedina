package com.walhalla.ytlib.home;

import android.content.Intent;

import com.walhalla.ytlib.domen.ListEntryUI;
import com.walhalla.ytlib.mvp.AuthView;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

/*
 * В настоящее время стратегией по умолчанию является AddToEndStrategy.
 * */

//@StateStrategyType(AddToEndStrategy.class) //Дефолтная опция работает
@StateStrategyType(AddToEndSingleStrategy.class)//Не сработает RecyclerView
public interface HomeView extends AuthView {

    //@StateStrategyType(AddToEndSingleStrategy.class)
    //@StateStrategyType(OneExecutionStateStrategy.class)//Не сработает RecyclerView
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showData(List<ListEntryUI> arr);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(int error);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(String tag, String s);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void getVideoRequest(String[] yt_entry, int position);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDrawerMenuList(List<ListEntryUI> entry);

    @StateStrategyType(AddToEndStrategy.class)
    void renderVideoList(List<ListEntryUI> listEntries, int position);


    @StateStrategyType(AddToEndSingleStrategy.class)
    void disableLoadMore(String tag);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void haveResultView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void retryView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void noResultView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onVideoItemSelected(String s);


    @StateStrategyType(AddToEndSingleStrategy.class)
    void setCustomLoadMoreView(Integer view_id);


    @StateStrategyType(AddToEndSingleStrategy.class)
    void swapRecyclerView(List<ListEntryUI> arr, int position);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showVideoFragment(int type, String id);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void startActivityForResult(Intent intent, int requestAuthorization);
}

