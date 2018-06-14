package com.hacai.exchange.module.myAccount.view;

import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.BankCardInfo;
import com.hacai.exchange.bean.RechargeInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
import com.hacai.exchange.ui.widget.PaymentAlertDialog;
import com.hacai.exchange.ui.widget.ResizeLayout;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.IDCardValidate;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func5;

/**
 * Created by lenovo on 2017/12/18.
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener,
        MyAccountInterface.IMyAccountView {

    private static final int RECHARGE_TAG = 1;
    public static final int CHANNEL_ACTIVITY_REQUEST_CODE = 100;
    public static final int CHANNEL_ACTIVITY_RESULT_CODE = 200;
    private static final int DECIMAL_DIGITS = 2;
    private TextView title_header_tv;
    private Button recharge_bt;
    private MyAccountPresenter myAccountPresenter;
    private PaymentAlertDialog alertDialog;
    private EditText recharge_money_et;
    private TextView recharge_account_available_money_tv;
    private ResizeLayout recharge_rl;
    private View recharge_bank_info_ll;
    private View recharge_bank_card_ll;
    private View recharge_agreement_read_ll;
    private TextView bind_card_accoun_channel_tv;
    private View recharge_bind_card_accoun_channel_iv;
    private EditText common_edit_item_name_et;
    private EditText common_edit_item_certificate_et;
    private EditText common_edit_item_bank_et;
    private ImageView recharge_agreement_iv;
    private TextView recharge_agreement_read_tv;
    private boolean isAgreement;
    private boolean isEditTextsEnable_unbind = false;
    private boolean isEditTextsEnable_bind = false;
    private TextView recharge_bind_card_accoun_channel_tv;
    private int channelId = 0;
    private String bankName;
    private BankCardInfo.ObjBean bankCardInfo;
    private boolean isBandCard;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        recharge_bt = (Button) findViewById(R.id.recharge_bt);
        recharge_money_et = (EditText) findViewById(R.id.recharge_money_et);
        recharge_account_available_money_tv = (TextView) findViewById(R.id
                .recharge_account_available_money_tv);
        recharge_rl = (ResizeLayout) findViewById(R.id.recharge_rl);
        recharge_bank_info_ll = findViewById(R.id.recharge_bank_info_ll);
        recharge_bank_card_ll = findViewById(R.id.recharge_bank_card_ll);
        recharge_agreement_read_ll = findViewById(R.id.recharge_agreement_read_ll);


        initEditText();
        RxView.clicks(recharge_bt).throttleFirst(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers
                .mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                rechargeStart(true);
            }
        });
    }

    private void initShowLayout(BankCardInfo.ObjBean objBean) {
        if (objBean != null) {
            recharge_bank_info_ll.setVisibility(View.VISIBLE);
            recharge_account_available_money_tv.setVisibility(View.VISIBLE);
            recharge_bank_card_ll.setVisibility(View.GONE);
            recharge_agreement_read_ll.setVisibility(View.GONE);
            initBindCardLayout(objBean);
        } else {
            recharge_bank_info_ll.setVisibility(View.GONE);
            recharge_account_available_money_tv.setVisibility(View.GONE);
            recharge_bank_card_ll.setVisibility(View.VISIBLE);
            recharge_agreement_read_ll.setVisibility(View.VISIBLE);
            initUnBindCardLayout();
        }
    }

    private void initUnBindCardLayout() {
        View recharge_bank_name = findViewById(R.id.recharge_bank_name);
        TextView common_edit_item_name_tv = (TextView) recharge_bank_name.findViewById(R.id
                .common_edit_item_tv);
        common_edit_item_name_et = (EditText) recharge_bank_name.findViewById(R.id
                .common_edit_item_et);
        common_edit_item_name_tv.setText(CommonUtil.getString(R.string.real_name));
        common_edit_item_name_et.setHint(CommonUtil.getString(R.string.real_name_input));
        common_edit_item_name_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PERSON_NAME);
        common_edit_item_name_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

        View recharge_bank_certificate_num = findViewById(R.id.recharge_bank_certificate_num);
        TextView common_edit_item_certificate_tv = (TextView) recharge_bank_certificate_num
                .findViewById(R.id.common_edit_item_tv);
        common_edit_item_certificate_et = (EditText) recharge_bank_certificate_num.findViewById(R
                .id.common_edit_item_et);
        common_edit_item_certificate_tv.setText(CommonUtil.getString(R.string.identity_num));
        common_edit_item_certificate_et.setHint(CommonUtil.getString(R.string.identity_num_input));
        common_edit_item_certificate_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter
                (18)});
        common_edit_item_certificate_et.setInputType(InputType.TYPE_CLASS_TEXT );

        recharge_bind_card_accoun_channel_tv = findViewById(R.id
                .recharge_bind_card_accoun_channel_tv);
        recharge_bind_card_accoun_channel_iv = findViewById(R.id
                .recharge_bind_card_accoun_channel_iv);

        View recharge_bank_num = findViewById(R.id.recharge_bank_num);
        TextView common_edit_item_bank_tv = (TextView) recharge_bank_num.findViewById(R.id
                .common_edit_item_tv);
        common_edit_item_bank_et = (EditText) recharge_bank_num.findViewById(R.id
                .common_edit_item_et);
        common_edit_item_bank_tv.setText(CommonUtil.getString(R.string.bind_bank_card_num));
        common_edit_item_bank_et.setHint(CommonUtil.getString(R.string.bind_bank_card_num_input));
        common_edit_item_certificate_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter
                (19)});

        recharge_agreement_iv = (ImageView) findViewById(R.id.recharge_agreement_iv);
        recharge_agreement_read_tv = (TextView) findViewById(R.id.recharge_agreement_read_tv);
        recharge_agreement_iv.setOnClickListener(this);
        recharge_agreement_read_tv.setOnClickListener(this);
        recharge_bind_card_accoun_channel_tv.setOnClickListener(this);
        recharge_bind_card_accoun_channel_iv.setOnClickListener(this);


        CommonUtil.setViewEnable(recharge_bt, false);

        Observable<CharSequence> moneyObservable = RxTextView.textChanges(recharge_money_et);
        Observable<CharSequence> nameObservable = RxTextView.textChanges(common_edit_item_name_et);
        Observable<CharSequence> identityObservable = RxTextView.textChanges
                (common_edit_item_bank_et);
        Observable<CharSequence> channelObservable = RxTextView.textChanges
                (recharge_bind_card_accoun_channel_tv);
        Observable<CharSequence> cardNumObservable = RxTextView.textChanges
                (common_edit_item_bank_et);
        Observable.combineLatest(moneyObservable, nameObservable, identityObservable,
                channelObservable, cardNumObservable, new Func5<CharSequence, CharSequence,
                        CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence money, CharSequence name, CharSequence identity,
                                CharSequence channel, CharSequence cardNum) {
                return checkInputCondition(money, name, identity, channel, cardNum);
            }
        }).subscribe(new Action1<Boolean>() {

            @Override
            public void call(Boolean aBoolean) {
                isEditTextsEnable_unbind = aBoolean;
                CommonUtil.setViewEnable(recharge_bt, aBoolean && isAgreement);
            }
        });
    }

    private Boolean checkInputCondition(CharSequence money, CharSequence name, CharSequence
            identity, CharSequence channel, CharSequence cardNum) {
        return !TextUtils.isEmpty(money) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty
                (identity) && !TextUtils.isEmpty(channel) && !TextUtils.isEmpty(cardNum);
    }

    private void initBindCardLayout(BankCardInfo.ObjBean objBean) {
        ImageView bank_card_logo_iv = findViewById(R.id.bank_card_logo_iv);
        TextView bank_card_name_tv = findViewById(R.id.bank_card_name_tv);
        TextView bank_card_num_tv = findViewById(R.id.bank_card_num_tv);
        TextView bank_card_discripte_tv = findViewById(R.id.bank_card_discripte_tv);
        String logo = objBean.getLogo();
        if (!TextUtils.isEmpty(logo) && !logo.startsWith("http")) {
            if (logo.startsWith("/")) {
                logo = logo.substring(1, logo.length());
            }
        }

        Glide.with(mContext).load(AppNetConfig.baseUrl + logo).placeholder(R.mipmap
                .img_bank_logo).diskCacheStrategy(DiskCacheStrategy.ALL).into(bank_card_logo_iv);
        bank_card_name_tv.setText(objBean.getBankName());
        bank_card_num_tv.setText(CommonUtil.formatBankcard(objBean.getCardNo()));
        bank_card_discripte_tv.setText(objBean.getBankLimit());
        recharge_account_available_money_tv.setText("账户余额  " + CommonUtil.formatMoney(objBean
                .getAbleMoney()) + "元");

    }

    private void initMoneyInput(CharSequence s) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + DECIMAL_DIGITS + 1);
                recharge_money_et.setText(s);
                recharge_money_et.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            recharge_money_et.setText(s);
            recharge_money_et.setSelection(2);
        }
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                recharge_money_et.setText(s.subSequence(0, 1));
                recharge_money_et.setSelection(1);
                return;
            }
        }
    }

    private void initEditText() {
        recharge_money_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        recharge_rl.setFocusable(true);
        recharge_rl.setFocusableInTouchMode(true);
        recharge_rl.requestFocus();

        recharge_money_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                recharge_money_et.setCursorVisible(hasFocus);
            }
        });

        Observable<CharSequence> rechargeMoneyChanges = RxTextView.textChanges(recharge_money_et);
        rechargeMoneyChanges.compose(this.<CharSequence>bindToLifecycle()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence s) {
                initMoneyInput(s);
                if (isBandCard) {
                    isEditTextsEnable_bind = !TextUtils.isEmpty(s);
                    CommonUtil.setViewEnable(recharge_bt, isEditTextsEnable_bind);
                }
            }
        });
    }

    @Override
    protected void initData() {
        bankCardInfo = getIntent().getParcelableExtra(Constant.START_ACTIVITY_PARCELABLE_1);
        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.attachView(this);

        isBandCard = bankCardInfo != null;
        initShowLayout(bankCardInfo);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.myaccount_recharge));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge_agreement_iv:
                isAgreement = !isAgreement;
                recharge_agreement_iv.setImageResource(isAgreement ? R.mipmap.icon_box_sel : R
                        .mipmap.icon_box_unsel);
                CommonUtil.setViewEnable(recharge_bt, isAgreement && isBandCard ?
                        isEditTextsEnable_bind : isEditTextsEnable_unbind);
                break;
            case R.id.recharge_agreement_read_tv://绑卡协议

                Intent agreementintent = new Intent(mContext, BindCardAgreementAcitivity.class);
                agreementintent.putExtra(Constant.WEBVIEW_AGREEMENT_TITLE, "哈财绑卡协议");
                CommonUtil.gotoActivity(mContext, agreementintent);
                break;
            case R.id.recharge_bind_card_accoun_channel_tv:
            case R.id.recharge_bind_card_accoun_channel_iv:
                Intent intent = new Intent(mContext, BankChannelActivity.class);
                CommonUtil.startActivityForResult(mContext, intent, CHANNEL_ACTIVITY_REQUEST_CODE);
                break;
        }
    }

    private void rechargeStart(boolean b) {
        String payMoney = recharge_money_et.getText().toString().trim();
        double parseDouble = 0;
        try {
            parseDouble = Double.parseDouble(payMoney);
            //            payMoney = parseDouble + "";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            toast("输入的金额有误，请重新输入");
            return;
            //                    if (payMoney.contains(".")) {
            //                        payMoney = payMoney.replace(".", "");
            //                    } else {
            //                        toast("输入的金额有误，请重新输入");
            //                        return;
            //                    }
        }
        if (parseDouble <= 0) {
            toast("输入的金额不能为零");
            return;
        }

        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();

        if (bankCardInfo != null) {
            baseMaps.put("amt", payMoney);
            baseMaps.put("payCode", bankCardInfo.getPayCode());
            baseMaps.put("bankName", bankCardInfo.getBankName());
            baseMaps.put("bankCard", bankCardInfo.getCardNo());
            baseMaps.put("name", bankCardInfo.getUserRealName());
            baseMaps.put("idType", 0);
            baseMaps.put("idNo", bankCardInfo.getCardNo());
            baseMaps.put("Type", 10);
        } else {//未绑卡
            String bankCardNum = common_edit_item_bank_et.getText().toString().trim();
            String certificateId = common_edit_item_certificate_et.getText().toString().trim();
            String name = common_edit_item_name_et.getText().toString().trim();
            boolean isReal = false;

            try {
                if (!IDCardValidate.IDCardValidate(certificateId)) {
                    toast("身份证号码输入有误");
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            baseMaps.put("amt", payMoney);
            baseMaps.put("payCode", channelId);
            baseMaps.put("bankName", bankName);
            baseMaps.put("bankCard", bankCardNum);
            baseMaps.put("name", name);
            baseMaps.put("idType", 0);
            baseMaps.put("idNo", certificateId);
            baseMaps.put("Type", 10);
        }

        myAccountPresenter.recharge(baseMaps, RECHARGE_TAG, b);
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case RECHARGE_TAG:
                RechargeInfo rechargeInfo = null;
                try {
                    rechargeInfo = (RechargeInfo) response;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String html = rechargeInfo.getObj();
                Intent intent = new Intent(mContext, RechargeWebViewAcitivity.class);
                intent.putExtra(Constant.WEBVIEW_AGREEMENT_URL, html);
                CommonUtil.gotoActivity(mContext, intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANNEL_ACTIVITY_REQUEST_CODE && resultCode ==
                CHANNEL_ACTIVITY_RESULT_CODE) {
            bankName = data.getStringExtra(Constant.START_ACTIVITY_STRING_1);
            channelId = data.getIntExtra(Constant.START_ACTIVITY_STRING_2, 0);
            String logoStr = data.getStringExtra(Constant.START_ACTIVITY_STRING_3);
            recharge_bind_card_accoun_channel_tv.setText(bankName);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        if (myAccountPresenter != null) {
            myAccountPresenter.detachView(this);
            myAccountPresenter = null;
        }
    }
}
