package com.hacai.exchange.module.home.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.HomeProductInfo;
import com.hacai.exchange.bean.TextShowInfo;
import com.hacai.exchange.common.CommonRecylerViewHolder;
import com.hacai.exchange.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class HomeBuyingAdapter<T> extends BaseRecylerAdapter {
    private Context mContext;
    private List<HomeProductInfo.IndexHotBorrowsBean> datas;

    public HomeBuyingAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public void convert(CommonRecylerViewHolder holder, int position) {
        HomeProductInfo.IndexHotBorrowsBean indexHotBorrowsBean = datas.get(position);
        if (indexHotBorrowsBean == null) return;
        TextView home_item_buying_title_tv = holder.getTextView(R.id.home_item_buying_title_tv);
        TextView home_buying_year_rate_show_tv = holder.getTextView(R.id
                .home_buying_year_rate_show_tv);
        TextView home_buying_deadline__tv = holder.getTextView(R.id.home_buying_deadline__tv);
        TextView home_buying_start_money__tv = holder.getTextView(R.id.home_buying_start_money__tv);
        TextView home_buying_year_rate_float_show_tv = holder.getTextView(R.id
                .home_buying_year_rate_float_show_tv);

        home_item_buying_title_tv.setText(indexHotBorrowsBean.getName());

        String format2Point = CommonUtil.format2Point(indexHotBorrowsBean.getBaseRate() + "");
        home_buying_year_rate_show_tv.setText(format2Point + "%");

        List<TextShowInfo> textShowInfoDates = new ArrayList<>();
        textShowInfoDates.add(new TextShowInfo("期限：", R.color.text_normal_hint_color, R.dimen
                .text_size_13));
        textShowInfoDates.add(new TextShowInfo(indexHotBorrowsBean.getTimeLimit() + "天", R.color
                .text_normal_color, R.dimen.text_size_13));

        home_buying_deadline__tv.setText(CommonUtil.setSpannableText(textShowInfoDates));

        List<TextShowInfo> textShowStartInfos = new ArrayList<>();
        String format0Point1 = CommonUtil.format0Point(indexHotBorrowsBean.getLowestAccount() + "");
        textShowStartInfos.add(new TextShowInfo("起投：", R.color.text_normal_hint_color, R.dimen
                .text_size_13));
        textShowStartInfos.add(new TextShowInfo(format0Point1 + "元", R.color.text_normal_color, R
                .dimen.text_size_13));
        home_buying_start_money__tv.setText(CommonUtil.setSpannableText(textShowStartInfos));


        int givenRate = (int) indexHotBorrowsBean.getGivenRate();
        if (givenRate > 0) {
            home_buying_year_rate_float_show_tv.setText("+" + givenRate + "%");
        } else {
            home_buying_year_rate_float_show_tv.setVisibility(View.GONE);
        }
    }
}
