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
package com.mindorks.framework.mvvm.core.data.remote

import com.mindorks.framework.mvvm.core.data.model.api.LoginRequest.FacebookLoginRequest
import com.mindorks.framework.mvvm.core.data.model.api.LoginResponse
import com.mindorks.framework.mvvm.core.data.model.api.LoginRequest.GoogleLoginRequest
import com.mindorks.framework.mvvm.core.data.model.api.LogoutResponse
import com.mindorks.framework.mvvm.core.data.model.api.LoginRequest.ServerLoginRequest
import com.mindorks.framework.mvvm.core.data.remote.ApiHeader
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse
import com.mindorks.framework.mvvm.core.data.model.api.OpenSourceResponse
import com.mindorks.framework.mvvm.core.data.model.api.response.LoginResponseApi
import com.mindorks.framework.mvvm.core.data.model.api.service.BookedResponse
import com.mindorks.framework.mvvm.core.data.model.api.service.ServiceResponse
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by amitshekhar on 07/07/17.
 */
interface ApiHelper {
    fun doFacebookLoginApiCall(request: FacebookLoginRequest): Single<LoginResponse>
    fun doGoogleLoginApiCall(request: GoogleLoginRequest): Single<LoginResponse>
    fun doLogoutApiCall(): Single<LogoutResponse>
    fun doServerLoginApiCall(request: ServerLoginRequest): Single<LoginResponse>
    val apiHeader: ApiHeader
    val blogApiCall: Single<BlogResponse>
    val openSourceApiCall: Single<OpenSourceResponse>
    fun login(email: String, pwd: String): Single<LoginResponseApi>

    fun serviceList(): Observable<ServiceResponse>
    fun notifyBookedList(): Single<BookedResponse>
}