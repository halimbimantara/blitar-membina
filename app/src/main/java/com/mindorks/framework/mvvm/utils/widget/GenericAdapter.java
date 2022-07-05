package com.mindorks.framework.mvvm.utils.widget;

import static android.view.View.GONE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ItemBlogEmptyViewBinding;
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogEmptyItemViewModel;
import com.mindorks.framework.mvvm.utils.NetworkUtils;
import com.mindorks.framework.mvvm.utils.ViewUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class GenericAdapter<T, D> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_LOAD = 2;
    private Context mContext;
    private ArrayList<T> mArrayList;
    private String message = "";
    private int mSelectedItem = -1;
    private boolean isCustomEmptyMessages = false;
    private boolean enableImageEmpty = false;

    public abstract void onBindData(T model, int position, D dataBinding);

    public abstract int getLayoutResId();

    public abstract void onItemClick(T model, int position);

    public abstract void onRetry();

    public abstract void noConnection();

    public abstract String setTitle();

    public void setCustomEmptyMessages(boolean customEmptyMessages) {
        isCustomEmptyMessages = customEmptyMessages;
    }
    //default is show
    public void setEnableImageEmpty(boolean enableImageEmpty) {
        this.enableImageEmpty = enableImageEmpty;
    }

    public int getmSelectedItem() {
        return mSelectedItem;
    }

    public void setmSelectedItem(int mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public GenericAdapter(Context context, ArrayList<T> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
    }


    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(mContext.getApplicationContext());
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_EMPTY:
                ItemBlogEmptyViewBinding blogViewBinding1 = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(blogViewBinding1);
            default:
                ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutResId(), parent, false);
                return new ItemViewHolder(dataBinding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mArrayList != null && !mArrayList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else if (mArrayList != null && isNetworkConnected()) {
            if (mArrayList.isEmpty()) {
                return VIEW_TYPE_EMPTY;
            } else {
                return VIEW_TYPE_NORMAL;
            }
        } else if (!isNetworkConnected() || mArrayList == null) {
            noConnection();
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        holder.itemView.setOnClickListener(view -> {
            if (!mArrayList.isEmpty()) {
                setmSelectedItem(position);
                onItemClick(mArrayList.get(position), position);
            }
        });
    }

    public void clearItems() {
        mArrayList.clear();
    }

    @Override
    public int getItemCount() {
        if (mArrayList != null && mArrayList.size() > 0) {
            return mArrayList.size();
        } else {
            return 1;
        }
    }

    public void addItems(ArrayList<T> arrayList) {
        mArrayList = arrayList;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mArrayList.get(position);
    }

    public class EmptyViewHolder extends BaseViewHolder implements BlogEmptyItemViewModel.BlogEmptyItemViewModelListener {
        private ItemBlogEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemBlogEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mArrayList.isEmpty() && isNetworkConnected()) {
                mBinding.imageViewEmpty.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_baseline_update));
                if (!message.isEmpty()) {
                    if(setTitle().equalsIgnoreCase("Organization Post")){
//                        mBinding.tvMessage.setText(ViewUtils.setValuesOf(mContext, R.string.info_data_empty_posting, "", ""));;
                        mBinding.imageViewEmpty.setVisibility(GONE);
                        mBinding.tvMessage.setVisibility(GONE);
                        mBinding.btnRetry.setVisibility(GONE);
                    }else{
                        mBinding.tvMessage.setText("Sorry, No List");
                    }

                    if(setTitle().equalsIgnoreCase("Code Group")){
                        mBinding.tvMessage.setText(ViewUtils.setValuesOf(mContext, R.string.info_data_empty, "Post", ""));
                        mBinding.btnRetry.setText("Try to post something");
                    }

                    if(setTitle().equalsIgnoreCase("Company Goal")){
                        mBinding.tvMessage.setText("Sorry, No List");
                        mBinding.btnRetry.setText("It seems we can't find any results based on your search");
                    }

                } else {
                    if (isCustomEmptyMessages)
                        mBinding.tvMessage.setText(ViewUtils.setValuesOf(mContext, R.string.search_empty_message, message, ""));
                    else
                        mBinding.tvMessage.setText("Sorry, No List");
                    mBinding.btnRetry.setText("It seems we can't find any results based on your search");
                }
            } else if (!isNetworkConnected()) {
                mBinding.imageViewEmpty.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cloud_off));
                mBinding.tvMessage.setText(mContext.getString(R.string.no_internet));
            }
            if (enableImageEmpty){
                mBinding.imageViewEmpty.setVisibility(GONE);
                mBinding.btnRetry.setVisibility(GONE);
            }
            BlogEmptyItemViewModel emptyItemViewModel = new BlogEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {

        }
    }

    class ItemViewHolder extends BaseViewHolder {
        protected D mDataBinding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mDataBinding = (D) binding;
        }

        @Override
        public void onBind(int position) {
            onBindData(mArrayList.get(position), position, mDataBinding);
        }
    }
}