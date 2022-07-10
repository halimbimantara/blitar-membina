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
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.PembinaApproval.status
import com.mindorks.framework.mvvm.core.data.remote.ApiEndPoint
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandling
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandlingTag
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.core.utils.rx.SingleLiveEvent
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import org.json.JSONObject

class DetailCourseViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {
    private val _getListUser = MutableLiveData<List<ModelListUserAppliedResponse.Data>>()
    val getListUserApplied: LiveData<List<ModelListUserAppliedResponse.Data>>
        get() = _getListUser
    val errorData = SingleLiveEvent<NetworkErrorHandlingTag>()
    val successApply = SingleLiveEvent<Boolean>()
    val successApproval = SingleLiveEvent<Boolean>()

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

    fun applyLowongan(serviceId: Int, desc: String) {
        compositeDisposable.add(dataManager
            .userApplyService(serviceId.toString(), desc, dataManager.currentUserId.toInt())
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({

            }) {
                setIsLoading(false)
            })
    }

    fun approvalBooking(id: Int, status: String, reason: String? = null) {
        val mObjects = HashMap<String, String>()
        mObjects["id"] = id.toString()
        if (!reason.isNullOrEmpty())
            mObjects["reason"] = reason
        mObjects["status"] = status
//        mObjects["provider_id"] = dataManager.currentUserId.toString()

        AndroidNetworking.post(ApiEndPoint.API_ENDPOINT_BOOKING_APPROVAL)
            .addHeaders("Authorization", "Bearer " + dataManager.accessToken)
            .addBodyParameter(mObjects)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    successApproval.postValue(response.getBoolean("success"))
                }

                override fun onError(error: ANError) {
                    // handle error
                    errorData.postValue(
                        NetworkErrorHandling.handleNetworkThrowableWithTag(
                            error,
                            "Failed Approval"
                        )
                    )
                }
            })
    }
}