package com.walhalla.ytlib.mvp

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface LoaderView : MvpView {
    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun progressDialogHide()

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun progressDialogShow()
}
