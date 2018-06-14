package com.hacai.exchange.module.login.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.FileProvider;

import com.hacai.exchange.common.Constant;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;
import java.util.List;

/**
 * Created by lenovo on 2018/1/19.
 */

public class FocusUpGradeService extends Service {
    String url;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("强制升级服务器启动");
    }

    @Override
    public IBinder onBind(Intent intent) {
        url = intent.getStringExtra(Constant.START_ACTIVITY_STRING_1);
        return new MyBind();
    }


    public class MyBind extends Binder {
        private FocusUpGradeService.onApKDownLoad listener;

        public void downLoadApk() {
            LogUtil.i("FinalHttp开始");
            FinalHttp finalHttp = new FinalHttp();
            String target = CommonUtil.createDir("download", CommonUtil.getFileNameFromUrl(url));
            finalHttp.download(url, target, false, new AjaxCallBack<File>() {

                @Override
                public void onLoading(long count, long current) {
                    super.onLoading(count, current);
                    int prog = (int) (((float) current / (float) count) * 100);
                    if (listener != null) {
                        listener.onLoading(prog);
                    }
                    LogUtil.i("onLoading=" + prog);
                }

                @Override
                public void onSuccess(File file) {
                    if (listener != null) {
                        listener.onFinish(file);
                    }
                    LogUtil.i("下载完成 file=" + file.getAbsolutePath());
                    installApk(getApplication(), file);
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                    if (listener != null) {
                        listener.failed();
                    }
                    stopSelf();
                    LogUtil.i("下载失败=" + strMsg + ";error=" + t.getMessage());
                }
            });
        }

        public void setonDownListener(onApKDownLoad listener) {
            this.listener = listener;
        }

    }

    public interface onApKDownLoad {
        void onFinish(File file);

        void failed();

        void onLoading(int progress);
    }

    /**
     * 描述：打开并安装文件.
     *
     * @param context the context
     * @param file    apk文件路径
     */
    public void installApk(Context context, File file) {
        LogUtil.i("安装开始");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (!file.exists()) {
            LogUtil.i("文件安装不存在");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileUri = FileProvider.getUriForFile(context, "com.hacai.exchange.fileprovider",
                    file);//android 7.0以上
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            grantUriPermission(context, fileUri, intent);
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        LogUtil.i("跳转安装界面");
        context.startActivity(intent);
    }

    private void grantUriPermission(Context context, Uri fileUri, Intent intent) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, fileUri, Intent
                    .FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            LogUtil.i("安装申请权限=" + fileUri.getAuthority());
        }
        LogUtil.i("安装申请权限");
    }
}
