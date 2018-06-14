package com.hacai.exchange.module.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.AccountInfo;
import com.hacai.exchange.bean.BankCardInfo;
import com.hacai.exchange.bean.CheckPayConditionInfo;
import com.hacai.exchange.bean.ProductDetailInfo;
import com.hacai.exchange.bean.TextShowInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.home.model.HomeInterface;
import com.hacai.exchange.module.home.presenter.AgreementAdapter;
import com.hacai.exchange.module.home.presenter.HomePresenter;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
import com.hacai.exchange.module.myAccount.view.RechargeActivity;
import com.hacai.exchange.module.myAccount.view.WithDrawSuccessActivity;
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.RechargeOrDrawEvent;
import com.hacai.exchange.ui.widget.MyListView;
import com.hacai.exchange.ui.widget.PaymentAlertDialog;
import com.hacai.exchange.ui.widget.ResizeLayout;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.EncryptUtlis;
import com.hacai.exchange.utils.LogUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lenovo on 2017/12/19.
 * 立即投资
 */

public class PaymentActivity extends BaseActivity implements View.OnClickListener,
        MyAccountInterface.IMyAccountView, HomeInterface.IHomeView {

    private static final int PAYMENT_ACCOUNT_INFO_TAG = 1;
    private static final int PAYMENT_TAG = 2;
    private static final int GET_BIND_CARD_TAG = 3;
    private TextView title_header_tv;
    private EditText payment_money_et;
    private ProductDetailInfo.ObjBean productDetailInfoObj;
    private CheckPayConditionInfo.ObjBean productPayInfoObj;
    private TextView payment_year_rate_tv;
    private TextView payment_deadline_tv;
    private TextView payment_start_money_tv;
    private TextView payment_available_investment_tv;
    private TextView payment_have_investment_tv;
    private ProgressBar payment_list_prg_bar;
    private TextView expected_profit_money_tv;
    private TextView available_money_accoun_show_tv;
    private Button payment_recharge_bt;
    private ImageView payment_agreement_iv;
    private Button payment_buy_bt;
    private boolean isAgreement = false;
    private boolean isEditTextsEnable = false;
    private HomePresenter homePresenter;
    private MyAccountPresenter myAccountPresenter;
    private ResizeLayout payment_rl;
    private PaymentAlertDialog alertDialog;
    private static final int DECIMAL_DIGITS = 2;
    private MyListView product_payment_agreement_lv;
    private View payment_agreement_read_ll;
    private AgreementAdapter<String> agreementAdapter;
    private TextView payment_limit_money_tv;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);

        payment_year_rate_tv = (TextView) findViewById(R.id.payment_year_rate_tv);
        payment_deadline_tv = (TextView) findViewById(R.id.payment_deadline_tv);
        payment_start_money_tv = (TextView) findViewById(R.id.payment_start_money_tv);
        payment_available_investment_tv = (TextView) findViewById(R.id
                .payment_available_investment_tv);
        payment_have_investment_tv = (TextView) findViewById(R.id.payment_have_investment_tv);
        payment_list_prg_bar = (ProgressBar) findViewById(R.id.payment_list_prg_bar);

        payment_money_et = (EditText) findViewById(R.id.payment_money_et);
        expected_profit_money_tv = (TextView) findViewById(R.id.expected_profit_money_tv);
        available_money_accoun_show_tv = (TextView) findViewById(R.id
                .available_money_accoun_show_tv);
        payment_recharge_bt = (Button) findViewById(R.id.payment_recharge_bt);
        payment_buy_bt = (Button) findViewById(R.id.payment_buy_bt);
        payment_agreement_iv = (ImageView) findViewById(R.id.payment_agreement_iv);
        payment_rl = (ResizeLayout) findViewById(R.id.payment_rl);
        product_payment_agreement_lv = (MyListView) findViewById(R.id.product_payment_agreement_lv);
        payment_agreement_read_ll = findViewById(R.id.payment_agreement_read_ll);
        payment_limit_money_tv = (TextView) findViewById(R.id.payment_limit_money_tv);

        payment_agreement_read_ll.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        CommonUtil.setViewEnable(payment_buy_bt, false);
        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.attachView(this);

        homePresenter = new HomePresenter(this);
        homePresenter.attachView(this);

        initCardData();
        initListAgreement();
        initEdittextFocus();
        //        getAccountInfo(true);

        payment_money_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        Observable<CharSequence> paymentNumObservable = RxTextView.textChanges(payment_money_et);
        paymentNumObservable.compose(this.<CharSequence>bindToLifecycle()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                initMoneyInput(s);
                calculateExpectMoney(s.toString());
            }
        });

        RxView.clicks(payment_buy_bt).throttleFirst(2, TimeUnit.SECONDS).compose(mContext
                .<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                showPaymentDialog();
            }
        });
        RxView.clicks(payment_recharge_bt).throttleFirst(2, TimeUnit.SECONDS).compose(mContext
                .<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                //获取银行卡信息
                getBankCardInfo(true, GET_BIND_CARD_TAG);

            }
        });
    }

    private void calculateExpectMoney(String s) {
        String expectMoney = "0";
        try {
            if (".".equals(s)) {
                s = "0";
            }
            double parseDouble = Double.parseDouble(s.toString());
            double time = Double.parseDouble(productPayInfoObj.getTimeLimit());
            double totaltime = 365;
            double rate = CommonUtil.div(productPayInfoObj.getRate(), 100, RoundingMode
                    .HALF_EVEN, 4);
            expectMoney = CommonUtil.formatMoney(CommonUtil.div(parseDouble * rate * time,
                    totaltime));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(s)) {
            isEditTextsEnable = true;
            CommonUtil.setViewEnable(payment_buy_bt, isAgreement && isEditTextsEnable);
        }
        List<TextShowInfo> canAccounTextList = new ArrayList<>();
        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo("预期收益:", R.color.text_normal_color, R.dimen
                .text_size_14));
        canAccounTextList.add(new TextShowInfo(expectMoney, R.color.bt_end_color, R.dimen
                .text_size_14));
        canAccounTextList.add(new TextShowInfo("元", R.color.text_normal_color, R.dimen
                .text_size_14));
        expected_profit_money_tv.setText(CommonUtil.setSpannableText(canAccounTextList));
    }

    private void getBankCardInfo(boolean b, int tag) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        myAccountPresenter.getBankInfo(baseMaps, tag, b);
    }

    private void showPaymentDialog() {
        alertDialog = new PaymentAlertDialog(this, "", R.style.payment_dialog_notice, "请输入交易密码");
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
                alertDialog.hideSoftInput(PaymentActivity.this);
                String pwd = alertDialog.getEditText().toString().trim();
                paymentProduct(pwd, true);
            }
        });

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog.showSoftInput(PaymentActivity.this);
        alertDialog.show();
    }

    private List<String> fileValueList = new ArrayList<>();

    private void initListAgreement() {
        fileValueList.add("《借款协议》");
        agreementAdapter = new AgreementAdapter<String>(mContext, fileValueList, R.layout
                .payment_agreement_list_item);
        //        int heigh = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
        // getResources().getDisplayMetrics());
        product_payment_agreement_lv.setDivider(null);
        //        mAgreement_lv.setDividerHeight(heigh);
        product_payment_agreement_lv.setAdapter(agreementAdapter);
        product_payment_agreement_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent agreementintent = new Intent(mContext, LoanAgreementAcitivity.class);
                        agreementintent.putExtra(Constant.WEBVIEW_AGREEMENT_TITLE, "借款协议");
                        agreementintent.putExtra(Constant.START_ACTIVITY_STRING_1,
                                productDetailInfoObj.getId() + "");
                        CommonUtil.gotoActivity(mContext, agreementintent);
                        break;
                }
            }
        });
    }

    private void initEdittextFocus() {
        payment_rl.setFocusable(true);
        payment_rl.setFocusableInTouchMode(true);
        payment_rl.requestFocus();

        payment_rl.setOnResizeListener(new ResizeLayout.InputListener() {
            @Override
            public void OnInputListener(boolean isHideInput) {
                LogUtil.i("isHideInput==" + isHideInput);
                if (isHideInput) {
                    payment_rl.setFocusable(true);
                    payment_rl.setFocusableInTouchMode(true);
                    payment_rl.requestFocus();
                    calculateBuyMoney();
                }
            }
        });

        payment_rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                payment_rl.setFocusable(true);
                payment_rl.setFocusableInTouchMode(true);
                payment_rl.requestFocus();
                CommonUtil.hideInputWindow(PaymentActivity.this);
                return false;
            }
        });

        payment_money_et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                LogUtil.i("keyCode=" + keyCode + "KeyEvent" + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent
                        .ACTION_DOWN) {
                /*隐藏软键盘*/
                    CommonUtil.hideInputWindow(PaymentActivity.this);
                    payment_rl.setFocusable(true);
                    payment_rl.setFocusableInTouchMode(true);
                    payment_rl.requestFocus();
                    return true;
                } else if (KeyEvent.KEYCODE_BACK == keyCode && event.getAction() == KeyEvent
                        .KEYCODE_SOFT_LEFT) {
                    payment_rl.setFocusable(true);
                    payment_rl.setFocusableInTouchMode(true);
                    payment_rl.requestFocus();
                    return true;
                }
                return false;
            }
        });
        payment_money_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                payment_money_et.setCursorVisible(hasFocus);
            }
        });
    }

    private void initMoneyInput(CharSequence s) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + DECIMAL_DIGITS + 1);
                payment_money_et.setText(s);
                payment_money_et.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            payment_money_et.setText(s);
            payment_money_et.setSelection(2);
        }
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                payment_money_et.setText(s.subSequence(0, 1));
                payment_money_et.setSelection(1);
                return;
            }
        }
    }

    private void calculateBuyMoney() {

    }

    private void paymentProduct(String pwd, boolean isShowProgress) {
        LogUtil.i("pwd=" + pwd);
        String payMoney = payment_money_et.getText().toString().trim();
        String sha256 = null;
        try {
            sha256 = EncryptUtlis.SHA256(pwd);
            pwd = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("tenderMoney", payMoney);
        baseMaps.put("clientType", "1");
        baseMaps.put("safepwd", sha256);
        baseMaps.put("id", productDetailInfoObj.getId());
        homePresenter.paymentProduct(baseMaps, PAYMENT_TAG, isShowProgress);
    }

    private void getAccountInfo(boolean isShowProgress) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        myAccountPresenter.getAccountInformation(baseMaps, PAYMENT_ACCOUNT_INFO_TAG,
                isShowProgress);
    }

    @Override
    protected void initTitle() {
        productDetailInfoObj = getIntent().getParcelableExtra(Constant.START_ACTIVITY_PARCELABLE_1);
        productPayInfoObj = getIntent().getParcelableExtra(Constant.START_ACTIVITY_PARCELABLE_2);
        title_header_tv.setText(productDetailInfoObj.getName());
        double ableMoney = productPayInfoObj.getAbleMoney();
        available_money_accoun_show_tv.setText(CommonUtil.formatMoney(ableMoney) + "元");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    private void initCardData() {
        List<TextShowInfo> canAccounTextList = new ArrayList<>();
        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo(CommonUtil.format2Point(productPayInfoObj.getRate
                ()), R.color.edittext_color, R.dimen.text_size_24));
        canAccounTextList.add(new TextShowInfo("%", R.color.text_normal_color, R.dimen
                .text_size_13));

        payment_year_rate_tv.setText(CommonUtil.setSpannableText(canAccounTextList));

        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo(productPayInfoObj.getTimeLimit(), R.color
                .edittext_color, R.dimen.text_size_24));
        canAccounTextList.add(new TextShowInfo("天", R.color.text_normal_color, R.dimen
                .text_size_13));
        payment_deadline_tv.setText(CommonUtil.setSpannableText(canAccounTextList));

        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo(CommonUtil.format0Point(productPayInfoObj
                .getLowestAccount()), R.color.edittext_color, R.dimen.text_size_24));
        canAccounTextList.add(new TextShowInfo("元", R.color.text_normal_color, R.dimen
                .text_size_13));
        payment_start_money_tv.setText(CommonUtil.setSpannableText(canAccounTextList));

        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo("可投", R.color.text_normal_color, R.dimen
                .text_size_13));
        canAccounTextList.add(new TextShowInfo(CommonUtil.format2PointMillion(productPayInfoObj
                .getBalance()), R.color.bt_end_color, R.dimen.text_size_13));
        canAccounTextList.add(new TextShowInfo("万元", R.color.text_normal_color, R.dimen
                .text_size_13));
        payment_available_investment_tv.setText(CommonUtil.setSpannableText(canAccounTextList));

        double precent = 0;
        try {
            float total = Float.parseFloat(productDetailInfoObj.getAccount());
            float available = Float.parseFloat(productDetailInfoObj.getCanAccount());
            precent = (1 - (CommonUtil.div(available, total, RoundingMode.HALF_EVEN, 4))) * 100;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (precent >= 1) {
            Observable.interval(10, TimeUnit.MILLISECONDS).take((int) precent).observeOn
                    (AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {

                @Override
                public void call(Long aLong) {
                    int progress = aLong.intValue() + 1;
                    payment_list_prg_bar.setProgress(progress);
                    //                    payment_have_investment_tv.setText("已投 " + progress +
                    // "%");
                }
            });
        } else {
            payment_list_prg_bar.setProgress(0);
        }
        payment_have_investment_tv.setText("已投 " + CommonUtil.format2Point(precent) + "%");


        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo("限投", R.color.text_normal_hint_color, R.dimen
                .text_size_12));
        canAccounTextList.add(new TextShowInfo(CommonUtil.format2PointMillion(productPayInfoObj
                .getMostAccount()), R.color.text_normal_hint_color, R.dimen.text_size_13));
        canAccounTextList.add(new TextShowInfo("万元", R.color.text_normal_hint_color, R.dimen
                .text_size_12));
        payment_limit_money_tv.setText(CommonUtil.setSpannableText(canAccounTextList));
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_agreement_read_ll:
                isAgreement = !isAgreement;
                payment_agreement_iv.setImageResource(isAgreement ? R.mipmap.icon_box_sel : R
                        .mipmap.icon_box_unsel);
                CommonUtil.setViewEnable(payment_buy_bt, isAgreement && isEditTextsEnable);
                break;
            //            case R.id.payment_recharge_bt:
            //                CommonUtil.gotoActivity(mContext, RechargeActivity.class);
            //                break;
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        switch (tag) {
            case PAYMENT_ACCOUNT_INFO_TAG:
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case PAYMENT_ACCOUNT_INFO_TAG:
                AccountInfo.ObjBean accountInfoObj = null;
                try {
                    AccountInfo accountInfo = (AccountInfo) response;
                    accountInfoObj = accountInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                double ableMoney = accountInfoObj.getAbleMoney();
                available_money_accoun_show_tv.setText(CommonUtil.formatMoney(ableMoney) + "元");
                break;
            case PAYMENT_TAG:
                toast("投资成功");
                RxBus.getInstance().post(new RechargeOrDrawEvent(true, "投资成功"));
                CommonUtil.gotoActivity(mContext, WithDrawSuccessActivity.class);
                AppManager.getInstance().removeCurrent();
                AppManager.getInstance().removeActivity(ProductDetailActivity.class);
                break;
            case GET_BIND_CARD_TAG:
                BankCardInfo.ObjBean bankCardInfoObj = null;
                try {
                    BankCardInfo bankCardInfo = (BankCardInfo) response;
                    bankCardInfoObj = bankCardInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext, RechargeActivity.class);
                if (bankCardInfoObj != null)
                    intent.putExtra(Constant.START_ACTIVITY_PARCELABLE_1, bankCardInfoObj);
                CommonUtil.gotoActivity(mContext, intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
