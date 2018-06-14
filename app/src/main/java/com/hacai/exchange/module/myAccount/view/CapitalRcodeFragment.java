package com.hacai.exchange.module.myAccount.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseFragment;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.CapitalRecodeInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.CapitalRecodeRecAdapter;
import com.hacai.exchange.module.myAccount.presenter.MyAccountPresenter;
import com.hacai.exchange.ui.widget.FullyLinearLayoutManager;
import com.hacai.exchange.ui.widget.HaCaiSwipeRefreshLayout;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/12/14.
 */

public class CapitalRcodeFragment extends BaseFragment implements BaseRecylerAdapter
        .OnItemClickLitener, MyAccountInterface.IMyAccountView {

    private static final int CAPITAL_RECODE_TAG = 1;
    private int mType;
    private RecyclerView mRecyclerView;
    private FullyLinearLayoutManager mLayoutManager;
    private CapitalRecodeRecAdapter<CapitalRecodeInfo.ObjBean.DatasBean> capitalRecodeRecAdapter;
    private MyAccountPresenter myAccountPresenter;

    private boolean isPullDown = true;//true标记是下拉刷新，false上拉加载更多
    private int countItem = 1;//当前加载的页数，默认为第一页
    private boolean hasMoreData = true;//当前加载的页数，默认为第一页
    private HaCaiSwipeRefreshLayout product_fragment_list_hcswipe;
    private View product_data_error_vs;

    private static final int SUCCESS = 0;
    private static final int ERROR_DATA = 1;
    private static final int NO_DATA = 2;

    public static CapitalRcodeFragment newInstance(int index) {
        Bundle args = new Bundle();
        CapitalRcodeFragment fragment = new CapitalRcodeFragment();
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
        getCapitalRecodeData(true);
    }

    @Override
    protected void initTitle() {

    }

    private void getCapitalRecodeData(boolean b) {
        int status = 1;
        switch (mType) {
            case CapitalRecodeActivity.TYPE_CAPITAL_MONEY:
                status = 1;
                break;
            case CapitalRecodeActivity.TYPE_CAPITAL_RECHARGE:
                status = 2;
                break;
            case CapitalRecodeActivity.TYPE_CAPITAL_WITHDRAW:
                status = 3;
                break;
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("status", status);
        baseMaps.put("pageNum", countItem);
        myAccountPresenter.getCapitalRecode(baseMaps, CAPITAL_RECODE_TAG, b);
    }

    List<CapitalRecodeInfo.ObjBean.DatasBean> datas = new ArrayList<>();

    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        capitalRecodeRecAdapter = new CapitalRecodeRecAdapter<CapitalRecodeInfo.ObjBean
                .DatasBean>(mContext, datas, R.layout.capital_recode_item);
        mLayoutManager = new FullyLinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        capitalRecodeRecAdapter.setOnItemClickLitener(this);

        mRecyclerView.setAdapter(capitalRecodeRecAdapter);

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
                getCapitalRecodeData(false);
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
                    getCapitalRecodeData(false);
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
    public int getLayoutId() {
        return R.layout.fragment_product_list;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mType = getArguments().getInt(Constant.PRODUCT_LIST_INDEX);
        LogUtil.i("onCreate=" + mType);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        LogUtil.i("onCreateView=" + mType);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        myAccountPresenter = new MyAccountPresenter((RxAppCompatActivity) getActivity());
        myAccountPresenter.attachView(this);
        initRecyclerView();
        initSwipeRefresh();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtil.i("onActivityCreated=" + mType);
        super.onActivityCreated(savedInstanceState);
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
    public void onResume() {
        LogUtil.i("onResume=" + mType);
        super.onResume();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i("setUserVisibleHint=" + mType + "isVisibleToUser=" + isVisibleToUser);
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        if (isPullDown) {
            product_fragment_list_hcswipe.setHCRefreshing(false);
        } else {
            product_fragment_list_hcswipe.setHCLoadMore(false);
        }
        switch (tag) {
            case CAPITAL_RECODE_TAG:
                showDataView(ERROR_DATA, product_fragment_list_hcswipe, product_data_error_vs);
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
            case CAPITAL_RECODE_TAG:
                List<CapitalRecodeInfo.ObjBean.DatasBean> capitalRecodeInfoObjDatas = null;
                CapitalRecodeInfo.ObjBean.PageBeanBean pageBean = null;
                try {
                    CapitalRecodeInfo capitalRecodeInfo = (CapitalRecodeInfo) response;
                    CapitalRecodeInfo.ObjBean capitalRecodeInfoObj = capitalRecodeInfo.getObj();
                    capitalRecodeInfoObjDatas = capitalRecodeInfoObj.getDatas();
                    pageBean = capitalRecodeInfoObj.getPageBean();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (capitalRecodeInfoObjDatas == null || capitalRecodeInfoObjDatas.size() == 0) {
                    showDataView(NO_DATA, product_fragment_list_hcswipe, product_data_error_vs);
                    return;
                }
                if (isPullDown) {
                    datas.clear();
                }
                datas.addAll(capitalRecodeInfoObjDatas);
                capitalRecodeRecAdapter.notifyDataSetChanged();

                calculatePage(pageBean);
                showDataView(SUCCESS, product_fragment_list_hcswipe, product_data_error_vs);
                break;
        }
    }

    private void calculatePage(CapitalRecodeInfo.ObjBean.PageBeanBean pageBean) {
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
            capitalRecodeRecAdapter.addFooterView(viewRoot);
            product_fragment_list_hcswipe.removeFooterView();
            hasMoreData = false;
        } else {
            product_fragment_list_hcswipe.setFooterView();
            capitalRecodeRecAdapter.removeFooterView();
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
                        getCapitalRecodeData(true);
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
