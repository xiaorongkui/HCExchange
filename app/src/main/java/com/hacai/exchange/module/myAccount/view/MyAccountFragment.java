package com.hacai.exchange.module.myAccount.view;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseFragment;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.AccountInfo;
import com.hacai.exchange.bean.BankCardInfo;
import com.hacai.exchange.bean.CheckWithDrawInfo;
import com.hacai.exchange.bean.CommonListInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.common.NetResponseCode;
import com.hacai.exchange.module.login.view.LoginActivity;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.AccountRecycleAdapter;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.LoginEvent;
import com.hacai.exchange.rxbus.event.PasswordEvent;
import com.hacai.exchange.rxbus.event.RechargeOrDrawEvent;
import com.hacai.exchange.ui.widget.AlterDialogUtils;
import com.hacai.exchange.ui.widget.FullyLinearLayoutManager;
import com.hacai.exchange.ui.widget.HaCaiSwipeRefreshLayout;
import com.hacai.exchange.ui.widget.MyRecycleView;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/12/7.
 */

public class MyAccountFragment extends BaseFragment implements View.OnClickListener,
        MyAccountInterface.IMyAccountView {

    private static final int ACCOUNT_INFO_TAG = 1;
    private static final int BANK_CARD_INTO_TAG = 2;
    private static final int BANK_CARD_INTO_TAG_1 = 3;
    private static final int WITH_DRAW_INFO_TAG = 4;
    private LinearLayout myaccount_frgment_ll;
    private MyRecycleView account_rv;
    private AccountRecycleAdapter accountRecycleAdapter;
    private FullyLinearLayoutManager mLayoutManager;
    private ImageView myaccount_person_icon_iv;
    private TextView myaccount_withdrawals_tv;
    private TextView myaccount_recharge_tv;
    private MyAccountPresenter myAccountPresenter;
    private TextView myaccount_money_total_show_tv;
    private TextView myaccount_money_available_show_tv;
    private TextView myaccount_money_expect_show_tv;
    private TextView account_user_name_tv;
    private ImageView myaccount_money_hide_show_iv;
    private boolean isLogin = false;
    private HaCaiSwipeRefreshLayout myaccount_hcswipe_refresh;
    private Subscription loginSubscription;
    private Subscription rechargeOrDrownSubscription;
    private int isBandCard = -1;
    private AlterDialogUtils alterDialogUtils;
    private Subscription passwordSubscribe;

    @Override
    protected void initView() {
        initStatus();
        initRecyclerView();
        initRefreshLayout();
        initEvent();
        myaccount_person_icon_iv.setOnClickListener(this);
        myaccount_withdrawals_tv.setOnClickListener(this);
        myaccount_recharge_tv.setOnClickListener(this);

        myaccount_money_hide_show_iv.setOnClickListener(this);
        account_user_name_tv.setOnClickListener(this);
    }

    private void initEvent() {
        loginSubscription = RxBus.getInstance().toObserverable(LoginEvent.class).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<LoginEvent>() {
            @Override
            public void call(LoginEvent loginEvent) {
                LogUtil.i("收到loginevent事件isLogin=" + loginEvent.isLogin());
                if (loginEvent.isLogin()) {
                    getAccountInfo(false);
                } else {
                    isLogin = false;
                    refreshAccountData(null);
                }
            }
        });

        rechargeOrDrownSubscription = RxBus.getInstance().toObserverable(RechargeOrDrawEvent
                .class).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RechargeOrDrawEvent>() {
            @Override
            public void call(RechargeOrDrawEvent rechargeOrDrawEvent) {
                LogUtil.i("RechargeOrDrawEvent=" + rechargeOrDrawEvent.isSuccess());

                if (rechargeOrDrawEvent.isSuccess()) {
                    getAccountInfo(false);
                }
            }
        });

        passwordSubscribe = RxBus.getInstance().toObserverable(PasswordEvent.class).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<PasswordEvent>() {

            @Override
            public void call(PasswordEvent passwordEvent) {
                LogUtil.i("收到密码事件passwordEvent=" + passwordEvent.isSuccess());
                getAccountInfo(false);
            }
        });
    }


    @Override
    protected void findViewId(View rootView) {
        myaccount_frgment_ll = (LinearLayout) rootView.findViewById(R.id.myaccount_frgment_ll);
        myaccount_person_icon_iv = (ImageView) rootView.findViewById(R.id.myaccount_person_icon_iv);
        myaccount_money_hide_show_iv = (ImageView) rootView.findViewById(R.id
                .myaccount_money_hide_show_iv);
        account_user_name_tv = (TextView) rootView.findViewById(R.id.account_user_name_tv);
        myaccount_withdrawals_tv = (TextView) rootView.findViewById(R.id.myaccount_withdrawals_tv);
        myaccount_recharge_tv = (TextView) rootView.findViewById(R.id.myaccount_recharge_tv);
        account_rv = (MyRecycleView) rootView.findViewById(R.id.account_rv);
        myaccount_money_total_show_tv = (TextView) rootView.findViewById(R.id
                .myaccount_money_total_show_tv);
        myaccount_money_available_show_tv = (TextView) rootView.findViewById(R.id
                .myaccount_money_available_show_tv);
        myaccount_money_expect_show_tv = (TextView) rootView.findViewById(R.id
                .myaccount_money_expect_show_tv);
        myaccount_hcswipe_refresh = (HaCaiSwipeRefreshLayout) rootView.findViewById(R.id
                .myaccount_hcswipe_refresh);

    }

    @Override
    protected void initData() {
        myAccountPresenter = new MyAccountPresenter((RxAppCompatActivity) getActivity());
        myAccountPresenter.attachView(this);
        getAccountInfo(true);
    }

    private void getAccountInfo(boolean b) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        myAccountPresenter.getAccountInformation(baseMaps, ACCOUNT_INFO_TAG, b);
    }

    @Override
    protected void initTitle() {
        refreshAccountData(null);
        initData();
    }

    private List<CommonListInfo> recycleDatas = new ArrayList<>();

    private void initRecyclerView() {

        account_rv.setHasFixedSize(true);
        account_rv.setItemAnimator(new DefaultItemAnimator());
        accountRecycleAdapter = new AccountRecycleAdapter<CommonListInfo>(mContext, recycleDatas,
                R.layout.account_list_rv_item);
        mLayoutManager = new FullyLinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        account_rv.setLayoutManager(mLayoutManager);
        accountRecycleAdapter.setOnItemClickLitener(new BaseRecylerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!isLogin || !CommonUtil.isNetworkAvailable(mContext)) {
                    CommonUtil.gotoActivity(mContext, LoginActivity.class);
                    return;
                }
                switch (position) {
                    case 0:
                        Intent intent = new Intent(mContext, InvestmentRecodeActivity.class);
                        CommonUtil.gotoActivity(mContext, intent);
                        break;
                    case 1://资金记录
                        CommonUtil.gotoActivity(mContext, CapitalRecodeActivity.class);
                        break;
                    case 2://银行卡
                        if (isBandCard == 0) {
                            CommonUtil.gotoActivity(mContext, BindCardInitActivity.class);
                        } else {
                            getBankCardInfo(true, BANK_CARD_INTO_TAG_1);
                        }
                        break;
                }
            }
        });
        account_rv.setAdapter(accountRecycleAdapter);
    }

    private void initStatus() {
        //状态栏占用的兼容性
        if (Build.VERSION.SDK_INT >= 21) {
            View statusView = new View(getActivity());
            statusView.setBackgroundColor(CommonUtil.getColor(R.color.bt_start_color));
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, CommonUtil.getStatusBarHeight());
            myaccount_frgment_ll.addView(statusView, 0, lp);
        }
    }


    private void initRefreshLayout() {
        myaccount_hcswipe_refresh.setTargetScrollWithLayout(true);
        myaccount_hcswipe_refresh.setOnHCPullRefreshListener(new HaCaiSwipeRefreshLayout
                .OnHCPullRefreshListener() {
            @Override
            public void onRefresh() {
                getAccountInfo(false);
                Observable.timer(10000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (myaccount_hcswipe_refresh.isRefreshing())
                            myaccount_hcswipe_refresh.setHCRefreshing(false);
                    }
                });
            }

            @Override
            public void onPullDistance(int distance) {
            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_myaccount;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        if (!isLogin || !CommonUtil.isNetworkAvailable(mContext)) {
            CommonUtil.gotoActivity(mContext, LoginActivity.class);
            return;
        }
        switch (v.getId()) {
            case R.id.account_user_name_tv:
            case R.id.myaccount_person_icon_iv:
                Intent intentPwd = new Intent(mContext, PersonSettingActivity.class);
                if (accountInfoObj != null) {
                    intentPwd.putExtra(Constant.START_ACTIVITY_STRING_1, accountInfoObj
                            .getIsPaypwd());
                }
                CommonUtil.gotoActivity(mContext, intentPwd);
                break;
            case R.id.myaccount_withdrawals_tv://充值
                if (accountInfoObj != null) {
                    if (accountInfoObj.getIsBind() == 0) {//未绑卡
                        toast("请先绑定银行卡");
                        return;
                    }
                    //                        alterDialogUtils = new AlterDialogUtils(mContext, R
                    // .style
                    //                                .payment_dialog_notice);
                    //                        alterDialogUtils.setAlertDialogWidth((int)
                    // CommonUtil.getDimen(R.dimen
                    //                                .x290));
                    //                        alterDialogUtils.setOneOrTwoBtn(false);
                    //                        alterDialogUtils.setMessage(CommonUtil.getString(R
                    // .string
                    //                                .acount_unbind_card));
                    //                        alterDialogUtils.setTwoCancelBtn("取消", new View
                    // .OnClickListener() {
                    //                            @Override
                    //                            public void onClick(View v) {
                    //                                alterDialogUtils.dismiss();
                    //                            }
                    //                        });
                    //                        alterDialogUtils.setTwoConfirmBtn("去绑卡", new View
                    // .OnClickListener() {
                    //                            @Override
                    //                            public void onClick(View v) {
                    //                                CommonUtil.gotoActivity(mContext,
                    // RechargeActivity.class);
                    //                                alterDialogUtils.dismiss();
                    //                            }
                    //                        });
                    //                        alterDialogUtils.show();
                    //                        return;
                    //                    }
                    if (accountInfoObj.getIsPaypwd() == 0) {
                        alterDialogUtils = new AlterDialogUtils(mContext, R.style
                                .payment_dialog_notice);
                        alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen
                                .x290));
                        alterDialogUtils.setOneOrTwoBtn(false);
                        alterDialogUtils.setMessage("请先设置交易密码");
                        alterDialogUtils.setTwoCancelBtn("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alterDialogUtils.dismiss();
                            }
                        });
                        alterDialogUtils.setTwoConfirmBtn("去设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ExchangePwdSettingActivity
                                        .class);
                                CommonUtil.gotoActivity(mContext, intent);
                                alterDialogUtils.dismiss();
                            }
                        });
                        alterDialogUtils.show();
                        return;
                    }
                }

                getWithDrawInfo(true);
                break;
            case R.id.myaccount_recharge_tv:
                //获取银行卡信息
                if (isBandCard == 1) {
                    getBankCardInfo(true, BANK_CARD_INTO_TAG);
                } else {
                    Intent intent = new Intent(mContext, RechargeActivity.class);
                    CommonUtil.gotoActivity(mContext, intent);
                }
                break;
            case R.id.myaccount_money_hide_show_iv:
                isShowMoney = !isShowMoney;
                if (isShowMoney && accountInfoObj != null) {
                    myaccount_money_total_show_tv.setText(CommonUtil.formatMoney(accountInfoObj
                            .getTotalMoney()));
                    myaccount_money_available_show_tv.setText(CommonUtil.formatMoney
                            (accountInfoObj.getAbleMoney()));
                    myaccount_money_expect_show_tv.setText(CommonUtil.formatMoney(accountInfoObj
                            .getRepaymentMoney()));
                } else {
                    myaccount_money_total_show_tv.setText("····");
                    myaccount_money_available_show_tv.setText("····");
                    myaccount_money_expect_show_tv.setText("····");
                }
                myaccount_money_hide_show_iv.setImageResource(isShowMoney ? R.mipmap
                        .myaccount_icon_display : R.mipmap.myaccount_icon_hide);
                break;

        }
    }

    private void getWithDrawInfo(boolean b) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        myAccountPresenter.checkWithDraw(baseMaps, WITH_DRAW_INFO_TAG, b);
    }

    private void getBankCardInfo(boolean b, int tag) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        myAccountPresenter.getBankInfo(baseMaps, tag, b);
    }

    private boolean isShowMoney = true;

    @Override
    public void onError(String errorMsg, String code, int tag) {

        if (myaccount_hcswipe_refresh.isRefreshing())
            myaccount_hcswipe_refresh.setHCRefreshing(false);
        switch (tag) {
            case ACCOUNT_INFO_TAG:
                if (TextUtils.isEmpty(CommonUtil.getTokenId())) {
                    toast("未登录");
                    CommonUtil.gotoActivity(mContext, LoginActivity.class);
                    return;
                } else {
                    toast(errorMsg);
                }
                switch (code) {
                    case NetResponseCode.HMC_LOGIN:
                        isLogin = false;
                        CommonUtil.gotoActivity(mContext, LoginActivity.class);
                        break;
                    case NetResponseCode.HMC_NETWORK_ERROR://无网络
                        break;

                }
                break;
            case BANK_CARD_INTO_TAG:
                switch (code) {
                    case NetResponseCode.HMC_UNBIND_CARD_ERROR://未绑定银行卡
                        toast("未绑定银行卡");
                        Intent intent = new Intent(mContext, RechargeActivity.class);
                        CommonUtil.gotoActivity(mContext, intent);
                        break;
                    default:
                        toast(errorMsg);
                        break;
                }
                break;
            case BANK_CARD_INTO_TAG_1:
                switch (code) {
                    case NetResponseCode.HMC_UNBIND_CARD_ERROR://未绑定银行卡
                        toast("未绑定银行卡");
                        Intent intent = new Intent(mContext, BindCardInitActivity.class);
                        CommonUtil.gotoActivity(mContext, intent);
                        break;
                    default:
                        toast(errorMsg);
                        break;
                }
                break;
            case WITH_DRAW_INFO_TAG:
                toast(errorMsg);
                break;
            default:
                toast(errorMsg);
                break;

        }
    }

    AccountInfo.ObjBean accountInfoObj = null;

    @Override
    public void onSuccess(Object response, int tag) {
        if (myaccount_hcswipe_refresh.isRefreshing())
            myaccount_hcswipe_refresh.setHCRefreshing(false);
        switch (tag) {
            case ACCOUNT_INFO_TAG:
                isLogin = true;
                try {
                    AccountInfo accountInfo = (AccountInfo) response;
                    accountInfoObj = accountInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshAccountData(accountInfoObj);
                break;
            case BANK_CARD_INTO_TAG:
                BankCardInfo.ObjBean bankCardInfoObj = null;
                try {
                    BankCardInfo bankCardInfo = (BankCardInfo) response;
                    bankCardInfoObj = bankCardInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext, RechargeActivity.class);
                if (isBandCard == 1 && bankCardInfoObj != null)
                    intent.putExtra(Constant.START_ACTIVITY_PARCELABLE_1, bankCardInfoObj);
                CommonUtil.gotoActivity(mContext, intent);
                break;
            case BANK_CARD_INTO_TAG_1:
                BankCardInfo.ObjBean bankCardInfoObj_1 = null;
                try {
                    BankCardInfo bankCardInfo = (BankCardInfo) response;
                    bankCardInfoObj_1 = bankCardInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bankCardInfoObj_1 != null) {
                    Intent intentBank = new Intent(mContext, BankCardInfoActivity.class);
                    intentBank.putExtra(Constant.START_ACTIVITY_PARCELABLE_1, bankCardInfoObj_1);
                    CommonUtil.gotoActivity(mContext, intentBank);
                } else {
                    toast("银行卡信息获取失败");
                }
                break;
            case WITH_DRAW_INFO_TAG:
                CheckWithDrawInfo checkWithDrawInfo = null;
                CheckWithDrawInfo.ObjBean checkWithDrawInfoObj = null;
                try {
                    checkWithDrawInfo = (CheckWithDrawInfo) response;
                    checkWithDrawInfoObj = checkWithDrawInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent withDrawIntent = new Intent(mContext, WithdrawalsActivity.class);
                if (checkWithDrawInfoObj != null)
                    withDrawIntent.putExtra(Constant.START_ACTIVITY_PARCELABLE_1,
                            checkWithDrawInfoObj);
                CommonUtil.gotoActivity(mContext, withDrawIntent);
                break;
        }
    }

    private void refreshAccountData(AccountInfo.ObjBean infoObj) {
        if (infoObj == null) {
            myaccount_person_icon_iv.setImageResource(R.mipmap.myaccount_icon_user);
            account_user_name_tv.setText(CommonUtil.getString(R.string.no_login_register));

            myaccount_money_total_show_tv.setText("");
            myaccount_money_available_show_tv.setText("");
            myaccount_money_expect_show_tv.setText("");
            myaccount_money_hide_show_iv.setVisibility(View.INVISIBLE);

            recycleDatas.clear();
            recycleDatas.add(new CommonListInfo("投资记录", ""));
            recycleDatas.add(new CommonListInfo("资金记录", ""));
            recycleDatas.add(new CommonListInfo("银行卡", ""));
            accountRecycleAdapter.notifyDataSetChanged();
            return;
        }
        Glide.with(mContext).load(TextUtils.isEmpty(infoObj.getHeadPic()) ? R.mipmap
                .myaccount_icon_user : infoObj.getHeadPic()).placeholder(R.mipmap
                .myaccount_icon_user).diskCacheStrategy(DiskCacheStrategy.ALL).into
                (myaccount_person_icon_iv);
        String userName = infoObj.getUserName();
        CommonUtil.setUserPhoneNum(userName);

        account_user_name_tv.setText(CommonUtil.dealPhoneNum(userName, 3, 4));
        myaccount_money_total_show_tv.setText(CommonUtil.formatMoney(infoObj.getTotalMoney()));
        myaccount_money_available_show_tv.setText(CommonUtil.formatMoney(infoObj.getAbleMoney()));
        double expectMoney = infoObj.getWaitCapital() + infoObj.getWaitInterest();
        myaccount_money_expect_show_tv.setText(CommonUtil.formatMoney(expectMoney));
        myaccount_money_hide_show_iv.setVisibility(View.VISIBLE);

        recycleDatas.clear();
        recycleDatas.add(new CommonListInfo("投资记录", ""));
        recycleDatas.add(new CommonListInfo("资金记录", ""));
        recycleDatas.add(new CommonListInfo("银行卡", infoObj.getIsBind() == 0 ? "未绑卡" : "已绑卡"));
        isBandCard = infoObj.getIsBind();
        accountRecycleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loginSubscription != null && !loginSubscription.isUnsubscribed()) {
            loginSubscription.unsubscribe();
        }
        if (rechargeOrDrownSubscription != null && !rechargeOrDrownSubscription.isUnsubscribed()) {
            rechargeOrDrownSubscription.unsubscribe();
        }
        if (passwordSubscribe != null && !passwordSubscribe.isUnsubscribed()) {
            passwordSubscribe.unsubscribe();
        }
        if (alterDialogUtils != null && alterDialogUtils.isShowing()) alterDialogUtils.dismiss();
    }
}
