package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.BuildConfig
import com.mindorks.framework.mvvm.core.data.model.api.response.ModelListUserAppliedResponse
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.StatusBook.STATUS_APPROVED
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.StatusBook.STATUS_REJECT
import com.mindorks.framework.mvvm.core.ui.common.extensions.etToString
import com.mindorks.framework.mvvm.core.utils.rx.observe
import com.mindorks.framework.mvvm.databinding.ContentAppliedCourseBinding
import com.mindorks.framework.mvvm.databinding.ItemUserContactsBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.features.course.activity.DetailCourse.Companion.EXTRA_ID_LOWONGAN
import com.mindorks.framework.mvvm.ui.features.course.viewmodels.DetailCourseViewModel
import com.mindorks.framework.mvvm.utils.ext.gone
import com.mindorks.framework.mvvm.utils.ext.loadImageRound
import com.mindorks.framework.mvvm.utils.ext.visible
import com.mindorks.framework.mvvm.utils.widget.GenericAdapter
import java.util.*
import kotlin.collections.ArrayList

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
                    dataBinding.tvLabelStatus.text = model.status?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }

                    if (model.status.toString() != "pending") {
                        dataBinding.tvApproval.gone()
                        dataBinding.tvApprovalDecline.gone()
                    } else {
                        dataBinding.tvApproval.visible()
                        dataBinding.tvApprovalDecline.visible()
                    }


                    dataBinding.tvApproval.setOnClickListener {
                        MaterialDialog(this@ListStudentApplyCourse).show {
                            title(text = "Peringatan")
                            message(text = "Apakah anda yakin menyetujuinya ?")
                            positiveButton(R.string.ya) { dialog ->
                                // Do something
                                showLoading()
                                mViewModel!!.approvalBooking(model.id!!, STATUS_APPROVED)
                            }
                            negativeButton(R.string.batal) { dialog ->
                                // Do something
                                dismiss()
                            }

                        }
                    }
                    dataBinding.tvApprovalDecline.setOnClickListener {
                        MaterialDialog(this@ListStudentApplyCourse).show {
                            title(text = "Peringatan")
                            message(text = "Apakah anda yakin menolak pengajukan lamaran ini ?")
                            val mView = customView(R.layout.dialog_content_enter_reason_apply)
                            val etReasonApply =
                                mView.getCustomView().findViewById<EditText>(R.id.EtEntryReason)
                            val labelAlasan =
                                mView.getCustomView().findViewById<TextView>(R.id.textView9)
                            labelAlasan.text = "Tulis Alasan anda tidak menerima kandidat ini ?"
                            positiveButton(R.string.submit) { dialog ->
                                // Do something
                                if (etReasonApply.etToString().isNotEmpty()) {
                                    showLoading()
                                    mViewModel!!.approvalBooking(
                                        model.id!!,
                                        STATUS_REJECT,
                                        etReasonApply.etToString()
                                    )
                                    dismiss()
                                }else{
                                    etReasonApply.error =getString(R.string.info_field_canempty)
                                }
                            }
                            negativeButton(R.string.batal) { dialog ->
                                // Do something
                                dismiss()
                            }
                        }
                    }
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
        observe(mViewModel!!.successApproval, ::checkStatusApproval)
    }

    private fun checkStatusApproval(success: Boolean) {
        hideLoading()
        if (success) {
            mViewModel!!.getListUserApplied("non")
        } else {
            showMessage("Gagal verifikasi lowongan")
        }
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