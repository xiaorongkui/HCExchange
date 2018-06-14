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
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
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

public class ExchangeModiftyPwdActivity extends BaseActivity implements MyAccountInterface
        .IMyAccountView {

    private static final int MODIFT_PWD_TAG = 1;
    private TextView title_header_tv;
    private View exchange_old_pwd_ll;
    private View exchange_new_pwd_ll;
    private View exchange_comfirm_pwd_ll;
    private Button exchange_pwd_change_bt;
    private TextView exchange_old_pwd_tv;
    private EditText exchange_old_pwd_et;
    private TextView exchange_new_pwd_tv;
    private EditText exchange_new_pwd_et;
    private TextView exchange_comfirm_pwd_tv;
    private EditText exchange_comfirm_pwd_et;
    private View modify_pwd_ll;
    private MyAccountPresenter myAccountPresenter;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        exchange_old_pwd_ll = findViewById(R.id.exchange_old_pwd_ll);
        exchange_old_pwd_tv = (TextView) exchange_old_pwd_ll.findViewById(R.id.common_edit_item_tv);
        exchange_old_pwd_et = (EditText) exchange_old_pwd_ll.findViewById(R.id.common_edit_item_et);

        exchange_new_pwd_ll = findViewById(R.id.exchange_new_pwd_ll);
        exchange_new_pwd_tv = (TextView) exchange_new_pwd_ll.findViewById(R.id.common_edit_item_tv);
        exchange_new_pwd_et = (EditText) exchange_new_pwd_ll.findViewById(R.id.common_edit_item_et);

        exchange_comfirm_pwd_ll = findViewById(R.id.exchange_comfirm_pwd_ll);
        exchange_comfirm_pwd_tv = (TextView) exchange_comfirm_pwd_ll.findViewById(R.id
                .common_edit_item_tv);
        exchange_comfirm_pwd_et = (EditText) exchange_comfirm_pwd_ll.findViewById(R.id
                .common_edit_item_et);

        exchange_pwd_change_bt = (Button) findViewById(R.id.exchange_pwd_change_bt);
        modify_pwd_ll = findViewById(R.id.modify_pwd_ll);

    }

    @Override
    protected void initData() {
        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.attachView(this);

        RxView.clicks(exchange_pwd_change_bt).throttleFirst(2, TimeUnit.SECONDS).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                modifyExchangePwd(true);
            }
        });

        modify_pwd_ll.setFocusableInTouchMode(true);
        modify_pwd_ll.setFocusable(true);
        modify_pwd_ll.requestFocus();

        CommonUtil.setViewEnable(exchange_pwd_change_bt, false);
        Observable<CharSequence> oldPwdObservable = RxTextView.textChanges(exchange_old_pwd_et);
        Observable<CharSequence> newPwdObservable = RxTextView.textChanges(exchange_new_pwd_et);
        Observable<CharSequence> newPwdConfirmObservable = RxTextView.textChanges
                (exchange_comfirm_pwd_et);
        Observable.combineLatest(oldPwdObservable, newPwdObservable, newPwdConfirmObservable, new
                Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence old, CharSequence pwd, CharSequence pwdConfirm) {
                return old.length() == 6 && pwd.length() == 6 && pwdConfirm.length() == 6;
            }
        }).subscribe(new Action1<Boolean>() {

            @Override
            public void call(Boolean aBoolean) {
                CommonUtil.setViewEnable(exchange_pwd_change_bt, aBoolean);
            }
        });
    }

    private void modifyExchangePwd(boolean b) {
        CommonUtil.hideKeyBoard(mContext);
        String old = exchange_old_pwd_et.getText().toString().trim();
        String newPwd = exchange_new_pwd_et.getText().toString().trim();
        String newPwdConfirm = exchange_comfirm_pwd_et.getText().toString().trim();

        if (!TextUtils.isEmpty(newPwd) && newPwd.equals(old)) {
            toast("新密码和旧密码不能相同");
            return;
        }

        if (TextUtils.isEmpty(newPwd) || !newPwd.equals(newPwdConfirm)) {
            toast("两次密码输入不一致");
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
        baseMaps.put("oldPassword", sha256_old);
        baseMaps.put("newPassword", sha256_new);
        myAccountPresenter.reviseExchangePwd(baseMaps, MODIFT_PWD_TAG, b);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.change_exchange_pwd));
        exchange_old_pwd_tv.setText(CommonUtil.getString(R.string.login_old_pwd));
        exchange_old_pwd_et.setHint(CommonUtil.getString(R.string.exchange_old_pwd_input));

        exchange_new_pwd_tv.setText(CommonUtil.getString(R.string.login_new_pwd));
        exchange_new_pwd_et.setHint(CommonUtil.getString(R.string.exchange_new_pwd_input));

        exchange_comfirm_pwd_tv.setText(CommonUtil.getString(R.string.password_comfire));
        exchange_comfirm_pwd_et.setHint(CommonUtil.getString(R.string.password_comfire));

        exchange_old_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        exchange_new_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        exchange_comfirm_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_exchange_pwd;
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
            case MODIFT_PWD_TAG:
                toast("交易密码修改成功");
                AppManager.getInstance().removeCurrent();
                break;
        }
    }
}
