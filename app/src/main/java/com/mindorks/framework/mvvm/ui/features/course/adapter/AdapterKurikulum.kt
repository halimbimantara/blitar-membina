package com.mindorks.framework.mvvm.ui.features.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.databinding.ItemKurikulumBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.features.course.model.KurikulumModel

class AdapterKurikulum(
    private val mModels: List<KurikulumModel>
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val context = parent.context
        val itemView =
            ItemKurikulumBinding.inflate(
                LayoutInflater.from(context),
                parent, false
            )
        return CourseViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return mModels.size
    }

    inner class CourseViewHolder(private val mBinding: ItemKurikulumBinding) : BaseViewHolder(
        mBinding.root
    ) {
        override fun onBind(position: Int) {
            val model = mModels[position]
            mBinding.tvTitleCourse.text = model.name
            mBinding.tvContent.text = model.description

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }
}