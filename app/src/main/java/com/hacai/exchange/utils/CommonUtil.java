package com.hacai.exchange.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.hacai.exchange.HCExchangeApp;
import com.hacai.exchange.R;
import com.hacai.exchange.bean.AccountInfo;
import com.hacai.exchange.bean.LoginReponse;
import com.hacai.exchange.bean.TextShowInfo;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.ui.ui.FixedSpeedScroller;
import com.meituan.android.walle.WalleChannelReader;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class CommonUtil {


    /**
     * 跳转activity
     */
    public static void gotoActivity(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        gotoActivity(context, intent);
    }

    public static void gotoActivity(Context context, Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 跳转activity
     */
    public static void startActivityForResult(Activity context, Class<?> clazz, int requestCode) {
        Intent intent = new Intent(context, clazz);
        startActivityForResult(context, intent, requestCode);
    }

    public static void startActivityForResult(Activity context, Intent intent, int requestCode) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int color) {
        return getContext().getResources().getColor(color);
    }

    public static int getTextSize(int id) {
        return getContext().getResources().getDimensionPixelOffset(id);
    }

    /**
     * 获取zi
     */
    public static String getString(int textId) {
        return getContext().getResources().getString(textId);
    }

    public static Drawable getDrawable(int res) {
        return getContext().getResources().getDrawable(res);
    }

    public static Bitmap getBitmap(int res) {
        return BitmapFactory.decodeResource(getContext().getResources(), res);
    }

    /**
     * 获取颜色
     */
    public static Drawable getColorDarwable(int color) {
        return getContext().getResources().getDrawable(color);
    }

    public static String[] getStringArr(int strArrId) {
        return getContext().getResources().getStringArray(strArrId);
    }

    /**
     * xml转换view
     *
     * @param layoutId
     * @return
     */
    public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /**
     * dp与px转换
     * 1dp----0.5px
     * 1dp----1px；
     * 1dp----1.25pd
     * 1dp---1.75px;
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    /**
     * 获取状态栏高度
     *
     * @return px
     */
    public static int getStatusBarHeight() {
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        return resourceId > 0 ? getContext().getResources().getDimensionPixelSize(resourceId) : 0;
    }

    private static final int INVALID_VAL = -1;


    /**
     * 设置沉浸式状态栏
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBar(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上版本
            if (statusColor != INVALID_VAL) {
                activity.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build
                .VERSION_CODES.LOLLIPOP) {//4.4-5.0
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int color = Color.parseColor("#20000000");
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, getStatusBarHeight());
            statusBarView.setBackgroundColor(color);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusBarView, lp);
            //给父容器设置参数
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.getChildAt(0).setFitsSystemWindows(true);
            contentView.setClipToPadding(true);
        }
    }


    /**
     * 检查SD卡是否存在
     */
    private static boolean checkSdCard() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取手机SD卡总空间
     *
     * @return
     */
    private static long getSDcardTotalSize() {

        if (checkSdCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();

            return blockSizeLong * blockCountLong;
        } else {
            return 0;
        }
    }

    /**
     * 获取SDka可用空间
     *
     * @return
     */
    private static long getSDcardAvailableSize() {

        if (checkSdCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else return 0;
    }


    /**
     * 获取手机内部存储总空间
     *
     * @return
     */
    public static long getPhoneTotalSize() {

        if (!checkSdCard()) {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();
            return blockSizeLong * blockCountLong;
        } else return getSDcardTotalSize();
    }


    /**
     * 获取手机内存存储可用空间
     *
     * @return
     */
    public static long getPhoneAvailableSize() {

        if (!checkSdCard()) {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else return getSDcardAvailableSize();
    }

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return HCExchangeApp.getContext();
    }

    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>
     *               移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *               </p>
     *               <p>
     *               联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *               </p>
     *               <p>
     *               电信的号段：133、153、180（未启用）、189
     *               </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p>
     *              <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
     *              的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
     *              </p>
     *              <p>
     *              <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。
     *              </p>
     *              <p>
     *              <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
     *              </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkLandlinePhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    public static void hideInputWindow(Activity context) {
        if (context == null) {
            return;
        }
        final View v = ((Activity) context).getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(context
                    .INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void hideLightWindow(Activity context, boolean isHide) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = isHide ? 0.5f : 1.0f;
        lp.dimAmount = isHide ? 0.5f : 1.0f;
        context.getWindow().setAttributes(lp);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 检查密码。必须包含数字和字母
     */
    public static boolean checkPassword(CharSequence str) {
        return checkPassword(str.toString());
    }

    /**
     * 检查密码。必须包含数字和字母
     */
    public static boolean checkPassword(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        boolean isLength = str.length() >= 8 && str.length() <= 16;//定义一个boolean值，用来表示是否8-16位长度

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex) && isLength;
        return isRight;
    }

    /**
     * 检查密码。必须包含数字和字母
     */
    public static boolean checkName(String str) {
        if (str.length() == 0) {
            return false;//正则表达式很慢
        }
        try {
            Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
            return p.matcher(str).matches();
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //检查是小数或者整数
    public static boolean isNumericorFloat(String str) {
        Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {

            return false;
        }
        return true;
    }


    public static int sp2px(float spValue) {
        float fontScale = HCExchangeApp.getContext().getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        float fontScale = HCExchangeApp.getContext().getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    //占用状态栏
    public static void setStatusBarInvisible(RxAppCompatActivity context, boolean isShow) {
        //得到当前界面的装饰视图
        int option;
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = context.getWindow().getDecorView();

            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            if (isShow) {
                option = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                CommonUtil.setStatusBar(context, CommonUtil.getColor(R.color.bt_start_color));
            } else {
                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                //设置状态栏颜色为透明
                context.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            decorView.setSystemUiVisibility(option);

        } else {
            CommonUtil.setStatusBar(context, CommonUtil.getColor(R.color.bt_start_color));
        }
        //隐藏标题栏
        ActionBar actionBar = context.getSupportActionBar();
        actionBar.hide();
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    public static String formatMoney(double str) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00");
        return myformat.format(str);
    }

    public static String formatMoney(String str) {
        double doubleMoney = 0;
        try {
            doubleMoney = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("formatMoney exception!");
        }
        return formatMoney(doubleMoney);
    }

    public static String format2Point(double str) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("#0.00");
        return myformat.format(str);
    }

    public static String format2PointMillion(String str) {
        return format2Point(div(str, "10000"));
    }

    public static String format2Point(String str) {
        double doubleMoney = 0;
        try {
            doubleMoney = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LogUtil.i("formatMoney exception!");
        }
        return format2Point(doubleMoney);
    }

    public static String format0Point(String str) {
        double doubleMoney = 0;
        try {
            doubleMoney = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LogUtil.i("formatMoney exception!");
        }
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("#");
        return myformat.format(doubleMoney);
    }

    public static String decodeFormatMoney(String str) {
        String[] split = str.split(",");
        String content = "";
        for (String s : split) {
            content += s;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        double doubleMoney = 0;
        try {
            doubleMoney = Double.parseDouble(content);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LogUtil.i("EncodeFormatMoney exception!");
        }
        return decimalFormat.format(doubleMoney);
    }


    //获取分辨率
    public static String getAndroidPix(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return "手机屏幕分辨率为：" + dm.widthPixels + " × " + dm.heightPixels;
    }

    /**
     * 创建文件目录，用于保存文件，便于统一管理,当程序被卸载的时候会自动清除该目录下的所有文件
     *
     * @param filename 文件名
     * @return 文件全路径
     */
    public static String createDir(String dirName, String filename) {
        File externalCacheDir = getContext().getExternalCacheDir();
        File file = new File(externalCacheDir, dirName);
        if (!file.exists()) file.mkdirs();
        // 若不存在，创建目录，可以在应用启动的时候创建
        return file.getAbsolutePath() + "/" + filename;
    }

    /**
     * 创建文件目录，用于保存文件，便于统一管理,当程序被卸载的时候会自动清除该目录下的所有文件
     *
     * @param filename 文件名
     * @return 文件全路径
     */
    public static String createSDCardDir(String dirName, String filename) {
        File externalCacheDir = getContext().getFilesDir();
        File file = new File(externalCacheDir, dirName);
        if (!file.exists()) file.mkdirs();
        // 若不存在，创建目录，可以在应用启动的时候创建
        return file.getAbsolutePath() + "/" + filename;
    }
    public static String formatBankcard(String bankcard) {
        StringBuilder sb = new StringBuilder("");
        String bankcardStr = bankcard.substring(bankcard.length() - 4, bankcard.length());
        sb.append("****\t\t****\t\t****\t\t").append(bankcardStr);
        return sb.toString();
    }

    public static int getRBGColor(int r, int g, int b) {
        return Color.rgb(r, g, b);
    }

    /**
     * 提供精确的除法运算方法div,保留两位小数
     *
     * @param value1       被除数
     * @param value2       除数
     * @param roundingMode 精确方式RoundingMode.HALF_EVEN
     * @param pointCount   保留到小数位数
     * @return 两个参数的商
     */
    public static double div(double value1, double value2, RoundingMode roundingMode, int
            pointCount) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, pointCount, roundingMode).doubleValue();
    }

    //四舍五入，保留2位小数
    public static double div(double value1, double value2) {
        return div(value1, value2, RoundingMode.HALF_EVEN, 2);
    }

    //四舍五入，保留2位小数
    public static double div(String value1, String value2) {

        double m = 0;
        double n = 0;
        try {
            m = Double.parseDouble(value1);
            n = Double.parseDouble(value2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (n == 0) {
            return 0;
        }
        return div(m, n, RoundingMode.HALF_EVEN, 2);
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    public static float getDimen(int id) {
        return HCExchangeApp.getContext().getResources().getDimension(id);
    }

    /**
     * 判断是否为平板
     *
     * @return
     */
    private boolean isPad(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = display.getWidth();
        // 屏幕高度
        float screenHeight = display.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于7尺寸则为Pad
        if (screenInches >= 7.0) {
            return true;
        }
        return false;
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager
                    .PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();//todo
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver
                        (), android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 必须通过Luhn算法来验证通过。该校验的过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。 *
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和。 *
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。例如，卡号是：5432123456788881 *
     * 则奇数、偶数位（用红色标出）分布：5432123456788881奇数位和=35 *
     * 偶数位乘以2（有些要减去9）的结果：16261577，求和=35。最后35+35=70可以被10整除，认定校验通过。 *
     * 请编写一个程序，从键盘输入卡号，然后判断是否校验通过。通过显示：“成功”，否则显示“失败”。比如，用户输入：356827027232780 * 程序输出：成功
     * <p>判断是否是银行卡号
     *
     * @param cardNo 卡号
     * @return boolean 是否合法
     */

    public static boolean checkBankCard(String cardNo) {
        char bit = getBankCardCheckCode(cardNo.substring(0, cardNo.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardNo.charAt(cardNo.length() - 1) == bit;
    }

    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 ||
                !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static void main(String[] agrs) {
        System.out.print(checkBankCard("1234551111111111111"));
    }

    public static void aysncWebView(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieString = SPHelper.getInstance().getString("");
        cookieManager.setCookie(url, cookieString);
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 返回当前程序版本名
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            LogUtil.i(e.getMessage());
        }
        return versioncode;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtil.i(e.getMessage());
        }
        return versionName;
    }

    //挂壁键盘
    public static void hideKeyBoard(Activity aty) {
        ((InputMethodManager) aty.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(aty.getCurrentFocus().getWindowToken(), 2);
    }

    /**
     * 检查是否是移动网络
     */
    public static boolean isMobile(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) return true;
        }
        return false;
    }

    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    /**
     * 检查是否是WIFI
     */
    public static boolean isWifi(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) return true;
        }
        return false;
    }


    /**
     * 获取渠道编号
     *
     * @return 异常情况返回null
     * @author Rongkui.xiao
     * created at 2017/6/22 9:33
     */
    public static String getChannelCode(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelName = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo
                    (context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                if (applicationInfo.metaData != null) {
                    channelName = applicationInfo.metaData.get(key) + "";
                }
            }

        } catch (Exception e) {
            LogUtil.i("获取设备市场渠道异常=" + e.getMessage());
        }
        return channelName;
    }

    /**
     * 获取META-INFO下面的渠道
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        //        ApplicationInfo appinfo = context.getApplicationInfo();
        //        String sourceDir = appinfo.sourceDir;
        //        ZipFile zipfile = null;
        //        final String start_flag = "META-INF/channel_";
        //        try {
        //            zipfile = new ZipFile(sourceDir);
        //            Enumeration<?> entries = zipfile.entries();
        //            while (entries.hasMoreElements()) {
        //                ZipEntry entry = ((ZipEntry) entries.nextElement());
        //                String entryName = entry.getName();
        //                if (entryName.contains(start_flag)) {
        //                    String channel = entryName.replaceAll(start_flag, "");
        //                    return channel;
        //                }
        //            }
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        } finally {
        //            if (zipfile != null) {
        //                try {
        //                    zipfile.close();
        //                } catch (IOException e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        }
        return WalleChannelReader.getChannel(context);
    }


    //社会自viewpager的滑动速率
    public static void setScrollerTime(Context mContext, int scrollerTime, ViewPager viewPager) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller mScroller = new FixedSpeedScroller(mContext, new
                    AccelerateInterpolator());
            mScroller.setmDuration(scrollerTime);    //在这里设置时间单位毫秒
            mField.set(viewPager, mScroller); //viewPager和FixedSpeedScrolle
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备id
     *
     * @author Rongkui.xiao
     * created at 2017/6/22 9:23
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneIMEI(final Activity cxt) {
        TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setData(Uri.fromFile(file));
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        //        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static void setViewEnable(View view, boolean isEnable) {
        if (isEnable) {
            view.setEnabled(true);
            view.setAlpha(1.0f);
        } else {
            view.setEnabled(false);
            view.setAlpha(0.5f);
        }
    }


    //保存登录时个人信息返回
    public static void saveLoginInfo(LoginReponse userInfo) {
        SPHelper.getInstance().saveObject(Constant.SP_SAVE_LOGIN_USERINFO, userInfo);
    }

    //获取登录时个人信息返回
    public static LoginReponse getLoginInfo() {
        LoginReponse userInfo = null;
        try {
            userInfo = (LoginReponse) SPHelper.getInstance().getObject(Constant
                    .SP_SAVE_LOGIN_USERINFO, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.i(("userInfo=" + (userInfo == null ? "null" : userInfo.toString())));
        return userInfo;
    }

    //保存个人账户信息返回
    public static void saveAccountInfo(AccountInfo accountInfo) {
        SPHelper.getInstance().saveObject(Constant.SP_SAVE_ACCOUNTINFO, accountInfo);
    }

    //保存个人账户信息返回
    public static AccountInfo getAccountInfo() {
        try {
            return (AccountInfo) SPHelper.getInstance().getObject(Constant.SP_SAVE_ACCOUNTINFO,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取tokenId
    public static String getTokenId() {
        return getLoginInfo() == null ? "" : getLoginInfo().getToken();
    }


    //获取登录时个人手机号
    public static String getLoginUserPhoneNum() {
        return getLoginInfo() == null ? "" : getLoginInfo().getUsername();
    }

    //获取个人手机号
    public static String getUserPhoneNum() {
        return SPHelper.getInstance().getString(Constant.USER_PHONE_NUMBER);
    }

    //获取登录时个人手机号
    public static void setUserPhoneNum(String unm) {
        SPHelper.getInstance().set(Constant.USER_PHONE_NUMBER, unm);
    }

    public static SpannableStringBuilder setSpannableText(List<TextShowInfo> textShowInfos) {
        //SpannableStringBuilder实现CharSequence接口
        int index = 0;
        SpannableStringBuilder styles = new SpannableStringBuilder("");
        for (TextShowInfo textShowInfo : textShowInfos) {
            String str = textShowInfo.getStr();
            int textSize = textShowInfo.getTextSizeId();
            int textColor = textShowInfo.getTextColorId();
            SpannableStringBuilder style = styles.append(str);
            style.setSpan(new ForegroundColorSpan(CommonUtil.getColor(textColor)), index, index +
                    str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new AbsoluteSizeSpan(CommonUtil.getTextSize(textSize)), index, index +
                    str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            index += str.length();
        }

        return styles;
    }

    /**
     * 获取文件缓存路径
     */
    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取文件缓存路径
     */
    public String getDiskFileDir(Context context) {
        String filePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            filePath = context.getExternalFilesDir(null).getPath();
        } else {
            filePath = context.getFilesDir().getPath();
        }
        LogUtil.i("filePath=" + filePath);
        return filePath;
    }

    //处理电话号码,返回加密的号
    public static String dealPhoneNum(String userName, int startLength, int endlength) {
        if (TextUtils.isEmpty(userName) || userName.length() < (startLength + endlength)) return "";
        String startStr = userName.substring(0, startLength);
        String endStr = userName.substring(userName.length() - endlength, userName.length());
        StringBuilder sb = new StringBuilder(startStr);
        for (int i = 0; i < (userName.length() - startLength - endlength); i++) {
            sb.append("*");
        }
        return sb.append(endStr).toString();
    }
}
