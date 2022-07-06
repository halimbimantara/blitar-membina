package com.mindorks.framework.mvvm.ui.features.pembina.viewmodels

import android.content.Context
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import id.zelory.compressor.Compressor
import java.io.File

class FormPostViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {

    //    private val _getListCMClassroomLiveData = MutableLiveData<GetListCMClassroomQuery.Data>()
//    val getListCMClassroomLiveData: LiveData<GetListCMClassroomQuery.Data>
//        get() = _getListCMClassroomLiveData

    fun postServiceLoker() {

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