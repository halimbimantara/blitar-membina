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
package com.mindorks.framework.mvvm.ui.login

import android.text.TextUtils
import android.util.Log
import com.androidnetworking.error.ANError
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import com.mindorks.framework.mvvm.core.utils.CommonUtils
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import org.json.JSONObject

/**
 * Created by amitshekhar on 08/07/17.
 */
class LoginViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<LoginNavigator?>(dataManager, schedulerProvider) {
    fun isEmailAndPasswordValid(email: String?, password: String?): Boolean {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false
        }
        return !TextUtils.isEmpty(password)
    }

    fun login(email: String?, password: String?) {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .login(email!!, password!!)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                //save session
                dataManager.updateUserLogged(
                    it.data?.apiToken,
                    it.data?.userRole?.get(0),
                    it.data?.userType,
                    it.data?.id?.toLong(),
                    DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                    it.data?.username,
                    it.data?.email,
                    it.data?.profileImage
                )


                navigator?.openMainActivity()

                Log.i("login response", "login: ${it.data?.firstName}")
            }) { throwable: Throwable? ->
                setIsLoading(false)
                navigator?.handleError(
                    throwable,
                    JSONObject((throwable as ANError).errorBody.toString()).get("message") as String?
                )
            })
    }

    fun onFbLoginClick() {
        setIsLoading(true)
    }

    fun onGoogleLoginClick() {
        setIsLoading(true)
    }

    fun onServerLoginClick() {
        navigator?.login()
    }
}