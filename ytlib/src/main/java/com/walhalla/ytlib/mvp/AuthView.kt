package com.walhalla.ytlib.mvp

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface AuthView : LoaderView, MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun errorResult(s: String?)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun chooseAccount(mCredential: GoogleAccountCredential?)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int)

    //    @StateStrategyType(AddToEndSingleTagStrategy.class)
    //    void errorResponse(Exception mLastError);
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun successResult(output: String?)
}
