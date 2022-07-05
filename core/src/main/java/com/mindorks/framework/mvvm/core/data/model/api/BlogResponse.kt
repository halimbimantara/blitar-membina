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
class BlogResponse {
    @Expose
    @SerializedName("data")
    val data: List<Blog>? = null

    @Expose
    @SerializedName("message")
    val message: String? = null

    @Expose
    @SerializedName("status_code")
    val statusCode: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is BlogResponse) {
            return false
        }
        val that = o
        if (statusCode != that.statusCode) {
            return false
        }
        return if (message != that.message) {
            false
        } else data == that.data
    }

    override fun hashCode(): Int {
        var result = statusCode.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }

    class Blog {
        @Expose
        @SerializedName("author")
        val author: String? = null

        @Expose
        @SerializedName("blog_url")
        val blogUrl: String? = null

        @Expose
        @SerializedName("img_url")
        val coverImgUrl: String? = null

        @Expose
        @SerializedName("published_at")
        val date: String? = null

        @Expose
        @SerializedName("description")
        val description: String? = null

        @Expose
        @SerializedName("title")
        val title: String? = null
        override fun equals(o: Any?): Boolean {
            if (this === o) {
                return true
            }
            if (o !is Blog) {
                return false
            }
            val blog = o
            if (blogUrl != blog.blogUrl) {
                return false
            }
            if (coverImgUrl != blog.coverImgUrl) {
                return false
            }
            if (title != blog.title) {
                return false
            }
            if (description != blog.description) {
                return false
            }
            return if (author != blog.author) {
                false
            } else date == blog.date
        }

        override fun hashCode(): Int {
            var result = blogUrl.hashCode()
            result = 31 * result + coverImgUrl.hashCode()
            result = 31 * result + title.hashCode()
            result = 31 * result + description.hashCode()
            result = 31 * result + author.hashCode()
            result = 31 * result + date.hashCode()
            return result
        }
    }
}