package com.hacai.exchange.module.login.view;

import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.CodeInfo;
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

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lenovo on 2017/12/11.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener,
        LoginInterface.ILoginView {
    public static final int REGISTER_CODE_TAG = 1;
    private static final int REGISTER_TAG = 2;
    private TextView title_header_tv;
    private View register_phone_ll;
    private View register_code_ll;
    private View register_pwd_ll;
    private View register_pwd_again_ll;
    private ImageView register_agreement_iv;
    private TextView register_agreement_read_tv;
    private Button register_start_bt;
    private EditText registerPhone_et;
    private EditText registerCode_et;
    private TextView registerGetCode_tv;
    private EditText registerPwd_et;
    private EditText registerPwdAgain_et;
    private EditText registerCommand_et;
    private View register_code_command_ll;
    private boolean isAgreement = false;
    private Subscription subscribe;
    private LoginPresenter loginPresenter;
    private Boolean isEditTextsEnable;
    private String mPhoneNumber;
    private String mVerificationCode;
    //    private String mPwd;
    //    private String mPwdAgain;
    private String smsCodeKey;


    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        register_phone_ll = findViewById(R.id.register_phone_ll);
        registerPhone_et = (EditText) register_phone_ll.findViewById(R.id.common_edit_item_et);
        register_code_ll = findViewById(R.id.register_code_ll);
        registerCode_et = (EditText) register_code_ll.findViewById(R.id.common_edit_item_et);
        registerGetCode_tv = (TextView) register_code_ll.findViewById(R.id
                .common_edit_item_code_tv);
        register_pwd_ll = findViewById(R.id.register_pwd_ll);
        registerPwd_et = (EditText) register_pwd_ll.findViewById(R.id.common_edit_item_et);
        register_pwd_again_ll = findViewById(R.id.register_pwd_again_ll);
        registerPwdAgain_et = (EditText) register_pwd_again_ll.findViewById(R.id
                .common_edit_item_et);
        register_code_command_ll = findViewById(R.id.register_pwd_command_ll);
        registerCommand_et = (EditText) register_code_command_ll.findViewById(R.id
                .common_edit_item_et);

        register_agreement_iv = (ImageView) findViewById(R.id.register_agreement_iv);
        register_agreement_read_tv = (TextView) findViewById(R.id.register_agreement_read_tv);
        register_start_bt = (Button) findViewById(R.id.register_start_bt);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.login_register));

        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);
        ((TextView) register_phone_ll.findViewById(R.id.common_edit_item_tv)).setText(CommonUtil
                .getString(R.string.phone));
        ((TextView) register_code_ll.findViewById(R.id.common_edit_item_tv)).setText(CommonUtil
                .getString(R.string.code));
        ((TextView) register_pwd_ll.findViewById(R.id.common_edit_item_tv)).setText(CommonUtil
                .getString(R.string.password));
        ((TextView) register_pwd_again_ll.findViewById(R.id.common_edit_item_tv)).setText
                (CommonUtil.getString(R.string.password_comfire));
        ((TextView) register_code_command_ll.findViewById(R.id.common_edit_item_tv)).setText
                (CommonUtil.getString(R.string.command));

        registerPhone_et.setHint(CommonUtil.getString(R.string.phone_input));
        registerCode_et.setHint(CommonUtil.getString(R.string.code_input));
        registerPwd_et.setHint(CommonUtil.getString(R.string.password_input));
        registerPwdAgain_et.setHint(CommonUtil.getString(R.string.password_comfire_input));
        registerCommand_et.setHint(CommonUtil.getString(R.string.command_input));

        registerPhone_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        registerCode_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        registerPwd_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        registerPwdAgain_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        registerCommand_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        registerPwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        registerPwdAgain_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);

        registerGetCode_tv.setVisibility(View.VISIBLE);

        registerGetCode_tv.setOnClickListener(this);
        register_agreement_iv.setOnClickListener(this);
        register_agreement_read_tv.setOnClickListener(this);
        register_start_bt.setOnClickListener(this);

        //        CommonUtil.setViewEnable(registerGetCode_tv, false);
        //        CommonUtil.setViewEnable(register_start_bt, false);


        //        Observable<CharSequence> phoneNumObservable = RxTextView.textChanges
        // (registerPhone_et);
        //        Observable<CharSequence> verificationCodeObservable = RxTextView.textChanges
        //                (registerCode_et);
        //        Observable<CharSequence> pwdObservable = RxTextView.textChanges(registerPwd_et);
        //        Observable<CharSequence> pwdAgainObservable = RxTextView.textChanges
        // (registerPwdAgain_et);
        //        Observable.combineLatest(phoneNumObservable, verificationCodeObservable,
        // pwdObservable,
        //                pwdAgainObservable, new Func4<CharSequence, CharSequence, CharSequence,
        //                        CharSequence, Boolean>() {
        //            @Override
        //            public Boolean call(CharSequence phoneNumber, CharSequence verificationCode,
        //                                CharSequence pwd, CharSequence pwdAgain) {
        //                return checkBankcard(phoneNumber, verificationCode, pwd, pwdAgain);
        //            }
        //        }).subscribe(new Action1<Boolean>() {
        //
        //            @Override
        //            public void call(Boolean aBoolean) {
        //                isEditTextsEnable = aBoolean;
        //                if (aBoolean) CommonUtil.hideKeyBoard(mContext);
        //                CommonUtil.setViewEnable(register_start_bt, aBoolean && isAgreement);
        //            }
        //        });
        //        phoneNumObservable.compose(this.<CharSequence>bindToLifecycle()).observeOn
        //                (AndroidSchedulers.mainThread()).subscribe(new Action1<CharSequence>() {
        //            @Override
        //            public void call(CharSequence charSequence) {
        //                CommonUtil.setViewEnable(registerGetCode_tv, charSequence.length() == 11);
        //            }
        //        });

        RxView.clicks(registerGetCode_tv).throttleFirst(1, TimeUnit.SECONDS).compose(this
                .<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String phoneNum = registerPhone_et.getText().toString().trim();
                if (phoneNum.length() == 11) sendSmsCode(phoneNum);
                else toast("请输入正确的手机号");
            }
        });
        RxView.clicks(register_start_bt).throttleFirst(2, TimeUnit.SECONDS).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String phoneNum = registerPhone_et.getText().toString().trim();
                String registerCommandCode = registerCode_et.getText().toString().trim();
                String pwd = registerPwd_et.getText().toString().trim();
                String pwdAgain = registerPwdAgain_et.getText().toString().trim();

                if (phoneNum.length() != 11) {
                    toast("请输入手机号码");
                    return;
                }

                if (registerCommandCode.length() != 6) {
                    toast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(pwd) || !CommonUtil.checkPassword(pwd)) {
                    toast(CommonUtil.getString(R.string.password_input));
                    return;
                }
                if (!pwd.equals(pwdAgain)) {
                    toast("两次输入的密码不一致");
                    return;
                }
                if (!isAgreement) {
                    toast("请阅读协议并勾选");
                    return;
                }
                if (TextUtils.isEmpty(smsCodeKey)) {
                    toast("请先获取验证码");
                    return;
                }
                register(phoneNum, registerCommandCode, pwd);
            }
        });
    }

    private void register(String mPhoneNumber, String registerCommandCode, String pwd) {
        CommonUtil.hideKeyBoard(mContext);
        String password = null;
        try {
            password = EncryptUtlis.SHA256(pwd.toString());
            pwd = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("phone", mPhoneNumber);
        baseMaps.put("codeReg", registerCommandCode);
        baseMaps.put("password", password);
        baseMaps.put("key", smsCodeKey);//todo
        baseMaps.put("appType", 1);//1表示android
        String phoneIMEI = CommonUtil.getPhoneIMEI(mContext);
        if (TextUtils.isEmpty(phoneIMEI)) {
            phoneIMEI = mPhoneNumber;
        }
        baseMaps.put("imei", phoneIMEI);
        loginPresenter.register(baseMaps, REGISTER_TAG, true);
    }

    private void sendSmsCode(String phoneNum) {
        setTimeCountDown(60);
        CommonUtil.hideKeyBoard(mContext);
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("regPhone", phoneNum);
        loginPresenter.getRegisterPwdCode(baseMaps, REGISTER_CODE_TAG, false);
    }

    private Boolean checkBankcard(CharSequence phoneNumber, CharSequence verificationCode,
                                  CharSequence pwd, CharSequence pwdAgain) {
        mPhoneNumber = phoneNumber.toString();
        mVerificationCode = verificationCode.toString();
        String mPwd = pwd.toString();
        String mPwdAgain = pwdAgain.toString();
        return phoneNumber.length() == 11 && verificationCode.length() == 6 && CommonUtil
                .checkPassword(pwd) && CommonUtil.checkPassword(pwdAgain);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_edit_item_code_tv://获取验证码
                break;
            case R.id.register_agreement_iv://协议框选中
                isAgreement = !isAgreement;
                register_agreement_iv.setImageResource(isAgreement ? R.mipmap.icon_box_sel : R
                        .mipmap.icon_box_unsel);
                //                CommonUtil.setViewEnable(register_start_bt, isAgreement &&
                // isEditTextsEnable);
                break;
            case R.id.register_agreement_read_tv://查看协议

                Intent intent = new Intent(mContext, RegisterAgreementAcitivity.class);
                intent.putExtra(Constant.WEBVIEW_AGREEMENT_TITLE, "哈财注册服务协议");
                CommonUtil.gotoActivity(mContext, intent);

                break;
            case R.id.register_start_bt://注册
                break;
        }
    }

    private void setTimeCountDown(final int splashTotalCountdownTime) {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers
                .mainThread()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Long,
                Integer>() {
            @Override
            public Integer call(Long increaseTime) {
                return splashTotalCountdownTime - increaseTime.intValue();
            }
        }).take(splashTotalCountdownTime + 1).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                if (integer == 0) {
                    registerGetCode_tv.setText(CommonUtil.getString(R.string
                            .get_register_code_again));
                    registerGetCode_tv.setTextColor(CommonUtil.getColor(R.color.bt_end_color));
                    registerGetCode_tv.setEnabled(true);
                } else {
                    registerGetCode_tv.setText(integer + "s");
                    registerGetCode_tv.setTextColor(CommonUtil.getColor(R.color
                            .text_normal_hint_color));
                    if (registerGetCode_tv.isEnabled()) registerGetCode_tv.setEnabled(false);
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
            case REGISTER_CODE_TAG:
                registerGetCode_tv.setText(CommonUtil.getString(R.string.get_register_code));
                registerGetCode_tv.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                registerGetCode_tv.setEnabled(true);
                if (subscribe != null && !subscribe.isUnsubscribed()) subscribe.unsubscribe();
                break;
            case REGISTER_TAG:
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case REGISTER_CODE_TAG:
                CodeInfo codeInfo = null;
                try {
                    codeInfo = (CodeInfo) response;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                smsCodeKey = codeInfo.getObj();
                toast("短信验证码发送成功");
                break;
            case REGISTER_TAG:
                toast("注册成功");
                CommonUtil.setUserPhoneNum(mPhoneNumber);
                CommonUtil.gotoActivity(mContext, LoginActivity.class);
                AppManager.getInstance().removeCurrent();
                break;
        }
    }

    @Override
    public void clearPassword() {

    }

}
