package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.BuildConfig
import com.mindorks.framework.mvvm.core.data.model.api.response.ModelListUserAppliedResponse
import com.mindorks.framework.mvvm.core.utils.rx.observe
import com.mindorks.framework.mvvm.databinding.ContentAppliedCourseBinding
import com.mindorks.framework.mvvm.databinding.ItemUserContactsBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.features.course.activity.DetailCourse.Companion.EXTRA_ID_LOWONGAN
import com.mindorks.framework.mvvm.ui.features.course.viewmodels.DetailCourseViewModel
import com.mindorks.framework.mvvm.utils.ext.loadImageRound
import com.mindorks.framework.mvvm.utils.widget.GenericAdapter

class ListStudentApplyCourse : BaseActivity<ContentAppliedCourseBinding, DetailCourseViewModel>() {
    private lateinit var binding: ContentAppliedCourseBinding
    private lateinit var bookedListUserAdapter: GenericAdapter<ModelListUserAppliedResponse.Data, ItemUserContactsBinding>
    private var bookedListUser = ArrayList<ModelListUserAppliedResponse.Data>()
    private val titleCourse by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }
    var mLayoutManager: LinearLayoutManager? = null
    private val idLowongan by lazy {
        intent.getIntExtra(EXTRA_ID_LOWONGAN, 0)
    }

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"

        fun newIntent(context: Context?, idService: Int?): Intent {
            return Intent(context, ListStudentApplyCourse::class.java).apply {
                putExtra(EXTRA_ID_LOWONGAN, idService)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        initUi()
        mViewModel!!.getListUserApplied("non")
    }

    private fun initUi() {
        //set adapter
        bookedListUserAdapter =
            object : GenericAdapter<ModelListUserAppliedResponse.Data, ItemUserContactsBinding>(
                this,
                bookedListUser
            ) {
                override fun onBindData(
                    model: ModelListUserAppliedResponse.Data?,
                    position: Int,
                    dataBinding: ItemUserContactsBinding?
                ) {
                    val imageCover = model?.customerProfilePict?.replace(
                        "http://localhost",
                        BuildConfig.BASE_URL
                    )
                    dataBinding!!.imvContent.loadImageRound(imageCover!!)
                    dataBinding.tvTitleName.text = model.customerName
                    dataBinding.tvContent.text = model.description.toString()
                }

                override fun getLayoutResId(): Int {
                    return R.layout.item_user_contacts
                }

                override fun onItemClick(
                    model: ModelListUserAppliedResponse.Data?,
                    position: Int
                ) {
                }

                override fun onRetry() {}

                override fun noConnection() {}

                override fun setTitle(): String {
                    return "Daftar pelamar"
                }

            }

        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.rvList.layoutManager = mLayoutManager
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.adapter = bookedListUserAdapter
    }

    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = R.layout.content_applied_course

    override fun observeChange() {
        observe(mViewModel!!.getListUserApplied, ::getDataUserBooking)
    }

    private fun getDataUserBooking(list: List<ModelListUserAppliedResponse.Data>) {
        bookedListUser.clear()
        bookedListUser.addAll(list)
        bookedListUserAdapter.notifyDataSetChanged()
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

}