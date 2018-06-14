package com.hacai.exchange.module.myAccount.view;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
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
import com.hacai.exchange.network.service.MyAccountService;
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.RiskEvent;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;

/**
 * Created by lenovo on 2017/12/20.
 */

public class RiskAssessmentActivity extends BaseActivity {

    private TextView title_header_tv;
    private WebView webView;
    private WebSettings settings;
    private String whereFrom;

    @Override
    protected void initView() {
        whereFrom = getIntent().getStringExtra(Constant.START_ACTIVITY_WHERE_FROM);
        title_header_tv = (TextView) findViewById(R.id.title_header_tv);
        webView = (WebView) findViewById(R.id.risk_web);
        settings = webView.getSettings();
    }

    @Override
    protected void initData() {
        getHtmlData(true);
    }


    @Override
    protected void initTitle() {
        title_header_tv.setText(CommonUtil.getString(R.string.risk_assessment));
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        //        webView.loadUrl(AppNetConfig.risk_url);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(getHtmlInterface(), "jsObj");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("url=" + url);
                webView.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_risk_assessment;
    }

    @JavascriptInterface
    private Object getHtmlInterface() {
        Object insertObj = new Object() {

            //暴露方法给js
            @JavascriptInterface
            public void gotoPage() {
                AppManager.getInstance().removeCurrent();
            }

            @JavascriptInterface
            public void onRiskFinish(String riskStyle) {//当风险评估完成后
                sendRiskLevel(riskStyle);
                LogUtil.i("1027", "onRiskFinish ");
            }

        };
        return insertObj;
    }

    private void sendRiskLevel(final String riskStyle) {
        BaseApi baseapi = new BaseApi(new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                LogUtil.i("风险评估结果发送后台成功");
                RxBus.getInstance().post(new RiskEvent(true));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                LogUtil.i("风险评估结果发送后台失败，e=" + e.getMessage());
            }
        }, this) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
                if (!TextUtils.isEmpty(riskStyle)) baseMaps.put("type", riskStyle);
                return myAccountService.sendRiskLevel(baseMaps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(false);
        //        baseapi.setGsonConverterFactory(ScalarsConverterFactory.create());
        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    private void getHtmlData(boolean b) {
        BaseApi baseapi = new BaseApi(new HttpOnNextListener() {
            @Override
            public void onNext(Object o) {
                String s = (String) o;
                //                if (!TextUtils.isEmpty(s)) {
                //                    s = s.replace("\"", "\\\"");
                //                }
                LogUtil.i("risk=" + o.toString());
                webView.loadDataWithBaseURL(AppNetConfig.baseUrl + AppNetConfig.urlPath, s,
                        "text/html", "utf-8", null);
            }
        }, this) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
                return myAccountService.getRiskhtml(baseMaps);
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
        //        CookieSyncManager.createInstance(mContext);
        //        CookieManager instance = CookieManager.getInstance();
        //        instance.removeAllCookie();
        //        CookieSyncManager.getInstance().sync();
        //        webView.clearCache(true);
        //
        //        // 先从父控件中移除WebView
        ////        webView.getParent().removeView(webView);
        //        webView.stopLoading();
        //        webView.getSettings().setJavaScriptEnabled(false);
        //        webView.clearHistory();
        //        webView.removeAllViews();
        //        webView.destroy();

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
