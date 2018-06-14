package com.hacai.exchange;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.umeng.analytics.MobclickAgent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/12/5.
 */
public class HCExchangeApp extends Application {
    private static Context context;
    public int count = 0;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        //友盟统计
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(context,
                "5a4ae56ff43e48267f0000ed", CommonUtil.getChannel(context), MobclickAgent
                .EScenarioType.E_UM_NORMAL));
        MobclickAgent.setSecret(context, "5a4ae56ff43e48267f0000ed");
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.enableEncrypt(true);//加密日志
    }
//
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    @Override
//    public void onBaseContextAttached(Context base) {
//        super.onBaseContextAttached(base);
//        MultiDex.install(base);//方法数超过65k
//        // Tinker管理类，保存当前对象
//        TinkerManager.setTinkerApplicationLike(this);
//
//        TinkerManager.initFastCrashProtect();
//        //should set before tinker is installed
//        TinkerManager.setUpgradeRetryEnable(true);
//
//        //optional set logIml, or you can use default debug log
//        TinkerInstaller.setLogIml(new MyLogImp());
//
//        //installTinker after load multiDex
//        //or you can put com.tencent.tinker.** to main dex
//        TinkerManager.installTinker(this);
//        Tinker tinker = Tinker.with(getApplication());
//    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 退出应用
     */
    public static void exit() {
        AppManager.getInstance().removeCurrent();
        AppManager.getInstance().clear();
        MobclickAgent.onKillProcess(context);
        System.exit(0);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                Glide.get(context).clearDiskCache();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Glide.get(context).clearMemory();
            }
        });
    }

//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks
//                                                               callback) {
//        // 生命周期，默认配置
//       registerActivityLifecycleCallbacks(callback);
//    }

}
