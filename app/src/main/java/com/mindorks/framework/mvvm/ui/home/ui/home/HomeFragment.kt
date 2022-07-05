package com.mindorks.framework.mvvm.ui.home.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.api.service.ServiceResponse
import com.mindorks.framework.mvvm.databinding.FragmentHomeBinding
import com.mindorks.framework.mvvm.di.component.FragmentComponent
import com.mindorks.framework.mvvm.ui.base.BaseFragment
import com.mindorks.framework.mvvm.ui.features.pembina.activity.FormPostLokerActivity
import com.mindorks.framework.mvvm.utils.PreferenceUtils
import org.hawlastudio.binaahli.utils.ext.gone
import org.hawlastudio.binaahli.utils.ext.visible

class HomeFragment : BaseFragment<FragmentHomeBinding?, HomeViewModel?>(),
    HomeListAdapter.HomeListAdapterListener {
    var binding: FragmentHomeBinding? = null
    var mAdapter: HomeListAdapter? = null
    private val listCourseItem: ArrayList<ServiceResponse.Data?>? = arrayListOf()

    var mLayoutManager: LinearLayoutManager? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
        initUI()
        setup()
        loadData()
        observer()
    }

    private fun loadData() {
        mViewModel!!.fetchService()
    }

    private fun observer() {
        mViewModel!!.serviceDataList.observe(requireActivity()) {
            if (it != null) {
                listCourseItem?.clear()
                it.data?.let { it1 -> listCourseItem?.addAll(it1) }
                mAdapter?.addItems(listCourseItem)
            } else {
                //empty
            }
        }
    }

    private fun initUI() {
        //rv
        mLayoutManager = LinearLayoutManager(requireContext())
        mAdapter = HomeListAdapter(listCourseItem)
        mAdapter!!.setListener(this)
        mLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding!!.rvCourse.layoutManager = mLayoutManager
        binding!!.rvCourse.itemAnimator = DefaultItemAnimator()
        binding!!.rvCourse.adapter = mAdapter
    }

    private fun setup() {
        val accessTipe = PreferenceUtils.userRole
        when (accessTipe) {
            PreferenceUtils.EXTRA_TYPE_ROLE_PELAMAR -> {
                binding!!.floatAddLoker.gone()
            }

            PreferenceUtils.EXTRA_TYPE_ROLE_PEMBINA -> {
                binding!!.floatAddLoker.visible()
            }
        }
        binding!!.floatAddLoker.setOnClickListener {
            startActivity(
                FormPostLokerActivity.newIntent(
                    requireContext(),
                    "Form Post Loker / Magang"
                )
            )
        }


    }

    override fun performDependencyInjection(buildComponent: FragmentComponent?) {
        buildComponent?.inject(this)
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onRetryClick() {

    }
}