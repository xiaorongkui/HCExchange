package com.hacai.exchange.module.home.presenter;

import android.content.Context;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.BidRecodeInfo;
import com.hacai.exchange.common.CommonRecylerViewHolder;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.DateUtils;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class ProductDetailBidRecAdapter<T> extends BaseRecylerAdapter {
    private Context mContext;
    private List<BidRecodeInfo.ObjBean.BorrowTenderItemListBean> datas;

    public ProductDetailBidRecAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = (List<BidRecodeInfo.ObjBean.BorrowTenderItemListBean>) datas;
        this.mContext = context;
    }

    @Override
    public void convert(CommonRecylerViewHolder holder, int position) {
        TextView capital_status_name_tv = holder.getTextView(R.id.capital_status_name_tv);
        TextView capital_date_tv = holder.getTextView(R.id.capital_date_tv);
        TextView capital_money_tv = holder.getTextView(R.id.capital_money_tv);
        capital_status_name_tv.setText(datas.get(position).getUsername());
        capital_date_tv.setText(DateUtils.millisToDateTime_1(datas.get(position).getCreateDate()));
        capital_money_tv.setText(CommonUtil.formatMoney(datas.get(position).getAccount()) + "元");
    }
}
