package com.hacai.exchange.module.myAccount.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;

/**
 * Created by lenovo on 2017/12/19.
 */

public class RechargeSuccessActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_header_tv;
    private Button recharge_success_bt;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        recharge_success_bt = (Button) findViewById(R.id.recharge_success_bt);
        recharge_success_bt.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.myaccount_withdrawals));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_success;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge_success_bt:
                CommonUtil.gotoActivity(mContext, RechargeSuccessActivity.class);
                AppManager.getInstance().removeCurrent();
                break;
        }
    }
}
