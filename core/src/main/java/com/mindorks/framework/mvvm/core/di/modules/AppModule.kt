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
package com.mindorks.framework.mvvm.core.di.modules

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mindorks.framework.mvvm.core.BuildConfig
import com.mindorks.framework.mvvm.core.R
import com.mindorks.framework.mvvm.core.data.AppDataManager
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.local.db.AppDatabase
import com.mindorks.framework.mvvm.core.data.local.db.AppDbHelper
import com.mindorks.framework.mvvm.core.data.local.db.DbHelper
import com.mindorks.framework.mvvm.core.data.local.prefs.AppPreferencesHelper
import com.mindorks.framework.mvvm.core.data.local.prefs.PreferencesHelper
import com.mindorks.framework.mvvm.core.data.remote.ApiHeader.ProtectedApiHeader
import com.mindorks.framework.mvvm.core.data.remote.ApiHelper
import com.mindorks.framework.mvvm.core.data.remote.AppApiHelper
import com.mindorks.framework.mvvm.core.di.ApiInfo
import com.mindorks.framework.mvvm.core.di.DatabaseInfo
import com.mindorks.framework.mvvm.core.di.PreferenceInfo
import com.mindorks.framework.mvvm.core.utils.AppConstants
import com.mindorks.framework.mvvm.core.utils.rx.AppSchedulerProvider
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

/**
 * Created by amitshekhar on 07/07/17.
 */
@Module
class AppModule {

    @Provides
    @Reusable
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @ApiInfo
    fun provideApiKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }


    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    fun provideProtectedApiHeader(
        @ApiInfo apiKey: String,
        preferencesHelper: PreferencesHelper
    ): ProtectedApiHeader {
        return ProtectedApiHeader(
            apiKey,
            preferencesHelper.currentUserId,
            preferencesHelper.accessToken
        )
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}