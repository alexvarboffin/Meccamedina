package com.walhalla.ytlib.newapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.walhalla.ytlib.databinding.FragmentPlayerBinding


open class YouTubePlayerFragment : Fragment() {

    protected var binding: FragmentPlayerBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //this.c = new binding.youtubePlayerView(this.getActivity());return this.c;

        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding?.youtubePlayerView?.release()
        binding = null
        super.onDestroyView()
    }

    fun onEnterFullScreen(fullscreenView: View?) {
        binding!!.youtubePlayerView.visibility = View.GONE
        binding!!.fullScreenViewContainer.visibility = View.VISIBLE
        binding!!.fullScreenViewContainer.addView(fullscreenView)
    }

    fun onExitFullscreen() {
        //isFullscreen = false
        binding!!.youtubePlayerView.visibility = View.VISIBLE
        binding!!.fullScreenViewContainer.visibility = View.GONE
        binding!!.fullScreenViewContainer.removeAllViews()
    }

    companion object {
        //protected String videoId;
        fun newInstance(): YouTubePlayerFragment {
            return YouTubePlayerFragment()
        }
    }
}
