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
import com.hacai.exchange.bean.InvestmentRecodeInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.myAccount.model.MyAccountInterface;
import com.hacai.exchange.module.myAccount.presenter.InvestmentRecodeRecAdapter;
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

public class InvestmentRcodeFragment extends BaseFragment implements BaseRecylerAdapter
        .OnItemClickLitener, MyAccountInterface.IMyAccountView {

    private static final int INVESTMENT_RECODE_TAG = 1;
    private int mType;
    private RecyclerView mRecyclerView;
    private FullyLinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private InvestmentRecodeRecAdapter<InvestmentRecodeInfo.ObjBean.UserTenderListBean>
            productListAdapter;
    private HaCaiSwipeRefreshLayout product_fragment_list_hcswipe;
    private boolean isPullDown = true;//true标记是下拉刷新，false上拉加载更多
    private int countItem = 1;//当前加载的页数，默认为第一页
    private boolean hasMoreData = true;//当前加载的页数，默认为第一页
    private MyAccountPresenter myAccountPresenter;
    private static final int SUCCESS = 0;
    private static final int ERROR_DATA = 1;
    private static final int NO_DATA = 2;
    private View product_data_error_vs;

    public static InvestmentRcodeFragment newInstance(int index) {
        Bundle args = new Bundle();
        InvestmentRcodeFragment fragment = new InvestmentRcodeFragment();
        args.putInt(Constant.PRODUCT_LIST_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void findViewId(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.product_list_rv);
        product_data_error_vs = rootView.findViewById(R.id.product_data_error_vs);
        product_fragment_list_hcswipe = (HaCaiSwipeRefreshLayout) rootView.findViewById(R.id
                .product_fragment_list_hcswipe);
    }

    @Override
    protected void initData() {
        getInvsetmentRecodeData(true);
    }

    @Override
    protected void initTitle() {

    }

    List<InvestmentRecodeInfo.ObjBean.UserTenderListBean> recodeDatas = new ArrayList<>();

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        productListAdapter = new InvestmentRecodeRecAdapter<>(mContext, recodeDatas, R.layout
                .investment_recode_item);
        mLayoutManager = new FullyLinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        productListAdapter.setOnItemClickLitener(this);

        mRecyclerView.setAdapter(productListAdapter);
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
                getInvsetmentRecodeData(false);
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
                    getInvsetmentRecodeData(false);
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

    private void getInvsetmentRecodeData(boolean b) {
        int status = 1;
        switch (mType) {
            case InvestmentRecodeActivity.INVESTMENT_TYPE_ING:
                status = 1;
                break;
            case InvestmentRecodeActivity.INVESTMENT_TYPE_PAYMENT:
                status = 2;
                break;
            case InvestmentRecodeActivity.INVESTMENT_TYPE_FINISH:
                status = 3;
                break;
        }
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("status", status);
        baseMaps.put("pageNum", countItem);
        myAccountPresenter.getInvestmentRecode(baseMaps, INVESTMENT_RECODE_TAG, b);
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
        initRecyclerView();
        initSwipeRefresh();
        myAccountPresenter = new MyAccountPresenter((RxAppCompatActivity) getActivity());
        myAccountPresenter.attachView(this);
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
    public void onError(String errorMsg, String code, int tag) {
        toast(errorMsg);
        if (isPullDown) {
            product_fragment_list_hcswipe.setHCRefreshing(false);
        } else {
            product_fragment_list_hcswipe.setHCLoadMore(false);
        }
        switch (tag) {
            case INVESTMENT_RECODE_TAG:
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
            case INVESTMENT_RECODE_TAG:
                InvestmentRecodeInfo.ObjBean.PageBeanBean objBeanPageBean = null;
                List<InvestmentRecodeInfo.ObjBean.UserTenderListBean> userTenderList = null;
                InvestmentRecodeInfo.ObjBean objBean=null;
                try {
                    InvestmentRecodeInfo investmentRecodeInfo = (InvestmentRecodeInfo) response;
                     objBean = investmentRecodeInfo.getObj();
                    objBeanPageBean = objBean.getPageBean();
                    userTenderList = objBean.getUserTenderList();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (userTenderList == null || userTenderList.size() == 0) {
                    showDataView(NO_DATA, product_fragment_list_hcswipe, product_data_error_vs);
                    return;
                }
                if (isPullDown) {
                    recodeDatas.clear();
                }
                recodeDatas.addAll(userTenderList);
                productListAdapter.notifyDataSetChanged();

                calculatePage(objBeanPageBean);
                initWaitcapital(objBean.getWaitPrincipal(),objBean.getWaitProfit());
                showDataView(SUCCESS, product_fragment_list_hcswipe, product_data_error_vs);
                break;
        }
    }

    private void initWaitcapital(double waitPrincipal, double waitProfit) {
        ((InvestmentRecodeActivity)this.getActivity()).setWaitcapital(waitPrincipal,waitProfit);
    }

    private void calculatePage(InvestmentRecodeInfo.ObjBean.PageBeanBean pageBean) {
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
                        getInvsetmentRecodeData(true);
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
