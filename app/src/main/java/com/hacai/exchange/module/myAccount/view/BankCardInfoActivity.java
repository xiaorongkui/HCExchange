package com.hacai.exchange.module.myAccount.view;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.BankCardInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.utils.CommonUtil;

/**
 * Created by lenovo on 2017/12/20.
 */

public class BankCardInfoActivity extends BaseActivity {

    private TextView title_header_tv;
    private BankCardInfo.ObjBean bankCardInfo;
    private ImageView bank_card_icon;
    private TextView bank_info_name_tv;
    private TextView bank_card_info_name_tv;
    private TextView bank_card_info_num_tv;
    private TextView bank_card_limit_tv;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        bank_card_icon = (ImageView) findViewById(R.id.bank_card_icon);
        bank_info_name_tv = (TextView) findViewById(R.id.bank_info_name_tv);
        bank_card_info_name_tv = (TextView) findViewById(R.id.bank_card_info_name_tv);
        bank_card_info_num_tv = (TextView) findViewById(R.id.bank_card_info_num_tv);
        bank_card_limit_tv = (TextView) findViewById(R.id.bank_card_limit_tv);
    }

    @Override
    protected void initData() {
        bankCardInfo = getIntent().getParcelableExtra(Constant.START_ACTIVITY_PARCELABLE_1);

        String logo = bankCardInfo.getLogo();
        if (!TextUtils.isEmpty(logo) && !logo.startsWith("http")) {
            if (logo.startsWith("/")) {
                logo = logo.substring(1, logo.length());
            }
        }

        Glide.with(mContext).load(AppNetConfig.baseUrl + logo).placeholder(R.mipmap
                .img_bank_logo).diskCacheStrategy(DiskCacheStrategy.ALL).into(bank_card_icon);
        bank_info_name_tv.setText(bankCardInfo.getBankName());
        bank_card_info_num_tv.setText(CommonUtil.formatBankcard(bankCardInfo.getCardNo()));
        bank_card_limit_tv.setText(bankCardInfo.getBankLimit());
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.bank_card_manager));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_card_info;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }
}
