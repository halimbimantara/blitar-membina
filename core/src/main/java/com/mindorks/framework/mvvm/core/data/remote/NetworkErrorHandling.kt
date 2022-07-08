package com.mindorks.framework.mvvm.core.data.remote

import com.androidnetworking.error.ANError
import com.mindorks.framework.mvvm.core.utils.AppConstants
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class NetworkErrorHandler(var tipeError: String?, var messages: String?)
class NetworkErrorHandlingTag(var tag: String?, var tipeError: String?, var messages: String?)
object NetworkErrorHandling {
    fun handleNetworkThrowableWithTag(throwable: Throwable, TAG: String): NetworkErrorHandlingTag {
        return when (throwable) {
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is IOException -> {
                if (throwable.message!!.contains("401")) {
                    NetworkErrorHandlingTag(
                        TAG,
                        AppConstants.ERROR_TYPE_TOKEN_FAILED,
                        throwable.message
                    )
                } else {
                    NetworkErrorHandlingTag(TAG, AppConstants.ERROR_TYPE_NETWORK, throwable.message)
                }
            }
            is ANError -> {
                val anError: ANError = throwable
                if (anError.errorCode != 0) {
                    if (anError.errorCode == 401) {
                        NetworkErrorHandlingTag(
                            TAG,
                            AppConstants.ERROR_TYPE_TOKEN_FAILED,
                            throwable.message
                        )
                    } else {
                        NetworkErrorHandlingTag(
                            TAG,
                            AppConstants.ERROR_TYPE_NETWORK,
                            throwable.message
                        )
                    }
                } else {
                    if (anError.errorBody != null) {
                        if (anError.errorBody.contains("401")) {
                            NetworkErrorHandlingTag(
                                TAG,
                                AppConstants.ERROR_TYPE_TOKEN_FAILED,
                                throwable.message
                            )
                        } else {
                            NetworkErrorHandlingTag(
                                TAG,
                                AppConstants.ERROR_TYPE_NETWORK,
                                throwable.message
                            )
                        }
                    } else {
                        NetworkErrorHandlingTag(
                            TAG,
                            AppConstants.ERROR_TYPE_NETWORK,
                            "Network error"
                        )
                    }
                }
            }
            else -> NetworkErrorHandlingTag(
                TAG,
                AppConstants.ERROR_TYPE_OTHER,
                "Something went wrong"
            )
        }
    }
}