package com.walhalla.ytlib.utils

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.walhalla.ytlib.repository.Const

public fun startRegistrationService(activity: Activity) {
    val api = GoogleApiAvailability.getInstance()
    val code = api.isGooglePlayServicesAvailable(activity)
    if (code == ConnectionResult.SUCCESS) {
        //onActivityResult(REQUEST_GOOGLE_PLAY_SERVICES, Activity.RESULT_OK, null);
    } else if (api.isUserResolvableError(code) &&
        api.showErrorDialogFragment(activity, code, Const.REQUEST_GOOGLE_PLAY_SERVICES)
    ) {
        // wait for onActivityResult call (see below)
    } else {
        Toast.makeText(activity, api.getErrorString(code), Toast.LENGTH_LONG).show()
    }
}