package com.hacai.exchange.module.myAccount.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.BankChannelInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.CommonRecylerViewHolder;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class BankChannelRecAdapter<T> extends BaseRecylerAdapter {
    private Context mContext;
    private List<BankChannelInfo.ObjBean> datas;

    public BankChannelRecAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        this.datas = (List<BankChannelInfo.ObjBean>) datas;
        this.mContext = context;
    }

    @Override
    public void convert(CommonRecylerViewHolder holder, int position) {
        BankChannelInfo.ObjBean objBean = datas.get(position);
        ImageView bank_card_channel_iv = holder.getImageView(R.id.bank_card_channel_iv);
        TextView bank_channel_name = holder.getTextView(R.id.bank_channel_name);
        TextView bank_card_channel_limit_tv = holder.getTextView(R.id.bank_card_channel_limit_tv);

        bank_channel_name.setText(objBean.getName());
        bank_card_channel_limit_tv.setText(objBean.getDescription());
        String logo = objBean.getLogo();
        if (!TextUtils.isEmpty(logo) && !logo.startsWith("http")) {
            if (logo.startsWith("/")) {
                logo = logo.substring(1, logo.length());
            }
        }

        Glide.with(mContext).load(AppNetConfig.baseUrl + logo).placeholder(R.mipmap
                .img_bank_logo).diskCacheStrategy(DiskCacheStrategy.ALL).into(bank_card_channel_iv);
    }
}
