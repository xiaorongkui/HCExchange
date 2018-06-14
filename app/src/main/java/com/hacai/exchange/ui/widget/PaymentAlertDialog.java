package com.hacai.exchange.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.module.myAccount.view.ExchangePwdForgetOneActivity;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Rongkui.xiao on 2017/4/24.
 *
 * @description
 */

public class PaymentAlertDialog extends Dialog implements View.OnClickListener {
    private final String money;
    private final String title;
    private Button positiveButton, negativeButton;
    private Activity mContext;
    private TextView mforgetPwd_tv;
    private List<Map<String, String>> valueList;
    private TextView[] tvList;
    private KurtEditText payment_kuret;
    private String mStr;
    private TextView mMoney_tv;
    private boolean isShowSoft = true;
    private TextView mTitle;
    private View mView;
    //    private VirtualKeyboardView payment_KeyboardView;
    private StringBuffer sb = new StringBuffer();
    private LinearLayout payment_ll;

    public boolean isShowSoft() {
        return isShowSoft;
    }

    public void setShowSoft(boolean showSoft) {
        isShowSoft = showSoft;
    }

    public PaymentAlertDialog(Activity context, String money, int theme, String title) {
        super(context, theme);
        mContext = context;
        this.money = money;
        this.title = title;
        setCustomDialog();
    }

    private void setCustomDialog() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.payment_dialog, null);

        payment_kuret = (KurtEditText) mView.findViewById(R.id.payment_transfer_ket);
        payment_ll = (LinearLayout) mView.findViewById(R.id.password_ll);
        //        payment_KeyboardView = (VirtualKeyboardView) mView.findViewById(R.id
        // .virtualKeyboardView);
        mMoney_tv = (TextView) mView.findViewById(R.id.paymen_dialog_money_tv);
        mforgetPwd_tv = (TextView) mView.findViewById(R.id.paymeny_forget_pwd_tv);
        mTitle = (TextView) mView.findViewById(R.id.payment_dialog_title_tv);
        positiveButton = (Button) mView.findViewById(R.id.payment_confirm_bt);
        negativeButton = (Button) mView.findViewById(R.id.payment_cancel_bt);
        if (TextUtils.isEmpty(money)) mMoney_tv.setVisibility(View.GONE);
        mMoney_tv.setText(CommonUtil.formatMoney(money) + "元");
        CommonUtil.setViewEnable(positiveButton, false);

        mTitle.setText(title);
        //        setContentView(mView);

        mforgetPwd_tv.setOnClickListener(this);
        payment_kuret.setKurtListener(new KurtEditText.KurtListener() {
            @Override
            public void keyword(String str) {
                if (str.length() == 6) {
                    CommonUtil.setViewEnable(positiveButton, true);
                }
                mStr = str;
            }
        });

        //        mforgetPwd_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //        mforgetPwd_tv.getPaint().setAntiAlias(true);
        super.setContentView(mView);

    }

    public <T extends View> T getView(int viewId, Class<T> clazz) {
        return (T) mView.findViewById(viewId);
    }

    public String getEditText() {
        LogUtil.i("getEditText=" + mStr);
        return mStr;
    }

    @Override
    public void setContentView(int layoutResID) {
    }


    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        positiveButton.setOnClickListener(listener);
    }

    /**
     * 取消键监听器
     *
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener) {
        negativeButton.setOnClickListener(listener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paymeny_forget_pwd_tv:
                CommonUtil.gotoActivity(mContext, ExchangePwdForgetOneActivity.class);
                hideSoftInput((RxAppCompatActivity) mContext);
                break;
        }
    }


    public void showSoftInput(final RxAppCompatActivity activity) {

        payment_kuret.getEditText().setFocusable(true);
        payment_kuret.getEditText().setFocusableInTouchMode(true);
        payment_kuret.getEditText().requestFocus();
        Observable.timer(300, TimeUnit.MILLISECONDS).compose(activity.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                InputMethodManager manager = (InputMethodManager) mContext.getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                if (manager != null)
                    manager.showSoftInput(payment_kuret.getEditText(), InputMethodManager
                            .SHOW_FORCED);
            }
        });

    }

    public void hideSoftInput(RxAppCompatActivity activity) {
        activity.getWindow().getDecorView().setFocusable(true);
        activity.getWindow().getDecorView().setFocusableInTouchMode(true);
        activity.getWindow().getDecorView().requestFocus();
        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (manager != null)
            manager.hideSoftInputFromWindow(payment_kuret.getEditText().getWindowToken(), 0);
    }

    public void setLayoutParmars(int width) {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.width = width;
        getWindow().setAttributes(attrs);
    }

}
