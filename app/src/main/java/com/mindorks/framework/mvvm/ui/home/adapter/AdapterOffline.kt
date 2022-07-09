package com.mindorks.framework.mvvm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ItemCourseBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.features.course.activity.DetailCourse
import com.mindorks.framework.mvvm.ui.features.course.activity.DetailCourseApplied
import com.mindorks.framework.mvvm.ui.features.course.activity.DetailCourseCompleted
import com.mindorks.framework.mvvm.ui.home.models.CourseModel
import com.mindorks.framework.mvvm.utils.ext.gone
import com.mindorks.framework.mvvm.utils.ext.loadImageDrawable
import com.mindorks.framework.mvvm.utils.ext.visible

class AdapterOffline(
    private val mModels: List<CourseModel>,
    private val userRole: String?,
    private val tipe: String? = null
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val context = parent.context
        val itemView =
            ItemCourseBinding.inflate(
                LayoutInflater.from(context),
                parent, false
            )
        return CourseViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return mModels.size
    }

    inner class CourseViewHolder(private val mBinding: ItemCourseBinding) : BaseViewHolder(
        mBinding.root
    ) {
        override fun onBind(position: Int) {
            val model = mModels[position]
            mBinding.tvTitleCourse.text = model.title
            mBinding.tvContent.text = model.description
            mBinding.imvContent.loadImageDrawable(
                model.imgDrawable!!
            )

            if (tipe != null) {
                mBinding.tvApproval.visible()
                when (model.status) {
                    CourseModel.extra_status_approve -> {
                        mBinding.tvApproval.setBackgroundColor(
                            ContextCompat.getColor(itemView.context, R.color.green)
                        )
                        mBinding.tvApproval.text = "Disetujui"

                        mBinding.imvContent.setOnClickListener {
                            itemView.context.startActivity(
                                DetailCourseApplied.newIntent(
                                    itemView.context, model.title, userRole!!
                                )
                            )
                        }
                    }

                    CourseModel.extra_status_reject -> {
                        mBinding.tvApproval.setBackgroundColor(
                            ContextCompat.getColor(itemView.context, R.color.red)
                        )
                        mBinding.tvApproval.text = "Tidak disetujui"
                    }
                    CourseModel.extra_status_pending -> {
                        mBinding.tvApproval.setBackgroundColor(
                            ContextCompat.getColor(itemView.context, R.color.secondary)
                        )
                        mBinding.tvApproval.text = "Menunggu verifikasi"
                    }
                    CourseModel.extra_status_sertifikat -> {
                        mBinding.tvApproval.setBackgroundColor(
                            ContextCompat.getColor(itemView.context, R.color.purple_500)
                        )
                        mBinding.tvApproval.text = "Sertifikat telah terbit"

                        mBinding.imvContent.setOnClickListener {
                            itemView.context.startActivity(
                                DetailCourseCompleted.newIntent(
                                    itemView.context, model.title, userRole!!
                                )
                            )
                        }
                    }
                }

            } else {
                mBinding.imvContent.setOnClickListener {
//                    itemView.context.startActivity(
//                        DetailCourse.newIntent(
//                            itemView.context, model.title, userRole!!
//                        )
//                    )
                }
                mBinding.tvApproval.gone()
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }
}