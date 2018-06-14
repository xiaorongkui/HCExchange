package com.hacai.exchange.module.myAccount.view;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.utils.CommonUtil;

/**
 * Created by lenovo on 2017/12/18.
 */

public class BindCardInitActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_header_tv;
    private RelativeLayout bind_card_init_rl;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        bind_card_init_rl = (RelativeLayout) findViewById(R.id.bind_card_init_rl);
        bind_card_init_rl.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.bank_card_manager));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_card_init;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_card_init_rl:
                CommonUtil.gotoActivity(mContext, RechargeActivity.class);
                break;
        }
    }
}
