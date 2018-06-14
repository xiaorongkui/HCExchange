package com.hacai.exchange.module.myAccount.view;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.AppNetConfig;
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
import rx.functions.Func3;

/**
 * Created by lenovo on 2017/12/18.
 */

public class LoginPwdModiftyActivity extends BaseActivity implements LoginInterface.ILoginView {

    private static final int MODIFT_LOGIN_PWD_TAG = 1;
    private TextView title_header_tv;
    private View login_old_pwd_ll;
    private View login_new_pwd_ll;
    private View login_comfirm_pwd_ll;
    private Button login_pwd_change_bt;
    private TextView login_old_pwd_tv;
    private EditText login_old_pwd_et;
    private TextView login_new_pwd_tv;
    private EditText login_new_pwd_et;
    private TextView login_comfirm_pwd_tv;
    private EditText login_comfirm_pwd_et;
    private LoginPresenter loginPresenter;
    private View login_modify_pwd_ll;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        login_old_pwd_ll = findViewById(R.id.login_old_pwd_ll);
        login_old_pwd_tv = (TextView) login_old_pwd_ll.findViewById(R.id.common_edit_item_tv);
        login_old_pwd_et = (EditText) login_old_pwd_ll.findViewById(R.id.common_edit_item_et);

        login_new_pwd_ll = findViewById(R.id.login_new_pwd_ll);
        login_new_pwd_tv = (TextView) login_new_pwd_ll.findViewById(R.id.common_edit_item_tv);
        login_new_pwd_et = (EditText) login_new_pwd_ll.findViewById(R.id.common_edit_item_et);

        login_comfirm_pwd_ll = findViewById(R.id.login_comfirm_pwd_ll);
        login_comfirm_pwd_tv = (TextView) login_comfirm_pwd_ll.findViewById(R.id
                .common_edit_item_tv);
        login_comfirm_pwd_et = (EditText) login_comfirm_pwd_ll.findViewById(R.id
                .common_edit_item_et);

        login_pwd_change_bt = (Button) findViewById(R.id.login_pwd_change_bt);
        login_modify_pwd_ll = findViewById(R.id.login_modify_pwd_ll);

    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);

        RxView.clicks(login_pwd_change_bt).throttleFirst(2, TimeUnit.SECONDS).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                modifyExchangePwd(true);
            }
        });

        login_modify_pwd_ll.setFocusableInTouchMode(true);
        login_modify_pwd_ll.setFocusable(true);
        login_modify_pwd_ll.requestFocus();

        CommonUtil.setViewEnable(login_pwd_change_bt, false);
        Observable<CharSequence> oldPwdObservable = RxTextView.textChanges(login_old_pwd_et);
        Observable<CharSequence> newPwdObservable = RxTextView.textChanges(login_new_pwd_et);
        Observable<CharSequence> newPwdConfirmObservable = RxTextView.textChanges
                (login_comfirm_pwd_et);
        Observable.combineLatest(oldPwdObservable, newPwdObservable, newPwdConfirmObservable, new
                Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence old, CharSequence pwd, CharSequence pwdConfirm) {
                return CommonUtil.checkPassword(old) && CommonUtil.checkPassword(pwd) &&
                        CommonUtil.checkPassword(pwdConfirm);
            }
        }).subscribe(new Action1<Boolean>() {

            @Override
            public void call(Boolean aBoolean) {
                CommonUtil.setViewEnable(login_pwd_change_bt, aBoolean);
            }
        });
    }

    private void modifyExchangePwd(boolean b) {
        CommonUtil.hideKeyBoard(mContext);
        String old = login_old_pwd_et.getText().toString().trim();
        String newPwd = login_new_pwd_et.getText().toString().trim();
        String newPwdConfirm = login_comfirm_pwd_et.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd) || !newPwd.equals(newPwdConfirm)) {
            toast("两次密码输入不一致");
            return;
        }
        if (!TextUtils.isEmpty(old) && old.equals(newPwd)) {
            toast("原密码和新密码不能相同");
            return;
        }
        String sha256_old = "";
        String sha256_new = "";
        try {
            sha256_old = EncryptUtlis.SHA256(old);
            sha256_new = EncryptUtlis.SHA256(newPwd);
            old = null;
            newPwd = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("oldpwd", sha256_old);
        baseMaps.put("passwd", sha256_new);
        loginPresenter.modifyLoginPwd(baseMaps, MODIFT_LOGIN_PWD_TAG, b);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.change_login_pwd));
        login_old_pwd_tv.setText(CommonUtil.getString(R.string.login_old_pwd));
        login_old_pwd_et.setHint(CommonUtil.getString(R.string.login_old_pwd_input));

        login_new_pwd_tv.setText(CommonUtil.getString(R.string.login_new_pwd));
        login_new_pwd_et.setHint(CommonUtil.getString(R.string.login_new_pwd_input));

        login_comfirm_pwd_tv.setText(CommonUtil.getString(R.string.password_comfire));
        login_comfirm_pwd_et.setHint(CommonUtil.getString(R.string.password_comfire));

        login_old_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        login_new_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        login_comfirm_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_login_pwd;
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
            case MODIFT_LOGIN_PWD_TAG:
                toast("登录密码修改成功");
                AppManager.getInstance().removeCurrent();
                break;
        }
    }

    @Override
    public void clearPassword() {

    }
}
