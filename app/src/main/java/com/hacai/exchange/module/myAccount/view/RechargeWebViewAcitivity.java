package com.hacai.exchange.module.myAccount.view;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hacai.exchange.MainActivity;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.rxbus.RxBus;
import com.hacai.exchange.rxbus.event.RechargeOrDrawEvent;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;


/**
 * Created by Rongkui.xiao on 2017/4/25.
 *
 * @description
 */

public class RechargeWebViewAcitivity extends BaseActivity {

    private WebView webView;
    private String html;
    private int isSuccess = -1;

    @Override
    protected void initView() {
        html = getIntent().getStringExtra(Constant.WEBVIEW_AGREEMENT_URL);
        webView = (WebView) findViewById(R.id.agreement_wb);
    }

    @Override
    protected void initData() {
        webView.loadDataWithBaseURL(AppNetConfig.baseUrl + AppNetConfig.urlPath, html,
                "text/html", "utf-8", AppNetConfig.baseUrl + AppNetConfig.urlPath);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("shouldOverrideUrlLoading=" + url);
                webView.loadUrl(url);
                if (url.contains(AppNetConfig.recharge_success)) {
                    isSuccess = 0;
                    RxBus.getInstance().post(new RechargeOrDrawEvent(true, "充值成功"));
                    CommonUtil.gotoActivity(mContext, MainActivity.class);
                    AppManager.getInstance().removeCurrent();
                    AppManager.getInstance().removeActivity(RechargeActivity.class);
                } else if (url.contains(AppNetConfig.recharge_failed)) {
                    isSuccess = 1;
                    //                    AppManager.getInstance().removeCurrent();
                } else if (url.contains("/mobile_pay/newpay/success_popbox.html")) {
                    isSuccess = 2;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String
                    defaultValue, JsPromptResult result) {
                LogUtil.i("onJsPrompt=" + result.toString());
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                LogUtil.i("result=" + result.toString());
                return super.onJsAlert(view, url, message, result);
            }
        });
    }

    @Override
    protected void initTitle() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview_recharge;
    }


    @Override
    public void onBackPressed() {
        LogUtil.i("isSuccess=" + isSuccess);
        if (isSuccess == 0) {
            RxBus.getInstance().post(new RechargeOrDrawEvent(true, "充值成功"));
            CommonUtil.gotoActivity(mContext, MainActivity.class);
            AppManager.getInstance().getCurrent();
            AppManager.getInstance().removeActivity(RechargeActivity.class);
        } else if (isSuccess == 1) {
            AppManager.getInstance().getCurrent();
        } else if (isSuccess == 2) {//成功但是不让返回
            return;
        } else {
            AppManager.getInstance().getCurrent();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    public void destroy() {
        if (webView != null) {
            LogUtil.i("destroy");
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

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }
}
