//package com.walhalla.meccamedina.mvp.presenter;
//
//import android.view.View;
//
//import com.walhalla.Utils;
//import com.walhalla.meccamedina.adapter.ComplexRecyclerViewAdapter;
//import com.walhalla.meccamedina.domen.YT_Entry;
//import com.walhalla.meccamedina.mvp.view.Home_F_View;
//import com.walhalla.ui.DLog;
//
//import java.util.List;
//
//import moxy.InjectViewState;
//import moxy.MvpPresenter;
//
//
//@InjectViewState
//public class Home_F_Presenter extends MvpPresenter<Home_F_View>
//        implements ComplexRecyclerViewAdapter.ChildItemClickListener {
//
//    private List<YT_Entry> arr;
//
//    public Home_F_Presenter() {}
//
//
//    @Override
//    public void onClick(View v, int position) {
//        try {
//            if (arr.get(position) != null) {
//                String[] ids = Utils.getIdList(arr);
//                getViewState().getVideoRequest(ids, position);
//            }
//        } catch (Exception e) {
//            DLog.handleException(e);
//        }
//    }
//}
