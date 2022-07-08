package com.mindorks.framework.mvvm.ui.features.pembina.viewmodels

import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.remote.ApiEndPoint
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import id.zelory.compressor.Compressor
import org.json.JSONObject
import java.io.File


class FormPostViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {

    //    private val _getListCMClassroomLiveData = MutableLiveData<GetListCMClassroomQuery.Data>()
//    val getListCMClassroomLiveData: LiveData<GetListCMClassroomQuery.Data>
//        get() = _getListCMClassroomLiveData

    fun postServiceLoker(mObject: HashMap<String, Any>, mFile: File) {
        val mLoker = HashMap<String, Any>()
        mLoker["name"] = "Loker Magang"
        mLoker["category_id"] = "2"
        mLoker["provider_id"] = "4"
        mLoker["type"] = "fixed"
        mLoker["discount"] = "0"
        mLoker["duration"] = "03:00"
        mLoker["description"] = "Operator 3d printer"
        mLoker["is_featured"] = "0"
        mLoker["status"] = "1"
        mLoker["price"] = "0"
        mLoker["added_by"] = "1"
        mLoker["quota"] = "5"
        mLoker["created_at"] = "2022-07-07 15:42:06"
        mLoker["update_at"] = "2022-07-07 15:42:06"
        val listFile = ArrayList<File>()
        listFile.add(mFile)
        AndroidNetworking.upload(ApiEndPoint.API_ENDPOINT_ADD_SERVICE)
            .addHeaders("Authorization", "Bearer " + dataManager.accessToken)
            .addMultipartFile("service_attachment", mFile)
            .addMultipartParameter(mLoker)
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener { bytesUploaded, totalBytes ->
                // do anything with progress
                setIsLoading(true)
            }
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response
                    Log.i("create post response", "create post: $response")
                }

                override fun onError(error: ANError) {
                    // handle error
                    Log.e("create post response", "create post: ${error.message}")
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
                    setIsLoading(false)
                    if (response != null) {
                    }
                }) { setIsLoading(false) })
    }
}