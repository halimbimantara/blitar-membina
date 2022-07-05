package com.mindorks.framework.mvvm.ui.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse
import com.mindorks.framework.mvvm.core.data.model.api.service.ServiceResponse
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseViewModel

class HomeViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {
    private val _serviceListData: MutableLiveData<ServiceResponse> = MutableLiveData()
    val serviceDataList: LiveData<ServiceResponse>
        get() = _serviceListData

    fun fetchService() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .serviceList()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                if (!it.data!!.isNullOrEmpty()) {
                    _serviceListData.postValue(it)
                } else {
                    _serviceListData.postValue(null)
                }
                setIsLoading(false)
            }) { throwable: Throwable? ->
                setIsLoading(false)
            })
    }
}