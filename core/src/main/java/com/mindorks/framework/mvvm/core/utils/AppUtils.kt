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

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import com.mindorks.framework.mvvm.core.R
import com.nabinbhandari.android.permissions.PermissionHandler
import java.io.File

/**
 * Created by amitshekhar on 07/07/17.
 */
object AppUtils {
    // file size in bytes 25mb
    const val FILE_MAX_SIZE_UPLOAD = (25 * 1024 * 1024).toLong()

    /**
     * Require Permission
     */
    fun setPermissions(mContext: Context?, permissionList: Array<String>, info: String): Boolean {
        val status = booleanArrayOf(false)
        val rationale = "Please provide permission so that you can $info"
        val options: com.nabinbhandari.android.permissions.Permissions.Options =
            com.nabinbhandari.android.permissions.Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning")
        com.nabinbhandari.android.permissions.Permissions.check(
            mContext /*context*/,
            permissionList,
            rationale /*rationale*/,
            options /*options*/,
            object : PermissionHandler() {
                override fun onGranted() {
                    // do your task.
                    status[0] = true
                }

                override fun onDenied(context: Context, deniedPermissions: ArrayList<String>) {
                    status[0] = false
                }
            })
        return status[0]
    }
    fun openPlayStoreForApp(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_market_link) + appPackageName
                    )
                )
            )
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_google_play_store_link) + appPackageName
                    )
                )
            )
        }
    }

    fun getFileType(mContext: Context, path: String?): String? {
        val uri = Uri.fromFile(File(path))
        //        //Check uri format to avoid null
        val extension: String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(mContext.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    fun getFileSize(file: File): String? {
        val sizeFile: Int
        val sizeInBytes = file.length().toString().toInt()
        var tipeSFile = ""
        val sizeInKb = sizeInBytes / 1024
        if (sizeInKb > 1024) {
            sizeFile = sizeInKb / 1024
            tipeSFile = " MB"
        } else {
            sizeFile = sizeInKb
            tipeSFile = " KB"
        }
        return " | $sizeFile$tipeSFile"
    }

    fun getFileSizeUpload(file: String): String? {
        val sizeFile: Int
        val sizeInBytes = file.toInt()
        var tipeSFile = ""
        val sizeInKb = sizeInBytes / 1024
        if (sizeInKb > 1024) {
            sizeFile = sizeInKb / 1024
            tipeSFile = " MB"
        } else {
            sizeFile = sizeInKb
            tipeSFile = " KB"
        }
        return " | $sizeFile$tipeSFile"
    }

    fun convertFileSizeUnit(size: Int): String? {
        var size = size
        var tipeSFile = ""
        val sizeInKb = size / 1024
        if (sizeInKb > 1024) {
            size = sizeInKb / 1024
            tipeSFile = " MB"
        } else {
            size = sizeInKb
            tipeSFile = " KB"
        }
        return " | $size$tipeSFile"
    }


    fun getFileSizeInt(file: File): Int {
        val sizeFile: Int
        val sizeInBytes = file.length().toString().toInt()
        val tipeSFile = ""
        val sizeInKb = sizeInBytes / 1024
        sizeFile = if (sizeInKb > 1024) {
            sizeInKb / 1024
        } else {
            sizeInKb
        }
        return sizeFile
    }

    fun getFileSizeInteger(size: Int): Int {
        val sizeFile: Int
        val tipeSFile = ""
        val sizeInKb = size / 1024
        sizeFile = if (sizeInKb > 1024) {
            sizeInKb / 1024
        } else {
            sizeInKb
        }
        return sizeFile
    }

}