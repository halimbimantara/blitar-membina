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
package com.mindorks.framework.mvvm.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by amitshekhar on 07/07/17.
 */
object DateUtils {

    fun formatDateto(dateIn: String?, FormatDate: String?): String {
        val originDate = "07/25/19"
        val formatIn = SimpleDateFormat(AppConstants.DATE_FORMAT_MDY_KOMA, Locale.ENGLISH)
        val formatOut = SimpleDateFormat(FormatDate, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        try {
            calendar.time = formatIn.parse(dateIn)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatOut.format(calendar.time)
    }
}