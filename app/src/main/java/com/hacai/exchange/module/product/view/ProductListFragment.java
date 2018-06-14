package com.hacai.exchange.module.product.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseFragment;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.ProductInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.home.view.ProductDetailActivity;
import com.hacai.exchange.module.product.model.ProductInterface;
import com.hacai.exchange.module.product.presenter.ProductListRecycleAdapter;
import com.hacai.exchange.module.product.presenter.ProductPresenter;
import com.hacai.exchange.ui.widget.FullyLinearLayoutManager;
import com.hacai.exchange.ui.widget.HaCaiSwipeRefreshLayout;
import com.hacai.exchange.ui.widget.SpaceItemDecoration;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/12/14.
 */

public class ProductListFragment extends BaseFragment implements BaseRecylerAdapter
        .OnItemClickLitener, ProductInterface.IProductView {

    private static final int PRODUCT_LIST_TAG = 1;
    private int mType;
    private RecyclerView mRecyclerView;
    private FullyLinearLayoutManager mLayoutManager;
    private ProductListRecycleAdapter<ProductInfo.ObjBean.BorrowItemListBean> productListAdapter;
    private HaCaiSwipeRefreshLayout product_fragment_list_hcswipe;
    private ProductPresenter productPresenter;
    private List<ProductInfo.ObjBean.BorrowItemListBean> productList = new ArrayList<>();
    private boolean isPullDown = true;//true标记是下拉刷新，false上拉加载更多
    private int countItem = 1;//当前加载的页数，默认为第一页
    private boolean hasMoreData = true;//当前加载的页数，默认为第一页
    private View product_data_error_vs;
    private static final int SUCCESS = 0;
    private static final int ERROR_DATA = 1;
    private static final int NO_DATA = 2;

    public static ProductListFragment newInstance(int index) {
        Bundle args = new Bundle();
        ProductListFragment fragment = new ProductListFragment();
        args.putInt(Constant.PRODUCT_LIST_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void findViewId(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.product_list_rv);
        product_fragment_list_hcswipe = (HaCaiSwipeRefreshLayout) rootView.findViewById(R.id
                .product_fragment_list_hcswipe);

        product_data_error_vs = rootView.findViewById(R.id.product_data_error_vs);
    }

    @Override
    protected void initData() {
        getProductListData(true);
    }

    private void getProductListData(boolean isShowProgress) {
        int status = 1;
        switch (mType) {
            case ProductFragment.PRODUCT_TYPE_HOT:
                status = 1;
                break;
            case ProductFragment.PRODUCT_TYPE_SELL_OUT:
                status = 3;
                break;
            case ProductFragment.PRODUCT_TYPE_PAYMENT:
                status = 7;
                break;
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("status", status);
        baseMaps.put("pageNumber", countItem);
        productPresenter.getProductList(baseMaps, PRODUCT_LIST_TAG, isShowProgress);
        LogUtil.i("列表被请求");
    }

    @Override
    protected void initTitle() {

    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productListAdapter = new ProductListRecycleAdapter<ProductInfo.ObjBean
                .BorrowItemListBean>(mContext, productList, R.layout.product_list_rv_item);
        mLayoutManager = new FullyLinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration((int) CommonUtil.getDimen(R.dimen
                .y10)));
        productListAdapter.setOnItemClickLitener(this);

        mRecyclerView.setAdapter(productListAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_list;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName() + mType;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext, ProductDetailActivity.class);
        intent.putExtra(Constant.START_ACTIVITY_STRING_1, productList.get(position).getId() + "");
        intent.putExtra(Constant.START_ACTIVITY_STRING_2, productList.get(position).getName());
        intent.putExtra(Constant.START_ACTIVITY_STRING_3, mType);
        CommonUtil.gotoActivity(mContext, intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mType = getArguments().getInt(Constant.PRODUCT_LIST_INDEX);
        LogUtil.i("onCreate=" + mType);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        initRecyclerView();
        initSwipeRefresh();
        productPresenter = new ProductPresenter((RxAppCompatActivity) getActivity());
        productPresenter.attachView(this);
    }

    private void initSwipeRefresh() {
        product_fragment_list_hcswipe.setTargetScrollWithLayout(true);
        product_fragment_list_hcswipe.setHeaderView();
        product_fragment_list_hcswipe.setOnHCPullRefreshListener(new HaCaiSwipeRefreshLayout
                .OnHCPullRefreshListener() {
            @Override
            public void onRefresh() {
                isPullDown = true;
                countItem = 1;
                getProductListData(false);
            }

            @Override
            public void onPullDistance(int distance) {
            }

            @Override
            public void onPullEnable(boolean enable) {
            }
        });
        product_fragment_list_hcswipe.setOnHCPushLoadMoreListener(new HaCaiSwipeRefreshLayout
                .OnHCPushLoadMoreListener() {


            @Override
            public void onLoadMore() {
                if (hasMoreData) {
                    isPullDown = false;
                    if (countItem == 1) countItem = 2;
                    getProductListData(false);
                } else {
                    product_fragment_list_hcswipe.setHCLoadMore(false);
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


    @Override
    public void onDestroyView() {
        LogUtil.i("onDestroyView=" + mType);
        super.onDestroyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtil.i("onHiddenChanged=" + mType);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i("setUserVisibleHint=" + mType + "isVisibleToUser=" + isVisibleToUser);
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        if (isPullDown) {
            product_fragment_list_hcswipe.setHCRefreshing(false);
        } else {
            product_fragment_list_hcswipe.setHCLoadMore(false);
        }
        switch (tag) {
            case PRODUCT_LIST_TAG:
                showDataView(ERROR_DATA, product_fragment_list_hcswipe, product_data_error_vs);
                toast(errorMsg);
                break;
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        if (isPullDown) {
            product_fragment_list_hcswipe.setHCRefreshing(false);
        } else {
            product_fragment_list_hcswipe.setHCLoadMore(false);
        }

        switch (tag) {
            case PRODUCT_LIST_TAG:
                ProductInfo.ObjBean productInfoObj = null;
                try {
                    ProductInfo productInfo = (ProductInfo) response;
                    productInfoObj = productInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (productInfoObj == null && productList.size() == 0) {
                    toast("数据为空");
                    showDataView(NO_DATA, product_fragment_list_hcswipe, product_data_error_vs);
                    return;
                }
                List<ProductInfo.ObjBean.BorrowItemListBean> borrowItemList = productInfoObj
                        .getBorrowItemList();
                ProductInfo.ObjBean.PageBeanBean pageBean = productInfoObj.getPageBean();
                if (borrowItemList == null || borrowItemList.size() == 0) {
                    //没有数据
                    showDataView(NO_DATA, product_fragment_list_hcswipe, product_data_error_vs);
                    return;
                }
                if (isPullDown) {
                    productList.clear();
                }
                productList.addAll(borrowItemList);

                productListAdapter.notifyDataSetChanged();
                calculatePage(pageBean);
                showDataView(SUCCESS, product_fragment_list_hcswipe, product_data_error_vs);
                break;
        }
    }

    private void calculatePage(ProductInfo.ObjBean.PageBeanBean pageBean) {
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
            productListAdapter.addFooterView(viewRoot);
            product_fragment_list_hcswipe.removeFooterView();
            hasMoreData = false;
        } else {
            product_fragment_list_hcswipe.setFooterView();
            productListAdapter.removeFooterView();
            hasMoreData = true;
            countItem++;
        }
    }

    /**
     * 0显示成功view,1显示错误view，2显示空数据
     */
    public void showDataView(int status, View showView, View errorView) {
        View loading_data_empty_ll = errorView.findViewById(R.id.loading_data_empty_ll);
        View loading_data_error_ll = errorView.findViewById(R.id.loading_data_error_ll);
        TextView loading_data_error_tv = (TextView) errorView.findViewById(R.id
                .loading_data_error_tv);
        loading_data_error_tv.setText("暂无数据");

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
                        getProductListData(true);
                    }
                });
                break;
            case NO_DATA:
                showView.setVisibility(View.INVISIBLE);
                showView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);

                loading_data_error_ll.setVisibility(View.INVISIBLE);
                loading_data_empty_ll.setVisibility(View.VISIBLE);

                if (ProductFragment.PRODUCT_TYPE_HOT == mType) {
                    loading_data_error_tv.setText("敬请期待");
                }
                break;
        }
    }

    public void refreshData() {
        if (productList.size() == 0 && !isViewInitiated) {
            getProductListData(true);
        }
    }
}
