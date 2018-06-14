package com.hacai.exchange.module.product.view;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseFragment;
import com.hacai.exchange.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/7.
 */

public class ProductFragment extends BaseFragment {

    private LinearLayout product_frgment_ll;
    private ViewPager product_vp;
    private XTabLayout mTablayout;
    public static final int PRODUCT_TYPE_HOT = 0;
    public static final int PRODUCT_TYPE_SELL_OUT = 1;
    public static final int PRODUCT_TYPE_PAYMENT = 2;

    @Override
    protected void initView() {
        initStatus();
    }

    @Override
    protected void findViewId(View rootView) {
        product_frgment_ll = (LinearLayout) rootView.findViewById(R.id.product_frgment_ll);
        product_vp = (ViewPager) rootView.findViewById(R.id.product_vp);

        mTablayout = (XTabLayout) rootView.findViewById(R.id.tab_layout);

        setupViewPager(product_vp);
        mTablayout.addTab(mTablayout.newTab().setText(R.string.product_buying_hot));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.product_sold_out));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.product_repayment));
        mTablayout.setupWithViewPager(product_vp);
    }

    @Override
    protected void initData() {
    }

    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        mViewPager.setOffscreenPageLimit(1);
        //        CommonUtil.setScrollerTime(mContext, 500, mViewPager);
        final MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ProductListFragment.newInstance(PRODUCT_TYPE_HOT), CommonUtil
                .getString(R.string.product_buying_hot));
        adapter.addFragment(ProductListFragment.newInstance(PRODUCT_TYPE_SELL_OUT), CommonUtil
                .getString(R.string.product_sold_out));
        adapter.addFragment(ProductListFragment.newInstance(PRODUCT_TYPE_PAYMENT), CommonUtil
                .getString(R.string.product_repayment));
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ProductListFragment item = (ProductListFragment) adapter.getItem(position);
                item.refreshData();
            }
        });
    }


    private void initStatus() {
        //状态栏占用的兼容性
        if (Build.VERSION.SDK_INT >= 21) {
            View statusView = new View(getActivity());
            statusView.setBackgroundColor(CommonUtil.getColor(R.color.bt_start_color));
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, CommonUtil.getStatusBarHeight());
            product_frgment_ll.addView(statusView, 0, lp);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        //        iniViewPager();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
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
