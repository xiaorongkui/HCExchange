package com.hacai.exchange.module.product.presenter;

import android.content.Context;
import android.view.View;

import com.hacai.exchange.base.BaseViewPagerAdapter;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class ProductListPageAdapter<T> extends BaseViewPagerAdapter {
    private final Context mContext;
    private List<View> views;
    private int layoutId;

    public ProductListPageAdapter(Context context, List<T> data, int layoutId) {
        super(data);
        this.views = (List<View>) data;
        this.mContext = context;
        this.layoutId = layoutId;
    }

    @Override
    public View newView(int position) {
        return views.get(position);
    }
}
