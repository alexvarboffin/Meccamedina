package com.walhalla.ytlib.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import com.walhalla.ui.DLog
import com.walhalla.ytlib.adapter.ComplexAdapter
import com.walhalla.ytlib.databinding.FragmentVideoListBinding
import com.walhalla.ytlib.domen.ListEntryUI
import com.walhalla.ytlib.mvp.view.ChannelVideosView
import com.walhalla.ytlib.ui.fragments.common.BaseFragment
import com.walhalla.ytlib.utils.Utils

/**
 * Fragment contains main GUI player
 */
class FragmentChannelVideos : BaseFragment(), ChannelVideosView {
    private var binding: FragmentVideoListBinding? = null

    //private String channel_id;
    //private int video_type;
    //Recycler
    private var complexAdapter: ComplexAdapter? = null
    private var context: Context? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.prgLoading?.setColorSchemeResources(
            android.R.color.holo_blue_bright, android.R.color.holo_green_light,
            android.R.color.holo_orange_light, android.R.color.holo_red_light
        )

        binding?.ultimateRecyclerView?.setHasFixedSize(false)


        //org recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        val layoutManager = LinearLayoutManager(getContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        binding!!.ultimateRecyclerView.layoutManager = layoutManager

        val headersDecor = StickyRecyclerHeadersDecoration(
            complexAdapter
        )
        binding!!.ultimateRecyclerView.addItemDecoration(headersDecor)

        // @@       binding.ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(getContext())
// @@               .inflate(R.layout.loadmore_progressbar, null));
        complexAdapter!!.customLoadMoreView = LayoutInflater.from(activity)
            .inflate(com.walhalla.ytlib.R.layout.loadmore_progressbar, null)

        binding!!.ultimateRecyclerView.setOnLoadMoreListener(context as ChannelVideosCallback?)
        binding!!.ultimateRecyclerView.mRecyclerView.addOnItemTouchListener(
            ItemTouchListenerAdapter(
                binding!!.ultimateRecyclerView.mRecyclerView,
                context as ChannelVideosCallback?
            )
        )
        binding!!.ultimateRecyclerView.setAdapter(complexAdapter)
        binding!!.ultimateRecyclerView.reenableLoadmore()
        complexAdapter!!.enableLoadMore(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)


        if (this.complexAdapter == null) {
            this.complexAdapter = ComplexAdapter(activity, ArrayList())
        }

        //            if (getArguments() != null) {
//                video_type = getArguments().getInt(Utils.TAG_VIDEO_TYPE);
//                channel_id = getArguments().getString(Utils.TAG_CHANNEL_ID);
//            }

//            if (savedInstanceState != null) {
//                video_type = savedInstanceState.getInt(Utils.TAG_VIDEO_TYPE);
//                channel_id = savedInstanceState.getString(Utils.TAG_CHANNEL_ID);
//            }
        binding = FragmentVideoListBinding.inflate(inflater, container, false)
        if (complexAdapter == null) {
            this.complexAdapter = ComplexAdapter(activity, ArrayList())
        }
        binding!!.raisedRetry.setOnClickListener { v: View? ->
            haveResultView()
            (context as ChannelVideosCallback).onClickSelectedVideo()
        }
        return binding!!.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == com.walhalla.ytlib.R.id.action_resume) {
            (context as ChannelVideosCallback).onClickSelectedVideo()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    //    @Override
    //    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
    //        super.onViewCreated(view, savedInstanceState);
    //
    //    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            this.context = context
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement HomeActivity")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun disableLoadMore() {
        if (complexAdapter == null) {
            DLog.d("disableLoadMore: sssssssssss")
            return
        }

        if (activity != null) {
            if (binding!!.ultimateRecyclerView.isLoadMoreEnabled) {
                binding!!.ultimateRecyclerView.disableLoadmore()
            }

            /**
             * Have this bug been fixed???
             * UltimateRecyclerView
             */
            //FragmentChannelVideos.this.complexAdapter.notifyDataSetChanged();
        }
    }

    override fun showError(message: String) {
        DLog.d("Error -->: $message")
        activity?.runOnUiThread {
            Utils.showSnackBar(activity, message)
        }
    }

    override fun showLoading() {
//        if (getActivity() != null) {
//            getActivity().runOnUiThread(() -> binding.prgLoading.setVisibility(View.VISIBLE));
//        }
    }

    override fun hideLoading() {
//        if (getActivity() != null) {
//            getActivity().runOnUiThread(() -> binding.prgLoading.setVisibility(View.GONE));
//        }
    }

    override fun showError(err: Int) {
        showError(getString(err))
    }

    override fun haveResultView() {
        binding!!.lytRetry.visibility = View.GONE
        binding!!.ultimateRecyclerView.visibility = View.VISIBLE
        binding!!.lblNoResult.visibility = View.GONE
    }

    override fun retryView() {
        binding!!.lytRetry.visibility = View.VISIBLE
        binding!!.ultimateRecyclerView.visibility = View.GONE
        binding!!.lblNoResult.visibility = View.GONE
    }

    override fun noResultView() {
        binding!!.lytRetry.visibility = View.GONE
        binding!!.ultimateRecyclerView.visibility = View.GONE
        binding!!.lblNoResult.visibility = View.VISIBLE
    }

    override fun onVideoItemSelected(videoId: String) {
        //none
    }


    fun setCustomLoadMoreView(view: View?) {
        complexAdapter!!.customLoadMoreView = view
        complexAdapter!!.notifyDataSetChanged()
    }

    override fun setCustomLoadMoreView(moreView: Int) {
        val view = LayoutInflater.from(activity)
            .inflate(com.walhalla.ytlib.R.layout.loadmore_progressbar, null)
        setCustomLoadMoreView(view)
    }

    override fun swapRecyclerView(uiList: List<ListEntryUI>, position: Int) {
        if (complexAdapter != null) {
            complexAdapter!!.swap(uiList)
            return
        }
        //this.complexAdapter.notifyItemInserted(uiList.size());
        //disableLoadMore();
    }

    override fun onResume() {
        super.onResume()
        var0?.fragmentConnected()
    }
}
