package com.mindorks.framework.mvvm.ui.features.pembina.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.response.ListCategoryItem
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.Register.email
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.Register.firstName
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.Register.gender
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.Register.lastName
import com.mindorks.framework.mvvm.core.data.remote.ApiEndPoint
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandling
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandling.handleNetworkThrowableWithTag
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandlingTag
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.core.utils.rx.SingleLiveEvent
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import id.zelory.compressor.Compressor
import org.json.JSONObject
import java.io.File


class FormPostViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {
    val errorData = SingleLiveEvent<NetworkErrorHandlingTag>()
    val successPostLoker = SingleLiveEvent<Boolean>()
    val successCompress = SingleLiveEvent<Boolean>()
    val fileCompress = SingleLiveEvent<File>()
    private val _listCategory = SingleLiveEvent<ListCategoryItem?>()
    val getListCategoryItem: SingleLiveEvent<ListCategoryItem?>
        get() = _listCategory

    private fun getCategory() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .categoryList()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                if (!it.data.isNullOrEmpty()) {
                    //passing login page
                    _listCategory.postValue(it)
                } else {
                    _listCategory.postValue(null)
                }
            }) {
                errorData.postValue(
                    NetworkErrorHandling.handleNetworkThrowableWithTag(
                        it,
                        it.message.toString()
                    )
                )
                setIsLoading(false)
            })
    }

    fun postServiceLoker(mObject: HashMap<String, Any>, mFile: File) {
        AndroidNetworking.upload(ApiEndPoint.API_ENDPOINT_ADD_SERVICE)
            .addHeaders("Authorization", "Bearer " + dataManager.accessToken)
            .addMultipartFile("service_attachment", mFile)
            .addMultipartParameter(mObject)
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener { bytesUploaded, totalBytes ->
                setIsLoading(true)
            }
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response
                    Log.i("create post response", "create post: $response")
                    successPostLoker.postValue(response.getBoolean("success"))
                }

                override fun onError(error: ANError) {
                    // handle error
                    errorData.postValue(handleNetworkThrowableWithTag(error, "posting loker"))
                }
            })

    }


    fun compressImage(mContext: Context?, path: String?) {
        val actualImage = File(path)
        compositeDisposable.add(
            Compressor(mContext)
                .compressToFileAsFlowable(actualImage)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ response: File? ->
                    if (response != null) {
                        fileCompress.postValue(response)
                    }
                    setIsLoading(false)
                }) { setIsLoading(false) })
    }

    init {
        getCategory()
    }
}