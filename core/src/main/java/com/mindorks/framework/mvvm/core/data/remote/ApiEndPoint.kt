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

import com.mindorks.framework.mvvm.core.BuildConfig

object ApiEndPoint {
    const val ENDPOINT_BLOG = BuildConfig.BASE_URL + "/5926ce9d11000096006ccb30"
    const val ENDPOINT_FACEBOOK_LOGIN = BuildConfig.BASE_URL + "/588d15d3100000ae072d2944"
    const val ENDPOINT_GOOGLE_LOGIN = BuildConfig.BASE_URL + "/588d14f4100000a9072d2943"
    const val ENDPOINT_LOGOUT = BuildConfig.BASE_URL + "/588d161c100000a9072d2946"
    const val ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL + "/5926c34212000035026871cd"
    const val ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL + "/588d15f5100000a8072d2945"


    const val API_ENDPOINT = BuildConfig.BASE_URL + "/blitar-membina/api"
    const val API_ENDPOINT_LOGIN_ACCOUNT = "$API_ENDPOINT/login"
    const val API_ENDPOINT_REGISTER_ACCOUNT = "$API_ENDPOINT/register"
    const val API_ENDPOINT_SERVICE_LIST = "$API_ENDPOINT/service-list"
    const val API_ENDPOINT_CATEGORY_LIST = "$API_ENDPOINT/category-list"
    const val API_ENDPOINT_KEC_LIST = "$API_ENDPOINT/kecamatan-list"
    const val API_ENDPOINT_USER_BOOKING_LIST = "$API_ENDPOINT/user-booking-list"
    const val API_ENDPOINT_ADD_SERVICE = "$API_ENDPOINT/pembina-add-service"
    const val API_ENDPOINT_BOOKED_NOTIFY_LIST = "$API_ENDPOINT/booking-list"
    const val API_ENDPOINT_USER_APPLY = "$API_ENDPOINT/booking-save"
    const val API_ENDPOINT_BOOKING_APPROVAL = "$API_ENDPOINT/approval-booking"
}