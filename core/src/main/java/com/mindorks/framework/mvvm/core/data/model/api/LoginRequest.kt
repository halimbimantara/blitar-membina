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
package com.mindorks.framework.mvvm.core.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by amitshekhar on 07/07/17.
 */
class LoginRequest private constructor() {
    class FacebookLoginRequest(
        @field:SerializedName("fb_user_id") @field:Expose val fbUserId: String?,
        @field:SerializedName(
            "fb_access_token"
        ) @field:Expose val fbAccessToken: String?
    ) {

        override fun equals(`object`: Any?): Boolean {
            if (this === `object`) {
                return true
            }
            if (`object` == null || javaClass != `object`.javaClass) {
                return false
            }
            val that = `object` as FacebookLoginRequest
            if (if (fbUserId != null) fbUserId != that.fbUserId else that.fbUserId != null) {
                return false
            }
            return if (fbAccessToken != null) fbAccessToken == that.fbAccessToken else that.fbAccessToken == null
        }

        override fun hashCode(): Int {
            var result = fbUserId?.hashCode() ?: 0
            result = 31 * result + (fbAccessToken?.hashCode() ?: 0)
            return result
        }
    }

    class GoogleLoginRequest(
        @field:SerializedName("google_user_id") @field:Expose val googleUserId: String?,
        @field:SerializedName(
            "google_id_token"
        ) @field:Expose val idToken: String?
    ) {

        override fun equals(`object`: Any?): Boolean {
            if (this === `object`) {
                return true
            }
            if (`object` == null || javaClass != `object`.javaClass) {
                return false
            }
            val that = `object` as GoogleLoginRequest
            if (if (googleUserId != null) googleUserId != that.googleUserId else that.googleUserId != null) {
                return false
            }
            return if (idToken != null) idToken == that.idToken else that.idToken == null
        }

        override fun hashCode(): Int {
            var result = googleUserId?.hashCode() ?: 0
            result = 31 * result + (idToken?.hashCode() ?: 0)
            return result
        }
    }

    class ServerLoginRequest(
        @field:SerializedName("email") @field:Expose val email: String?, @field:SerializedName(
            "password"
        ) @field:Expose val password: String?
    ) {

        override fun equals(`object`: Any?): Boolean {
            if (this === `object`) {
                return true
            }
            if (`object` == null || javaClass != `object`.javaClass) {
                return false
            }
            val that = `object` as ServerLoginRequest
            if (if (email != null) email != that.email else that.email != null) {
                return false
            }
            return if (password != null) password == that.password else that.password == null
        }

        override fun hashCode(): Int {
            var result = email?.hashCode() ?: 0
            result = 31 * result + (password?.hashCode() ?: 0)
            return result
        }
    }
}