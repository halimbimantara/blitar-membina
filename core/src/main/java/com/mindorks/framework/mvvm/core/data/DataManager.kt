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
package com.mindorks.framework.mvvm.core.data

import com.mindorks.framework.mvvm.core.data.local.db.DbHelper
import com.mindorks.framework.mvvm.core.data.local.prefs.PreferencesHelper
import com.mindorks.framework.mvvm.core.data.remote.ApiHelper
import com.mindorks.framework.mvvm.core.data.model.others.QuestionCardData
import com.mindorks.framework.mvvm.core.data.DataManager.LoggedInMode
import io.reactivex.Observable

/**
 * Created by amitshekhar on 07/07/17.
 */
interface DataManager : DbHelper, PreferencesHelper, ApiHelper {
    val questionCardData: Observable<List<QuestionCardData>>
    fun seedDatabaseOptions(): Observable<Boolean?>?
    fun seedDatabaseQuestions(): Observable<Boolean?>?
    fun setUserAsLoggedOut()
    fun updateApiHeader(userId: Long?, accessToken: String?)
    fun updateUserInfo(
        accessToken: String?,
        userId: Long?,
        loggedInMode: LoggedInMode?,
        userName: String?,
        email: String?,
        profilePicPath: String?
    )

    fun updateUserLogged(
        apiToken: String?,
        userRole: String?,
        userType: String?,
        userId: Long?,
        loggedInMode: LoggedInMode?,
        userName: String?,
        displayName: String?,
        email: String?,
        profilePicPath: String?
    )

    enum class LoggedInMode(val type: Int) {
        LOGGED_IN_MODE_LOGGED_OUT(0), LOGGED_IN_MODE_GOOGLE(1), LOGGED_IN_MODE_SERVER(
            3
        );

    }
}