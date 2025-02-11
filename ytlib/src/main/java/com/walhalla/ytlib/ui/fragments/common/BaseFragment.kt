package com.walhalla.ytlib.ui.fragments.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.walhalla.ui.DLog
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    protected var var0: Callback? = null


    override fun onDetach() {
        super.onDetach()
        //var0 = null
    }


    //    @Override
    //    public void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        setHasOptionsMenu(true);
    //    }
    //    @Override
    //    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    //    inflater.inflate(R.menu.menu_calls_fragment, menu);
    //        super.onCreateOptionsMenu(menu, inflater);
    //        Log.d(TAG, "onCreateOptionsMenu: " + vb());
    //    }
    //
    //    @Override
    //    public void onPrepareOptionsMenu(Menu menu) {
    //        super.onPrepareOptionsMenu(menu);
    //
    //        Log.d(TAG, "onPrepareOptionsMenu" + vb());
    //    }
    override fun onAttach(context: Context) {
        super.onAttach(context)


        DLog.d("onAttach: 1" + vb())

        if (context is Callback) {
            var0 = context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement AdvertCallback"
            )
        }
    }

    private fun vb(): String {
        return " :: isVisible -> " + this.isVisible + " " + javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        DLog.d("onCreate: 2" + vb());
    }

    //
    //
    //  From back-stack
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        DLog.d("onCreateView: 3" + vb());
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //        DLog.d("onViewCreated: 4" + vb());
    }


    //    @Override
    //    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    //        super.onActivityCreated(savedInstanceState);
    //        Log.d(TAG, "onCreate: 4"+ vb());
    //        //....initInstances();
    //    }
    //    @Override
    //    public void onStart() {
    //        super.onStart();
    //        Log.d(TAG, "onStart: 5"+ vb());
    //    }
    //
    //    @Override
    //    public void onResume() {
    //        super.onResume();
    //        Log.d(TAG, "onResume: 6"+ vb());
    //    }
    //
    //
    //
    //    @Override
    //    public void onPause() {
    //        super.onPause();
    //        Log.d(TAG, "onPause: 7"+ vb());
    //    }
    //    @Override
    //    public void onStop() {
    //        super.onStop();
    //        Log.d(TAG, "onStop: 8"+ vb());
    //    }
    //
    //    @Override
    //    public void onDestroyView() {
    //        super.onDestroyView();
    //        Log.d(TAG, "onDestroyView: 9"+ vb());
    //    }
    interface Callback {
        //    void setActionBarTitle(String title);
        //
        //    void showBottomBanner(boolean b);
        //
        //    void onClickGetLessonRequest(ZSign zSign);
        fun fragmentConnected()
    }
}