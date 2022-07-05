package com.mindorks.framework.mvvm.di.component;

import com.mindorks.framework.mvvm.ui.feedbuilder.FeedActivityModule;
import com.mindorks.framework.mvvm.ui.feedbuilder.FeedActivityV2;
import com.mindorks.framework.mvvm.ui.feedbuilder.blogs.BlogFragmentProvider;
import com.mindorks.framework.mvvm.ui.feedbuilder.opensource.OpenSourceFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {
            FeedActivityModule.class,
            BlogFragmentProvider.class,
            OpenSourceFragmentProvider.class
    }
    )
    abstract FeedActivityV2 bindFeedActivityV2();
}
