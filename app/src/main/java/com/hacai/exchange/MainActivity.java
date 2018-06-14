package com.hacai.exchange;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.home.view.HomeFragment;
import com.hacai.exchange.module.more.view.MoreFragment;
import com.hacai.exchange.module.myAccount.view.MyAccountFragment;
import com.hacai.exchange.module.product.view.ProductFragment;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.hacai.exchange.utils.ToastUtil;
import com.umeng.analytics.AnalyticsConfig;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout homeBottom;
    private TextView homeBottomTv;
    private ImageView homeBottomIv;
    private LinearLayout productBottom;
    private TextView productBottomTv;
    private ImageView productBottomIv;
    private LinearLayout accountBottom;
    private TextView accountBottomTv;
    private ImageView accountBottomIv;
    private LinearLayout moreBottom;
    private TextView moreBottomTv;
    private ImageView moreBottomIv;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private ProductFragment productFragment;
    private MyAccountFragment accountFragment;
    private MoreFragment moreFragment;
    public static final int HOME = 0;
    public static final int PRODUCT = 1;
    public static final int ACCOUNT = 2;
    public static final int MORE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            setTheme(R.style.mainActivityTheme);
            CommonUtil.setStatusBarInvisible(this, false);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        LogUtil.i("MainActivity onCreate,状态栏高度=" + CommonUtil.getStatusBarHeight() + ";\t 分辨率=" +
                CommonUtil.getAndroidPix(mContext) + ";\t设备信息" + CommonUtil.getDeviceInfo
                (mContext));

        String channelId = AnalyticsConfig.getChannel(getApplicationContext()); //获取友盟的渠道配置信息
        LogUtil.i("channelId = " + channelId);
    }

    private long recodeTime = 0;

    @Override
    protected void initView() {
        homeBottom = (LinearLayout) findViewById(R.id.ll_home);
        homeBottomTv = (TextView) findViewById(R.id.tv_home);
        homeBottomIv = (ImageView) findViewById(R.id.iv_home);

        productBottom = (LinearLayout) findViewById(R.id.ll_product);
        productBottomTv = (TextView) findViewById(R.id.tv_product);
        productBottomIv = (ImageView) findViewById(R.id.iv_product);

        accountBottom = (LinearLayout) findViewById(R.id.ll_myAccount);
        accountBottomTv = (TextView) findViewById(R.id.tv_myAccount);
        accountBottomIv = (ImageView) findViewById(R.id.iv_myAccount);

        moreBottom = (LinearLayout) findViewById(R.id.ll_more);
        moreBottomTv = (TextView) findViewById(R.id.tv_more);
        moreBottomIv = (ImageView) findViewById(R.id.iv_more);

        homeBottom.setOnClickListener(this);
        productBottom.setOnClickListener(this);
        accountBottom.setOnClickListener(this);
        moreBottom.setOnClickListener(this);
        setSelect(0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int index = intent.getIntExtra(Constant.START_ACTIVITY_STRING_1, -1);
            switch (index) {
                case HOME:
                    setSelect(0);
                    break;
                case PRODUCT:
                    setSelect(1);
                    break;
                case ACCOUNT:
                    setSelect(2);
                    break;
                case MORE:
                    setSelect(3);
                    break;
            }
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    protected boolean isNeedCountPage() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - recodeTime < 2000) {
                //退出程序
                HCExchangeApp.exit();
            } else {
                ToastUtil.toast(this, getResources().getString(R.string.click_exit));
                recodeTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                setSelect(0);
                break;
            case R.id.ll_product:
                setSelect(1);
                break;
            case R.id.ll_myAccount:
                setSelect(2);
                break;
            case R.id.ll_more:
                setSelect(3);
                break;
        }
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        hideFragments();
        resetImages();

        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.main_container, homeFragment);
                }
                ft.show(homeFragment);
                homeBottomIv.setImageResource(R.mipmap.tabbar_icon_home_sel);
                homeBottomTv.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                break;
            case 1:
                if (productFragment == null) {
                    productFragment = new ProductFragment();
                    ft.add(R.id.main_container, productFragment);
                }
                ft.show(productFragment);
                productBottomIv.setImageResource(R.mipmap.tabbar_icon_licai_sel);
                productBottomTv.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                break;
            case 2:
                if (accountFragment == null) {
                    accountFragment = new MyAccountFragment();
                    ft.add(R.id.main_container, accountFragment);
                }
                ft.show(accountFragment);
                accountBottomIv.setImageResource(R.mipmap.tabbar_icon_account_sel);
                accountBottomTv.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    ft.add(R.id.main_container, moreFragment);
                }
                ft.show(moreFragment);
                moreBottomIv.setImageResource(R.mipmap.tabbar_icon_more_sel);
                moreBottomTv.setTextColor(CommonUtil.getColor(R.color.bt_start_color));
                break;
        }
        ft.commit();
    }

    private void hideFragments() {
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (productFragment != null) {
            ft.hide(productFragment);
        }
        if (accountFragment != null) {
            ft.hide(accountFragment);
        }
        if (moreFragment != null) {
            ft.hide(moreFragment);
        }
    }

    private void resetImages() {

        homeBottomIv.setImageResource(R.mipmap.tabbar_icon_home);
        productBottomIv.setImageResource(R.mipmap.tabbar_icon_licai);
        accountBottomIv.setImageResource(R.mipmap.tabbar_icon_account);
        moreBottomIv.setImageResource(R.mipmap.tabbar_icon_more);

        homeBottomTv.setTextColor(CommonUtil.getColor(R.color.text_normal_color));
        productBottomTv.setTextColor(CommonUtil.getColor(R.color.text_normal_color));
        accountBottomTv.setTextColor(CommonUtil.getColor(R.color.text_normal_color));
        moreBottomTv.setTextColor(CommonUtil.getColor(R.color.text_normal_color));
    }

    /**
     * 是否使用沉浸式状态栏，默认为true；
     */
    @Override
    public boolean isImmersiveStatusBar() {
        return Build.VERSION.SDK_INT < 21;
    }

}
