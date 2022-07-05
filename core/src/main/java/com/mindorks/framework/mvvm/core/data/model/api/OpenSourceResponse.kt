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
class OpenSourceResponse {
    @Expose
    @SerializedName("data")
    var data: List<Repo>? = null

    @Expose
    @SerializedName("message")
    var message: String? = null

    @Expose
    @SerializedName("status_code")
    var statusCode: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is OpenSourceResponse) {
            return false
        }
        val that = o
        if (statusCode != that.statusCode) {
            return false
        }
        if (message != that.message) {
            return false
        }
        return if (data != null) data == that.data else that.data == null
    }

    override fun hashCode(): Int {
        var result = statusCode.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + if (data != null) data.hashCode() else 0
        return result
    }

    class Repo {
        @Expose
        @SerializedName("img_url")
        val coverImgUrl: String? = null

        @Expose
        @SerializedName("description")
        val description: String? = null

        @Expose
        @SerializedName("project_url")
        val projectUrl: String? = null

        @Expose
        @SerializedName("title")
        val title: String? = null
        override fun equals(o: Any?): Boolean {
            if (this === o) {
                return true
            }
            if (o !is Repo) {
                return false
            }
            val repo = o
            if (projectUrl != repo.projectUrl) {
                return false
            }
            if (coverImgUrl != repo.coverImgUrl) {
                return false
            }
            return if (title != repo.title) {
                false
            } else description == repo.description
        }

        override fun hashCode(): Int {
            var result = projectUrl.hashCode()
            result = 31 * result + coverImgUrl.hashCode()
            result = 31 * result + title.hashCode()
            result = 31 * result + description.hashCode()
            return result
        }
    }
}