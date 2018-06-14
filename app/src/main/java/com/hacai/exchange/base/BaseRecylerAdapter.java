package com.hacai.exchange.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hacai.exchange.common.CommonRecylerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rongkui.xiao on 2017/3/29.<br/>
 * <b>notice:在使用此Adapter时，要给填充的布局跟节点最好加上id<b/>
 *
 * @description
 */
public abstract class BaseRecylerAdapter<T> extends RecyclerView.Adapter<CommonRecylerViewHolder> implements View
        .OnLongClickListener, View.OnClickListener {
    protected List<T> mDatas = new ArrayList<T>();
    private int mLayoutId;
    private LayoutInflater mLayoutInflater;
    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;
    private RecyclerView mRecyclerView;
    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    public BaseRecylerAdapter(Context context, List<T> datas, int layoutId) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        this.mDatas = datas;
    }

    @Override
    public CommonRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_FOOTER) {
            view = VIEW_FOOTER;
        } else if (viewType == TYPE_HEADER) {
            view = VIEW_HEADER;
        } else {
            view = mLayoutInflater.inflate(mLayoutId, null, false);
        }
        if (mOnItemClickLitener != null) view.setOnClickListener(this);
        if (mOnItemLongClickLitener != null) view.setOnLongClickListener(this);
        CommonRecylerViewHolder holder = new CommonRecylerViewHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public final void onBindViewHolder(CommonRecylerViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            holder.getHolderView().setTag(position);
            convert(holder, position);
        }
    }

    public abstract void convert(CommonRecylerViewHolder holder, int position);

    public T getItem(int positon) {
        return mDatas != null && mDatas.size() > positon ? mDatas.get(positon) : null;
    }

    @Override
    public int getItemCount() {
        int count = (mDatas == null ? 0 : mDatas.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    public void addItem(T item, boolean isNotify) {
        mDatas.add(item);
        if (isNotify) notifyDataSetChanged();
    }

    public void addItem(T item) {
        addItem(item, true);
    }

    public void addAllItem(List<T> items, boolean isNotify) {
        mDatas.addAll(items);
        if (isNotify) notifyDataSetChanged();
    }

    public void addAllItem(List<T> items) {
        addAllItem(items, true);
    }

    public void clearItems() {
        mDatas.clear();
    }

    public void addAllAndClear(List<T> items) {
        clearItems();
        addAllItem(items);
    }

    public void destroyAdapter() {
        mDatas.clear();
    }


    /**
     * 点击事件的 处理   start
     */

    private OnItemClickLitener mOnItemClickLitener;
    private OnItemLongClickLitener mOnItemLongClickLitener;

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        if (mOnItemClickLitener != null) mOnItemClickLitener.onItemClick(view, position);
    }

    @Override
    public boolean onLongClick(View view) {
        int position = (Integer) view.getTag();
        if (mOnItemLongClickLitener != null) {
            mOnItemLongClickLitener.onItemLongClick(view, position);
            return true;
        }
        return false;
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickLitener {
        void onItemLongClick(View view, int position);
    }

    /**
     * 该方法需要在setAdapter之前调用
     */
    public BaseRecylerAdapter<T> setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
        return this;
    }

    /**
     * 该方法需要在setAdapter之前调用
     */
    public BaseRecylerAdapter<T> setOnLongItemClickLitener(OnItemLongClickLitener mOnItemLongClickLitener) {
        this.mOnItemLongClickLitener = mOnItemLongClickLitener;
        return this;
    }

    public void removeHeaderView() {
        VIEW_HEADER = null;
        notifyItemInserted(0);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void removeFooterView() {
        VIEW_FOOTER = null;
        notifyItemInserted(getItemCount() - 1);
        notifyDataSetChanged();
    }

    public void addFooterView(View footerView) {
        footerView.setClickable(false);
        footerView.setEnabled(false);
        if (haveFooterView()) {
//            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }

    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup = ((GridLayoutManager) layoutManager)
                    .getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ? ((GridLayoutManager) layoutManager)
                            .getSpanCount() : 1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    public boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
