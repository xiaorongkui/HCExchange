package com.hacai.exchange.module.home.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseViewPagerAdapter;
import com.hacai.exchange.bean.HomeProductInfo;
import com.hacai.exchange.utils.CommonUtil;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class HomeNewAdapter<T> extends BaseViewPagerAdapter<T> {
    private final Context mContext;
    private List<HomeProductInfo.IndexBorrowBean> datas;
    private int layoutId;

    public HomeNewAdapter(Context context, List<T> data, int layoutId) {
        super(data);
        this.datas = (List<HomeProductInfo.IndexBorrowBean>) data;
        this.mContext = context;
        this.layoutId = layoutId;
    }

    @Override
    public View newView(int position) {
        View rootView = View.inflate(mContext, layoutId, null);
        HomeProductInfo.IndexBorrowBean indexBorrowBean = datas.get(position);
        Button home_new_immediate_buy_bt = (Button) rootView.findViewById(R.id
                .home_new_immediate_buy_bt);
        TextView home_item_new_title_tv = (TextView) rootView.findViewById(R.id
                .home_item_new_title_tv);

        TextView home_new_year_rate_show_tv = (TextView) rootView.findViewById(R.id
                .home_new_year_rate_show_tv);
        TextView home_item_deadline_date_tv = (TextView) rootView.findViewById(R.id
                .home_item_deadline_date_tv);

        TextView home_item_investment_start_tv = (TextView) rootView.findViewById(R.id
                .home_item_investment_start_tv);
        TextView home_new_year_rate_float_show_tv = (TextView) rootView.findViewById(R.id
                .home_new_year_rate_float_show_tv);
        if (indexBorrowBean != null) {
            home_item_new_title_tv.setText(indexBorrowBean.getName());

            String format2Point = CommonUtil.format2Point(indexBorrowBean.getBaseRate() + "");
            home_new_year_rate_show_tv.setText(format2Point + "%");

            home_item_deadline_date_tv.setText(indexBorrowBean.getTimeLimit() + "天期限");

            String format0Point1 = CommonUtil.format0Point(indexBorrowBean.getLowestAccount() + "");
            home_item_investment_start_tv.setText(format0Point1 + "元起投");

            //        home_new_immediate_buy_bt.setText(datas.get(position).getShowBorrowStatus());

            int givenRate = (int) indexBorrowBean.getGivenRate();
            if (givenRate > 0) {
                home_new_year_rate_float_show_tv.setText("+" + givenRate + "%");
            } else {
                home_new_year_rate_float_show_tv.setVisibility(View.GONE);
            }

        }

        return rootView;
    }
}
