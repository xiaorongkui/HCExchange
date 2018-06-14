package com.hacai.exchange.module.myAccount.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lenovo on 2017/12/18.
 */

public class BindCardActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private TextView title_header_tv;
    private RelativeLayout bind_card_init_rl;
    private View bind_card_name_ll;
    private View bind_card_identy_num_ll;
    private View bind_card_num_ll;
    private View bind_card_reserved_phone_ll;
    private View bind_card_reserved_code_ll;
    private TextView bind_card_identy_num_tv;
    private TextView bind_card_identy_num_et;
    private TextView bind_card_name_tv;
    private TextView bind_card_name_et;
    private TextView bind_card_num_tv;
    private TextView bind_card_num_et;
    private TextView bind_card_reserved_phone_tv;
    private TextView bind_card_reserved_phone_et;
    private TextView bind_card_reserved_code_tv;
    private TextView bind_card_reserved_code_et;
    private TextView bind_card_reserved_code_get_tv;
    private Subscription subscribe;
    private ImageView bind_card_accoun_channel_iv;
    private TextView bind_card_accoun_channel_tv;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        bind_card_name_ll = findViewById(R.id.bind_card_name_ll);
        bind_card_name_tv = (TextView) bind_card_name_ll.findViewById(R.id.common_edit_item_tv);
        bind_card_name_et = (TextView) bind_card_name_ll.findViewById(R.id.common_edit_item_et);

        bind_card_identy_num_ll = findViewById(R.id.bind_card_identy_num_ll);
        bind_card_identy_num_tv = (TextView) bind_card_identy_num_ll.findViewById(R.id.common_edit_item_tv);
        bind_card_identy_num_et = (TextView) bind_card_identy_num_ll.findViewById(R.id.common_edit_item_et);

        bind_card_num_ll = findViewById(R.id.bind_card_num_ll);
        bind_card_num_tv = (TextView) bind_card_num_ll.findViewById(R.id.common_edit_item_tv);
        bind_card_num_et = (TextView) bind_card_num_ll.findViewById(R.id.common_edit_item_et);

        bind_card_reserved_phone_ll = findViewById(R.id.bind_card_reserved_phone_ll);
        bind_card_reserved_phone_tv = (TextView) bind_card_reserved_phone_ll.findViewById(R.id.common_edit_item_tv);
        bind_card_reserved_phone_et = (TextView) bind_card_reserved_phone_ll.findViewById(R.id.common_edit_item_et);

        bind_card_reserved_code_ll = findViewById(R.id.bind_card_reserved_code_ll);
        bind_card_reserved_code_tv = (TextView) bind_card_reserved_code_ll.findViewById(R.id.common_edit_item_tv);
        bind_card_reserved_code_et = (TextView) bind_card_reserved_code_ll.findViewById(R.id.common_edit_item_et);
        bind_card_reserved_code_get_tv = (TextView) bind_card_reserved_code_ll.findViewById(R.id.common_edit_item_code_tv);

        bind_card_accoun_channel_tv = (TextView) findViewById(R.id.bind_card_accoun_channel_tv);
        bind_card_accoun_channel_iv = (ImageView) findViewById(R.id.bind_card_accoun_channel_iv);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.open_payment_account));
        bind_card_name_tv.setText(CommonUtil.getString(R.string.real_name));
        bind_card_name_et.setHint(CommonUtil.getString(R.string.real_name_input));

        bind_card_identy_num_tv.setText(CommonUtil.getString(R.string.identity_num));
        bind_card_identy_num_et.setHint(CommonUtil.getString(R.string.identity_num_input));

        bind_card_num_tv.setText(CommonUtil.getString(R.string.bind_bank_card_num));
        bind_card_num_et.setHint(CommonUtil.getString(R.string.bind_bank_card_num_input));

        bind_card_reserved_phone_tv.setText(CommonUtil.getString(R.string.reserved_information_phone));
        bind_card_reserved_phone_et.setHint(CommonUtil.getString(R.string.reserved_information_phone_input));

        bind_card_reserved_code_tv.setText(CommonUtil.getString(R.string.reserved_information_code));
        bind_card_reserved_code_et.setHint(CommonUtil.getString(R.string.reserved_information_code_input));
        bind_card_reserved_code_get_tv.setVisibility(View.VISIBLE);
        bind_card_reserved_code_get_tv.setOnClickListener(this);
        bind_card_accoun_channel_iv.setOnClickListener(this);
        bind_card_accoun_channel_tv.setOnClickListener(this);
        bind_card_name_et.setOnFocusChangeListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_card;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_edit_item_code_tv:
                setTimeCountDown(60);
                break;
            case R.id.bind_card_accoun_channel_tv:
            case R.id.bind_card_accoun_channel_iv:
                //todo 银行渠道
                CommonUtil.gotoActivity(mContext,BankChannelActivity.class);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ((TextView) v).setCursorVisible(hasFocus);
    }

    private void setTimeCountDown(final int splashTotalCountdownTime) {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        LogUtil.i("increaseTime=" + increaseTime);
                        return splashTotalCountdownTime - increaseTime.intValue();
                    }
                })
                .take(splashTotalCountdownTime + 1).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtil.i("integer=" + integer);

                        if (integer == 0) {
                            bind_card_reserved_code_get_tv.setText(CommonUtil.getString(R.string.get_register_code));
                            bind_card_reserved_code_get_tv.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                            bind_card_reserved_code_get_tv.setEnabled(true);
                        } else {
                            bind_card_reserved_code_get_tv.setText(integer + "s");
                            bind_card_reserved_code_get_tv.setTextColor(CommonUtil.getColor(R.color.text_normal_hint_color));
                            if (bind_card_reserved_code_get_tv.isEnabled())
                                bind_card_reserved_code_get_tv.setEnabled(false);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isUnsubscribed()) subscribe.unsubscribe();
    }
}
