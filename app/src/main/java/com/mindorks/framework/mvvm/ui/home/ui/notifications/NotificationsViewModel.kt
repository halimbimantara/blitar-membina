package com.mindorks.framework.mvvm.ui.home.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.data.model.api.service.BookedResponse
import com.mindorks.framework.mvvm.core.data.model.api.service.ServiceResponse
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseViewModel

class NotificationsViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {

    private val _notifyBookedListData: MutableLiveData<BookedResponse> = MutableLiveData()
    val notifBookedDataList: LiveData<BookedResponse>
        get() = _notifyBookedListData

    fun fetchService() {
        setIsLoading(true)
        compositeDisposable.add(dataManager
            .notifyBookedList()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                if (!it.data!!.isNullOrEmpty()) {
//                    _serviceListData.postValue(it)
                } else {
//                    _serviceListData.postValue(null)
                }
                setIsLoading(false)
            }) { throwable: Throwable? ->
                setIsLoading(false)
            })
    }
}