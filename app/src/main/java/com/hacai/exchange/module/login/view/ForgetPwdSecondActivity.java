package com.hacai.exchange.module.login.view;

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
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.presenter.LoginPresenter;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.EncryptUtlis;
import com.jakewharton.rxbinding.view.RxView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lenovo on 2017/12/11.
 */

public class ForgetPwdSecondActivity extends BaseActivity implements LoginInterface.ILoginView {

    private static final int FORGETPWD_CODE_RESET_TAG = 1;
    private TextView title_header_tv;
    private View forget_pwd_ll;
    private View forget_again_pwd_ll;
    private EditText forgetPwdAgain_et;
    private EditText forgetPwd_et;
    private TextView forget_pwd_account_tv;
    private Button forget_pwd_two_bt;
    private String phone;
    private String code;
    private LoginPresenter loginPresenter;
    private boolean isPwdOne;
    private boolean isPwdTwo;
    private String key;
    private View forget_pwd_two_ll;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        forget_pwd_account_tv = (TextView) findViewById(R.id.forget_pwd_account_tv);
        forget_pwd_ll = findViewById(R.id.forget_pwd_ll);
        forgetPwd_et = (EditText) forget_pwd_ll.findViewById(R.id.common_edit_item_et);
        forget_again_pwd_ll = findViewById(R.id.forget_again_pwd_ll);
        forgetPwdAgain_et = (EditText) forget_again_pwd_ll.findViewById(R.id.common_edit_item_et);
        forget_pwd_two_bt = (Button) findViewById(R.id.forget_pwd_two_bt);
        forget_pwd_two_ll = findViewById(R.id.forget_pwd_two_ll);

        forget_pwd_two_ll.setFocusable(true);
        forget_pwd_two_ll.setFocusableInTouchMode(true);
        forget_pwd_two_ll.requestFocus();
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_1);
        code = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_2);
        key = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_3);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);
        forget_pwd_account_tv.setText("账号： " + CommonUtil.dealPhoneNum(phone, 3, 4));

        forgetPwd_et.addTextChangedListener(new MyTextWatcher(1));
        forgetPwdAgain_et.addTextChangedListener(new MyTextWatcher(2));
        CommonUtil.setViewEnable(forget_pwd_two_bt, false);
        RxView.clicks(forget_pwd_two_bt).throttleFirst(2, TimeUnit.SECONDS).compose(mContext
                .<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                String pwd1 = forgetPwd_et.getText().toString().trim();
                String pwd2 = forgetPwdAgain_et.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd1) && pwd1.equals(pwd2)) resetPwd(pwd1);
                else {
                    toast("两次输入的密码不一致");
                    return;
                }
            }
        });
    }

    private void resetPwd(String pwd1) {
        CommonUtil.hideKeyBoard(mContext);
        String password = null;
        try {
            password = EncryptUtlis.SHA256(pwd1.toString());
            pwd1 = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("phone", phone);
        baseMaps.put("codeReq", code);
        baseMaps.put("newPassword", password);
        baseMaps.put("key", key);
        loginPresenter.resetPwd(baseMaps, FORGETPWD_CODE_RESET_TAG, true);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.forget_login_password));
        forgetPwd_et.setHint(CommonUtil.getString(R.string.password_input));
        forgetPwdAgain_et.setHint(CommonUtil.getString(R.string.password_comfire_input));
        forgetPwd_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        forgetPwdAgain_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

        ((TextView) forget_pwd_ll.findViewById(R.id.common_edit_item_tv)).setText(CommonUtil
                .getString(R.string.password_new));
        ((TextView) forget_again_pwd_ll.findViewById(R.id.common_edit_item_tv)).setText
                (CommonUtil.getString(R.string.password_comfire));

        forgetPwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        forgetPwdAgain_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_login_pwd_two;
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        switch (tag) {
            case FORGETPWD_CODE_RESET_TAG:
                toast(errorMsg);
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case FORGETPWD_CODE_RESET_TAG:
                toast("登录密码重置成功");
                CommonUtil.setUserPhoneNum(phone);
                CommonUtil.gotoActivity(mContext, LoginActivity.class);
                AppManager.getInstance().removeCurrent();
                AppManager.getInstance().removeActivity(ForgetPwdFristActivity.class);
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
                    isPwdOne = CommonUtil.checkPassword(s.toString());
                    break;
                case 2://密码
                    isPwdTwo = CommonUtil.checkPassword(s.toString());
                    break;
            }
            CommonUtil.setViewEnable(forget_pwd_two_bt, isPwdOne && isPwdTwo);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }
}
