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

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mindorks.framework.mvvm.core.data.DataManager.LoggedInMode
import com.mindorks.framework.mvvm.core.data.local.db.DbHelper
import com.mindorks.framework.mvvm.core.data.local.prefs.PreferencesHelper
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse
import com.mindorks.framework.mvvm.core.data.model.api.LoginRequest.*
import com.mindorks.framework.mvvm.core.data.model.api.LoginResponse
import com.mindorks.framework.mvvm.core.data.model.api.LogoutResponse
import com.mindorks.framework.mvvm.core.data.model.api.OpenSourceResponse
import com.mindorks.framework.mvvm.core.data.model.api.response.LoginResponseApi
import com.mindorks.framework.mvvm.core.data.model.api.service.BookedResponse
import com.mindorks.framework.mvvm.core.data.model.api.service.ServiceResponse
import com.mindorks.framework.mvvm.core.data.model.db.Option
import com.mindorks.framework.mvvm.core.data.model.db.Question
import com.mindorks.framework.mvvm.core.data.model.db.User
import com.mindorks.framework.mvvm.core.data.model.others.QuestionCardData
import com.mindorks.framework.mvvm.core.data.remote.ApiHeader
import com.mindorks.framework.mvvm.core.data.remote.ApiHelper
import com.mindorks.framework.mvvm.core.utils.AppConstants
import com.mindorks.framework.mvvm.core.utils.CommonUtils.loadJSONFromAsset
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by amitshekhar on 07/07/17.
 */
@Singleton
class AppDataManager @Inject constructor(
    private val mContext: Context,
    private val mDbHelper: DbHelper,
    private val mPreferencesHelper: PreferencesHelper,
    private val mApiHelper: ApiHelper,
    private val mGson: Gson
) : DataManager {
    override fun doFacebookLoginApiCall(request: FacebookLoginRequest): Single<LoginResponse> {
        return mApiHelper.doFacebookLoginApiCall(request)
    }

    override fun doGoogleLoginApiCall(request: GoogleLoginRequest): Single<LoginResponse> {
        return mApiHelper.doGoogleLoginApiCall(request)
    }

    override fun doLogoutApiCall(): Single<LogoutResponse> {
        return mApiHelper.doLogoutApiCall()
    }

    override fun doServerLoginApiCall(request: ServerLoginRequest): Single<LoginResponse> {
        return mApiHelper.doServerLoginApiCall(request)
    }

    override val apiHeader: ApiHeader
        get() = mApiHelper.apiHeader

    override val blogApiCall: Single<BlogResponse>
        get() = mApiHelper.blogApiCall
    override val openSourceApiCall: Single<OpenSourceResponse>
        get() = mApiHelper.openSourceApiCall

    override fun getAccessToken(): String {
        return mPreferencesHelper.accessToken
    }

    override fun setAccessToken(accessToken: String?) {
        mPreferencesHelper.accessToken = accessToken
        mApiHelper.apiHeader.protectedApiHeader.accessToken = accessToken
    }

    override val allQuestions: Observable<List<Question>>
        get() = mDbHelper.allQuestions
    override val allUsers: Observable<List<User>>
        get() = mDbHelper.allUsers


    override fun getCurrentUserEmail(): String {
        return mPreferencesHelper.currentUserEmail
    }

    override fun setCurrentUserEmail(email: String) {
        mPreferencesHelper.currentUserEmail = email
    }

    override fun getCurrentUserId(): Long {
        return mPreferencesHelper.currentUserId
    }

    override fun setCurrentUserId(userId: Long) {
        mPreferencesHelper.currentUserId = userId
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return mPreferencesHelper.currentUserLoggedInMode
    }

    override fun setCurrentUserLoggedInMode(mode: LoggedInMode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode)
    }

    override fun getCurrentUserName(): String {
        return mPreferencesHelper.currentUserName
    }

    override fun setCurrentUserName(userName: String) {
        mPreferencesHelper.currentUserName = userName
    }

    override fun getCurrentUserProfilePicUrl(): String {
        return mPreferencesHelper.currentUserProfilePicUrl
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String) {
        mPreferencesHelper.currentUserProfilePicUrl = profilePicUrl
    }

    override fun getUserTypes(): String {
        return mPreferencesHelper.userTypes
    }

    override fun getUserRole(): String {
        return mPreferencesHelper.userRole
    }

    override fun setUserRole(userRole: String) {
        mPreferencesHelper.userRole = userRole
    }

    override fun setUserTypes(userTypes: String?) {
        mPreferencesHelper.userTypes = userTypes
    }


    override fun login(email: String, pwd: String): Single<LoginResponseApi> {
        return mApiHelper.login(email, pwd)
    }

    override fun serviceList(): Observable<ServiceResponse> {
        return mApiHelper.serviceList()
    }

    override fun notifyBookedList(): Single<BookedResponse> {
        return mApiHelper.notifyBookedList()
    }

    override fun insertUser(user: User): Observable<Boolean> {
        return mDbHelper.insertUser(user)
    }

    override val isOptionEmpty: Observable<Boolean>
        get() = mDbHelper.isOptionEmpty
    override val isQuestionEmpty: Observable<Boolean>
        get() = mDbHelper.isQuestionEmpty

    override fun saveOption(option: Option): Observable<Boolean> {
        return mDbHelper.saveOption(option)
    }

    override fun saveOptionList(optionList: List<Option>): Observable<Boolean> {
        return mDbHelper.saveOptionList(optionList)
    }

    override fun saveQuestion(question: Question): Observable<Boolean> {
        return mDbHelper.saveQuestion(question)
    }

    override fun saveQuestionList(questionList: List<Question>): Observable<Boolean> {
        return mDbHelper.saveQuestionList(questionList)
    }

    override val questionCardData: Observable<List<QuestionCardData>>
        get() = mDbHelper.allQuestions
            .flatMap { questions: List<Question>? -> Observable.fromIterable(questions) }
            .flatMap { question: Question ->
                Observable.zip<List<Option?>, Question, QuestionCardData>(
                    mDbHelper.getOptionsForQuestionId(question.id!!),
                    Observable.just(question)
                ) { options: List<Option?>?, question1: Question? ->
                    QuestionCardData(
                        question1,
                        options
                    )
                }
            }
            .toList()
            .toObservable()

    override fun seedDatabaseOptions(): Observable<Boolean?> {
        return mDbHelper.isOptionEmpty
            .concatMap { isEmpty: Boolean ->
                if (isEmpty) {
                    val type = object : TypeToken<List<Option?>?>() {}.type
                    val optionList = mGson.fromJson<List<Option>>(
                        loadJSONFromAsset(
                            mContext,
                            AppConstants.SEED_DATABASE_OPTIONS
                        ), type
                    )
                    return@concatMap saveOptionList(optionList)
                }
                Observable.just(false)
            }
    }

    override fun seedDatabaseQuestions(): Observable<Boolean?> {
        return if (mPreferencesHelper.currentUserId != null) Observable.just(
            true
        ) else Observable.just(false)
    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(
            null,
            null,
            LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
            null,
            null,
            null
        )
    }


    override fun updateApiHeader(userId: Long?, accessToken: String?) {
        mApiHelper.apiHeader.protectedApiHeader.userId = userId
        mApiHelper.apiHeader.protectedApiHeader.accessToken = accessToken
    }

    override fun updateUserInfo(
        accessToken: String?,
        userId: Long?,
        loggedInMode: LoggedInMode?,
        userName: String?,
        email: String?,
        profilePicPath: String?
    ) {
        setAccessToken(accessToken!!)
        currentUserId = userId!!
        setCurrentUserLoggedInMode(loggedInMode!!)
        currentUserName = userName!!
        currentUserEmail = email!!
        currentUserProfilePicUrl = profilePicPath!!
        updateApiHeader(userId, accessToken)
    }

    override fun updateUserLogged(
        apiToken: String?,
        userRole: String?,
        userType: String?,
        userId: Long?,
        loggedInMode: LoggedInMode?,
        userName: String?,
        email: String?,
        profilePicPath: String?
    ) {
        accessToken = apiToken!!
        currentUserId = userId!!
        setCurrentUserLoggedInMode(loggedInMode!!)
        currentUserName = userName!!
        currentUserEmail = email!!
        currentUserProfilePicUrl = profilePicPath!!
        setUserRole(userRole!!)
        setUserTypes(userType)
    }

    override fun getOptionsForQuestionId(questionId: Long): Observable<List<Option>> {
        return mDbHelper.getOptionsForQuestionId(questionId)
    }

    companion object {
        private const val TAG = "AppDataManager"
    }
}