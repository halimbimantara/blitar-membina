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
package com.mindorks.framework.mvvm.ui.feed.opensource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.OpenSourceResponse
import com.mindorks.framework.mvvm.core.data.model.api.OpenSourceResponse.Repo
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by amitshekhar on 10/07/17.
 */
class OpenSourceViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) : BaseViewModel<OpenSourceNavigator?>(dataManager, schedulerProvider) {
    private val openSourceItemsLiveData: MutableLiveData<List<OpenSourceItemViewModel?>?> =
        MutableLiveData()

    fun fetchRepos() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .openSourceApiCall.map { openSourceResponse: OpenSourceResponse -> openSourceResponse.data }
            .flatMap { repoList: List<Repo?>? -> getViewModelList(repoList) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ openSourceResponse: List<OpenSourceItemViewModel?>? ->
                openSourceItemsLiveData.setValue(openSourceResponse)
                setIsLoading(false)
            }) { throwable: Throwable? ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
    }

    fun getOpenSourceItemsLiveData(): LiveData<List<OpenSourceItemViewModel?>?> {
        return openSourceItemsLiveData
    }

    private fun getViewModelList(repoList: List<Repo?>?): Single<List<OpenSourceItemViewModel?>?> {
        return Observable.fromIterable(repoList)
            .map { repo: Repo? ->
                OpenSourceItemViewModel(
                    repo?.coverImgUrl, repo?.title,
                    repo?.description, repo?.projectUrl
                )
            }.toList()
    }

    init {
        fetchRepos()
    }
}