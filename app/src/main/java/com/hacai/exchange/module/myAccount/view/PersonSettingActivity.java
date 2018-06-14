package com.hacai.exchange.module.myAccount.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hacai.exchange.MainActivity;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.CommonListInfo;
import com.hacai.exchange.bean.LoginReponse;
import com.hacai.exchange.bean.RiskInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.presenter.LoginPresenter;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
import com.hacai.exchange.module.myAccount.presenter.PersonSettingsRecycleAdapter;
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.LoginEvent;
import com.hacai.exchange.rxbus.event.PasswordEvent;
import com.hacai.exchange.rxbus.event.RiskEvent;
import com.hacai.exchange.ui.widget.AlterDialogUtils;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/12/15.
 */

public class PersonSettingActivity extends BaseActivity implements LoginInterface.ILoginView,
        MyAccountInterface.IMyAccountView {

    private static final int LOGIN_OUT_TAG = 1;
    private static final int RISK_LEVEL_TAG = 2;
    private TextView title_header_tv;
    private RecyclerView person_setting_rv;
    private TextView person_setting_login_out_tv;
    private LoginPresenter loginPresenter;
    private int setPayPwdTag;
    private PersonSettingsRecycleAdapter settingsRecycleAdapter;
    private AlterDialogUtils alterDialogUtils;
    private MyAccountPresenter myAccountPresenter;
    private boolean isRisk;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        person_setting_rv = (RecyclerView) findViewById(R.id.person_setting_rv);
        person_setting_login_out_tv = (TextView) findViewById(R.id.person_setting_login_out_tv);
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);
        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.attachView(this);

        getRiskLevel(true);
    }

    private void getRiskLevel(boolean b) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        myAccountPresenter.getRiskLevel(baseMaps, RISK_LEVEL_TAG, b);
    }

    @Override
    protected void initTitle() {
        setPayPwdTag = getIntent().getIntExtra(Constant.START_ACTIVITY_STRING_1, -1);
        title_header_tv.setText(CommonUtil.getString(R.string.person_settings));
        initRecyclerView();
        initEvent();
        RxView.clicks(person_setting_login_out_tv).throttleFirst(2, TimeUnit.SECONDS).compose
                (this.<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startLoginOut();
            }
        });
    }

    private void initEvent() {
        RxBus.getInstance().toObserverable(PasswordEvent.class).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<PasswordEvent>() {

            @Override
            public void call(PasswordEvent passwordEvent) {
                LogUtil.i("收到密码事件passwordEvent=" + passwordEvent.isSuccess());
                if (passwordEvent.isSuccess() && passwordEvent.getTag() == 1) {
                    datas.clear();
                    datas.add(new CommonListInfo("手机号", CommonUtil.dealPhoneNum(CommonUtil
                            .getLoginUserPhoneNum(), 3, 4)));
                    //        datas.add(new CommonListInfo("实名认证", "立即认证"));
                    datas.add(new CommonListInfo("风险评估", "未评测"));
                    datas.add(new CommonListInfo("修改登录密码", ""));
                    datas.add(new CommonListInfo("修改交易密码", ""));
                    datas.add(new CommonListInfo("忘记交易密码", ""));

                    settingsRecycleAdapter.notifyDataSetChanged();
                }

            }
        });
        RxBus.getInstance().toObserverable(RiskEvent.class).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<RiskEvent>() {

            @Override
            public void call(RiskEvent riskEvent) {
                LogUtil.i("收到风险评估事件passwordEvent=" + riskEvent.isSuccess());
                if (riskEvent.isSuccess()) {
                    getRiskLevel(false);
                }

            }
        });
    }

    private void startLoginOut() {
        //退出登录
        alterDialogUtils = new AlterDialogUtils(mContext, R.style.payment_dialog_notice);
        alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen.x290));
        alterDialogUtils.setOneOrTwoBtn(false);
        alterDialogUtils.setMessage("是否退出登录？");
        alterDialogUtils.setTwoCancelBtn("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterDialogUtils.dismiss();
            }
        });
        alterDialogUtils.setTwoConfirmBtn("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
                baseMaps.put("token", CommonUtil.getTokenId());
                loginPresenter.loginOut(baseMaps, LOGIN_OUT_TAG, true);

            }
        });
        alterDialogUtils.show();
    }

    private List<CommonListInfo> datas = new ArrayList<>();

    private void initRecyclerView() {
        datas.add(new CommonListInfo("手机号", CommonUtil.dealPhoneNum(CommonUtil
                .getLoginUserPhoneNum(), 3, 4)));
        //        datas.add(new CommonListInfo("实名认证", "立即认证"));
        datas.add(new CommonListInfo("风险评估", "未评测"));
        datas.add(new CommonListInfo("修改登录密码", ""));
        if (setPayPwdTag == 1) {//已设置
            datas.add(new CommonListInfo("修改交易密码", ""));
            datas.add(new CommonListInfo("忘记交易密码", ""));
        } else {
            datas.add(new CommonListInfo("设置交易密码", ""));
        }

        person_setting_rv.setHasFixedSize(true);
        person_setting_rv.setItemAnimator(new DefaultItemAnimator());
        settingsRecycleAdapter = new PersonSettingsRecycleAdapter<CommonListInfo>(mContext,
                datas, R.layout.account_list_rv_item);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        person_setting_rv.setLayoutManager(mLayoutManager);
        settingsRecycleAdapter.setOnItemClickLitener(new BaseRecylerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        if (!isRisk)
                            CommonUtil.gotoActivity(mContext, RiskAssessmentActivity.class);
                        break;
                    case 2://修改登录密码
                        CommonUtil.gotoActivity(mContext, LoginPwdModiftyActivity.class);
                        break;
                    case 3://设置交易密码
                        if (setPayPwdTag == 1) {//已设置交易密码，这里去修改
                            CommonUtil.gotoActivity(mContext, ExchangeModiftyPwdActivity.class);
                        } else {//设置交易密码
                            CommonUtil.gotoActivity(mContext, ExchangePwdSettingActivity.class);
                        }
                        break;
                    case 4:
                        CommonUtil.gotoActivity(mContext, ExchangePwdForgetOneActivity.class);
                        break;
                }
            }
        });
        person_setting_rv.setAdapter(settingsRecycleAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_settings;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        switch (tag) {
            case LOGIN_OUT_TAG:
                toast(errorMsg);
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case LOGIN_OUT_TAG:
                toast("退出登录成功");
                RxBus.getInstance().post(new LoginEvent(false));
                LoginReponse loginInfo = CommonUtil.getLoginInfo();
                loginInfo.setToken("");
                CommonUtil.saveLoginInfo(loginInfo);
                MobclickAgent.onProfileSignOff();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, MainActivity.ACCOUNT);
                CommonUtil.gotoActivity(mContext, intent);
                AppManager.getInstance().removeCurrent();
                break;
            case RISK_LEVEL_TAG:
                String level = "";
                try {
                    RiskInfo riskInfo = (RiskInfo) response;
                    level = riskInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(level)) {
                    if (level.equals("0")) {
                        isRisk = false;
                        datas.set(1, new CommonListInfo("风险评估", "未评测"));
                    } else {
                        isRisk = true;
                        datas.set(1, new CommonListInfo("风险评估", level));
                    }
                    settingsRecycleAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void clearPassword() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alterDialogUtils != null && alterDialogUtils.isShowing()) alterDialogUtils.dismiss();
    }
}
