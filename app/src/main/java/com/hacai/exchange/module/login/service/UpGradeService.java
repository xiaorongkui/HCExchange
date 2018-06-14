package com.hacai.exchange.module.login.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;

import com.hacai.exchange.R;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;
import java.util.List;

/**
 * Created by Rongkui.xiao on 2017/5/17.
 *
 * @description
 */

public class UpGradeService extends Service {

    private String url;
    private Notification notification;
    private NotificationManager manager;
    private static final int NOTIFICATION_ID = 0x12;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("onStartCommand");
        url = intent.getStringExtra(Constant.START_ACTIVITY_STRING_1);
        initNotify();
        downLoadApk(url);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.cancelAll();
    }

    /**
     * 描述：打开并安装文件.
     *
     * @param context the context
     * @param file    apk文件路径
     */
    public void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileUri = FileProvider.getUriForFile(context, "com.hacai.exchange.fileprovider",
                    file);//android 7.0以上
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            grantUriPermission(context, fileUri, intent);
        } else {
            intent.setDataAndType(/*uri*/Uri.fromFile(file), "application/vnd.android" + "" +
                    ".package-archive");
        }
        context.startActivity(intent);
    }

    private void grantUriPermission(Context context, Uri fileUri, Intent intent) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, fileUri, Intent
                    .FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    /**
     * 下载
     *
     * @param url
     */
    private void downLoadApk(String url) {
        FinalHttp finalHttp = new FinalHttp();
        String target = CommonUtil.createDir("download", CommonUtil.getFileNameFromUrl(url));
        finalHttp.download(url, target, false, new AjaxCallBack<File>() {

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                float prog = ((float) current / (float) count) * 100;
                notification.contentView.setProgressBar(R.id.prg_bar, (int) count, (int) current,
                        false);
                notification.contentView.setTextViewText(R.id.tv_prog, "下载进度   " + (int) prog +
                        "%");
                manager.notify(NOTIFICATION_ID, notification);
            }

            @Override
            public void onSuccess(File file) {
                super.onSuccess(file);
                manager.cancel(NOTIFICATION_ID);
                installApk(getApplication(), file);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                stopSelf();
            }
        });
    }

    /**
     * 初始化通知栏
     */
    private void initNotify() {
        notification = new Notification(R.mipmap.img_login_logo, "下载提醒", System.currentTimeMillis
                ());
        notification.contentView = new RemoteViews(getApplication().getPackageName(), R.layout
                .layout_down_progress);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

}
