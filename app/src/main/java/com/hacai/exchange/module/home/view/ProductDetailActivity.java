package com.hacai.exchange.module.home.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.base.BaseRecylerAdapter;
import com.hacai.exchange.bean.CheckPayConditionInfo;
import com.hacai.exchange.bean.CommonListInfo;
import com.hacai.exchange.bean.ProductDetailInfo;
import com.hacai.exchange.bean.TextShowInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.common.NetResponseCode;
import com.hacai.exchange.module.home.model.HomeInterface;
import com.hacai.exchange.module.home.presenter.HomePresenter;
import com.hacai.exchange.module.home.presenter.ProductDetailRecycleAdapter;
import com.hacai.exchange.module.login.view.LoginActivity;
import com.hacai.exchange.module.myAccount.view.ExchangePwdSettingActivity;
import com.hacai.exchange.module.myAccount.view.RechargeActivity;
import com.hacai.exchange.module.myAccount.view.RiskAssessmentActivity;
import com.hacai.exchange.ui.widget.AlterDialogUtils;
import com.hacai.exchange.ui.widget.ScrollViewListener;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.DateUtils;
import com.hacai.exchange.utils.LogUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/12/14.
 */

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener,
        HomeInterface.IHomeView {

    private static final int PRODUCT_DETAIL_TAG = 1;
    private static final int PRODUCT_DETAIL_CHECK_CONDITION_TAG = 2;
    private View product_detail_header;
    //    private ImageView title_right;
    private TextView title_header_tv;
    private ImageView common_header_back_iv;
    private CardView product_detail_cv;
    private RecyclerView product_detail_rv;
    private ProductDetailRecycleAdapter detailRecycleAdapter;
    private ScrollViewListener product_detail_scroll_view;
    private Button product_buy_bt;
    private HomePresenter homePresenter;
    private String productId;
    private String productName;
    private TextView product_detail_expect_income_show_tv;
    private TextView product_detail_total_money_tv;
    private TextView product_detail_limit_date_tv;
    private TextView product_detail_start_money_tv;
    private TextView product_detail_available_acnaccount_tv;
    private ProgressBar product_detail_list_prg_bar;
    private TextView product_detail_percent_bar_tv;
    private TextView buy_date_tv;
    private TextView start_interest_date_tv;
    private TextView interest_end_time_date_tv;
    private TextView interest_arrival_date_tv;
    private static final int SUCCESS = 0;
    private static final int ERROR_DATA = 1;
    private static final int NO_DATA = 2;
    private View product_detail_show_ll;
    private View product_data_error_vs;
    private AlterDialogUtils alterDialogUtils;
    private int status;
    private View product_detail_buy_rl;
    private View investment_cycle_line_1;
    private ImageView investment_cycle_iv_1;
    private View investment_cycle_line_2;
    private ImageView investment_cycle_iv_2;
    private View investment_cycle_line_3;
    private ImageView investment_cycle_iv_3;
    private View investment_cycle_line_4;
    private ImageView investment_cycle_iv_4;
    private TextView product_detail_year_rate_give_tv;

    @Override
    protected void initView() {
        product_detail_header = findViewById(R.id.product_detail_header);
        //        title_right = (ImageView) findViewById(R.id.title_right);
        //        title_right.setVisibility(View.INVISIBLE);
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        common_header_back_iv = (ImageView) findViewById(R.id.common_header_back_iv);
        product_detail_cv = (CardView) findViewById(R.id.product_detail_cv);
        product_detail_rv = (RecyclerView) findViewById(R.id.product_detail_rv);
        product_detail_scroll_view = (ScrollViewListener) findViewById(R.id
                .product_detail_scroll_view);
        product_buy_bt = (Button) findViewById(R.id.product_buy_bt);
        product_detail_expect_income_show_tv = (TextView) findViewById(R.id
                .product_detail_expect_income_show_tv);
        product_detail_total_money_tv = (TextView) findViewById(R.id.product_detail_total_money_tv);
        product_detail_limit_date_tv = (TextView) findViewById(R.id.product_detail_limit_date_tv);
        product_detail_start_money_tv = (TextView) findViewById(R.id.product_detail_start_money_tv);
        product_detail_available_acnaccount_tv = (TextView) findViewById(R.id
                .product_detail_available_acnaccount_tv);
        product_detail_list_prg_bar = (ProgressBar) findViewById(R.id.product_detail_list_prg_bar);
        product_detail_percent_bar_tv = (TextView) findViewById(R.id.product_detail_percent_bar_tv);
        buy_date_tv = (TextView) findViewById(R.id.buy_date_tv);
        start_interest_date_tv = (TextView) findViewById(R.id.start_interest_date_tv);
        interest_end_time_date_tv = (TextView) findViewById(R.id.interest_end_time_date_tv);
        interest_arrival_date_tv = (TextView) findViewById(R.id.interest_arrival_date_tv);
        product_detail_show_ll = findViewById(R.id.product_detail_show_ll);
        product_data_error_vs = findViewById(R.id.product_data_error_vs);
        product_detail_buy_rl = findViewById(R.id.product_detail_buy_rl);
        product_detail_year_rate_give_tv = (TextView) findViewById(R.id
                .product_detail_year_rate_give_tv);

        investment_cycle_line_1 = findViewById(R.id.investment_cycle_line_1);
        investment_cycle_iv_1 = (ImageView) findViewById(R.id.investment_cycle_iv_1);

        investment_cycle_line_2 = findViewById(R.id.investment_cycle_line_2);
        investment_cycle_iv_2 = (ImageView) findViewById(R.id.investment_cycle_iv_2);

        investment_cycle_line_3 = findViewById(R.id.investment_cycle_line_3);
        investment_cycle_iv_3 = (ImageView) findViewById(R.id.investment_cycle_iv_3);

        investment_cycle_line_4 = findViewById(R.id.investment_cycle_line_4);
        investment_cycle_iv_4 = (ImageView) findViewById(R.id.investment_cycle_iv_4);

        product_buy_bt.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        productId = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_1);
        productName = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_2);
        status = getIntent().getIntExtra(Constant.START_ACTIVITY_STRING_3, -1);

        title_header_tv.setText(productName);
        switch (status) {
            case 0:
                product_buy_bt.setText("立即购买");
                product_detail_buy_rl.setBackgroundColor(CommonUtil.getColor(R.color.colorWhite));
                CommonUtil.setViewEnable(product_buy_bt, true);
                break;
            case 1:
                product_buy_bt.setText("已售罄");
                product_detail_buy_rl.setBackgroundColor(CommonUtil.getColor(R.color.colorWhite));
                CommonUtil.setViewEnable(product_buy_bt, false);
                break;
            case 2:
                product_buy_bt.setText("已还款");
                CommonUtil.setViewEnable(product_buy_bt, false);
                break;
        }

        homePresenter = new HomePresenter(this);
        homePresenter.attachView(this);

        getProductDetailData(true);
    }

    @Override
    protected void initTitle() {
        product_detail_header.setBackground(CommonUtil.getColorDarwable(R.color.bt_end_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            product_detail_header.setElevation(0);
        }
        //        title_right.setVisibility(View.VISIBLE);
        //        title_right.setImageResource(R.mipmap.nav_icon_share);
        common_header_back_iv.setImageResource(R.mipmap.nav_icon_back_white);
        title_header_tv.setTextColor(CommonUtil.getColor(R.color.colorWhite));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) product_detail_cv
                .getLayoutParams();
        layoutParams.topMargin = (int) -CommonUtil.getDimen(R.dimen.y20);
        product_detail_cv.setLayoutParams(layoutParams);
        initRecyclerView();

        RxView.clicks(product_buy_bt).throttleFirst(2, TimeUnit.SECONDS).compose(mContext
                .<Void>bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {

            @Override
            public void call(Void aVoid) {
                checkBuyCondition(true);
            }
        });
    }

    private void checkBuyCondition(boolean isShowprogress) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("id", productId);
        homePresenter.checkBuyCondition(baseMaps, PRODUCT_DETAIL_CHECK_CONDITION_TAG,
                isShowprogress);
    }

    private void getProductDetailData(boolean isShowprogress) {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        if (!TextUtils.isEmpty(productId)) baseMaps.put("id", productId);
        else {
            toast("产品不存在");
            return;
        }
        homePresenter.getProductDetail(baseMaps, PRODUCT_DETAIL_TAG, isShowprogress);
    }

    private List<CommonListInfo> datas = new ArrayList<>();

    private void initRecyclerView() {
        datas.add(new CommonListInfo("项目概述", ""));
        datas.add(new CommonListInfo("安全保障", ""));
        datas.add(new CommonListInfo("投标记录", ""));
        datas.add(new CommonListInfo("还款方式", ""));

        product_detail_rv.setHasFixedSize(true);
        product_detail_rv.setItemAnimator(new DefaultItemAnimator());
        detailRecycleAdapter = new ProductDetailRecycleAdapter<CommonListInfo>(mContext, datas, R
                .layout.account_list_rv_item);

        product_detail_rv.setLayoutManager(new LinearLayoutManager(this));
        detailRecycleAdapter.setOnItemClickLitener(new BaseRecylerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        Intent summaryIntent = new Intent(mContext, ProductDetailSummaryActivity
                                .class);
                        if (productDetailInfoObj == null || TextUtils.isEmpty
                                (productDetailInfoObj.getId() + "")) {
                            toast("产品id为空");
                            return;
                        }
                        summaryIntent.putExtra(Constant.START_ACTIVITY_STRING_1,
                                productDetailInfoObj.getId());
                        CommonUtil.gotoActivity(mContext, summaryIntent);
                        break;
                    case 1:
                        Intent capitalIntent = new Intent(mContext, ProductDetailCapitalActivity
                                .class);
                        if (productDetailInfoObj == null || TextUtils.isEmpty
                                (productDetailInfoObj.getId() + "")) {
                            toast("产品id为空");
                            return;
                        }
                        capitalIntent.putExtra(Constant.START_ACTIVITY_STRING_1,
                                productDetailInfoObj.getId());
                        CommonUtil.gotoActivity(mContext, capitalIntent);
                        break;
                    case 2:
                        Intent recodeIntent = new Intent(mContext, ProductDetailBidRecodeActivity
                                .class);
                        recodeIntent.putExtra(Constant.START_ACTIVITY_STRING_1, productId);
                        CommonUtil.gotoActivity(mContext, recodeIntent);
                        break;
                    case 3:
                        return;

                }

            }
        });
        product_detail_rv.setAdapter(detailRecycleAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    protected int immersiveStatusBarColor() {
        return R.color.bt_end_color;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_buy_bt:

                break;
        }
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {

        switch (tag) {
            case PRODUCT_DETAIL_TAG:
                toast(errorMsg);
                showDataView(ERROR_DATA, product_detail_show_ll, product_data_error_vs);
                break;
            case PRODUCT_DETAIL_CHECK_CONDITION_TAG:
                if (TextUtils.isEmpty(CommonUtil.getTokenId())) {
                    toast("未登录");
                    CommonUtil.gotoActivity(mContext, LoginActivity.class);
                    return;
                } else {
                    toast(errorMsg);
                }
                switch (code) {
                    case NetResponseCode.HMC_LOGIN:
                        CommonUtil.gotoActivity(mContext, LoginActivity.class);
                        break;
                    case NetResponseCode.HMC_UNBIND_CARD_ERROR://未绑定银行卡

                        break;
                    case NetResponseCode.HMC_NO_PAYMENT_PWD://未设置交易密码

                        break;
                    case NetResponseCode.HMC_NO_RISK:
                        alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen
                                .x290));
                        alterDialogUtils.setOneOrTwoBtn(false);
                        alterDialogUtils.setMessage("投资前请完成风险测评");
                        alterDialogUtils.setTwoCancelBtn("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alterDialogUtils.dismiss();
                            }
                        });
                        alterDialogUtils.setTwoConfirmBtn("去测评", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, RiskAssessmentActivity.class);
                                CommonUtil.gotoActivity(mContext, intent);

                                alterDialogUtils.dismiss();
                            }
                        });
                        alterDialogUtils.show();
                        break;
                    case NetResponseCode.HMC_RISK_LOWLEVEL:
                        alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen
                                .x290));
                        alterDialogUtils.setOneOrTwoBtn(false);
                        alterDialogUtils.setMessage("对不起，您的风险测评等级无法购买此产品");

                        alterDialogUtils.setTwoCancelBtn("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alterDialogUtils.dismiss();
                            }
                        });
                        alterDialogUtils.setTwoConfirmBtn("重新测评", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, RiskAssessmentActivity.class);
                                CommonUtil.gotoActivity(mContext, intent);
                                alterDialogUtils.dismiss();
                            }
                        });
                        alterDialogUtils.show();
                        break;
                    case NetResponseCode.HMC_NEW_ONLY:
                        alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen
                                .x290));
                        alterDialogUtils.setOneOrTwoBtn(true);
                        alterDialogUtils.setMessage("对不起，该产品仅限于新手客户！");

                        alterDialogUtils.setOneConfirmBtn("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alterDialogUtils.dismiss();
                            }
                        });
                        alterDialogUtils.show();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private ProductDetailInfo.ObjBean productDetailInfoObj = null;

    @Override
    public void onSuccess(Object response, int tag) {
        LogUtil.i("数据返回" + response);
        switch (tag) {
            case PRODUCT_DETAIL_TAG:
                try {
                    ProductDetailInfo productDetailInfo = (ProductDetailInfo) response;
                    productDetailInfoObj = productDetailInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (productDetailInfoObj == null) {
                    toast("请求返回参数为空");
                    showDataView(NO_DATA, product_detail_show_ll, product_data_error_vs);
                    return;
                }
                updateDetailData(productDetailInfoObj);
                showDataView(SUCCESS, product_detail_show_ll, product_data_error_vs);
                break;
            case PRODUCT_DETAIL_CHECK_CONDITION_TAG:
                CheckPayConditionInfo.ObjBean productPayInfoObj = null;
                try {
                    CheckPayConditionInfo productPayInfo = (CheckPayConditionInfo) response;
                    productPayInfoObj = productPayInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (productPayInfoObj == null) {
                    toast("数据异常,请重试");
                    return;
                }
                int isBound = productPayInfoObj.getIsBound();
                int isPaypwd = productPayInfoObj.getIsPaypwd();

                if (alterDialogUtils == null) {
                    alterDialogUtils = new AlterDialogUtils(ProductDetailActivity.this, R.style
                            .payment_dialog_notice);
                }

                if (isBound != 1) {//去绑卡
                    alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen.x290));
                    alterDialogUtils.setOneOrTwoBtn(false);
                    alterDialogUtils.setMessage(CommonUtil.getString(R.string.acount_unbind_card));
                    alterDialogUtils.setTwoCancelBtn("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alterDialogUtils.dismiss();
                        }
                    });
                    alterDialogUtils.setTwoConfirmBtn("去绑卡", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CommonUtil.gotoActivity(mContext, RechargeActivity.class);
                            alterDialogUtils.dismiss();
                        }
                    });
                    alterDialogUtils.show();
                    return;
                }

                if (isPaypwd != 1) {
                    alterDialogUtils.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen.x290));
                    alterDialogUtils.setOneOrTwoBtn(false);
                    alterDialogUtils.setMessage("请先设置交易密码");
                    alterDialogUtils.setTwoCancelBtn("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alterDialogUtils.dismiss();
                        }
                    });
                    alterDialogUtils.setTwoConfirmBtn("去设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ExchangePwdSettingActivity.class);
                            CommonUtil.gotoActivity(mContext, intent);
                            alterDialogUtils.dismiss();
                        }
                    });
                    alterDialogUtils.show();
                    return;
                }
                Intent intent = new Intent(mContext, PaymentActivity.class);

                if (productDetailInfoObj != null) {
                    intent.putExtra(Constant.START_ACTIVITY_PARCELABLE_1, productDetailInfoObj);
                }
                if (productPayInfoObj != null) {
                    intent.putExtra(Constant.START_ACTIVITY_PARCELABLE_2, productPayInfoObj);
                }
                CommonUtil.gotoActivity(mContext, intent);
                break;
        }
    }

    private void updateDetailData(ProductDetailInfo.ObjBean detailInfoObj) {
        product_detail_expect_income_show_tv.setText(CommonUtil.format2Point(detailInfoObj
                .getBaseRate()) + "%");
        int giveRate = (int) detailInfoObj.getGiveRate();
        if (giveRate > 0) {
            product_detail_year_rate_give_tv.setText("+" + giveRate + "%");
        } else {
            product_detail_year_rate_give_tv.setVisibility(View.GONE);
        }

        List<TextShowInfo> canAccounTextList = new ArrayList<>();
        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo(CommonUtil.format2PointMillion(detailInfoObj
                .getAccount()), R.color.edittext_color, R.dimen.text_size_24));
        canAccounTextList.add(new TextShowInfo("万元", R.color.text_normal_color, R.dimen
                .text_size_13));
        product_detail_total_money_tv.setText(CommonUtil.setSpannableText(canAccounTextList));

        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo(detailInfoObj.getBorrowTime(), R.color
                .edittext_color, R.dimen.text_size_24));
        canAccounTextList.add(new TextShowInfo("天", R.color.text_normal_color, R.dimen
                .text_size_13));
        product_detail_limit_date_tv.setText(CommonUtil.setSpannableText(canAccounTextList));

        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo(CommonUtil.format0Point(detailInfoObj
                .getLowAccount()), R.color.edittext_color, R.dimen.text_size_24));
        canAccounTextList.add(new TextShowInfo("元", R.color.text_normal_color, R.dimen
                .text_size_13));
        product_detail_start_money_tv.setText(CommonUtil.setSpannableText(canAccounTextList));

        canAccounTextList.clear();
        canAccounTextList.add(new TextShowInfo("可投", R.color.text_normal_color, R.dimen
                .text_size_13));
        canAccounTextList.add(new TextShowInfo(CommonUtil.format2PointMillion(detailInfoObj
                .getCanAccount()), R.color.bt_end_color, R.dimen.text_size_13));
        canAccounTextList.add(new TextShowInfo("万元", R.color.text_normal_color, R.dimen
                .text_size_13));
        product_detail_available_acnaccount_tv.setText(CommonUtil.setSpannableText
                (canAccounTextList));
        double precent = 0;
        try {
            float total = Float.parseFloat(detailInfoObj.getAccount());
            float available = Float.parseFloat(detailInfoObj.getCanAccount());
            precent = (1 - CommonUtil.div(available, total, RoundingMode.HALF_EVEN, 4)) * 100;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        LogUtil.i("percent=" + precent);
        if (precent >= 1) {
            Observable.interval(10, TimeUnit.MILLISECONDS).take((int) precent).onBackpressureDrop
                    ().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Long>() {
                @Override
                public void onCompleted() {
                    LogUtil.i("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.i("onError" + e.getMessage() + e.toString());
                }

                @Override
                public void onNext(Long aLong) {
                    LogUtil.i("onNext=" + aLong);
                    int progressint = aLong.intValue() + 1;
                    if (progressint <= 100) product_detail_list_prg_bar.setProgress(progressint);
                }
            });
        } else {
            product_detail_list_prg_bar.setProgress(0);
        }
        product_detail_percent_bar_tv.setText("已投 " + CommonUtil.format2Point(precent) + "%");
        buy_date_tv.setText(DateUtils.millisToDateTime_2(detailInfoObj.getPutDate()));
        start_interest_date_tv.setText(DateUtils.millisToDateTime_2(detailInfoObj.getFullDate()));
        interest_end_time_date_tv.setText(DateUtils.millisToDateTime_2(detailInfoObj.getCipalDate
                ()));
        interest_arrival_date_tv.setText(DateUtils.millisToDateTime_2(detailInfoObj.getArriveDate
                ()));
        int light = detailInfoObj.getLight();
        if (light >= 1) {
            investment_cycle_line_1.setBackgroundColor(CommonUtil.getColor(R.color.bt_end_color));
            investment_cycle_iv_1.setImageResource(R.mipmap.progress_dot_active);
        }
        if (light >= 2) {
            investment_cycle_line_2.setBackgroundColor(CommonUtil.getColor(R.color.bt_end_color));
            investment_cycle_iv_2.setImageResource(R.mipmap.progress_dot_active);
        }

        if (light >= 3) {
            investment_cycle_line_3.setBackgroundColor(CommonUtil.getColor(R.color.bt_end_color));
            investment_cycle_iv_3.setImageResource(R.mipmap.progress_dot_active);
        }
        if (light >= 4) {
            investment_cycle_line_4.setBackgroundColor(CommonUtil.getColor(R.color.bt_end_color));
            investment_cycle_iv_4.setImageResource(R.mipmap.progress_dot_active);
        }


        datas.clear();
        datas.add(new CommonListInfo("项目概述", ""));
        datas.add(new CommonListInfo("资金保障", ""));
        datas.add(new CommonListInfo("投标记录", "已投资" + detailInfoObj.getTenderCount() + "笔"));
        datas.add(new CommonListInfo("还款方式", detailInfoObj.getPayType()));
        detailRecycleAdapter.notifyDataSetChanged();
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
                        getProductDetailData(true);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alterDialogUtils != null && alterDialogUtils.isShowing()) {
            alterDialogUtils.dismiss();
            alterDialogUtils = null;
        }
    }
}
