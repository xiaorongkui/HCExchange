package com.hacai.exchange.module.login.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hacai.exchange.HCExchangeApp;
import com.hacai.exchange.utils.LogUtil;

/**
 * Created by lenovo on 2018/1/8.
 */

public class TimeoutService extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    boolean isrun = true;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        forceApplicationExit();
        //        return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void forceApplicationExit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i("退出应用");
                stopSelf();
                HCExchangeApp.exit();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isrun = false;
    }
}
