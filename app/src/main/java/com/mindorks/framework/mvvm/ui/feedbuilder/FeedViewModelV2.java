package com.mindorks.framework.mvvm.ui.feedbuilder;


import com.mindorks.framework.mvvm.core.data.DataManager;
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;

/**
 * Created by Jyoti on 29/07/17.
 */

public class FeedViewModelV2 extends BaseViewModel {

    public FeedViewModelV2(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
