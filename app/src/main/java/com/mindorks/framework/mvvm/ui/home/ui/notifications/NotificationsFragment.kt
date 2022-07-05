package com.mindorks.framework.mvvm.ui.home.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.api.service.BookedResponse
import com.mindorks.framework.mvvm.databinding.FragmentNotificationsBinding
import com.mindorks.framework.mvvm.databinding.ItemCourseBinding
import com.mindorks.framework.mvvm.di.component.FragmentComponent
import com.mindorks.framework.mvvm.ui.base.BaseFragment
import com.mindorks.framework.mvvm.ui.home.ui.home.HomeViewModel
import com.mindorks.framework.mvvm.utils.widget.GenericAdapter
import java.util.ArrayList

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding?, HomeViewModel?>() {
    private lateinit var bookedListAdapter: GenericAdapter<BookedResponse.Data, ItemCourseBinding>
    private val listItemBookNotify = ArrayList<BookedResponse.Data>()
    private var binding: FragmentNotificationsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
        bookedListAdapter = object : GenericAdapter<BookedResponse.Data, ItemCourseBinding>(
            requireContext(),
            listItemBookNotify
        ) {
            override fun onBindData(
                model: BookedResponse.Data?,
                position: Int,
                dataBinding: ItemCourseBinding?
            ) {

            }

            override fun getLayoutResId(): Int {
                return R.layout.item_course
            }

            override fun onItemClick(model: BookedResponse.Data?, position: Int) {

            }

            override fun onRetry() {

            }

            override fun noConnection() {

            }

            override fun setTitle(): String {
                return getString(R.string.title_notifications)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_notifications
    }

    override fun performDependencyInjection(buildComponent: FragmentComponent?) {
        buildComponent!!.inject(this)
    }
}