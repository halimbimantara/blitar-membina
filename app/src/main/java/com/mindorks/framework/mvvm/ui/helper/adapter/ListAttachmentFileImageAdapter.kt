package com.mindorks.framework.mvvm.ui.helper.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.others.AttachFileModel
import com.mindorks.framework.mvvm.core.utils.AppUtils.getFileSize
import com.mindorks.framework.mvvm.core.utils.AppUtils.getFileType
import com.mindorks.framework.mvvm.databinding.ItemBlogEmptyViewBinding
import com.mindorks.framework.mvvm.databinding.ItemFileCreatePostBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogEmptyItemViewModel
import com.mindorks.framework.mvvm.utils.NetworkUtils.isNetworkConnected
import java.io.File

class ListAttachmentFileImageAdapter(var mContext: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {
    var drop = true
    var allCheck = false

    //    private List<GetListDetailTaskQuery.Performance_task> mBlogResponseList = Collections.emptyList();
    private val mBlogResponseList: MutableList<AttachFileModel>? = ArrayList()
    private var mListener: AllTrendingListListener? = null
    fun setPosts(dataList: List<AttachFileModel>?) {
        mBlogResponseList!!.addAll(dataList!!)
        notifyDataSetChanged()
    }

    val isNetworkConnected: Boolean
        get() = isNetworkConnected(mContext.applicationContext)

    override fun getItemCount(): Int {
        return mBlogResponseList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mBlogResponseList != null && !mBlogResponseList.isEmpty() && isNetworkConnected) {
            VIEW_TYPE_NORMAL
        } else if (!isNetworkConnected || mBlogResponseList!!.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else if (mBlogResponseList != null && !mBlogResponseList.isEmpty() && !isNetworkConnected) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_LOAD
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_EMPTY -> {
                val blogViewBinding1 =
                    ItemBlogEmptyViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                EmptyViewHolder(
                    blogViewBinding1
                )
            }
            else -> {
                val blogViewBinding =
                    ItemFileCreatePostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                ItemListViewHolder(
                    blogViewBinding
                )
            }
        }
    }

    fun addItems(blogList: List<AttachFileModel>?) {
        mBlogResponseList!!.addAll(blogList!!)
        notifyDataSetChanged()
    }

    fun addItem(item: AttachFileModel) {
        mBlogResponseList!!.add(item)
        notifyDataSetChanged()
    }

    fun selectAll() {
        allCheck = true
        notifyDataSetChanged()
    }

    fun unSelectAll() {
        allCheck = false
        notifyDataSetChanged()
    }

    fun clearItems() {
        mBlogResponseList!!.clear()
    }

    fun clearItemsBYPosition(position: Int) {
        mBlogResponseList!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun setListener(listener: AllTrendingListListener?) {
        mListener = listener
    }

    interface AllTrendingListListener {
        fun onDeleteItemFileImage(pos: Int)
    }

    inner class ItemListViewHolder(private val mBinding: ItemFileCreatePostBinding) :
        BaseViewHolder(
            mBinding.root
        ) {
        var data: AttachFileModel? = null
        override fun onBind(position: Int) {
            val blog = mBlogResponseList!![position]
            mBinding.imageClose.setOnClickListener { view: View? ->
                mListener!!.onDeleteItemFileImage(position)
                notifyDataSetChanged()
            }
            mBinding.executePendingBindings()
        }

        fun setPost(post: AttachFileModel) {
            val extension = getFileType(mContext, post.originalPath)
            println("Extensionnya $extension")
            when (extension) {
                "doc", "docx", "rtf" -> {
                    mBinding.imageIcon.setImageResource(R.drawable.ic_file_word_solid)
                    mBinding.editFileName.setText(post.fileName)
                }
                "xls", "xlsx" -> {
                    mBinding.imageIcon.setImageResource(R.drawable.ic_file_excel_solid)
                    mBinding.editFileName.setText(post.fileName)
                }
                "ppt", "pptx" -> {
                    mBinding.imageIcon.setImageResource(R.drawable.ic_file_powerpoint_solid_new)
                    mBinding.editFileName.setText(post.fileName)
                }
                "pdf" -> {
                    mBinding.imageIcon.setImageResource(R.drawable.ic_file_pdf_solid)
                    mBinding.editFileName.setText(post.fileName)
                }
                "jpg", "JPG", "jpeg", "JPEG", "PNG", "png", "gif", "GIF" -> {
                    if (post.cameraImageUri != null) {
                        Glide.with(mContext)
                            .load(post.cameraImageUri)
                            .into(mBinding.imageIcon)
                    } else if (post.queryUri != null) {
                        Glide.with(mContext)
                            .load(Uri.parse(post.queryUri))
                            .into(mBinding.imageIcon)
                    } else {
                        if (post.previewThumbnail != null && post.originalPath != null) Glide.with(
                            mContext
                        )
                            .load(Uri.fromFile(File(post.previewThumbnail)))
                            .error(R.drawable.ef_image_placeholder)
                            .into(mBinding.imageIcon) else if (post.originalPath == null) Glide.with(
                            mContext
                        )
                            .load(R.drawable.ic_baseline_broken_image_24)
                            .error(R.drawable.ef_image_placeholder)
                            .into(mBinding.imageIcon) else Glide.with(mContext)
                            .load(Uri.fromFile(File(post.originalPath)))
                            .error(R.drawable.ef_image_placeholder)
                            .into(mBinding.imageIcon)
                    }
                    mBinding.editFileName.setText(post.fileName)
                }
                else -> {
                    mBinding.imageIcon.setImageResource(R.drawable.ic_file_solid)
                    mBinding.editFileName.setText(post.fileName)
                }
            }
            mBinding.textFileSize.text = getFileSize(File(post.originalPath))
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemBlogEmptyViewBinding) : BaseViewHolder(
        mBinding.root
    ), BlogEmptyItemViewModel.BlogEmptyItemViewModelListener {
        override fun onBind(position: Int) {
            if (mBlogResponseList!!.isEmpty() && isNetworkConnected) {
                mBinding.linearLayoutView.visibility = View.GONE
                mBinding.imageViewEmpty.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_baseline_update))
                mBinding.tvMessage.setText(R.string.empty_content)
            } else if (!isNetworkConnected) {
                mBinding.linearLayoutView.visibility = View.GONE
                mBinding.imageViewEmpty.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_cloud_off_24px))
                mBinding.tvMessage.setText(R.string.tap_to_retry)
            }
            val emptyItemViewModel = BlogEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {}
    }

    fun removeAt(position: Int) {
        mBlogResponseList!!.removeAt(position)
        notifyItemRemoved(position)
    }

    val isEmpty: Boolean
        get() = mBlogResponseList!!.isEmpty()
    val items: List<AttachFileModel>?
        get() = mBlogResponseList

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_LOAD = 2
    }
}