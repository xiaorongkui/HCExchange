package com.hacai.exchange.base;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.hacai.exchange.R;
import com.hacai.exchange.module.login.receiver.ScreenObserver;
import com.hacai.exchange.module.login.service.TimeoutService;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.hacai.exchange.utils.ToastUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends RxAppCompatActivity {

    protected BaseActivity mContext;
    private boolean activityIsActive;
    private ScreenObserver mScreenObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View backView = findViewById(R.id.title_left_back);
        if (backView != null) {
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        AppManager.getInstance().addActivity(this);
        if (isImmersiveStatusBar())
            CommonUtil.setStatusBar(this, CommonUtil.getColor(immersiveStatusBarColor()));
        mContext = this;
        initView();
        initListener();
        initTitle();
        initData();
        initTime();//应用会话超时设置
    }

    private void initTime() {
        mScreenObserver = new ScreenObserver(this);
        mScreenObserver.requestScreenStateUpdate(new ScreenObserver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                if (!ScreenObserver.isApplicationBroughtToBackground(mContext)) {
                    cancelAlarmManager();
                }
            }

            @Override
            public void onScreenOff() {
                if (!ScreenObserver.isApplicationBroughtToBackground(mContext)) {
                    cancelAlarmManager();
                    setAlarmManager();
                }
            }
        });
    }

    //初始化监听事件
    protected void initListener() {
    }

    /**
     * 是否使用沉浸式状态栏，默认使用
     */
    public boolean isImmersiveStatusBar() {
        return true;
    }

    protected void toast(int res) {
        toast(mContext.getResources().getText(res));
    }


    protected void toastdefine(int str, int resImage) {
        toastdefine(mContext.getResources().getText(str));
    }

    protected void toast(CharSequence res) {
        //        Toast mToast = null;
        //        if (mToast == null) {
        //            mToast = Toast.makeText(mContext, res, Toast.LENGTH_SHORT);
        //        } else {
        //            mToast.setText(res);
        //            mToast.setDuration(Toast.LENGTH_SHORT);
        //        }
        //        mToast.show();
        //        ToastUtil.showToastTime(mToast,3000);
        toastdefine(res);
    }

    protected void toastdefine(CharSequence s) {
        ToastUtil.showToastWithImg(this, s);
    }

    /**
     * 沉浸式状态栏颜色，默认蓝色
     */
    protected int immersiveStatusBarColor() {
        return R.color.bt_start_color;
    }

    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化头部
     */
    protected abstract void initTitle();

    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScreenObserver != null) {
            mScreenObserver.stopScreenStateUpdate();
            mScreenObserver = null;
        }
        cancelAlarmManager();
        AppManager.getInstance().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCountPage()) MobclickAgent.onPageStart(getSimpleNme());
        MobclickAgent.onResume(this);
    }

    protected abstract String getSimpleNme();

    @Override
    protected void onPause() {
        super.onPause();
        if (isNeedCountPage()) MobclickAgent.onPageEnd(getSimpleNme());
        MobclickAgent.onPause(this);
        LogUtil.e("MainActivity-onResume");
        cancelAlarmManager();
        activityIsActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ScreenObserver.isApplicationBroughtToBackground(this)) {
            cancelAlarmManager();
            setAlarmManager();
        }
    }

    protected boolean isNeedCountPage() {
        return true;
    }


    ///此处省略一大坨代码

    /**
     * 设置定时器管理器
     */
    private void setAlarmManager() {

        long numTimeout = 30 * 1000;//5分钟
        LogUtil.i("isTimeOutMode=yes,timeout=" + numTimeout);
        Intent alarmIntent = new Intent(mContext, TimeoutService.class);
        alarmIntent.putExtra("action", "timeout"); //自定义参数
        PendingIntent pi = PendingIntent.getService(mContext, 1024, alarmIntent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerAtTime = (System.currentTimeMillis() + numTimeout);
        am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pi); //设定的一次性闹钟，这里决定是否使用绝对时间
        LogUtil.i("----->设置定时器");
    }

    /**
     * 取消定时管理器
     */
    private void cancelAlarmManager() {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, TimeoutService.class);
        PendingIntent pi = PendingIntent.getService(mContext, 1024, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        // 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消
        alarmMgr.cancel(pi);
        LogUtil.i("----->取消定时器");
    }
}
