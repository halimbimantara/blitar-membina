/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.mindorks.framework.mvvm.ui.feed.blogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse.Blog
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider

/**
 * Created by amitshekhar on 10/07/17.
 */
class BlogViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) : BaseViewModel<BlogNavigator?>(dataManager, schedulerProvider) {
    private val blogListLiveData: MutableLiveData<List<Blog?>?> = MutableLiveData()
    fun fetchBlogs() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .blogApiCall
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ blogResponse: BlogResponse? ->
                if (blogResponse?.data != null) {
                    blogListLiveData.setValue(blogResponse.data)
                }
                setIsLoading(false)
            }) { throwable: Throwable? ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
    }

    fun getBlogListLiveData(): LiveData<List<Blog?>?> {
        return blogListLiveData
    }

    init {
        fetchBlogs()
    }
}