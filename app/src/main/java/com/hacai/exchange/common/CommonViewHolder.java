package com.hacai.exchange.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonViewHolder {
    public View convertView;
    private int mPosition;
    private SparseArray<View> views = new SparseArray<>();

    public CommonViewHolder(Context context, int position, int layoutId, ViewGroup parent) {
        super();
        this.mPosition = position;
        convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }


    // 类型推导 通过调用的要求推导
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        if (views.get(id) == null) {
            views.put(id, convertView.findViewById(id));
        }
        return (T) views.get(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id, Class<T> klass) {
        return (T) getView(id);
    }

    public static CommonViewHolder getCommonViewHolder(Context context, int layoutId, int position, View convertView,
                                                       ViewGroup parent) {
        if (convertView == null) {
            return new CommonViewHolder(context, position, layoutId, parent);
        } else {
            CommonViewHolder holder = (CommonViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 为textview类型填充内容
     * @param resourceId
     * @param text
     * @return CommonViewHolder
     */
    public CommonViewHolder setText(int resourceId,CharSequence text ) {
        ((TextView) getView(resourceId)).setText(text);
        return this;
    }
    public CommonViewHolder setText(int resourceId,int resid ) {
        ((TextView) getView(resourceId)).setText(resid);
        return this;
    }
    /**
     * 为ImageView设置Bitmap
     * @param resourceId
     * @param bm
     * @return
     */
    public CommonViewHolder setBitmap(int resourceId,Bitmap bm) {
        ((ImageView)getView(resourceId)).setImageBitmap(bm);
        return  this;
    }
    public CommonViewHolder setImageDrawable(int resourceId,Drawable drawable) {
        ((ImageView)getView(resourceId)).setImageDrawable(drawable);
        return  this;
    }
    public CommonViewHolder setImageResource(int resourceId,int resId) {
        ((ImageView)getView(resourceId)).setImageResource(resId);
        return  this;
    }


    public View getConvertView() {
        return convertView;
    }
    /**
     * 获取当前item的位置
     * @return
     */
    public int getPosition() {
        return mPosition;
    }
}
