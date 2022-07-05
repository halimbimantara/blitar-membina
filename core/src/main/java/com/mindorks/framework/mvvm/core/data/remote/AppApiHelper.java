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

package com.mindorks.framework.mvvm.core.data.remote;

import androidx.annotation.NonNull;

import com.androidnetworking.common.Priority;
import com.mindorks.framework.mvvm.core.data.local.prefs.AppPreferencesHelper;
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.core.data.model.api.LoginRequest;
import com.mindorks.framework.mvvm.core.data.model.api.LoginResponse;
import com.mindorks.framework.mvvm.core.data.model.api.LogoutResponse;
import com.mindorks.framework.mvvm.core.data.model.api.OpenSourceResponse;
import com.mindorks.framework.mvvm.core.data.model.api.response.LoginResponseApi;
import com.mindorks.framework.mvvm.core.data.model.api.service.BookedResponse;
import com.mindorks.framework.mvvm.core.data.model.api.service.ServiceResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private AppPreferencesHelper appDataManager;

    @Inject
    public AppApiHelper(ApiHeader apiHeader, AppPreferencesHelper manager) {
        mApiHeader = apiHeader;
        appDataManager = manager;
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(LogoutResponse.class);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(BlogResponse.class);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }

    @Override
    public Single<LoginResponseApi> login(String email, String pwd) {
        JSONObject mLogin = new JSONObject();
        try {
            mLogin.put("email", email);
            mLogin.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Rx2AndroidNetworking.post(ApiEndPoint.API_ENDPOINT_LOGIN_ACCOUNT)
                .addJSONObjectBody(mLogin)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectSingle(LoginResponseApi.class);
    }

    @NonNull
    @Override
    public Observable<ServiceResponse> serviceList() {
        return Rx2AndroidNetworking.get(ApiEndPoint.API_ENDPOINT_SERVICE_LIST)
                .build()
                .getObjectObservable(ServiceResponse.class);
    }

    @NonNull
    @Override
    public Single<BookedResponse> notifyBookedList() {
        return Rx2AndroidNetworking.get(ApiEndPoint.API_ENDPOINT_BOOKED_NOTIFY_LIST)
                .addHeaders("Authorization", "Bearer " + appDataManager.getAccessToken())
                .build()
                .getObjectSingle(BookedResponse.class);
    }
}
