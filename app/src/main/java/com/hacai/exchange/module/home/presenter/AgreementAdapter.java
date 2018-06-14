package com.hacai.exchange.module.home.presenter;

import android.content.Context;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseListAdpater;
import com.hacai.exchange.common.CommonViewHolder;

import java.util.List;

/**
 * Created by Rongkui.xiao on 2017/5/4.
 *
 * @description
 */

public class AgreementAdapter<T> extends BaseListAdpater<T> {
    private final List<T> datas;

    public AgreementAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = datas;
    }

    @Override
    public void convert(CommonViewHolder holder, int position) {
        holder.getView(R.id.payment_agreement_tv, TextView.class).setText(datas.get(position)
                .toString());
    }
}
