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
package com.mindorks.framework.mvvm.ui.splash

import android.os.Handler
import android.os.Looper
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.DataManager.LoggedInMode
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseViewModel

/**
 * Created by amitshekhar on 08/07/17.
 */
class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator?>(dataManager, schedulerProvider) {
    fun startSeeding() {
        setIsLoading(true)
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            decideNextActivity()
        }, 3000)
    }

    private fun decideNextActivity() {
        if (dataManager.currentUserLoggedInMode == LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type) {
            navigator?.openLoginActivity()
        } else {
            navigator?.openMainActivity()
        }
        setIsLoading(false)
    }
}