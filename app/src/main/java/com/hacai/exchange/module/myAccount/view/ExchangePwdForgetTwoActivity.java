package com.hacai.exchange.module.myAccount.view;

import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.presenter.LoginPresenter;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.EncryptUtlis;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * Created by lenovo on 2017/12/20.
 */

public class ExchangePwdForgetTwoActivity extends BaseActivity implements LoginInterface
        .ILoginView {


    private static final int SET_EXCHANGE_PWD_TAG = 1;
    private TextView title_header_tv;
    private View forget_exchange_pwd_two_ll;
    private View forget_exchange_pwd_comfire_two_ll;
    private Button forget_exchange_pwd_two_bt;
    private TextView common_edit_item_tv;
    private TextView forget_exchange_pwd_two_tv;
    private TextView forget_exchange_pwd_two_et;
    private TextView forget_exchange_pwd_two_comfire_tv;
    private TextView forget_exchange_pwd_two_comfire_et;
    private String code;
    private String codeKey;
    private LoginPresenter loginPresenter;
    private View for_exchange_pwd_two_ll;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        forget_exchange_pwd_two_ll = findViewById(R.id.forget_exchange_pwd_two_ll);
        forget_exchange_pwd_two_tv = (TextView) forget_exchange_pwd_two_ll.findViewById(R.id
                .common_edit_item_tv);
        forget_exchange_pwd_two_et = (TextView) forget_exchange_pwd_two_ll.findViewById(R.id
                .common_edit_item_et);

        forget_exchange_pwd_comfire_two_ll = findViewById(R.id.forget_exchange_pwd_comfire_two_ll);
        forget_exchange_pwd_two_comfire_tv = (TextView) forget_exchange_pwd_comfire_two_ll
                .findViewById(R.id.common_edit_item_tv);
        forget_exchange_pwd_two_comfire_et = (TextView) forget_exchange_pwd_comfire_two_ll
                .findViewById(R.id.common_edit_item_et);

        forget_exchange_pwd_two_bt = (Button) findViewById(R.id.forget_exchange_pwd_two_bt);
        for_exchange_pwd_two_ll = findViewById(R.id.for_exchange_pwd_two_ll);

        code = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_1);
        codeKey = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_2);
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.exchange_pwd_forget));
        forget_exchange_pwd_two_tv.setText(CommonUtil.getString(R.string.exchange_pwd_new));
        forget_exchange_pwd_two_et.setHint(CommonUtil.getString(R.string.exchange_pwd_input));
        forget_exchange_pwd_two_et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType
                .TYPE_NUMBER_VARIATION_PASSWORD);
        forget_exchange_pwd_two_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        forget_exchange_pwd_two_comfire_tv.setText(CommonUtil.getString(R.string.password_comfire));
        forget_exchange_pwd_two_comfire_et.setHint(CommonUtil.getString(R.string
                .exchange_pwd_input));
        forget_exchange_pwd_two_comfire_et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType
                .TYPE_NUMBER_VARIATION_PASSWORD);
        forget_exchange_pwd_two_comfire_et.setFilters(new InputFilter[]{new InputFilter
                .LengthFilter(6)});

        for_exchange_pwd_two_ll.setFocusable(true);
        for_exchange_pwd_two_ll.setFocusableInTouchMode(true);
        for_exchange_pwd_two_ll.requestFocus();
        CommonUtil.setViewEnable(forget_exchange_pwd_two_bt, false);
        RxView.clicks(forget_exchange_pwd_two_bt).throttleFirst(2, TimeUnit.SECONDS).compose
                (mContext.<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                resetExchanegPwd(true);
            }
        });

        Observable<CharSequence> moneyObservable = RxTextView.textChanges
                (forget_exchange_pwd_two_et);
        Observable<CharSequence> codeObservable = RxTextView.textChanges
                (forget_exchange_pwd_two_comfire_et);
        Observable.combineLatest(moneyObservable, codeObservable, new Func2<CharSequence,
                CharSequence, Boolean>() {

            @Override
            public Boolean call(CharSequence money, CharSequence code) {
                return code.length() == 6 && code.length() == 6;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                CommonUtil.setViewEnable(forget_exchange_pwd_two_bt, aBoolean);
            }
        });
    }

    private void resetExchanegPwd(boolean b) {
        String pwd = forget_exchange_pwd_two_et.getText().toString().trim();
        String pwdcomfire = forget_exchange_pwd_two_comfire_et.getText().toString().trim();
        if (TextUtils.isEmpty(pwd) || !pwd.equals(pwdcomfire)) {
            toast("两次输入密码不一致");
            return;
        }

        String sha256 = null;
        try {
            sha256 = EncryptUtlis.SHA256(pwd);
        } catch (Exception e) {
            e.printStackTrace();
            sha256 = pwd;
        }
        String loginUserPhoneNum = CommonUtil.getLoginUserPhoneNum();

        if (TextUtils.isEmpty(loginUserPhoneNum)) {
            toast("登录手机号为空");
            return;
        }
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(codeKey)) {
            toast("请先获取短信验证码");
            return;
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("phone", loginUserPhoneNum);
        baseMaps.put("codeReq", code);
        baseMaps.put("newPayPassword", sha256);
        baseMaps.put("key", codeKey);
        loginPresenter.setForgetExchangePwd(baseMaps, SET_EXCHANGE_PWD_TAG, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_exchange_pwd_two;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case SET_EXCHANGE_PWD_TAG:
                toast("交易密码重置成功");
                AppManager.getInstance().removeCurrent();
                AppManager.getInstance().removeActivity(ExchangePwdForgetOneActivity.class);
                break;
        }
    }

    @Override
    public void clearPassword() {

    }
}
