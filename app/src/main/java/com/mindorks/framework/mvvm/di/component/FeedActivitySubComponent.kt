package com.mindorks.framework.mvvm.di.component

import com.mindorks.framework.mvvm.ui.feedbuilder.FeedActivityV2
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface FeedActivitySubComponent:AndroidInjector<FeedActivityV2> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<FeedActivityV2>()
}