package ai.meccamedinatv.mekkalive.online.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleTagStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleTagStrategy.class)
public interface LoaderView extends MvpView
{

    @StateStrategyType(AddToEndSingleTagStrategy.class)
    void progressDialogHide();

    @StateStrategyType(AddToEndSingleTagStrategy.class)
    void progressDialogShow();
}
