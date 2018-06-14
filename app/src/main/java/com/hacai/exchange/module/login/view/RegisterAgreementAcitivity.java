package com.hacai.exchange.module.login.view;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.network.BaseApi;
import com.hacai.exchange.network.HttpOnNextListener;
import com.hacai.exchange.network.RetrofitHelper;
import com.hacai.exchange.network.service.MyAccountService;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;


/**
 * Created by Rongkui.xiao on 2017/4/25.
 *
 * @description
 */

public class RegisterAgreementAcitivity extends BaseActivity {

    private WebView webView;
    private String url;
    private String title;
    private LinearLayout webView_container;

    @Override
    protected void initView() {
        url = getIntent().getStringExtra(Constant.WEBVIEW_AGREEMENT_URL);
        title = getIntent().getStringExtra(Constant.WEBVIEW_AGREEMENT_TITLE);
        webView = (WebView) findViewById(R.id.agreement_wb);
        webView_container = findViewById(R.id.webView_container);
    }

    @Override
    protected void initData() {

        getHtmlData(true);
//        webView.loadUrl(url);
        CommonUtil.aysncWebView(mContext, url);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setDomStorageEnabled(true);
        webView.requestFocus();
        //        webView.getSettings().setUseWideViewPort(true);//坑，第二次加载不出来
        webView.getSettings().setLoadWithOverviewMode(true);
        //        webView.getSettings().setSupportZoom(true);//设置网页是否可以缩放
        //        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSavePassword(false);
        //        webView.getSettings().setSaveFormData(false);//坑，第二次加载不出来
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("url="+url);
                webView.loadUrl(url);
                return true;
            }
        });
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
                webView.loadDataWithBaseURL(AppNetConfig.baseUrl + AppNetConfig.urlPath, s, "text/html", "utf-8", null);
            }

            @Override
            public void onError(Throwable e) {
               toast("页面加载失败");
            }
        }, this) {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                MyAccountService myAccountService = retrofit.create(MyAccountService.class);
                Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
                return myAccountService.getRegisterAgreement(baseMaps);
            }
        };
        baseapi.setCache(false);
        baseapi.setShowProgress(b);
        baseapi.setGsonConverterFactory(ScalarsConverterFactory.create());
        RetrofitHelper.getInstance().sendHttpRequest(baseapi);
    }

    @Override
    protected void initTitle() {
        ((TextView) findViewById(R.id.title_header_tv)).setText(title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview_agreement;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CookieSyncManager.createInstance(mContext);
        CookieManager instance = CookieManager.getInstance();
        instance.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        webView.clearCache(true);

        // 先从父控件中移除WebView
        webView_container.removeView(webView);
        webView.stopLoading();
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearHistory();
        webView.removeAllViews();
        webView.destroy();
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }
}
