package com.hacai.exchange.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hacai.exchange.R;

/**
 * Created by Rongkui.xiao on 2017/3/22.
 *
 * @description
 */

public class ToastUtil {

    private static TextView textView;

    /**
     * 在任何地方都可以弹的Toast,保证运行在ui线程中
     */
    public static void toast(final Activity activity, final String s) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 在任何地方都可以弹的Toast,保证运行在ui线程中
     */
    public static void toast(final Context context, final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    /**
     * 自定义toast
     */
    public static void showDefinedToast(Context context, CharSequence s) {
        View view = View.inflate(context, R.layout.toast_zifae, null);
        TextView text = (TextView) view.findViewById(R.id.toast_tv);
        text.setText(s);
        Toast mToast = new Toast(context);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        mToast.show();
    }

    public static void showToastWithImg(Context context, CharSequence text) {
        Toast mToast = null;
        if (mToast == null) {
            mToast = new Toast(context);
            // 获取LayoutInflater对象
            LayoutInflater inflater = LayoutInflater.from(context);
            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.toast_zifae, null);
            // 实例化ImageView和TextView对象
            textView = (TextView) layout.findViewById(R.id.toast_tv);

            mToast.setView(layout);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            //        imageTitle.setMinimumHeight(30);
            //        imageTitle.setMinimumWidth(30);
            textView.setText(text);
        } else {
            textView.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 自定义toast时间
     */
    public static void showToastTime(final Toast toast, final int duration) {
        final long SHORT = 2000;
        final long LONG = 3500;
        final long ONE_SECOND = 1000;
        final long d = duration <= SHORT ? SHORT : duration > LONG ? duration : LONG;
        new CountDownTimer(Math.max(d, duration), ONE_SECOND) {

            @Override
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            @Override
            public void onFinish() {
                toast.cancel();
            }
        }.start();
    }
}
