package com.hacai.exchange.module.myAccount.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.CodeInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.presenter.LoginPresenter;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lenovo on 2017/12/20.
 */

public class ExchangePwdForgetOneActivity extends BaseActivity implements View.OnClickListener,
        LoginInterface.ILoginView {

    private static final int EXCHANGE_CODE_CHECK_TAG = 1;
    private static final int GET_EXCHANGE_CODE_TAG = 2;
    private TextView title_header_tv;
    private Button forget_exchange_pwd_next_bt;
    private TextView forget_exchange_pwd_code_tv;
    private Subscription subscribe;
    private LoginPresenter loginPresenter;
    private TextView forget_exchange_pwd_phone_tv;
    private String userPhoneNum;
    private EditText forget_exchange_pwd_item_et;
    private String codeKey;
    private View forget_exchange_pwd_ll;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        forget_exchange_pwd_next_bt = (Button) findViewById(R.id.forget_exchange_pwd_next_bt);
        forget_exchange_pwd_code_tv = (TextView) findViewById(R.id.forget_exchange_pwd_code_tv);
        forget_exchange_pwd_phone_tv = (TextView) findViewById(R.id.forget_exchange_pwd_phone_tv);
        forget_exchange_pwd_item_et = (EditText) findViewById(R.id.forget_exchange_pwd_item_et);
        forget_exchange_pwd_ll = findViewById(R.id.forget_exchange_pwd_ll);

        forget_exchange_pwd_code_tv.setOnClickListener(this);

        userPhoneNum = CommonUtil.getLoginUserPhoneNum();
        forget_exchange_pwd_phone_tv.setText(CommonUtil.dealPhoneNum(userPhoneNum, 3, 4));
        CommonUtil.setViewEnable(forget_exchange_pwd_next_bt, false);
        forget_exchange_pwd_ll.setFocusable(true);
        forget_exchange_pwd_ll.setFocusableInTouchMode(true);
        forget_exchange_pwd_ll.requestFocus();
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);

        RxView.clicks(forget_exchange_pwd_next_bt).throttleFirst(2, TimeUnit.SECONDS).compose
                (mContext.<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                checkCode();
            }
        });
    }

    private void checkCode() {
        CommonUtil.hideKeyBoard(mContext);
        String code = forget_exchange_pwd_item_et.getText().toString().trim();
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("codeReq", code);
        if (TextUtils.isEmpty(codeKey)) {
            toast("请先获取短信验证码");
            return;
        }
        baseMaps.put("key", codeKey);
        baseMaps.put("phone", userPhoneNum);
        loginPresenter.checkForgetLoginCode(baseMaps, EXCHANGE_CODE_CHECK_TAG, true);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.exchange_pwd_forget));
        Observable<CharSequence> codeObservable = RxTextView.textChanges
                (forget_exchange_pwd_item_et);
        codeObservable.compose(this.<CharSequence>bindToLifecycle()).observeOn(AndroidSchedulers
                .mainThread()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                CommonUtil.setViewEnable(forget_exchange_pwd_next_bt, s.length() == 6);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_exchange_pwd_one;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_exchange_pwd_next_bt:
                CommonUtil.gotoActivity(mContext, ExchangePwdForgetTwoActivity.class);
                break;
            case R.id.forget_exchange_pwd_code_tv:
                sendExhangeCode();
                break;
        }
    }

    private void sendExhangeCode() {
        CommonUtil.hideKeyBoard(mContext);
        setTimeCountDown(60);
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        loginPresenter.getExchangePwdCode(baseMaps, GET_EXCHANGE_CODE_TAG, false);
    }

    private void setTimeCountDown(final int splashTotalCountdownTime) {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers
                .mainThread()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Long,
                Integer>() {
            @Override
            public Integer call(Long increaseTime) {
                LogUtil.i("increaseTime=" + increaseTime);
                return splashTotalCountdownTime - increaseTime.intValue();
            }
        }).take(splashTotalCountdownTime + 1).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LogUtil.i("integer=" + integer);

                if (integer == 0) {
                    forget_exchange_pwd_code_tv.setText(CommonUtil.getString(R.string
                            .get_register_code));
                    forget_exchange_pwd_code_tv.setTextColor(CommonUtil.getColor(R.color
                            .bt_start_color));
                    forget_exchange_pwd_code_tv.setEnabled(true);
                } else {
                    forget_exchange_pwd_code_tv.setText(integer + "s");
                    forget_exchange_pwd_code_tv.setTextColor(CommonUtil.getColor(R.color
                            .text_normal_hint_color));
                    if (forget_exchange_pwd_code_tv.isEnabled())
                        forget_exchange_pwd_code_tv.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isUnsubscribed()) subscribe.unsubscribe();
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        switch (tag) {
            case GET_EXCHANGE_CODE_TAG:
                forget_exchange_pwd_code_tv.setText(CommonUtil.getString(R.string
                        .get_register_code));
                forget_exchange_pwd_code_tv.setTextColor(CommonUtil.getColor(R.color
                        .bt_start_color));
                forget_exchange_pwd_code_tv.setEnabled(true);
                if (subscribe != null && !subscribe.isUnsubscribed()) subscribe.unsubscribe();
                break;
            case EXCHANGE_CODE_CHECK_TAG:
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case GET_EXCHANGE_CODE_TAG:
                String obj = null;
                try {
                    obj = ((CodeInfo) response).getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                codeKey = obj;
                toast("获取短信验证码成功");
                break;
            case EXCHANGE_CODE_CHECK_TAG:
                String code = forget_exchange_pwd_item_et.getText().toString().trim();
                Intent intent = new Intent(mContext, ExchangePwdForgetTwoActivity.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, code);
                intent.putExtra(Constant.START_ACTIVITY_STRING_2, codeKey);
                CommonUtil.gotoActivity(mContext, intent);
                break;
        }
    }

    @Override
    public void clearPassword() {

    }
}
