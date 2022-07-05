package com.mindorks.framework.mvvm.ui.features.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.databinding.ItemCourseBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.features.course.activity.DetailCourse
import com.mindorks.framework.mvvm.ui.home.models.CourseModel
import com.mindorks.framework.mvvm.utils.ext.loadImageDrawable

class AdapterStudent(
    private val mModels: List<CourseModel>
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
            mBinding.imvContent.setOnClickListener {
                itemView.context.startActivity(
                    DetailCourse.newIntent(
                        itemView.context,
                        model.title
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }
}