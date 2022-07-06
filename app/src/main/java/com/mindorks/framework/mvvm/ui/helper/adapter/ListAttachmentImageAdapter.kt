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
import com.mindorks.framework.mvvm.databinding.AdapterFilesBinding
import com.mindorks.framework.mvvm.databinding.ItemBlogEmptyViewBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogEmptyItemViewModel
import com.mindorks.framework.mvvm.utils.NetworkUtils.isNetworkConnected
import java.io.File

class ListAttachmentImageAdapter(var mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {
    var drop = true
    var allCheck = false
    private var items: MutableList<AttachFileModel>? = ArrayList()
    private var mListener: ImageAttachListListener? = null
    fun setPosts(dataList: MutableList<AttachFileModel>?) {
        items = dataList
        notifyDataSetChanged()
    }

    val isNetworkConnected: Boolean
        get() = isNetworkConnected(mContext.applicationContext)

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items != null && !items!!.isEmpty() && isNetworkConnected) {
            VIEW_TYPE_NORMAL
        } else if (!isNetworkConnected || items!!.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else if (items != null && !items!!.isEmpty() && !isNetworkConnected) {
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
                    AdapterFilesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                ItemListViewHolder(
                    blogViewBinding
                )
            }
        }
    }

    fun addItems(items: MutableList<AttachFileModel?>) {
        items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: AttachFileModel) {
        items!!.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        items!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int): AttachFileModel {
        return items!![position]
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
        items!!.clear()
    }

    fun setListener(listener: ImageAttachListListener?) {
        mListener = listener
    }

    interface ImageAttachListListener {
        fun onDeleteItemImage(pos: Int)
    }

    inner class ItemListViewHolder(private val mBinding: AdapterFilesBinding) : BaseViewHolder(
        mBinding.root
    ) {
        var data: AttachFileModel? = null
        override fun onBind(position: Int) {
            val blog = items!![position]
            setPost(blog)
            mBinding.itemMediaCloseButtonImage.setOnClickListener { view: View? ->
                mListener!!.onDeleteItemImage(
                    position
                )
            }
            mBinding.executePendingBindings()
        }

        private fun setPost(post: AttachFileModel) {
            val extension: String?
            if (post.type != null) {
                extension = post.type
            } else {
                extension = post.originalPath!!.substring(post.originalPath!!.lastIndexOf("."))
                post.type = extension.replace(".", "")
            }
            if (extension!!.contains("jpg") ||
                extension.contains("JPG") ||
                extension.contains("jpeg") ||
                extension.contains("JPEG") ||
                extension.contains("png") ||
                extension.contains("PNG") ||
                extension.contains("gif") ||
                extension.contains("GIF")
            ) {
                mBinding.imageLy.visibility = View.VISIBLE
                if (post.cameraImageUri != null) {
                    Glide.with(mContext)
                        .load(post.cameraImageUri)
                        .into(mBinding.thumbnailGambar)
                } else if (post.queryUri != null) {
                    Glide.with(mContext)
                        .load(Uri.parse(post.queryUri))
                        .into(mBinding.thumbnailGambar)
                } else {
                    if (post.previewThumbnail != null && post.originalPath != null) Glide.with(
                        mContext
                    )
                        .load(Uri.fromFile(File(post.previewThumbnail)))
                        .error(R.drawable.ef_image_placeholder)
                        .into(mBinding.thumbnailGambar) else if (post.originalPath == null) Glide.with(
                        mContext
                    )
                        .load(R.drawable.ic_baseline_broken_image_24)
                        .error(R.drawable.ef_image_placeholder)
                        .into(mBinding.thumbnailGambar) else Glide.with(mContext)
                        .load(Uri.fromFile(File(post.originalPath)))
                        .error(R.drawable.ef_image_placeholder)
                        .into(mBinding.thumbnailGambar)
                }
            } else {
                when (extension) {
                    "doc", "docx", "rtf" -> {
                        mBinding.docLy.visibility = View.VISIBLE
                        mBinding.docImage.setImageResource(R.drawable.ic_file_word_solid)
                        mBinding.namaFileDoc.text = post.fileName
                    }
                    "xls", "xlsx" -> {
                        mBinding.docLy.visibility = View.VISIBLE
                        mBinding.docImage.setImageResource(R.drawable.ic_file_excel_solid)
                        mBinding.namaFileDoc.text = post.fileName
                    }
                    "ppt", "pptx" -> {
                        mBinding.docLy.visibility = View.VISIBLE
                        mBinding.docImage.setImageResource(R.drawable.ic_file_powerpoint_solid_new)
                        mBinding.namaFileDoc.text = post.fileName
                    }
                    "pdf" -> {
                        mBinding.docLy.visibility = View.VISIBLE
                        mBinding.docImage.setImageResource(R.drawable.ic_file_pdf_solid)
                        mBinding.namaFileDoc.text = post.fileName
                    }
                    else -> {
                        mBinding.docLy.visibility = View.VISIBLE
                        mBinding.docImage.setImageResource(R.drawable.ic_file_solid)
                        mBinding.namaFileDoc.text = post.fileName
                    }
                }
            }
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemBlogEmptyViewBinding) : BaseViewHolder(
        mBinding.root
    ), BlogEmptyItemViewModel.BlogEmptyItemViewModelListener {
        override fun onBind(position: Int) {
            if (items!!.isEmpty() && isNetworkConnected) {
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

    val isEmpty: Boolean
        get() = items!!.isEmpty()
    val isNotEmpty: Boolean
        get() = !isEmpty

    fun getItems(): List<AttachFileModel>? {
        return items
    }

    fun updateCompressedPath(name: String, path: String?) {
        for (item in items!!) {
            if (item.fileName == name) {
                item.compressedPath = path
            }
        }
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_LOAD = 2
    }
}