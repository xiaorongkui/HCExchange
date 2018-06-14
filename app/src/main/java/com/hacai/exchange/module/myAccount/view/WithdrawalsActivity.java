package com.hacai.exchange.module.myAccount.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.CheckWithDrawInfo;
import com.hacai.exchange.bean.CodeInfo;
import com.hacai.exchange.bean.DrawCalculateInfo;
import com.hacai.exchange.bean.TextShowInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.presenter.LoginPresenter;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.RechargeOrDrawEvent;
import com.hacai.exchange.ui.widget.PaymentAlertDialog;
import com.hacai.exchange.ui.widget.ResizeLayout;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.EncryptUtlis;
import com.hacai.exchange.utils.LogUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by lenovo on 2017/12/18.
 * 提现
 */

public class WithdrawalsActivity extends BaseActivity implements View.OnClickListener,
        LoginInterface.ILoginView, MyAccountInterface.IMyAccountView {

    private static final int DRWA_WITH_CODE_TAG = 1;
    private static final int CALAULATE_MONEY_TAG = 2;
    private static final int SUBMIT_WITH_DRAW_TAG = 3;
    private TextView title_header_tv;
    private Button with_drawals_bt;
    private PaymentAlertDialog alertDialog;
    private EditText whithdraw_money_et;
    private TextView real_drwam_moeny_tv;
    private TextView with_draw_phone_tv;
    private TextView with_draw_describe_tv;
    private View with_draw_phone_code_ll;
    private CheckWithDrawInfo.ObjBean objBean;
    private TextView smsVerify_code_tv;
    private EditText smsVerify_code_et;
    private TextView smsVerify_code_bt;
    private Subscription subscribe;
    private LoginPresenter loginPresenter;
    private String codeKey;
    private TextView draw_with_bank_name_tv;
    private ImageView draw_with_bank_icon_iv;
    private TextView draw_with_bank_num_tv;
    private TextView draw_with_account_available_tv;
    private View with_draw_describe_rl;
    private MyAccountPresenter myAccountPresenter;
    private View with_draw_describe_line;
    private static final int DECIMAL_DIGITS = 2;
    private ResizeLayout draw_with_resizelayout;
    private View with_draw_bottom_rl;
    private boolean isCalaulateSuccess;
    private boolean isMoneyInput;

    @Override
    protected void initView() {
        objBean = getIntent().getParcelableExtra(Constant.START_ACTIVITY_PARCELABLE_1);
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        with_drawals_bt = (Button) findViewById(R.id.with_drawals_bt);
        whithdraw_money_et = (EditText) findViewById(R.id.whithdraw_money_tv);
        real_drwam_moeny_tv = (TextView) findViewById(R.id.real_drwam_moeny_tv);
        with_draw_phone_tv = (TextView) findViewById(R.id.with_draw_phone_tv);
        with_draw_describe_tv = (TextView) findViewById(R.id.with_draw_describe_tv);
        with_draw_describe_rl = findViewById(R.id.with_draw_describe_rl);
        with_draw_phone_code_ll = findViewById(R.id.with_draw_phone_code_ll);
        smsVerify_code_tv = with_draw_phone_code_ll.findViewById(R.id.common_edit_item_tv);
        smsVerify_code_et = with_draw_phone_code_ll.findViewById(R.id.common_edit_item_et);
        smsVerify_code_bt = with_draw_phone_code_ll.findViewById(R.id.common_edit_item_code_tv);

        draw_with_bank_icon_iv = (ImageView) findViewById(R.id.draw_with_bank_icon_iv);
        draw_with_bank_name_tv = (TextView) findViewById(R.id.draw_with_bank_name_tv);
        draw_with_bank_num_tv = (TextView) findViewById(R.id.draw_with_bank_num_tv);
        draw_with_account_available_tv = (TextView) findViewById(R.id
                .draw_with_account_available_tv);
        draw_with_resizelayout = (ResizeLayout) findViewById(R.id.draw_with_resizelayout);
        with_draw_describe_line = findViewById(R.id.with_draw_describe_line);
        with_draw_bottom_rl = findViewById(R.id.with_draw_bottom_rl);

        CommonUtil.setViewEnable(with_drawals_bt, false);
        RxView.clicks(with_drawals_bt).throttleFirst(2, TimeUnit.SECONDS).compose((this
                .<Void>bindToLifecycle())).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Action1<Void>() {

            @Override
            public void call(Void aVoid) {

                if (TextUtils.isEmpty(codeKey)) {
                    toast("请先获取验证码");
                    return;
                }
                if (isCalaulateSuccess) showWithDrawDialog();
                else {
                    toast("请输入正确的金额");
                    return;
                }
            }
        });
    }

    private void showWithDrawDialog() {
        alertDialog = new PaymentAlertDialog(mContext, "", R.style.payment_dialog_notice,
                "请输入交易密码");
        alertDialog.setCanceledOnTouchOutside(false);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, (int) CommonUtil
                .getDimen(R.dimen.x283), getResources().getDisplayMetrics());
        int heigh = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, (int) CommonUtil
                .getDimen(R.dimen.y180), getResources().getDisplayMetrics());

        alertDialog.setLayoutParmars(width);//设置宽度
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                lp.dimAmount = 1.0f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });

        alertDialog.setOnNegativeListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hideSoftInput(mContext);
                String pwd = alertDialog.getEditText().toString().trim();
                submitWithDraw(pwd, true);
            }
        });

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog.showSoftInput(mContext);
        alertDialog.show();
    }

    private void submitWithDraw(String pwd, boolean b) {
        String sha256 = EncryptUtlis.SHA256(pwd);
        String money = whithdraw_money_et.getText().toString().trim();
        String code = smsVerify_code_et.getText().toString().trim();

        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("money", money);
        baseMaps.put("key", codeKey);
        baseMaps.put("safepwd", sha256);
        baseMaps.put("codeReg", code);
        myAccountPresenter.submitWithDraw(baseMaps, SUBMIT_WITH_DRAW_TAG, b);
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);

        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.attachView(this);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.myaccount_withdrawals));
        initBankCardData(objBean);

        smsVerify_code_tv.setText(CommonUtil.getString(R.string.reserved_information_code_1));
        smsVerify_code_et.setHint(CommonUtil.getString(R.string.reserved_information_code_input));
        smsVerify_code_bt.setVisibility(View.VISIBLE);
        smsVerify_code_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        smsVerify_code_bt.setOnClickListener(this);

        initfoucusBoardkey();
        Observable<CharSequence> moneyObservable = RxTextView.textChanges(whithdraw_money_et);
        Observable<CharSequence> codeObservable = RxTextView.textChanges(smsVerify_code_et);
        Observable.combineLatest(moneyObservable, codeObservable, new Func2<CharSequence,
                CharSequence, Boolean>() {

            @Override
            public Boolean call(CharSequence money, CharSequence code) {

                return !TextUtils.isEmpty(money) && code.length() == 6;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                CommonUtil.setViewEnable(with_drawals_bt, aBoolean);
            }
        });

        moneyObservable.compose(this.<CharSequence>bindToLifecycle()).observeOn(AndroidSchedulers
                .mainThread()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                initMoneyInput(s);
                isMoneyInput = true;
            }
        });
        codeObservable.compose(this.<CharSequence>bindToLifecycle()).observeOn(AndroidSchedulers
                .mainThread()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                isMoneyInput = false;
            }
        });
    }

    private void initBankCardData(CheckWithDrawInfo.ObjBean objBean) {
        if (objBean != null) {
            with_draw_phone_tv.setText("手机号：" + CommonUtil.dealPhoneNum(this.objBean.getPhone(),
                    3, 4));

            List<TextShowInfo> textShowInfos = new ArrayList<>();

            textShowInfos.clear();
            textShowInfos.add(new TextShowInfo("账户余额 ", R.color.text_normal_color, R.dimen
                    .text_size_14));
            textShowInfos.add(new TextShowInfo(CommonUtil.formatMoney(this.objBean.getCashMoney()
            ), R.color.bt_end_color, R.dimen.text_size_14));
            textShowInfos.add(new TextShowInfo("元 ", R.color.text_normal_color, R.dimen
                    .text_size_14));
            draw_with_account_available_tv.setText(CommonUtil.setSpannableText(textShowInfos));

            draw_with_bank_name_tv.setText(objBean.getBankName());
            draw_with_bank_num_tv.setText(CommonUtil.formatBankcard(objBean.getCardNo()));

            String logo = objBean.getBankId();
            if (!TextUtils.isEmpty(logo) && !logo.startsWith("http")) {
                if (logo.startsWith("/")) {
                    logo = logo.substring(1, logo.length());
                }
            }
            Glide.with(mContext).load(AppNetConfig.baseUrl + logo).placeholder(R.mipmap
                    .img_bank_logo).diskCacheStrategy(DiskCacheStrategy.ALL).into
                    (draw_with_bank_icon_iv);
        }
    }

    private void initfoucusBoardkey() {
        draw_with_resizelayout.setFocusable(true);
        draw_with_resizelayout.setFocusableInTouchMode(true);
        draw_with_resizelayout.requestFocus();

        draw_with_resizelayout.setOnResizeListener(new ResizeLayout.InputListener() {
            @Override
            public void OnInputListener(boolean isHideInput) {
                LogUtil.i("isHideInput=" + isHideInput);
                if (isHideInput) {
                    draw_with_resizelayout.setFocusable(true);
                    draw_with_resizelayout.setFocusableInTouchMode(true);
                    draw_with_resizelayout.requestFocus();
                    if (isMoneyInput && (alertDialog == null || !alertDialog.isShowing()))
                        calculateMoney(true);
                }
            }
        });

        draw_with_resizelayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                draw_with_resizelayout.setFocusable(true);
                draw_with_resizelayout.setFocusableInTouchMode(true);
                draw_with_resizelayout.requestFocus();
                CommonUtil.hideInputWindow(mContext);
                return false;
            }
        });

        whithdraw_money_et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                LogUtil.i("keyCode=" + keyCode + "KeyEvent" + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent
                        .ACTION_DOWN) {
                /*隐藏软键盘*/
                    CommonUtil.hideInputWindow(mContext);
                    draw_with_resizelayout.setFocusable(true);
                    draw_with_resizelayout.setFocusableInTouchMode(true);
                    draw_with_resizelayout.requestFocus();
                    return true;
                } else if (KeyEvent.KEYCODE_BACK == keyCode && event.getAction() == KeyEvent
                        .KEYCODE_SOFT_LEFT) {
                    draw_with_resizelayout.setFocusable(true);
                    draw_with_resizelayout.setFocusableInTouchMode(true);
                    draw_with_resizelayout.requestFocus();
                    return true;
                }
                return false;
            }
        });

        whithdraw_money_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                whithdraw_money_et.setCursorVisible(hasFocus);
            }
        });

    }

    private void initMoneyInput(CharSequence s) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + DECIMAL_DIGITS + 1);
                whithdraw_money_et.setText(s);
                whithdraw_money_et.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            whithdraw_money_et.setText(s);
            whithdraw_money_et.setSelection(2);
        }
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                whithdraw_money_et.setText(s.subSequence(0, 1));
                whithdraw_money_et.setSelection(1);
                return;
            }
        }
    }

    private void calculateMoney(boolean b) {
        String money = whithdraw_money_et.getText().toString().trim();
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("money", money);
        myAccountPresenter.calculateWithDrawMoney(baseMaps, CALAULATE_MONEY_TAG, b);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_with_drawals;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_edit_item_code_tv:
                getDrawCode();
                break;
        }
    }

    private void getDrawCode() {
        CommonUtil.hideKeyBoard(mContext);
        setTimeCountDown(60);
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        if (objBean != null) baseMaps.put("phone", objBean.getPhone());
        loginPresenter.getDrawWithCode(baseMaps, DRWA_WITH_CODE_TAG, false);
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
                    smsVerify_code_bt.setText(CommonUtil.getString(R.string
                            .get_register_code_again));
                    smsVerify_code_bt.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                    smsVerify_code_bt.setEnabled(true);
                } else {
                    smsVerify_code_bt.setText(integer + "s");
                    smsVerify_code_bt.setTextColor(CommonUtil.getColor(R.color
                            .text_normal_hint_color));
                    if (smsVerify_code_bt.isEnabled()) smsVerify_code_bt.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isUnsubscribed()) subscribe.unsubscribe();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        LogUtil.i("errorMsg=" + errorMsg + ";code=" + code);
        toast(errorMsg);
        switch (tag) {
            case DRWA_WITH_CODE_TAG:
                smsVerify_code_bt.setText(CommonUtil.getString(R.string.get_register_code));
                smsVerify_code_bt.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                smsVerify_code_bt.setEnabled(true);
                if (subscribe != null && !subscribe.isUnsubscribed()) subscribe.unsubscribe();
                break;
            case CALAULATE_MONEY_TAG:
                isCalaulateSuccess = false;
                real_drwam_moeny_tv.setText("实际到账");
                break;
            case SUBMIT_WITH_DRAW_TAG:
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case DRWA_WITH_CODE_TAG:
                toast("短信验证码获取成功");
                CodeInfo codeInfo = null;
                try {
                    codeInfo = (CodeInfo) response;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String codeInfoObj = codeInfo.getObj();
                codeKey = codeInfoObj;
                break;
            case CALAULATE_MONEY_TAG:
                isCalaulateSuccess = true;
                DrawCalculateInfo.ObjBean drawCalculateInfoObj = null;
                try {
                    DrawCalculateInfo drawCalculateInfo = (DrawCalculateInfo) response;
                    drawCalculateInfoObj = drawCalculateInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                with_draw_describe_rl.setVisibility(View.VISIBLE);
                with_draw_describe_line.setVisibility(View.GONE);
                with_draw_describe_tv.setText(drawCalculateInfoObj.getMsg());

                List<TextShowInfo> textShowInfos = new ArrayList<>();

                textShowInfos.clear();
                textShowInfos.add(new TextShowInfo("实际到账 ", R.color.text_normal_color, R.dimen
                        .text_size_14));
                textShowInfos.add(new TextShowInfo(CommonUtil.formatMoney(drawCalculateInfoObj
                        .getRealMoney()), R.color.text_normal_color, R.dimen.text_size_14));
                textShowInfos.add(new TextShowInfo("元 ", R.color.text_normal_color, R.dimen
                        .text_size_14));
                real_drwam_moeny_tv.setText(CommonUtil.setSpannableText(textShowInfos));

                break;
            case SUBMIT_WITH_DRAW_TAG:
                toast("提现成功");
                RxBus.getInstance().post(new RechargeOrDrawEvent(true, "提现成功"));
                Intent intent = new Intent(mContext, WithDrawSuccessActivity.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, getClass().getSimpleName());
                CommonUtil.gotoActivity(mContext, intent);
                AppManager.getInstance().removeCurrent();
                break;
        }
    }

    @Override
    public void clearPassword() {

    }
}
