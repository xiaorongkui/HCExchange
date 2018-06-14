package com.hacai.exchange.module.myAccount.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hacai.exchange.MainActivity;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;

/**
 * Created by lenovo on 2017/12/19.
 */

public class WithDrawSuccessActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_header_tv;
    private Button with_drawals_success_bt;
    private String whereFrom;

    @Override
    protected void initView() {
        whereFrom = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_1);
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        with_drawals_success_bt = (Button) findViewById(R.id.with_drawals_success_bt);
        with_drawals_success_bt.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        if (!TextUtils.isEmpty(whereFrom)) {
            switch (whereFrom) {
                case "WithdrawalsActivity":
                    title_header_tv.setText(CommonUtil.getString(R.string.myaccount_withdrawals));
                    break;
                case "PaymentActivity":
                    title_header_tv.setText(CommonUtil.getString(R.string.buy));
                    break;
            }
        } else {
            title_header_tv.setText(CommonUtil.getString(R.string.myaccount_withdrawals));
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_with_drawals_success;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.with_drawals_success_bt:
                if (!TextUtils.isEmpty(whereFrom)) {
                    switch (whereFrom) {
                        case "WithdrawalsActivity":
                            CommonUtil.gotoActivity(mContext, MainActivity.class);
                            break;
                        case "PaymentActivity":
                            CommonUtil.gotoActivity(mContext, MainActivity.class);
                            break;
                    }
                } else {
                    AppManager.getInstance().removeCurrent();
                }

                break;
        }
    }
}
