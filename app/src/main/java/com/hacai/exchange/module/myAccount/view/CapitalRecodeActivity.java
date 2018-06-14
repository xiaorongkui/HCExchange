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
 * Created by lenovo on 2017/12/19.资金记录
 */

public class CapitalRecodeActivity extends BaseActivity {

    private TextView title_header_tv;
    private XTabLayout capital_recode_tab_layout;
    private ViewPager capital_recode_vp;
    public static final int TYPE_CAPITAL_MONEY = 0;
    public static final int TYPE_CAPITAL_RECHARGE = 1;
    public static final int TYPE_CAPITAL_WITHDRAW = 2;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        capital_recode_tab_layout = (XTabLayout) findViewById(R.id.capital_recode_tab_layout);
        capital_recode_vp = (ViewPager) findViewById(R.id.capital_recode_vp);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.capital_recode));
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
        capital_recode_tab_layout.addTab(capital_recode_tab_layout.newTab().setText(R.string.capital_detail));
        capital_recode_tab_layout.addTab(capital_recode_tab_layout.newTab().setText(R.string.recharge_recode));
        capital_recode_tab_layout.addTab(capital_recode_tab_layout.newTab().setText(R.string.withdraw_recode));
        capital_recode_tab_layout.setupWithViewPager(capital_recode_vp);
    }

    private void initViewPager() {
        capital_recode_vp.setOffscreenPageLimit(1);
        CommonUtil.setScrollerTime(mContext, 100, capital_recode_vp);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CapitalRcodeFragment.newInstance(TYPE_CAPITAL_MONEY), CommonUtil.getString(R.string.capital_detail));
        adapter.addFragment(CapitalRcodeFragment.newInstance(TYPE_CAPITAL_RECHARGE), CommonUtil.getString(R.string.recharge_recode));
        adapter.addFragment(CapitalRcodeFragment.newInstance(TYPE_CAPITAL_WITHDRAW), CommonUtil.getString(R.string.withdraw_recode));
        capital_recode_vp.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_capital_recode;
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
}
