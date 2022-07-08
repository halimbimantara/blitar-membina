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
package com.mindorks.framework.mvvm.ui.account.login.register

import android.util.Log
import com.androidnetworking.error.ANError
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.Register.email
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.Register.password
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandler
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandling
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandlingTag
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.core.utils.rx.SingleLiveEvent
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import org.json.JSONObject

/**
 * Created by amitshekhar on 08/07/17.
 */
class RegisterViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {
    val errorData = SingleLiveEvent<NetworkErrorHandlingTag>()
    val successRegister = SingleLiveEvent<Boolean>()
    fun postRegister(
        firstName: String,
        lastName: String,
        gender: String,
        userType: String,
        email: String,
        username: String,
        pwd: String,
    ) {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .register(firstName, lastName, gender, userType, email, username, pwd)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                if (it.data != null) {
                    //passing login page
                    successRegister.postValue(true)
                } else {
                    successRegister.postValue(false)
                }
            }) {
                errorData.postValue(
                    NetworkErrorHandling.handleNetworkThrowableWithTag(
                        it,
                        it.message.toString()
                    )
                )
                setIsLoading(false)
            })
    }
}