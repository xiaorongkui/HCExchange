package com.hacai.exchange.module.login.view;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lenovo on 2017/12/11.
 */

public class ForgetPwdFristActivity extends BaseActivity implements View.OnClickListener,
        LoginInterface.ILoginView {

    private static final int FORGETPWD_CODE_TAG = 1;
    private static final int FORGETPWD_CODE_CHECK_TAG = 2;
    private TextView title_header_tv;
    private View forget_phone_ll;
    private EditText forgetPhone_et;
    private View forget_code_ll;
    private EditText forgetCode_et;
    private TextView forgetCodeGet_tv;
    private Button forget_next_bt;
    private Subscription subscribe;
    private LoginPresenter loginPresenter;
    private boolean isPhoneNumber;
    private boolean isCode;
    private String code;
    private String phone;
    private String codeInfoObj;
    private View forget_pwd_one_ll;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        forget_phone_ll = findViewById(R.id.forget_phone_ll);
        forgetPhone_et = (EditText) forget_phone_ll.findViewById(R.id.common_edit_item_et);

        forget_code_ll = findViewById(R.id.forget_code_ll);
        forgetCode_et = (EditText) forget_code_ll.findViewById(R.id.common_edit_item_et);
        forgetCodeGet_tv = (TextView) forget_code_ll.findViewById(R.id.common_edit_item_code_tv);
        forget_next_bt = (Button) findViewById(R.id.forget_next_bt);
        forget_pwd_one_ll = findViewById(R.id.forget_pwd_one_ll);

        forget_pwd_one_ll.setFocusable(true);
        forget_pwd_one_ll.setFocusableInTouchMode(true);
        forget_pwd_one_ll.requestFocus();
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);
        RxView.clicks(forget_next_bt).throttleFirst(2, TimeUnit.SECONDS).compose(mContext
                .<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                checkCode();
            }
        });
    }

    private void checkCode() {
        CommonUtil.hideKeyBoard(mContext);
        phone = forgetPhone_et.getText().toString().trim();
        code = forgetCode_et.getText().toString().trim();
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("phone", phone);
        baseMaps.put("codeReq", code);
        if (TextUtils.isEmpty(codeInfoObj)) {
            toast("请先获取短信验证码");
            return;
        }
        baseMaps.put("key", codeInfoObj);
        loginPresenter.checkForgetLoginCode(baseMaps, FORGETPWD_CODE_CHECK_TAG, true);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.forget_login_password));
        ((TextView) forget_phone_ll.findViewById(R.id.common_edit_item_tv)).setText(CommonUtil
                .getString(R.string.phone));
        ((TextView) forget_code_ll.findViewById(R.id.common_edit_item_tv)).setText(CommonUtil
                .getString(R.string.code));

        forgetPhone_et.setHint(CommonUtil.getString(R.string.phone_input));
        forgetCode_et.setHint(CommonUtil.getString(R.string.code_input));
        forgetPhone_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        forgetCode_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});


        forgetCodeGet_tv.setVisibility(View.VISIBLE);

        forgetCodeGet_tv.setOnClickListener(this);
        forget_next_bt.setOnClickListener(this);
        forgetPhone_et.addTextChangedListener(new MyTextWatcher(1));
        forgetCode_et.addTextChangedListener(new MyTextWatcher(2));
        CommonUtil.setViewEnable(forgetCodeGet_tv, false);
        CommonUtil.setViewEnable(forget_next_bt, false);

        forgetPhone_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        forgetCode_et.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_login_pwd_one;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_edit_item_code_tv://获取验证码
                getForgetPwdCode();
                break;
            case R.id.forget_next_bt://下一步
                break;
        }
    }

    private void getForgetPwdCode() {
        CommonUtil.hideKeyBoard(mContext);
        setTimeCountDown(60);
        String regPhone = forgetPhone_et.getText().toString().trim();
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("phoneReg", regPhone);
        loginPresenter.getLoginPwdCode(baseMaps, FORGETPWD_CODE_TAG, false);
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
                    forgetCodeGet_tv.setText(CommonUtil.getString(R.string
                            .get_register_code_again));
                    forgetCodeGet_tv.setTextColor(CommonUtil.getColor(R.color.bt_end_color));
                    forgetCodeGet_tv.setEnabled(true);
                } else {
                    forgetCodeGet_tv.setText(integer + "s");
                    forgetCodeGet_tv.setTextColor(CommonUtil.getColor(R.color
                            .text_normal_hint_color));
                    if (forgetCodeGet_tv.isEnabled()) forgetCodeGet_tv.setEnabled(false);
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
        switch (tag) {
            case FORGETPWD_CODE_TAG:
                toast(errorMsg);
                forgetCodeGet_tv.setText(CommonUtil.getString(R.string.get_register_code));
                forgetCodeGet_tv.setTextColor(CommonUtil.getColor(R.color.bt_end_color));
                forgetCodeGet_tv.setEnabled(true);
                if (subscribe != null && !subscribe.isUnsubscribed()) subscribe.unsubscribe();
                break;
            case FORGETPWD_CODE_CHECK_TAG:
                toast(errorMsg);
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case FORGETPWD_CODE_TAG:
                CodeInfo codeInfo = null;
                try {
                    codeInfo = (CodeInfo) response;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                codeInfoObj = codeInfo.getObj();
                toast("短信验证码发送成功");
                break;
            case FORGETPWD_CODE_CHECK_TAG:
                Intent intent = new Intent(mContext, ForgetPwdSecondActivity.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, phone);
                intent.putExtra(Constant.START_ACTIVITY_STRING_2, code);
                intent.putExtra(Constant.START_ACTIVITY_STRING_3, codeInfoObj);
                CommonUtil.gotoActivity(mContext, intent);
                break;
        }
    }


    @Override
    public void clearPassword() {

    }

    public class MyTextWatcher implements TextWatcher {
        private int mView;


        public MyTextWatcher(int viewTag) {
            this.mView = viewTag;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (mView) {
                case 1://账号
                    isPhoneNumber = s.length() == 11;
                    break;
                case 2://密码
                    isCode = s.length() == 6;
                    break;
            }
            CommonUtil.setViewEnable(forgetCodeGet_tv, isPhoneNumber);
            CommonUtil.setViewEnable(forget_next_bt, isCode && isPhoneNumber);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
