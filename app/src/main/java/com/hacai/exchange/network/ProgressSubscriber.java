package com.hacai.exchange.network;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.hacai.exchange.HCExchangeApp;
import com.hacai.exchange.R;
import com.hacai.exchange.bean.BaseResultEntry;
import com.hacai.exchange.common.NetResponseCode;
import com.hacai.exchange.exception.HandlerException;
import com.hacai.exchange.ui.widget.NetProgressDialog;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    /*是否弹框*/
    private boolean showPorgress = true;
    /* 软引用回调接口*/
    private HttpOnNextListener mSubscriberOnNextListener;
    /*软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;
    /*加载框可自己定义*/
    private NetProgressDialog pd;
    /*请求数据*/
    private BaseApi api;
    private BaseResultEntry resultEntry;


    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        setShowPorgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }


    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Activity context = mActivity.get();
        if (pd == null && context != null) {
            pd = NetProgressDialog.createLoadingDialog(context, "加载中...");
            pd.setAlertDialogSize((int) CommonUtil.getDimen(R.dimen.x100), (int) CommonUtil
                    .getDimen(R.dimen.y100));
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mSubscriberOnNextListener != null) {
                            mSubscriberOnNextListener.onCancel();
                        }
                        onCancelProgress();
                    }
                });
            }
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowPorgress()) return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (!isShowPorgress()) return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        if (!CommonUtil.isNetworkAvailable(HCExchangeApp.getContext())) {
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(HandlerException.handleException(new
                        HandlerException.ResponeThrowable("网络连接失败，请检查网络后重试", NetResponseCode
                        .HMC_NETWORK_ERROR)));
            }
            return;
        } else {
            showProgressDialog();
        }
        LogUtil.i("1027", "初始化加载框。订阅开始。");
        /*缓存并且有网*/
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        LogUtil.i("1027", "初始化加载框。订阅完成。");
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        LogUtil.i("1027", "初始化加载框。订阅出错。异常情况=" + e.toString() + ":" + e.getMessage());
        /*需要緩存并且本地有缓存才返回*/
        errorDo(e);
    }

    /*错误统一处理*/
    private void errorDo(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        if (resultEntry != null && NetResponseCode.HMC_SUCCESS.equalsIgnoreCase(resultEntry
                .getResponseCode())) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(new HandlerException.ResponeThrowable("数据为空",
                        NetResponseCode.HMC_SUCCESS));
            }
        } else {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(HandlerException.handleException(e));
            }
        }

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        try {
            resultEntry = (BaseResultEntry) t;
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtil.i("1027", "初始化加载框。订阅交给Activity处理。");
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }
}