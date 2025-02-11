package com.walhalla.ytlib.repository

import android.view.View

object Const{

    const val AUTOPLAY = true


    const val RSS_URL = "https://www.youtube.com/feeds/videos.xml?channel_id="
    const val VIDEO_LIMIT = 30L




    const val LANDSCAPE_VIDEO_PADDING_DP = 5
    const val RECOVERY_DIALOG_REQUEST = 123


    fun setLayoutSize(view: View, width: Int, height: Int) {
        val params = view.layoutParams
        params.width = width
        params.height = height
        view.layoutParams = params
    }


    const val REQUEST_AUTHORIZATION: Int = 1001
    const val REQUEST_GOOGLE_PLAY_SERVICES: Int = 1002


    const val REQUEST_ACCOUNT_PICKER: Int = 1000
    const val REQUEST_PERMISSION_GET_ACCOUNTS_: Int = 1003

    const val PREF_ACCOUNT_NAME = "accountName"
}
