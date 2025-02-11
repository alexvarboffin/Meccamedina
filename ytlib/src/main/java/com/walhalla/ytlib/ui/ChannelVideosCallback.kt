package com.walhalla.ytlib.ui

import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView

interface ChannelVideosCallback
    : ItemTouchListenerAdapter.RecyclerViewOnItemClickListener,
    UltimateRecyclerView.OnLoadMoreListener {


    fun onClickSelectedVideo()

    fun onVideoSelected(video_id: String)
}