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
package com.mindorks.framework.mvvm.ui.main

import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.LogoutResponse
import com.mindorks.framework.mvvm.core.data.model.others.QuestionCardData
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider

/**
 * Created by amitshekhar on 07/07/17.
 */
class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator?>(dataManager, schedulerProvider) {
    val appVersion = ObservableField<String>()
    private val questionCardData: MutableLiveData<List<QuestionCardData?>> = MutableLiveData()
    var questionDataList: ObservableList<QuestionCardData?> = ObservableArrayList()
        set(questionCardDatas) {
            action = ACTION_ADD_ALL
            questionDataList.clear()
            questionDataList.addAll(questionCardDatas)
        }
    val userEmail = ObservableField<String?>()
    val userName = ObservableField<String?>()
    val userProfilePicUrl = ObservableField<String?>()
    var action = NO_ACTION
        private set

    fun getQuestionCardData(): LiveData<List<QuestionCardData?>> {
        return questionCardData
    }

    fun loadQuestionCards() {
        compositeDisposable.add(dataManager
            .questionCardData
            .doOnNext { list: List<QuestionCardData?>? ->
                Log.d(
                    TAG,
                    "loadQuestionCards: " + list!!.size
                )
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ questionList: List<QuestionCardData?>? ->
                if (questionList != null) {
                    Log.d(TAG, "loadQuestionCards: " + questionList.size)
                    action = ACTION_ADD_ALL
                    questionCardData.value = questionList
                }
            }) { throwable: Throwable -> Log.d(TAG, "loadQuestionCards: $throwable") })
    }

    fun logout() {
        setIsLoading(true)
        compositeDisposable.add(dataManager.doLogoutApiCall()
            .doOnSuccess { response: LogoutResponse? -> dataManager.setUserAsLoggedOut() }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ response: LogoutResponse? ->
                setIsLoading(false)
                navigator?.openLoginActivity()
            }) { throwable: Throwable? ->
                setIsLoading(false)
                navigator?.handleError(throwable)
            })
    }

    fun onNavMenuCreated() {
        val currentUserName = dataManager.currentUserName
        if (!TextUtils.isEmpty(currentUserName)) {
            userName.set(currentUserName)
        }
        val currentUserEmail = dataManager.currentUserEmail
        if (!TextUtils.isEmpty(currentUserEmail)) {
            userEmail.set(currentUserEmail)
        }
        val profilePicUrl = dataManager.currentUserProfilePicUrl
        if (!TextUtils.isEmpty(profilePicUrl)) {
            userProfilePicUrl.set(profilePicUrl)
        }
    }

    fun removeQuestionCard() {
        action = ACTION_DELETE_SINGLE
//        questionCardData.value.removeAt(0)
    }

    fun updateAppVersion(version: String) {
        appVersion.set(version)
    }

    companion object {
        private const val TAG = "MainViewModel"
        const val NO_ACTION = -1
        const val ACTION_ADD_ALL = 0
        const val ACTION_DELETE_SINGLE = 1
    }

    init {
        loadQuestionCards()
    }
}