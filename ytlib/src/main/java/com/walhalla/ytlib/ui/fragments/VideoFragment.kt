package com.walhalla.ytlib.ui.fragments


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

import com.walhalla.ui.DLog
import com.walhalla.ytlib.newapi.YouTubePlayerFragment
import com.walhalla.ytlib.repository.Const

//import com.google.android.youtube.player.YouTubePlayerFragment; //deprecated
class VideoFragment : YouTubePlayerFragment() {
    private val mListener9: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
        override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
            super.onError(youTubePlayer, error)
            if (helper != null) {
                helper!!.onInitializationFailure(error)
            }
        }

        override fun onReady(youTubePlayer: YouTubePlayer) {
            __player = youTubePlayer


            //__player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);// Hiding player controls

            //@@ __player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);//With youtube controls
            //~~~__player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);//With youtube controls


//            __player.addFullscreenControlFlag(
//                    //YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT
//                    YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
//            );
//            __player.setOnFullscreenListener((YouTubePlayer.OnFullscreenListener) getActivity());
//
//            __player.setPlayerStateChangeListener(aaaa);
//            __player.setPlaybackEventListener(eventListener);
//            __player.setPlaylistEventListener(playlistEventListener);
            if ( /*!wasRestored && */videoId != null) {
                try {
                    if (Const.AUTOPLAY) {
                        __player!!.loadVideo(videoId!!, 0f)
                    } else {
                        __player!!.cueVideo(videoId!!, 0f)
                    }
                } catch (e: Exception) {
                    if (helper != null) {
                        helper!!.errorResponse(e)
                    }
                }
            }
        }
    }

    fun backnormal() {
        __player!!.toggleFullscreen() // if the player is in fullscreen, exit fullscreen
    }

    interface VideoFragmentCallback : FullscreenListener {
        fun errorResponse(e: Exception)

        fun onInitializationFailure(errorReason: PlayerConstants.PlayerError)
    }

    private var helper: VideoFragmentCallback? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            this.helper = context as VideoFragmentCallback
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement VideoFragmentCallback")
        }
    }

    private var __player: YouTubePlayer? = null
    private var videoId: String? = null

    override fun onResume() {
        super.onResume()
        if (__player == null) {
            initPlayer()
        }
    }

    private fun initPlayer() {
        //DLog.d("============================");
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //c = view.findViewById(R.id.youtube_player_view);

        //not use==> c.addYouTubePlayerListener(mListener9);
        val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .rel(0)
            .ivLoadPolicy(0)
            .ccLoadPolicy(0)
            .fullscreen(1)
            .build()
        binding?.youtubePlayerView?.enableAutomaticInitialization = false
        binding?.youtubePlayerView?.addFullscreenListener(helper!!)
        binding?.youtubePlayerView?.initialize(mListener9, iFramePlayerOptions)
        binding?.let { lifecycle.addObserver(it.youtubePlayerView) }
    }

    override fun onPause() {
        super.onPause()
        //        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//            if (__player != null) {
//                __player.release();
//                __player = null;
//            }
//        }
        //DLog.d("-->" + isMyServiceRunning("YouTubeService"));
    }

    override fun onStop() {
        super.onStop()
        //        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            if (__player != null) {
//                __player.release();
//                __player = null;
//            }
//        }
        //DLog.d("-->" + isMyServiceRunning("YouTubeService"));
    }

    fun setVideoId(newValue: String) {
        if (TextUtils.isEmpty(newValue) || newValue == this.videoId) {
            return
        }


        this.videoId = newValue

        DLog.d("{{{{{{}}}}}}" + this.videoId + " @@@ " + (__player == null))

        if (this.__player != null) {
            DLog.d("@@@@@@@@@@@@@@@@@@@@@" + this.videoId)
            try {
                if (Const.AUTOPLAY) {
                    __player!!.loadVideo(videoId!!, 0f)
                } else {
                    __player!!.cueVideo(videoId!!, 0f)
                }
                //this.player.play();
            } catch (e: IllegalStateException) {
                DLog.handleException(e)
                initPlayer()
            }
        }
    }

    companion object {
        fun newInstance(): VideoFragment {
            return VideoFragment()
        }
    }
}

