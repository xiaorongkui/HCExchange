package com.hacai.exchange.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hacai.exchange.common.CommonViewHolder;

import java.util.List;

/**
 * Created by Rongkui.xiao on 2017/4/5.
 *
 * @description
 */

public abstract class BaseListAdpater<T> extends BaseAdapter {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<T> mDatas;
    private int layoutId;

    public BaseListAdpater(Context context, List<T> datas, int layoutId) {
        layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.getCommonViewHolder(mContext, layoutId, position, convertView,
                parent);
        convert(holder, position);
        return holder.getConvertView();
    }

    /**
     * 填充holder里面控件的数据
     *
     * @param holder
     * @param position
     */
    public abstract void convert(CommonViewHolder holder, int position);

    public void setList(List<T> list) {
        this.mDatas = list;
    }

    public void clear() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        if (list != null) {
            this.mDatas.addAll(list);
            notifyDataSetChanged();
        }
    }
}
