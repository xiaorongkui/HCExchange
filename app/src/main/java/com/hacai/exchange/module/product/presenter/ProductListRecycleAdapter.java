package com.hacai.exchange.module.product.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.ProductInfo;
import com.hacai.exchange.bean.TextShowInfo;
import com.hacai.exchange.common.CommonRecylerViewHolder;
import com.hacai.exchange.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class ProductListRecycleAdapter<T> extends BaseRecylerAdapter {
    private Context mContext;
    private List<ProductInfo.ObjBean.BorrowItemListBean> datas;

    public ProductListRecycleAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = (List<ProductInfo.ObjBean.BorrowItemListBean>) datas;
        this.mContext = context;
    }

    @Override
    public void convert(CommonRecylerViewHolder holder, int position) {
        ProductInfo.ObjBean.BorrowItemListBean borrowItemListBean = datas.get(position);
        int status = 1;
        try {
            status = Integer.parseInt(borrowItemListBean.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView product_item_buying_title_tv = holder.getTextView(R.id
                .product_item_buying_title_tv);
        TextView product_item_buying_tag_tv = holder.getTextView(R.id.product_item_buying_tag_tv);
        ProgressBar product_list_prg_bar = holder.getView(R.id.product_list_prg_bar);
        TextView product_list_year_rate_show_tv = holder.getTextView(R.id
                .product_list_year_rate_show_tv);
        TextView product_list_year_rate_give_tv = holder.getTextView(R.id
                .product_list_year_rate_give_tv);
        TextView product_list_payment_method_tv = holder.getTextView(R.id
                .product_list_payment_method_tv);
        TextView product_list_deadline_tv = holder.getTextView(R.id.product_list_deadline_tv);
        TextView product_list_investment_money_tv = holder.getTextView(R.id
                .product_list_investment_money_tv);
        TextView product_list_available_investment_tv = holder.getTextView(R.id
                .product_list_available_investment_tv);
        TextView product_list_buying_bt = holder.getTextView(R.id.product_list_buying_bt);


        int shadowcolor = CommonUtil.getColor(R.color.text_normal_hint_color);
        List<TextShowInfo> textShowInfos = new ArrayList<>();

        switch (status) {
            case 1:
                product_list_buying_bt.setText("立即购买");
                textShowInfos.clear();
                textShowInfos.add(new TextShowInfo(CommonUtil.format2Point(borrowItemListBean
                        .getBaseApr()), R.color.bt_end_color, R.dimen.text_size_24));
                textShowInfos.add(new TextShowInfo("%", R.color.bt_end_color, R.dimen
                        .text_size_13));
                product_list_year_rate_show_tv.setText(CommonUtil.setSpannableText(textShowInfos));
                int giveApr = (int) borrowItemListBean.getGiveApr();
                if (giveApr > 0) {
                    product_list_year_rate_give_tv.setText("+" + giveApr + "%");
                } else {
                    product_list_year_rate_give_tv.setVisibility(View.GONE);
                }

                textShowInfos.clear();
                textShowInfos.add(new TextShowInfo(borrowItemListBean.getTimeLimit() + "", R
                        .color.text_normal_color, R.dimen.text_size_24));
                textShowInfos.add(new TextShowInfo("天期限", R.color.text_normal_color, R.dimen
                        .text_size_13));
                product_list_deadline_tv.setText(CommonUtil.setSpannableText(textShowInfos));

                textShowInfos.clear();
                textShowInfos.add(new TextShowInfo("可投", R.color.text_normal_color, R.dimen
                        .text_size_13));
                textShowInfos.add(new TextShowInfo(CommonUtil.format2PointMillion
                        (borrowItemListBean.getBalance()), R.color.bt_end_color, R.dimen
                        .text_size_13));
                textShowInfos.add(new TextShowInfo("万元", R.color.text_normal_color, R.dimen
                        .text_size_13));
                product_list_available_investment_tv.setText(CommonUtil.setSpannableText
                        (textShowInfos));

                int precent = 0;
                try {
                    precent = Integer.parseInt(borrowItemListBean.getSchedule());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                product_list_prg_bar.setProgress(precent);
                break;
            case 3:
            case 7:
                product_list_buying_bt.setText(status == 3 ? "已售罄" : "已还款");
                product_item_buying_title_tv.setTextColor(shadowcolor);
                product_item_buying_tag_tv.setTextColor(shadowcolor);

                textShowInfos.clear();
                textShowInfos.add(new TextShowInfo(CommonUtil.format2Point(borrowItemListBean
                        .getApr()), R.color.text_normal_hint_color, R.dimen.text_size_24));
                textShowInfos.add(new TextShowInfo("%", R.color.text_normal_hint_color, R.dimen
                        .text_size_13));
                product_list_year_rate_show_tv.setText(CommonUtil.setSpannableText(textShowInfos));
                int giveAprOut = (int) borrowItemListBean.getGiveApr();
                if (giveAprOut > 0) {
                    product_list_year_rate_give_tv.setText("+" + giveAprOut + "%");
                } else {
                    product_list_year_rate_give_tv.setVisibility(View.GONE);
                }


                textShowInfos.clear();
                textShowInfos.add(new TextShowInfo(borrowItemListBean.getTimeLimit() + "", R
                        .color.text_normal_hint_color, R.dimen.text_size_24));
                textShowInfos.add(new TextShowInfo("天期限", R.color.text_normal_hint_color, R.dimen
                        .text_size_13));
                product_list_deadline_tv.setText(CommonUtil.setSpannableText(textShowInfos));

                textShowInfos.clear();
                textShowInfos.add(new TextShowInfo("可投", R.color.text_normal_hint_color, R.dimen
                        .text_size_13));
                textShowInfos.add(new TextShowInfo(CommonUtil.format2PointMillion
                        (borrowItemListBean.getBalance()), R.color.text_normal_hint_color, R
                        .dimen.text_size_13));
                textShowInfos.add(new TextShowInfo("元", R.color.text_normal_hint_color, R.dimen
                        .text_size_13));
                product_list_available_investment_tv.setText(CommonUtil.setSpannableText
                        (textShowInfos));

                product_list_payment_method_tv.setTextColor(shadowcolor);
                product_list_investment_money_tv.setTextColor(shadowcolor);
                product_list_prg_bar.setProgress(0);
                product_list_buying_bt.setBackgroundColor(shadowcolor);
                break;
        }


        product_item_buying_title_tv.setText(borrowItemListBean.getName());
        product_item_buying_tag_tv.setText(borrowItemListBean.getShowBorrowType());

        product_list_payment_method_tv.setText(borrowItemListBean.getRepaymentTypeMsg());
        product_list_investment_money_tv.setText(CommonUtil.format0Point(borrowItemListBean
                .getLowestAccount()) + "元起投");

    }
}
