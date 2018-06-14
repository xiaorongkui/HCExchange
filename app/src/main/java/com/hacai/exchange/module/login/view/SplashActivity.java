package com.hacai.exchange.module.login.view;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hacai.exchange.MainActivity;
import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.UpdateInfo;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.module.login.model.LoginInterface;
import com.hacai.exchange.module.login.presenter.LoginPresenter;
import com.hacai.exchange.module.login.service.FocusUpGradeService;
import com.hacai.exchange.module.login.service.UpGradeService;
import com.hacai.exchange.ui.widget.AlterDialogUtils;
import com.hacai.exchange.manager.AppManager;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.hacai.exchange.utils.SPHelper;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by dove on 2017/2/10.
 */
public class SplashActivity extends BaseActivity implements LoginInterface.ILoginView {
    private static final int SPLASH_TOTAL_COUNTDOWN_TIME = 3;
    private static final int CHECK_UPDATE_TAG = 1;
    private TextView logoTime_tv;
    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE};
    private boolean permissionFinish;
    private boolean timeFinish;
    private Subscription subscribe;
    private LoginPresenter loginPresenter;
    private boolean isNormalUpdate = false;
    private boolean isFocuseUpdate = false;
    private AlterDialogUtils alterNormalDialog;
    private AlterDialogUtils alterFocusDialog;
    private ProgressBar focuse_update_prg_bar;
    private TextView only_confirm_btn;

    @Override
    protected void initView() {
        logoTime_tv = (TextView) findViewById(R.id.splashTime);
        logoTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMianActivity();
                subscribe.unsubscribe();

            }
        });
        initTimeView(logoTime_tv);
    }

    private void initTimeView(TextView logoTime_tv) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) logoTime_tv
                .getLayoutParams();
        layoutParams.topMargin = CommonUtil.getStatusBarHeight() + (int) CommonUtil.getDimen(R
                .dimen.y20);
        logoTime_tv.setLayoutParams(layoutParams);
    }

    private void initPermission() {
        AndPermission.with(mContext).requestCode(200).permission(permissions).rationale(new RationaleListener() {

            @Override
            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                LogUtil.i("splash permission  showRequestPermissionRationale requestCode=" + requestCode);
                AndPermission.rationaleDialog(mContext, rationale).show();
            }
        }).callback(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                LogUtil.i("splash permission  onSucceed requestCode=" + requestCode);
                if (requestCode == 200 && grantPermissions.get(grantPermissions.size() - 1)
                        .equalsIgnoreCase(Manifest.permission.ACCESS_WIFI_STATE)) {
                    permissionFinish = true;
                    if (timeFinish) goMianActivity();
                }
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                LogUtil.i("splash permission  onFailed requestCode=" + requestCode);
                if (requestCode == 200 && deniedPermissions.get(deniedPermissions.size() - 1)
                        .equalsIgnoreCase(Manifest.permission.ACCESS_WIFI_STATE)) {
                    permissionFinish = true;
                    if (timeFinish) goMianActivity();
                }
            }
        }).start();
    }

    private void setTimeCountDown(final int splashTotalCountdownTime) {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers
                .mainThread()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Long,
                Integer>() {
            @Override
            public Integer call(Long increaseTime) {
                LogUtil.i("increaseTime=" + increaseTime);
                return splashTotalCountdownTime - increaseTime.intValue();
            }
        }).take(splashTotalCountdownTime + 1).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LogUtil.i("integer=" + integer);
                logoTime_tv.setText(getSpannableStringBuilder(integer));
                if (integer == 0) {
                    timeFinish = true;
                    if (permissionFinish) goMianActivity();
                }
            }
        });
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);
        logoTime_tv.setText(getSpannableStringBuilder(SPLASH_TOTAL_COUNTDOWN_TIME));
        boolean isFirstOpen = SPHelper.getInstance().getBoolean(Constant.IS_FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            CommonUtil.gotoActivity(this, WelcomeGuideActivity.class);
            AppManager.getInstance().removeCurrent();
            return;
        }
        //        else {
        //            CommonUtil.gotoActivity(mContext, LoginActivity.class);
        //        }
        setTimeCountDown(SPLASH_TOTAL_COUNTDOWN_TIME);
        initPermission();
        checkAppUpdate();
    }

    private void checkAppUpdate() {
        Map<String, Object> baseMaps = AppNetConfig.getBaseMaps();
        baseMaps.put("machine", 1);
        loginPresenter.checkUpdate(baseMaps, CHECK_UPDATE_TAG, false);
    }


    private void goMianActivity() {
        if (!isNormalUpdate && !isFocuseUpdate) {
            CommonUtil.gotoActivity(SplashActivity.this, MainActivity.class);
            AppManager.getInstance().removeCurrent();
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_splash;
    }

    private SpannableStringBuilder getSpannableStringBuilder(long seconds) {
        SpannableStringBuilder builder = new SpannableStringBuilder(getResources().getString(R
                .string.splash_count_down_timer, seconds));
        ForegroundColorSpan redSpan = new ForegroundColorSpan(0xffffa939);
        builder.setSpan(redSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public boolean isImmersiveStatusBar() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        if (alterNormalDialog != null && alterNormalDialog.isShowing()) {
            alterNormalDialog.dismiss();
            alterNormalDialog = null;
        }
        if (alterFocusDialog != null && alterFocusDialog.isShowing()) {
            alterFocusDialog.dismiss();
            alterFocusDialog = null;
        }

        try {
            unbindService(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onError(String errorMsg, String code, int tag) {
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case CHECK_UPDATE_TAG:
                UpdateInfo.ObjBean updateInfoObj = null;
                try {
                    UpdateInfo updateInfo = (UpdateInfo) response;
                    updateInfoObj = updateInfo.getObj();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                calculateUpdate(updateInfoObj);
                break;
        }
    }

    private void calculateUpdate(UpdateInfo.ObjBean updateInfoObj) {
        if (updateInfoObj == null) return;

        int tag = 0;
        try {
            String isForce = updateInfoObj.getIsForce();
            tag = Integer.parseInt(isForce);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String newVersion = updateInfoObj.getVersion();
        String oldVersion = CommonUtil.getAppVersionName(mContext);
        if (!TextUtils.isEmpty(newVersion) && (newVersion.startsWith("V") || newVersion
                .startsWith("v"))) {
            newVersion = newVersion.substring(1, newVersion.length());
        }
        if (!TextUtils.isEmpty(oldVersion) && (oldVersion.startsWith("V") || oldVersion
                .startsWith("v"))) {
            oldVersion = oldVersion.substring(1, oldVersion.length());
        }
        try {
            if (compareVersion(newVersion, oldVersion) == 1) {
                if (tag == 1) {//强制升级
                    LogUtil.i("强制升级");
                    focuseUpdate(updateInfoObj.getUrl(), updateInfoObj.getUpdateDesc());
                } else {
                    LogUtil.i("一般升级");
                    normalUpdate(updateInfoObj.getUrl(), updateInfoObj.getUpdateDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.i("newVersion=" + newVersion + ";oldVersion=" + oldVersion);

    }

    private void normalUpdate(final String url, String updateDesc) {
        isNormalUpdate = true;
        alterNormalDialog = new AlterDialogUtils(mContext, R.style.payment_dialog_notice);
        alterNormalDialog.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen.x290));
        alterNormalDialog.setCanceledOnTouchOutside(false);
        alterNormalDialog.setOneOrTwoBtn(false);
        alterNormalDialog.setTitle(CommonUtil.getString(R.string.upgrade_notice));
        alterNormalDialog.setMessage(updateDesc);
        alterNormalDialog.setTwoCancelBtn("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterNormalDialog.dismiss();
            }
        });
        alterNormalDialog.setTwoConfirmBtn("升级", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpGradeService.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, url);
                mContext.startService(intent);
                alterNormalDialog.dismiss();
            }
        });
        alterNormalDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isNormalUpdate = false;
                goMianActivity();
            }
        });
        alterNormalDialog.show();
    }

    private void focuseUpdate(final String url, String updateDesc) {
        isFocuseUpdate = true;
        alterFocusDialog = new AlterDialogUtils(mContext, R.style.payment_dialog_notice, R.layout
                .focuse_update_dialog);
        alterFocusDialog.setAlertDialogWidth((int) CommonUtil.getDimen(R.dimen.x290));
        alterFocusDialog.setOneOrTwoBtn(true);
        focuse_update_prg_bar = alterFocusDialog.getView(R.id.focuse_update_prg_bar, ProgressBar
                .class);
        only_confirm_btn = alterFocusDialog.getView(R.id.only_confirm_btn, TextView.class);
        alterFocusDialog.setCanceledOnTouchOutside(false);
        alterFocusDialog.setCancelable(false);
        alterFocusDialog.setTitle(CommonUtil.getString(R.string.upgrade_notice));
        alterFocusDialog.setMessage(updateDesc);

        alterFocusDialog.setOneConfirmBtn("升级", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FocusUpGradeService.class);
                intent.putExtra(Constant.START_ACTIVITY_STRING_1, url);
                mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
            }
        });
        alterFocusDialog.show();
    }

    private FocusUpGradeService.MyBind myService;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = (FocusUpGradeService.MyBind) service;
            System.out.println("Service连接成功");
            AndPermission.with(mContext).requestCode(500).permission(Manifest.permission
                    .INSTALL_PACKAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                    .permission.READ_EXTERNAL_STORAGE).rationale(new RationaleListener() {

                @Override
                public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                    AndPermission.rationaleDialog(mContext, rationale).show();
                }
            }).callback(new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    LogUtil.i("splash permission  onSucceed requestCode=" + requestCode);
                    if (requestCode == 500 && AndPermission.hasPermission(mContext,
                            grantPermissions)) {
                        startDownFocusApk();
                    }
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    if (requestCode == 500) {
                        if (AndPermission.hasPermission(mContext, deniedPermissions)) {
                            startDownFocusApk();
                        } else {
                            if (AndPermission.hasAlwaysDeniedPermission(mContext,
                                    deniedPermissions)) {
                                AndPermission.defaultSettingDialog(mContext, 500).show();
                            } else {
                                only_confirm_btn.setText("升级");
                                only_confirm_btn.setEnabled(true);
                                focuse_update_prg_bar.setVisibility(View.VISIBLE);
                                toast("权限不足无法升级");
                            }
                        }
                    }
                }
            }).start();
        }
    };

    private void startDownFocusApk() {
        only_confirm_btn.setText(CommonUtil.getString(R.string.upgrade_focuse));
        only_confirm_btn.setEnabled(false);
        focuse_update_prg_bar.setVisibility(View.VISIBLE);
        myService.setonDownListener(new FocusUpGradeService.onApKDownLoad() {
            @Override
            public void onFinish(File file) {
                if (alterFocusDialog != null) {
                    alterFocusDialog.dismiss();
                }
                focuse_update_prg_bar.setProgress(100);
            }

            @Override
            public void failed() {//下载失败
                toast("下载失败请重试...");
                only_confirm_btn.setText("升级");
                only_confirm_btn.setEnabled(true);
            }

            @Override
            public void onLoading(int progress) {
                LogUtil.i("progress=" + progress);
                focuse_update_prg_bar.setProgress(progress);
            }
        });
        myService.downLoadApk();
    }


    @Override
    public void clearPassword() {
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return 0代表相等，1代表version1大于version2，-1代表version1小于version2
     */
    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1.equalsIgnoreCase(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer
                .parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

}
