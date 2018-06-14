package com.hacai.exchange.module.home.view;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.BidRecodeInfo;
import com.hacai.exchange.bean.TextShowInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.common.NetResponseCode;
import com.hacai.exchange.module.home.model.HomeInterface;
import com.hacai.exchange.module.home.presenter.HomePresenter;
import com.hacai.exchange.module.home.presenter.ProductDetailBidRecAdapter;
import com.hacai.exchange.ui.widget.FullyLinearLayoutManager;
import com.hacai.exchange.ui.widget.HaCaiSwipeRefreshLayout;
import com.hacai.exchange.ui.widget.MyRecycleView;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/12/15.
 * 投标记录
 */

public class ProductDetailBidRecodeActivity extends BaseActivity implements HomeInterface
        .IHomeView {

    private static final int PRODUCT_BID_RECODE_TAG = 1;
    private TextView title_header_tv;
    private MyRecycleView product_detail_bid_rv;
    private ProductDetailBidRecAdapter productDetailBidRecAdapter;
    private String productId;
    private HomePresenter homePresenter;
    private int countItem = 1;//当前加载的页数，默认为第一页
    private boolean isPullDown = true;//true标记是下拉刷新，false上拉加载更多
    private boolean hasMoreData = true;//当前加载的页数，默认为第一页
    private HaCaiSwipeRefreshLayout product_bid_recode_list_hcswipe;
    private static final int SUCCESS = 0;
    private static final int ERROR_DATA = 1;
    private static final int NO_DATA = 2;
    private View bid_recode_data_error_vs;
    private TextView product_bid_recode_people_num_tv;
    private TextView product_bid_recode_total_money_tv;

    @Override
    protected void initView() {
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        product_detail_bid_rv = (MyRecycleView) findViewById(R.id.product_detail_bid_rv);
        product_bid_recode_list_hcswipe = (HaCaiSwipeRefreshLayout) findViewById(R.id
                .product_bid_recode_list_hcswipe);
        bid_recode_data_error_vs = findViewById(R.id.bid_recode_data_error_vs);
        product_bid_recode_people_num_tv = (TextView) findViewById(R.id
                .product_bid_recode_people_num_tv);
        product_bid_recode_total_money_tv = (TextView) findViewById(R.id
                .product_bid_recode_total_money_tv);
    }

    @Override
    protected void initData() {
        productId = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_1);
        homePresenter = new HomePresenter(this);
        homePresenter.attachView(this);
        getProductBidRecod(true);
    }

    private void getProductBidRecod(boolean isShowProgress) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("id", productId);
        baseMaps.put("pageNumber", countItem);
        homePresenter.getProductBidRecode(baseMaps, PRODUCT_BID_RECODE_TAG, isShowProgress);
    }

    List<BidRecodeInfo.ObjBean.BorrowTenderItemListBean> bidRecodeDatas = new ArrayList<>();

    private void initRecyclerView() {
        product_detail_bid_rv.setItemAnimator(new DefaultItemAnimator());
        FullyLinearLayoutManager mLayoutManager = new FullyLinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        product_detail_bid_rv.setLayoutManager(mLayoutManager);
        productDetailBidRecAdapter = new ProductDetailBidRecAdapter<BidRecodeInfo.ObjBean
                .BorrowTenderItemListBean>(mContext, bidRecodeDatas, R.layout.capital_recode_item);
        product_detail_bid_rv.setLayoutManager(new LinearLayoutManager(this));
        product_detail_bid_rv.setAdapter(productDetailBidRecAdapter);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.program_bid_recode));
        initRecyclerView();
        initSwipeRefresh();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detail_bid_recode;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        if (isPullDown) {
            product_bid_recode_list_hcswipe.setHCRefreshing(false);
        } else {
            product_bid_recode_list_hcswipe.setHCLoadMore(false);
        }
        switch (tag) {
            case PRODUCT_BID_RECODE_TAG:
                if (NetResponseCode.HMC_SUCCESS.equalsIgnoreCase(code)) {
                    showDataView(NO_DATA, product_bid_recode_list_hcswipe,
                            bid_recode_data_error_vs);
                } else {
                    showDataView(ERROR_DATA, product_bid_recode_list_hcswipe,
                            bid_recode_data_error_vs);
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        if (isPullDown) {
            product_bid_recode_list_hcswipe.setHCRefreshing(false);
        } else {
            product_bid_recode_list_hcswipe.setHCLoadMore(false);
        }

        switch (tag) {
            case PRODUCT_BID_RECODE_TAG:
                List<BidRecodeInfo.ObjBean.BorrowTenderItemListBean> borrowTenderItemList = null;
                BidRecodeInfo.ObjBean.PageBeanBean pageBean = null;
                BidRecodeInfo.ObjBean bidRecodeInfoObj = null;
                try {
                    BidRecodeInfo bidRecodeInfo = (BidRecodeInfo) response;
                    bidRecodeInfoObj = bidRecodeInfo.getObj();
                    borrowTenderItemList = bidRecodeInfoObj.getBorrowTenderItemList();
                    pageBean = bidRecodeInfoObj.getPageBean();
                } catch (Exception e) {
                    LogUtil.i("Exception=" + e.getMessage());
                    if (borrowTenderItemList == null) {
                        showDataView(NO_DATA, product_bid_recode_list_hcswipe,
                                bid_recode_data_error_vs);
                    }
                }

                if (borrowTenderItemList == null || borrowTenderItemList.size() == 0) {
                    toast("投标记录为空");
                    showDataView(NO_DATA, product_bid_recode_list_hcswipe,
                            bid_recode_data_error_vs);
                }
                if (isPullDown) {
                    bidRecodeDatas.clear();
                }
                bidRecodeDatas.addAll(borrowTenderItemList);
                productDetailBidRecAdapter.notifyDataSetChanged();
                showDataView(SUCCESS, product_bid_recode_list_hcswipe, bid_recode_data_error_vs);


                List<TextShowInfo> canAccounTextList = new ArrayList<>();
                canAccounTextList.clear();
                canAccounTextList.add(new TextShowInfo(bidRecodeInfoObj.getTenderSum() + "", R
                        .color.bt_end_color, R.dimen.text_size_13));
                canAccounTextList.add(new TextShowInfo(" 人", R.color.text_normal_color, R.dimen
                        .text_size_13));
                product_bid_recode_people_num_tv.setText(CommonUtil.setSpannableText
                        (canAccounTextList));


                canAccounTextList.clear();
                canAccounTextList.add(new TextShowInfo(CommonUtil.format2PointMillion
                        (bidRecodeInfoObj.getAmount()), R.color.bt_end_color, R.dimen
                        .text_size_13));
                canAccounTextList.add(new TextShowInfo("万元", R.color.text_normal_color, R.dimen
                        .text_size_13));
                product_bid_recode_total_money_tv.setText(CommonUtil.setSpannableText
                        (canAccounTextList));
                calculatePage(pageBean);
                break;
        }
    }

    private void calculatePage(BidRecodeInfo.ObjBean.PageBeanBean pageBean) {
        if (pageBean == null) {
            return;
        }
        int pageTotal = pageBean.getPageCount();
        int pageCurrent = pageBean.getPageNumber();
        if (pageCurrent >= pageTotal) {
            View viewRoot = LayoutInflater.from(mContext).inflate(R.layout
                    .swipe_refresh_layout_footer, null);
            TextView footer_tv = (TextView) viewRoot.findViewById(R.id.swipe_refresh_footer_tv);
            footer_tv.setText(CommonUtil.getString(R.string.no_more_data));
            productDetailBidRecAdapter.addFooterView(viewRoot);
            product_bid_recode_list_hcswipe.removeFooterView();
            hasMoreData = false;
        } else {
            product_bid_recode_list_hcswipe.setFooterView();
            productDetailBidRecAdapter.removeFooterView();
            hasMoreData = true;
            countItem++;
        }
    }

    private void initSwipeRefresh() {
        product_bid_recode_list_hcswipe.setTargetScrollWithLayout(true);
        product_bid_recode_list_hcswipe.setHeaderView();
        product_bid_recode_list_hcswipe.setOnHCPullRefreshListener(new HaCaiSwipeRefreshLayout
                .OnHCPullRefreshListener() {
            @Override
            public void onRefresh() {
                isPullDown = true;
                countItem = 1;
                getProductBidRecod(false);
            }

            @Override
            public void onPullDistance(int distance) {
            }

            @Override
            public void onPullEnable(boolean enable) {
            }
        });
        product_bid_recode_list_hcswipe.setOnHCPushLoadMoreListener(new HaCaiSwipeRefreshLayout
                .OnHCPushLoadMoreListener() {


            @Override
            public void onLoadMore() {
                if (hasMoreData) {
                    isPullDown = false;
                    if (countItem == 1) countItem = 2;
                    getProductBidRecod(false);
                } else {
                    product_bid_recode_list_hcswipe.setHCLoadMore(false);
                }
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });

    }

    /**
     * 0显示成功view,1显示错误view，2显示空数据
     */
    public void showDataView(int status, View showView, View errorView) {
        View loading_data_empty_ll = errorView.findViewById(R.id.loading_data_empty_ll);
        View loading_data_error_ll = errorView.findViewById(R.id.loading_data_error_ll);

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
                        getProductBidRecod(true);
                    }
                });
                break;
            case NO_DATA:
                showView.setVisibility(View.INVISIBLE);
                showView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);

                loading_data_error_ll.setVisibility(View.INVISIBLE);
                loading_data_empty_ll.setVisibility(View.VISIBLE);
                break;
        }
    }
}
