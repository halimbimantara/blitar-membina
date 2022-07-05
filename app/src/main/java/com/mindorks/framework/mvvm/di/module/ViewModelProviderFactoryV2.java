package com.mindorks.framework.mvvm.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mindorks.framework.mvvm.core.data.DataManager;
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider;
import com.mindorks.framework.mvvm.di.scope.AppScope;
import com.mindorks.framework.mvvm.ui.feedbuilder.FeedViewModelV2;
import com.mindorks.framework.mvvm.ui.feedbuilder.blogs.BlogViewModel;
import com.mindorks.framework.mvvm.ui.feedbuilder.opensource.OpenSourceViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jyotidubey on 22/02/19.
 */
@AppScope
public class ViewModelProviderFactoryV2 extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactoryV2(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FeedViewModelV2.class)) {
            //noinspection unchecked
            return (T) new FeedViewModelV2(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(OpenSourceViewModel.class)) {
            //noinspection unchecked
            return (T) new OpenSourceViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(BlogViewModel.class)) {
            //noinspection unchecked
            return (T) new BlogViewModel(dataManager, schedulerProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}