package com.mindorks.framework.mvvm.ui.features.course.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.response.ModelListUserAppliedResponse
import com.mindorks.framework.mvvm.core.data.remote.ApiEndPoint
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandling
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import org.json.JSONObject

class DetailCourseViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {
    private val _getListUser = MutableLiveData<List<ModelListUserAppliedResponse.Data>>()
    val getListUserApplied: LiveData<List<ModelListUserAppliedResponse.Data>>
        get() = _getListUser

    fun getListUserApplied(type: String?) {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .serviceUserListBook("non")
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                if (it.data!!.isNotEmpty()) {
                    _getListUser.postValue(it.data as List<ModelListUserAppliedResponse.Data>?)
                } else {
                    _getListUser.postValue(arrayListOf())
                }
            }) {
                setIsLoading(false)
                _getListUser.postValue(arrayListOf())
            })
    }


    fun approvalBooking(status: String, reason: String) {
        AndroidNetworking.post(ApiEndPoint.API_ENDPOINT_BOOKING_APPROVAL)
            .addHeaders("Authorization", "Bearer " + dataManager.accessToken)
            .addBodyParameter("reason", reason)
            .addBodyParameter("status", status)
            .addBodyParameter("provider_id", dataManager.currentUserId.toString())
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                }

                override fun onError(error: ANError) {
                    // handle error
//                    errorData.postValue(
//                        NetworkErrorHandling.handleNetworkThrowableWithTag(
//                            error,
//                            "posting loker"
//                        )
//                    )
                }
            })
    }
}