package com.hacai.exchange.module.myAccount.view;

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
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.PasswordEvent;
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

public class ExchangePwdSettingActivity extends BaseActivity implements MyAccountInterface
        .IMyAccountView {

    private static final int SET_PWD_TAG = 1;
    private TextView title_header_tv;
    private View setting_exchange_pwd_ll;
    private View setting_exchange_pwd_comfire_ll;
    private TextView exchange_pwd_tv;
    private EditText exchange_pwd_et;
    private TextView exchange_pwd_comfire_tv;
    private EditText exchange_pwd_comfire_et;
    private Button setting_exchange_pwd_bt;
    private MyAccountPresenter myAccountPresenter;
    private View exchange_pwd_ll;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        setting_exchange_pwd_ll = findViewById(R.id.setting_exchange_pwd_ll);
        exchange_pwd_ll = findViewById(R.id.exchange_pwd_ll);
        exchange_pwd_tv = (TextView) setting_exchange_pwd_ll.findViewById(R.id.common_edit_item_tv);
        exchange_pwd_et = (EditText) setting_exchange_pwd_ll.findViewById(R.id.common_edit_item_et);

        setting_exchange_pwd_comfire_ll = findViewById(R.id.setting_exchange_pwd_comfire_ll);
        exchange_pwd_comfire_tv = (TextView) setting_exchange_pwd_comfire_ll.findViewById(R.id
                .common_edit_item_tv);
        exchange_pwd_comfire_et = (EditText) setting_exchange_pwd_comfire_ll.findViewById(R.id
                .common_edit_item_et);

        setting_exchange_pwd_bt = (Button) findViewById(R.id.setting_exchange_pwd_bt);
    }

    @Override
    protected void initData() {
        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.attachView(this);
        RxView.clicks(setting_exchange_pwd_bt).throttleFirst(2, TimeUnit.SECONDS).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setPwd(true);
            }
        });

        exchange_pwd_ll.setFocusableInTouchMode(true);
        exchange_pwd_ll.setFocusable(true);
        exchange_pwd_ll.requestFocus();
        CommonUtil.setViewEnable(setting_exchange_pwd_bt, false);
        Observable<CharSequence> channelObservable = RxTextView.textChanges(exchange_pwd_et);
        Observable<CharSequence> cardNumObservable = RxTextView.textChanges
                (exchange_pwd_comfire_et);
        Observable.combineLatest(channelObservable, cardNumObservable, new Func2<CharSequence,
                CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence pwd, CharSequence pwdConfirm) {
                return pwd.length() == 6 && pwdConfirm.length() == 6;
            }
        }).subscribe(new Action1<Boolean>() {

            @Override
            public void call(Boolean aBoolean) {
                CommonUtil.setViewEnable(setting_exchange_pwd_bt, aBoolean);
            }
        });
    }

    private void setPwd(boolean b) {
        CommonUtil.hideKeyBoard(mContext);
        String pwd = exchange_pwd_et.getText().toString().trim();
        String pwdAgain = exchange_pwd_comfire_et.getText().toString().trim();
        if (TextUtils.isEmpty(pwd) || !pwd.equals(pwdAgain)) {
            toast("两次输入的密码不一致");
            return;
        }
        String s = "";
        try {
            s = EncryptUtlis.SHA256(pwd);
            pwd = null;
            pwdAgain = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("safepasswd", s);
        baseMaps.put("confirmpasswd", s);
        myAccountPresenter.setExchangePwd(baseMaps, SET_PWD_TAG, b);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.setting_exchange_pwd));
        exchange_pwd_tv.setText(CommonUtil.getString(R.string.exchange_pwd));
        exchange_pwd_et.setHint(CommonUtil.getString(R.string.exchange_pwd_input));

        exchange_pwd_comfire_tv.setText(CommonUtil.getString(R.string.password_comfire));
        exchange_pwd_comfire_et.setHint(CommonUtil.getString(R.string.exchange_pwd_input_again));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_exchange_pwd_setting;
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
            case SET_PWD_TAG:
                toast("设置交易密码成功");
                RxBus.getInstance().post(new PasswordEvent(true, 1));
                AppManager.getInstance().removeCurrent();
                break;
        }
    }
}
