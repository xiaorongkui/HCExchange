package com.hacai.exchange.module.home.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseFragment;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.base.BaseViewPagerAdapter;
import com.hacai.exchange.bean.HomeProductInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.home.model.HomeInterface;
import com.hacai.exchange.module.home.presenter.HomeBuyingAdapter;
import com.hacai.exchange.module.home.presenter.HomeNewAdapter;
import com.hacai.exchange.module.home.presenter.HomePresenter;
import com.hacai.exchange.ui.widget.AutoRollLayout;
import com.hacai.exchange.ui.widget.FullyLinearLayoutManager;
import com.hacai.exchange.ui.widget.HaCaiSwipeRefreshLayout;
import com.hacai.exchange.ui.widget.ScrollViewListener;
import com.hacai.exchange.ui.widget.SpaceItemDecoration;
import com.hacai.exchange.ui.widget.WrapContentHeightViewPager;
import com.hacai.exchange.ui.widget.bean.RollItem;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/12/7.
 */

public class HomeFragment extends BaseFragment implements HomeInterface.IHomeView {

    private static final int SHOW_HOME_DATA_TAG = 1;

    private static final int SUCCESS = 0;
    private static final int ERROR_DATA = 1;
    private static final int NO_DATA = 2;
    private static final int NO_DATA_EXPECT = -1;
    private AutoRollLayout home_auto_ll;
    private GridView home_introduce_gv;
    private ImageView home_msg_iv;
    private View home_status_bar_view;
    //头部图片(轮播图的高度)
    private View home_header_status_ll;
    private int mHeaderBannerHeight;
    private int mHeaderStatusHeight;
    ;
    private ScrollViewListener home_scroll_view;
    private ImageView home_header_iv;
    private View home_header_bg_view;
    private View home_header_line_view;
    private WrapContentHeightViewPager home_new_producet_vp;
    private RecyclerView home_buying_rv;
    private HaCaiSwipeRefreshLayout home_fragment_hcswipe_refresh;
    private HomePresenter homePresenter;
    private HomeNewAdapter<HomeProductInfo.IndexBorrowBean> homeNewAdapter;
    private HomeBuyingAdapter<HomeProductInfo.IndexHotBorrowsBean> buyingAdapter;
    private View home_data_error_vs;
    private View home_show_data_ll;
    private TextView home_hot_product_notice_title_tv;
    private View rootView;

    @Override
    protected void initView() {
        initAuto(null);
        initGrideView();
        initMsgView();
        initScrollChange();
        initViewPager();
        initRecycleView();
        initRefreshLayout();
    }

    @Override
    protected void findViewId(View rootView) {
        this.rootView = rootView;
        home_auto_ll = (AutoRollLayout) rootView.findViewById(R.id.home_auto_ll);
        home_introduce_gv = (GridView) rootView.findViewById(R.id.home_introduce_gv);
        home_msg_iv = (ImageView) rootView.findViewById(R.id.home_msg_iv);
        home_scroll_view = (ScrollViewListener) rootView.findViewById(R.id.home_scroll_view);
        home_header_status_ll = rootView.findViewById(R.id.home_header_status_ll);
        home_status_bar_view = rootView.findViewById(R.id.home_status_bar_view);
        home_header_bg_view = rootView.findViewById(R.id.home_header_bg_view);
        home_header_iv = (ImageView) rootView.findViewById(R.id.home_header_iv);
        home_header_line_view = rootView.findViewById(R.id.home_header_line_view);
        home_new_producet_vp = (WrapContentHeightViewPager) rootView.findViewById(R.id
                .home_new_producet_vp);
        home_buying_rv = (RecyclerView) rootView.findViewById(R.id.home_buying_rv);
        home_fragment_hcswipe_refresh = (HaCaiSwipeRefreshLayout) rootView.findViewById(R.id
                .home_fragment_hcswipe_refresh);


        home_data_error_vs = rootView.findViewById(R.id.home_data_error_vs);

        home_show_data_ll = rootView.findViewById(R.id.home_show_data_ll);
        home_hot_product_notice_title_tv = (TextView) rootView.findViewById(R.id
                .home_hot_product_notice_title_tv);
    }

    @Override
    protected void initData() {
        homePresenter = new HomePresenter((RxAppCompatActivity) getActivity());
        homePresenter.attachView(this);
        getHomeData(true);
    }

    private void getHomeData(boolean isShowProgress) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        homePresenter.showHomeData(baseMaps, SHOW_HOME_DATA_TAG, isShowProgress);
    }

    private void initRefreshLayout() {
        home_fragment_hcswipe_refresh.setTargetScrollWithLayout(true);
        home_fragment_hcswipe_refresh.setOnHCPullRefreshListener(new HaCaiSwipeRefreshLayout
                .OnHCPullRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeData(false);
                Observable.timer(10000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (home_fragment_hcswipe_refresh.isRefreshing())
                            home_fragment_hcswipe_refresh.setHCRefreshing(false);
                    }
                });
            }

            @Override
            public void onPullDistance(int distance) {
                if (distance > 0) {
                    home_msg_iv.setVisibility(View.INVISIBLE);
                } else {
                    home_msg_iv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
    }

    List<HomeProductInfo.IndexHotBorrowsBean> productDatas = new ArrayList<>();

    private void initRecycleView() {

        buyingAdapter = new HomeBuyingAdapter<HomeProductInfo.IndexHotBorrowsBean>(mContext,
                productDatas, R.layout.home_buying_rv_item);
        FullyLinearLayoutManager mLayoutManager = new FullyLinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        home_buying_rv.setLayoutManager(mLayoutManager);
        home_buying_rv.addItemDecoration(new SpaceItemDecoration((int) CommonUtil.getDimen(R
                .dimen.y10)));
        home_buying_rv.setAdapter(buyingAdapter);
        buyingAdapter.setOnItemClickLitener(new BaseRecylerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, productDatas.get(position)
                        .getId());
                intent.putExtra(Constant.START_ACTIVITY_STRING_2, productDatas.get(position)
                        .getName());
                intent.putExtra(Constant.START_ACTIVITY_STRING_3, 0);
                CommonUtil.gotoActivity(mContext, intent);
            }
        });
    }

    private List<HomeProductInfo.IndexBorrowBean> newProductdatas = new ArrayList<>();

    private void initViewPager() {
        CommonUtil.setScrollerTime(mContext, 100, home_new_producet_vp);
        homeNewAdapter = new HomeNewAdapter<HomeProductInfo.IndexBorrowBean>(mContext,
                newProductdatas, R.layout.home_new_item);
        homeNewAdapter.setOnPagerItemClickListener(new BaseViewPagerAdapter
                .OnPagerItemClickListener() {
            @Override
            public void onPagerItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, newProductdatas.get(position)
                        .getId());
                intent.putExtra(Constant.START_ACTIVITY_STRING_2, newProductdatas.get(position)
                        .getName());
                intent.putExtra(Constant.START_ACTIVITY_STRING_3, 0);
                CommonUtil.gotoActivity(mContext, intent);
            }
        });
        home_new_producet_vp.setAdapter(homeNewAdapter);
    }

    private void initScrollChange() {
        mHeaderBannerHeight = home_auto_ll.getMesureHeight();

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        home_header_status_ll.measure(w, h);
        mHeaderStatusHeight = home_header_status_ll.getMeasuredHeight();
        home_scroll_view.setOnScrollListener(new ScrollViewListener.ScrollListener() {
            @Override
            public void scrollOritention(int x, int y, int oldx, int oldy) {
                //                LogUtil.i(";x=" + x + "y=" + y + "oldx=" + oldx + "oldy=" + oldy);
                //设置其透明度
                float alpha = 0;
                int scollYHeight = y;
                //起始截止变化高度,如可以变化高度为mRecyclerHeaderHeight
                int baseHeight = mHeaderBannerHeight - mHeaderStatusHeight;
                if (scollYHeight > 0) {
                    if (scollYHeight >= baseHeight) {
                        //完全不透明
                        alpha = 1;
                    } else {
                        //产生渐变效果
                        alpha = scollYHeight / (baseHeight * 1.0f);
                    }
                } else {//向上划
                    if (Math.abs(scollYHeight) >= baseHeight) {
                        //完全不透明
                        alpha = 1;
                    } else {
                        //产生渐变效果
                        alpha = Math.abs(scollYHeight) / (baseHeight * 1.0f);
                    }
                }
                home_status_bar_view.setAlpha(alpha);
                home_header_bg_view.setAlpha(alpha);
                home_header_iv.setAlpha(alpha);
                home_header_line_view.setAlpha(alpha);

                home_status_bar_view.setVisibility(View.VISIBLE);
                home_header_bg_view.setVisibility(View.VISIBLE);
                home_header_iv.setVisibility(View.VISIBLE);
                home_header_line_view.setVisibility(View.VISIBLE);
                home_msg_iv.setVisibility(View.VISIBLE);
                if (alpha == 1) {
                    //                    home_msg_iv.setImageResource(R.mipmap.nav_icon_msg);
                }
                if (alpha == 0) {
                    //                    home_msg_iv.setImageResource(R.mipmap.nav_icon_msg_white);
                }
            }
        });
    }

    private void initAuto(List<RollItem> rollItems) {
        if (rollItems == null) {
            List<RollItem> deafeultRollItems = new ArrayList<>();
            deafeultRollItems.add(new RollItem("", R.mipmap.img_home_banner, ""));
            rollItems = deafeultRollItems;
        }
        home_auto_ll.setItems(rollItems);
        home_auto_ll.setAutoRoll(true);
        home_auto_ll.setOnAutoItemClickListener(new AutoRollLayout.OnAutoItemClickListener() {
            @Override
            public void OnAutoItemClick(View view, int position) {
                LogUtil.i("点击图片=" + position);
            }
        });
    }

    private void initGrideView() {
        int[] icon = {R.mipmap.home_icon_fengkong, R.mipmap.home_icon_licai, R.mipmap
                .home_icon_shouyi};
        String[] iconName = {"风控体系", "理财项目", "稳定收益"};
        String[] iconName_1 = {"更用心", "更真心", "更诚心"};
        ArrayList<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            map.put("text_1", iconName_1[i]);
            data_list.add(map);
        }

        String[] from = {"image", "text", "text_1"};
        int[] to = {R.id.gride_image, R.id.gride_text, R.id.gride_text_1};
        SimpleAdapter sim_adapter = new SimpleAdapter(mContext, data_list, R.layout
                .home_item_grideview, from, to);
        home_introduce_gv.setAdapter(sim_adapter);
        home_introduce_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
            }
        });
    }

    private void initMsgView() {
        //状态栏占用的兼容性
        if (Build.VERSION.SDK_INT >= 21) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.height = CommonUtil.getStatusBarHeight();
            home_status_bar_view.setLayoutParams(lp);
        } else {//没有状态栏不用设置

        }
        home_status_bar_view.setVisibility(View.INVISIBLE);
        home_header_bg_view.setVisibility(View.INVISIBLE);
        home_header_iv.setVisibility(View.INVISIBLE);
        home_header_line_view.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initTitle() {
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        home_fragment_hcswipe_refresh.setHCRefreshing(false);
        switch (tag) {
            case SHOW_HOME_DATA_TAG:
                showDataView(ERROR_DATA, home_show_data_ll, home_data_error_vs);
                toast(errorMsg);
                //                homeNewAdapter.notifyDataSetChanged();
                //                buyingAdapter.notifyDataSetChanged();
                //                showDataView(NO_DATA_EXPECT, home_show_data_ll,
                // home_data_error_vs);
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        home_fragment_hcswipe_refresh.setHCRefreshing(false);
        switch (tag) {
            case SHOW_HOME_DATA_TAG:
                HomeProductInfo homeProductInfo = null;
                try {
                    homeProductInfo = (HomeProductInfo) response;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (homeProductInfo == null) {
                    showDataView(NO_DATA, home_show_data_ll, home_data_error_vs);
                    return;
                }
                //轮播图
                List<HomeProductInfo.IndexImageItemListBean> indexImageItemList = homeProductInfo
                        .getIndexImageItemList();
                if (indexImageItemList != null) {
                    List<RollItem> rollItems = new ArrayList<>();
                    for (HomeProductInfo.IndexImageItemListBean indexImageItemListBean :
                            indexImageItemList) {

                        String imageUrl = indexImageItemListBean.getImageUrl();
                        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.startsWith("http")) {
                            if (imageUrl.startsWith("/")) {
                                imageUrl = imageUrl.substring(1, imageUrl.length());
                            }
                        }
                        rollItems.add(new RollItem(AppNetConfig.baseUrl + imageUrl, ""));
                    }
                    initAuto(rollItems);
                }

                //新手标
                HomeProductInfo.IndexBorrowBean indexBorrow = homeProductInfo.getIndexBorrow();
                //产品页面
                List<HomeProductInfo.IndexHotBorrowsBean> indexHotBorrows = homeProductInfo
                        .getIndexHotBorrows();
                if (indexBorrow == null && (indexHotBorrows == null || indexHotBorrows.size() ==
                        0)) {
                    showDataView(NO_DATA_EXPECT, home_show_data_ll, home_data_error_vs);
                    return;
                }

                if (indexBorrow != null) {
                    newProductdatas.clear();
                    newProductdatas.add(indexBorrow);
                }
                homeNewAdapter.notifyDataSetChanged();


                if (indexHotBorrows != null && indexHotBorrows.size() > 0) {
                    productDatas.clear();
                    productDatas.addAll(indexHotBorrows);
                    home_hot_product_notice_title_tv.setVisibility(View.VISIBLE);
                } else {
                    home_hot_product_notice_title_tv.setVisibility(View.GONE);
                }
                buyingAdapter.notifyDataSetChanged();
                showDataView(SUCCESS, home_show_data_ll, home_data_error_vs);
                break;
        }
    }

    /**
     * 0显示成功view,1显示错误view，2显示空数据
     */
    public void showDataView(int status, View showView, View errorView) {
        View loading_data_empty_ll = errorView.findViewById(R.id.loading_data_empty_ll);
        View loading_data_error_ll = errorView.findViewById(R.id.loading_data_error_ll);
        View loading_data_expect_ll = errorView.findViewById(R.id.loading_data_expect_ll);

        switch (status) {
            case SUCCESS:
                showView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.INVISIBLE);
                break;
            case ERROR_DATA:
                showView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);

                loading_data_error_ll.setVisibility(View.VISIBLE);
                loading_data_empty_ll.setVisibility(View.INVISIBLE);
                errorView.findViewById(R.id.error_error_ll).setOnClickListener(new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getHomeData(true);
                    }
                });
                break;
            case NO_DATA:
                showView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);

                loading_data_error_ll.setVisibility(View.INVISIBLE);
                loading_data_empty_ll.setVisibility(View.VISIBLE);
                break;
            case NO_DATA_EXPECT:
                showView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);

                loading_data_error_ll.setVisibility(View.INVISIBLE);
                loading_data_empty_ll.setVisibility(View.INVISIBLE);
                loading_data_expect_ll.setVisibility(View.VISIBLE);


                RelativeLayout home_data_error_rl = (RelativeLayout) rootView.findViewById(R.id
                        .home_data_error_rl);
                FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                        .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                home_data_error_rl.setLayoutParams(fl);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        home_auto_ll.setAutoRoll(!hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
        home_auto_ll.setAutoRoll(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        home_auto_ll.setAutoRoll(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        home_auto_ll.setAutoRoll(isVisibleToUser);
    }
}
