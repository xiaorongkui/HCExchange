package com.hacai.exchange.module.myAccount.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.InvestmentRecodeInfo;
import com.hacai.exchange.common.CommonRecylerViewHolder;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.DateUtils;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class InvestmentRecodeRecAdapter<T> extends BaseRecylerAdapter {
    private Context mContext;
    private List<InvestmentRecodeInfo.ObjBean.UserTenderListBean> datas;

    public InvestmentRecodeRecAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = (List<InvestmentRecodeInfo.ObjBean.UserTenderListBean>) datas;
        this.mContext = context;
    }

    @Override
    public void convert(CommonRecylerViewHolder holder, int position) {
        InvestmentRecodeInfo.ObjBean.UserTenderListBean userTenderListBean = datas.get(position);
        if (position == datas.size() - 1) {
            holder.getView(R.id.investment_recode_bottom_line).setVisibility(View.INVISIBLE);
        } else {
            holder.getView(R.id.investment_recode_bottom_line).setVisibility(View.VISIBLE);
        }
        if (userTenderListBean == null) return;
        TextView investment_recode_name_tv = holder.getTextView(R.id.investment_recode_name_tv);
        ImageView investment_recode_right_iv = holder.getImageView(R.id.investment_recode_right_iv);
        TextView investment_money_show_tv = holder.getTextView(R.id.investment_money_show_tv);
        TextView investment_profit_show_tv = holder.getTextView(R.id.investment_profit_show_tv);
        TextView investment_repayment_date_show_tv = holder.getTextView(R.id
                .investment_repayment_date_show_tv);

        investment_recode_name_tv.setText(userTenderListBean.getBorrowName());
        investment_money_show_tv.setText(CommonUtil.formatMoney(userTenderListBean
                .getTenderAccount()));
        investment_profit_show_tv.setText(CommonUtil.formatMoney(userTenderListBean.getInterest()));
        investment_repayment_date_show_tv.setText(DateUtils.millisToDateTime_3(userTenderListBean
                .getCreateDate()));
    }
}
