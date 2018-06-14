package com.hacai.exchange.module.myAccount.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.CapitalRecodeInfo;
import com.hacai.exchange.common.CommonRecylerViewHolder;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.DateUtils;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class CapitalRecodeRecAdapter<T> extends BaseRecylerAdapter {
    private Context mContext;
    private List<CapitalRecodeInfo.ObjBean.DatasBean> datas;

    public CapitalRecodeRecAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = (List<CapitalRecodeInfo.ObjBean.DatasBean>) datas;
        this.mContext = context;
    }

    @Override
    public void convert(CommonRecylerViewHolder holder, int position) {
        CapitalRecodeInfo.ObjBean.DatasBean datasBean = datas.get(position);
        if (position == datas.size() - 1) {
            holder.getView(R.id.capital_recode_bottom_line).setVisibility(View.INVISIBLE);
        } else {
            holder.getView(R.id.capital_recode_bottom_line).setVisibility(View.VISIBLE);
        }

        if (datasBean == null) return;
        TextView capital_status_name_tv = holder.getTextView(R.id.capital_status_name_tv);
        TextView capital_date_tv = holder.getTextView(R.id.capital_date_tv);
        TextView capital_money_tv = holder.getTextView(R.id.capital_money_tv);

        capital_status_name_tv.setText(datasBean.getTypeName());
        capital_date_tv.setText(DateUtils.millisToDateTime_1(datasBean.getCreateDate()));
        capital_money_tv.setText(datasBean.getPrefix() + CommonUtil.formatMoney(datasBean
                .getMoney()));

    }
}
