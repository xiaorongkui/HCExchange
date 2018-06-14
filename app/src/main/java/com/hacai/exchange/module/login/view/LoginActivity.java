package com.hacai.exchange.module.login.view;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hacai.exchange.MainActivity;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.presenter.LoginPresenter;
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.LoginEvent;
import com.hacai.exchange.ui.widget.SecureCharSequence;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.EncryptUtlis;
import com.hacai.exchange.utils.LogUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lenovo on 2017/12/11.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginInterface
        .ILoginView {

    private TextView title_header_tv;
    private EditText login_account_et;
    private EditText login_account_pwd_et;
    private Button login_start_bt;
    private TextView login_register_tv;
    private TextView login_forgetpassword_tv;
    private LoginPresenter loginPresenter;
    private boolean isPhoneNumber;
    private boolean isPwd;
    private String username;
    public static final int LOGIN_TAG = 1;
    private MyTextWatcher myTextWatcher1;
    private MyTextWatcher myTextWatcher2;
    private View login_ll;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        login_account_et = (EditText) findViewById(R.id.login_account_et);
        login_account_pwd_et = (EditText) findViewById(R.id.login_account_pwd);
        login_start_bt = (Button) findViewById(R.id.login_start_bt);
        login_register_tv = (TextView) findViewById(R.id.login_register_tv);
        login_forgetpassword_tv = (TextView) findViewById(R.id.login_forgetpassword_tv);
        login_ll = findViewById(R.id.login_ll);

        login_start_bt.setOnClickListener(this);
        login_register_tv.setOnClickListener(this);
        login_forgetpassword_tv.setOnClickListener(this);
        login_account_et.setOnClickListener(this);

        myTextWatcher1 = new MyTextWatcher(R.id.login_account_et);
        myTextWatcher2 = new MyTextWatcher(R.id.login_account_pwd);
        login_account_et.addTextChangedListener(myTextWatcher1);
        login_account_pwd_et.addTextChangedListener(myTextWatcher2);
        login_account_et.clearFocus();
        login_account_pwd_et.clearFocus();

        login_ll.setFocusable(true);
        login_ll.setFocusableInTouchMode(true);
        login_ll.requestFocus();

        CommonUtil.setViewEnable(login_start_bt, false);
    }

    @Override
    protected void initData() {
        login_account_et.setText(CommonUtil.getUserPhoneNum());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        login_account_et.setText(CommonUtil.getUserPhoneNum());
    }

    @Override
    protected void initTitle() {
        login_account_et.setText(CommonUtil.getUserPhoneNum());
        login_account_pwd_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        login_account_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);

        RxView.clicks(login_start_bt).throttleFirst(2, TimeUnit.SECONDS).compose(mContext
                .<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                username = login_account_et.getText().toString().trim();

                SecureCharSequence pwd = new SecureCharSequence(login_account_pwd_et.getText()
                        .toString().trim());
                startLogin(username, pwd);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_start_bt:
                //                startLogin();
                break;
            case R.id.login_register_tv:
                CommonUtil.gotoActivity(mContext, RegisterActivity.class);
                break;
            case R.id.login_forgetpassword_tv:
                CommonUtil.gotoActivity(mContext, ForgetPwdFristActivity.class);
                break;
            case R.id.login_account_et:
                //                login_account_et.setCursorVisible(true);
                break;
        }
    }

    private void startLogin(String username, SecureCharSequence pwd) {
        CommonUtil.hideInputWindow(mContext);

        String password = null;
        try {
            password = EncryptUtlis.SHA256(pwd.toString());
            pwd = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("username", username);
        baseMaps.put("password", password);

        loginPresenter.login(baseMaps, LOGIN_TAG, true);
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        switch (tag) {
            case LOGIN_TAG:

                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case LOGIN_TAG://登录成功
                toast("登陆成功！");
                RxBus.getInstance().post(new LoginEvent(true));
                MobclickAgent.onProfileSignIn(username);
                CommonUtil.setUserPhoneNum(username);
                CommonUtil.gotoActivity(mContext, MainActivity.class);
                AppManager.getInstance().removeCurrent();
                break;
        }
    }


    @Override
    public void clearPassword() {
        login_account_pwd_et.setText("");
    }


    public class MyTextWatcher implements TextWatcher {
        private int viewId;


        public MyTextWatcher(int id) {
            this.viewId = id;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (viewId) {
                case R.id.login_account_et://账号
                    isPhoneNumber = s.length() == 11;
                    break;
                case R.id.login_account_pwd://密码
                    isPwd = CommonUtil.checkPassword(s.toString());
                    break;
            }

            CommonUtil.setViewEnable(login_start_bt, isPhoneNumber && isPwd);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CommonUtil.gotoActivity(mContext, MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        login_account_et.removeTextChangedListener(myTextWatcher1);
        login_account_pwd_et.removeTextChangedListener(myTextWatcher1);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        LogUtil.i("onKeyDown keyCode=" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
            CommonUtil.hideKeyBoard(mContext);
        }
        return super.dispatchKeyEvent(event);
    }

}
