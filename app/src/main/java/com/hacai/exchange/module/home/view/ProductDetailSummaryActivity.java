package com.hacai.exchange.module.home.view;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.network.BaseApi;
import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.network.RetrofitHelper;
import com.hacai.exchange.network.service.HomeService;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;

/**
 * Created by lenovo on 2017/12/15.
 * 项目概述
 */

public class ProductDetailSummaryActivity extends BaseActivity {

    private TextView title_header_tv;
    private String productId;
    private WebView webView;

    @Override
    protected void initView() {
        productId = getIntent().getStringExtra(Constant.START_ACTIVITY_STRING_1);
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        webView = (WebView) findViewById(R.id.summary_wb);
    }

    @Override
    protected void initData() {
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("url=" + url);
                webView.loadUrl(url);
                return true;
            }
        });
        getHtmlData(true);
    }

    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.program_summary));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detail_summary;
    }

    private void getHtmlData(boolean b) {
        BaseApi baseapi = new BaseApi(new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                String s = (String) o;
                LogUtil.i("risk=" + o.toString());
                webView.loadDataWithBaseURL(AppNetConfig.baseUrl + AppNetConfig.urlPath, s,
                        "text/html", "utf-8", null);
            }

            @Override
            public void onError(Throwable e) {
                toast("页面加载失败");
            }
        }, this) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                HomeService homeService = retrofit.create(HomeService.class);
                Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
                if (!TextUtils.isEmpty(productId)) {
                    baseMaps.put("borrowId", productId);
                }
                return homeService.getProductSummary(baseMaps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(b);
        baseapi.setGsonConverterFactory(ScalarsConverterFactory.create());
        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            try {
                webView.destroy();
            } catch (Throwable ex) {

            }
        }
    }
}
