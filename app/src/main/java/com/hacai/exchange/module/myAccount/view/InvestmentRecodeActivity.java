package com.hacai.exchange.module.myAccount.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/18.投资记录
 */

public class InvestmentRecodeActivity extends BaseActivity {

    private TextView title_header_tv;
    private XTabLayout investment_recode_tab_layout;
    private ViewPager investment_recode_vp;
    public static final int INVESTMENT_TYPE_ING = 0;
    public static final int INVESTMENT_TYPE_PAYMENT = 1;
    public static final int INVESTMENT_TYPE_FINISH = 2;
    private TextView invsetment_recode_expect_tv;
    private TextView invsetment_recode_interest_tv;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        investment_recode_tab_layout = (XTabLayout) findViewById(R.id.investment_recode_tab_layout);
        investment_recode_vp = (ViewPager) findViewById(R.id.investment_recode_vp);
        invsetment_recode_expect_tv = (TextView) findViewById(R.id.invsetment_recode_expect_tv);
        invsetment_recode_interest_tv = (TextView) findViewById(R.id.invsetment_recode_interest_tv);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.investment_recode));
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        investment_recode_vp.setOffscreenPageLimit(1);
        CommonUtil.setScrollerTime(mContext, 100, investment_recode_vp);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(InvestmentRcodeFragment.newInstance(INVESTMENT_TYPE_ING), CommonUtil
                .getString(R.string.investment_recodeing));
        adapter.addFragment(InvestmentRcodeFragment.newInstance(INVESTMENT_TYPE_PAYMENT),
                CommonUtil.getString(R.string.investment_recode_money_back));
        adapter.addFragment(InvestmentRcodeFragment.newInstance(INVESTMENT_TYPE_FINISH),
                CommonUtil.getString(R.string.investment_recode_finish));
        investment_recode_vp.setAdapter(adapter);
    }

    private void initTabLayout() {
        investment_recode_tab_layout.addTab(investment_recode_tab_layout.newTab().setText(R
                .string.investment_recodeing));
        investment_recode_tab_layout.addTab(investment_recode_tab_layout.newTab().setText(R
                .string.investment_recode_money_back));
        investment_recode_tab_layout.addTab(investment_recode_tab_layout.newTab().setText(R
                .string.investment_recode_finish));
        investment_recode_tab_layout.setupWithViewPager(investment_recode_vp);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_investment_recode;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private final FragmentManager fm;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = mFragments.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    void setWaitcapital(double waitPrincipal, double waitProfit) {
        invsetment_recode_expect_tv.setText(CommonUtil.formatMoney(waitPrincipal));
        invsetment_recode_interest_tv.setText(CommonUtil.formatMoney(waitProfit));
    }
}
