package com.mindorks.framework.mvvm.ui.features.pembina.viewmodels

import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseViewModel

class FormPostViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider) {

    //    private val _getListCMClassroomLiveData = MutableLiveData<GetListCMClassroomQuery.Data>()
//    val getListCMClassroomLiveData: LiveData<GetListCMClassroomQuery.Data>
//        get() = _getListCMClassroomLiveData

    fun postServiceLoker() {

    }
}