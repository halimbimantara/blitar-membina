package com.wlb.framework.learning.ui.performance.adapters.detailGoalUmam;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wlb.framework.learning.R;
import com.wlb.framework.learning.databinding.AdapterFilesBinding;
import com.wlb.framework.learning.databinding.ItemBlogEmptyViewBinding;
import com.wlb.framework.learning.databinding.ItemBlogLoadViewBinding;
import com.wlb.framework.learning.ui.base.BaseViewHolder;
import com.wlb.framework.learning.ui.feed.blogs.BlogEmptyItemViewModel;
import com.wlb.framework.learning.ui.learning.allList.viewModel.AllItemListViewLoadModel;
import com.wlb.framework.learning.ui.learning.category.model.CategorySubModel;
import com.wlb.framework.learning.ui.learning.model.CategoryModel;
import com.wlb.framework.learning.ui.performance.models.goals.data.AttachFileModel;
import com.wlb.framework.learning.ui.performance.viewmodels.goals.ItemAllAttachViewModel;
import com.wlb.framework.learning.utils.NetworkUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ListAttachmentImageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_LOAD = 2;

    boolean drop = true;
    boolean allCheck;
    ArrayList<CategoryModel.ListDataCategory> listDataCategories = new ArrayList<CategoryModel.ListDataCategory>();
    ArrayList<CategorySubModel.DataEntity> category1 = new ArrayList<CategorySubModel.DataEntity>();
    Context mContext;
    //    private List<GetListDetailTaskQuery.Performance_task> items = Collections.emptyList();
    private List<AttachFileModel> items = new ArrayList<>();
    private AllTrendingListListener mListener;

    public ListAttachmentImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setPosts(List<AttachFileModel> dataList) {
        this.items = dataList;
        this.notifyDataSetChanged();
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(mContext.getApplicationContext());
    }

    @Override
    public int getItemCount() {
//        if (items != null && items.size() > 0) {
//            return items.size();
//        } else {
//            return 1;
//        }
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items != null && !items.isEmpty() && isNetworkConnected()) {
            return VIEW_TYPE_NORMAL;
        } else if (!isNetworkConnected() || items.isEmpty()) {
            return VIEW_TYPE_EMPTY;
        } else if (items != null && !items.isEmpty() && !isNetworkConnected()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_LOAD;
        }


    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                AdapterFilesBinding blogViewBinding = AdapterFilesBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new ListAttachmentImageAdapter.ItemListViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
                ItemBlogEmptyViewBinding blogViewBinding1 = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new ListAttachmentImageAdapter.EmptyViewHolder(blogViewBinding1);
            default:
                ItemBlogLoadViewBinding blogViewBinding2 = ItemBlogLoadViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new ListAttachmentImageAdapter.LoadViewHolder(blogViewBinding2);
        }
    }

    public void addItems(List<AttachFileModel> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(AttachFileModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public AttachFileModel getItem(int position) {
        return items.get(position);
    }

    public void selectAll() {
        allCheck = true;
        notifyDataSetChanged();
    }

    public void unSelectAll() {
        allCheck = false;
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
    }

    public void setListener(AllTrendingListListener listener) {
        this.mListener = listener;
    }

    public interface AllTrendingListListener {
        void onDeleteItemImage(int pos);
    }

    public static class LoadViewHolder extends BaseViewHolder implements AllItemListViewLoadModel.AllItemListViewLoadListener {

        private ItemBlogLoadViewBinding mBinding;

        public LoadViewHolder(ItemBlogLoadViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            AllItemListViewLoadModel emptyItemViewModel = new AllItemListViewLoadModel(this);
//            mBinding.shimmerViewHome.startShimmerAnimation();
            mBinding.setViewModel(emptyItemViewModel);

        }

        @Override
        public void onRetryClick() {

        }
    }

    public class ItemListViewHolder extends BaseViewHolder implements ItemAllAttachViewModel.AllItemSubCategoryListener {

        CategoryModel.Data course;
        AttachFileModel data;
        private AdapterFilesBinding mBinding;
        private ItemAllAttachViewModel mBlogItemViewModel;

        public ItemListViewHolder(AdapterFilesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            AttachFileModel blog = items.get(position);
            mBlogItemViewModel = new ItemAllAttachViewModel(data, this);

            mBinding.setViewModel(mBlogItemViewModel);
            setPost(blog);
//            mBinding.nameCategory.setText(blog.getName());


//            mBinding.author.setText(getTutorName(position));
//            mBinding.price.setText(getPrice(blog));
//            mBinding.rating.rate(blog.getAverageRating());

            itemView.setOnClickListener(view -> mBlogItemViewModel.onItemClick(position));

            mBinding.itemMediaCloseButtonImage.setOnClickListener(view -> {

//                mListener.onDeleteItemImage(position);
                mListener.onDeleteItemImage(getAdapterPosition());
//                notifyDataSetChanged();
            });

            mBinding.executePendingBindings();
        }

        void setPost(final AttachFileModel post) {
            String extension;
            if (post.getType() != null) {
                extension = post.getType();
            } else {
                extension = post.getOriginalPath().substring(post.getOriginalPath().lastIndexOf("."));
                post.setType(extension.replace(".", ""));
            }
            if (
                    extension.contains("jpg") ||
                            extension.contains("JPG") ||
                            extension.contains("jpeg") ||
                            extension.contains("JPEG") ||
                            extension.contains("png") ||
                            extension.contains("PNG") ||
                            extension.contains("gif") ||
                            extension.contains("GIF")
            ) {
                mBinding.imageLy.setVisibility(View.VISIBLE);
                if (post.getCameraImageUri() != null) {
                    Glide.with(mContext)
                            .load(post.getCameraImageUri())
                            .into(mBinding.thumbnailGambar);
                } else if (post.getQueryUri() != null) {
                    Glide.with(mContext)
                            .load(Uri.parse(post.getQueryUri()))
                            .into(mBinding.thumbnailGambar);
                } else {
                    if (post.getPreviewThumbnail() != null && post.getOriginalPath() != null)
                        Glide.with(mContext)
                                .load(Uri.fromFile(new File(post.getPreviewThumbnail())))
                                .error(R.drawable.ef_image_placeholder)
                                .into(mBinding.thumbnailGambar);
                    else
                        if (post.getOriginalPath() == null)
                        Glide.with(mContext)
                                .load(R.drawable.ic_baseline_broken_image_24)
                                .error(R.drawable.ef_image_placeholder)
                                .into(mBinding.thumbnailGambar);
                        else
                            Glide.with(mContext)
                                    .load(Uri.fromFile(new File(post.getOriginalPath())))
                                    .error(R.drawable.ef_image_placeholder)
                                    .into(mBinding.thumbnailGambar);
                }
//                Glide.with(mContext).load(Uri.fromFile(new File(post.getPreviewThumbnail()))).into(mBinding.thumbnailGambar);
            } else {
                switch (extension) {
                    case "doc":
                    case "docx":
                    case "rtf":
                        mBinding.docLy.setVisibility(View.VISIBLE);
                        mBinding.docImage.setImageResource(R.drawable.ic_file_word_solid);
                        mBinding.namaFileDoc.setText(post.getFileName());
                        break;
                    case "xls":
                    case "xlsx":
                        mBinding.docLy.setVisibility(View.VISIBLE);
                        mBinding.docImage.setImageResource(R.drawable.ic_file_excel_solid);
                        mBinding.namaFileDoc.setText(post.getFileName());
                        break;
                    case "ppt":
                    case "pptx":
                        mBinding.docLy.setVisibility(View.VISIBLE);
                        mBinding.docImage.setImageResource(R.drawable.ic_file_powerpoint_solid_new);
                        mBinding.namaFileDoc.setText(post.getFileName());
                        break;
                    case "pdf":
                        mBinding.docLy.setVisibility(View.VISIBLE);
                        mBinding.docImage.setImageResource(R.drawable.ic_file_pdf_solid);
                        mBinding.namaFileDoc.setText(post.getFileName());
                        break;
                    default:
                        mBinding.docLy.setVisibility(View.VISIBLE);
                        mBinding.docImage.setImageResource(R.drawable.ic_file_solid);
                        mBinding.namaFileDoc.setText(post.getFileName());
                        break;
                }
            }

//
        }

        @Override
        public void onItemClick(int position) {
//
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements BlogEmptyItemViewModel.BlogEmptyItemViewModelListener {

        private ItemBlogEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemBlogEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (items.isEmpty() && isNetworkConnected()) {
                mBinding.linearLayoutView.setVisibility(View.GONE);
                mBinding.imageViewEmpty.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_baseline_update));
                mBinding.tvMessage.setText(R.string.empty_content);
            } else if (!isNetworkConnected()) {
                mBinding.linearLayoutView.setVisibility(View.GONE);
                mBinding.imageViewEmpty.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cloud_off_24px));
                mBinding.tvMessage.setText(R.string.tap_to_retry);
            }
            BlogEmptyItemViewModel emptyItemViewModel = new BlogEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);

        }


        @Override
        public void onRetry() {
//            notifyDataSetChanged();
//            mListener.onRetryClick();
        }
    }


//    private String getTutorName(int position){
//        if(items.get(position).getCreatorUser() != null){
//            return items.get(position).getCreatorUser().getName();
//        }
//
//        return "";
//    }
//
//    private String getPrice(CourseModelTrending.CommentItem course){
//
//        if(course.getPrice() != null){
//            if(course.getPrice().getPricing() != null){
//                return addRp(course.getPrice().getPricing().getIDR());
//            }
//        }
//        return "";
//    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public List<AttachFileModel> getItems() {
        return items;
    }


    public void updateCompressedPath(String name, String path){
        for(AttachFileModel item: items){
            if(item.getFileName().equals(name)){
                item.setCompressedPath(path);
            }
        }
    }

}