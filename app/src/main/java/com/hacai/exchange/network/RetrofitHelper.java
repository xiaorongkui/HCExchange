package com.hacai.exchange.network;

import android.content.Context;

import com.hacai.exchange.HCExchangeApp;
import com.hacai.exchange.common.AppNetConfig;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.exception.RetryWhenNetworkException;
import com.hacai.exchange.utils.CommonUtil;
import com.hacai.exchange.utils.LogUtil;
import com.hacai.exchange.utils.SPHelper;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Retrofit帮助类
 */
public class RetrofitHelper {
    public static final int connectionTime = 10;//连接时间

    static {
        initOkHttpClient();
    }

    private volatile static RetrofitHelper instance;

    //构造方法私有
    private RetrofitHelper() {
    }

    //获取单例
    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void sendHttpRequest(BaseApi basePar) {
        //用于保存请求头
        //        mOkHttpClientBuilder.addInterceptor(new SaveCookiesInterceptor());
        //用于添加公共请求头
        //        mOkHttpClientBuilder.addInterceptor(new ReadCookiesInterceptor());
        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(basePar
                .getGsonConverterFactory()).addCallAdapterFactory(RxJavaCallAdapterFactory.create
                ()).client(mOkHttpClientBuilder.build()).baseUrl(basePar.getBaseUrl()).build();
        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
        Observable observable = basePar.getObservable(retrofit).retryWhen(new
                RetryWhenNetworkException())//失败后的retry配置
                //               .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn
                        (AndroidSchedulers.mainThread()).map(basePar);
        /*数据回调*/
        observable.subscribe(subscriber);
    }


    private static OkHttpClient.Builder mOkHttpClientBuilder;
    private static OkHttpClient mOkHttpClient;


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i("OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (mOkHttpClientBuilder == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClientBuilder == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File("httplog", "HttpCache"), 1024 * 1024 * 10);
                    mOkHttpClientBuilder = new OkHttpClient.Builder().cache(cache).addInterceptor
                            (loggingInterceptor).addNetworkInterceptor(new CacheInterceptor())
                            .retryOnConnectionFailure(true) //手动创建一个OkHttpClient并设置超时时间缓存等设置
                            .readTimeout(connectionTime, TimeUnit.SECONDS).writeTimeout
                                    (connectionTime, TimeUnit.SECONDS).connectTimeout
                                    (connectionTime, TimeUnit.SECONDS);
                }
            }
        }
    }

    /**
     * 获取okhttp对象
     */
    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClientBuilder.build();
    }

    /**
     * 根据逻辑需要，给客户端设置Interceptor
     */
    public RetrofitHelper setOkHttpClientInterceptor(Interceptor interceptor) {
        mOkHttpClientBuilder.addInterceptor(interceptor);
        return this;
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     *
     * @param clazz ff
     * @param <T>
     * @return
     */
    private static <T> T createApi(Class<T> clazz) {
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) mOkHttpClient = mOkHttpClientBuilder.build();
            }
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppNetConfig.baseUrl).client
                (mOkHttpClient).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(clazz);
    }

    /**
     * 用于设置共有的参数，比如网络请求的token
     */
    public class CustomInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl httpUrl = request.url().newBuilder().addQueryParameter("token", "tokenValue")
                    .build();
            request = request.newBuilder().url(httpUrl).build();
            return chain.proceed(request);
        }
    }


    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        Context context;

        @Override
        public Response intercept(Chain chain) throws IOException {

            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();

            if (CommonUtil.isNetworkAvailable(HCExchangeApp.getContext())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(HCExchangeApp.getContext())) {
                response = response.newBuilder().removeHeader("Pragma").header("Cache-Control",
                        "public, max-age=" + maxAge).build();
            } else {
                response = response.newBuilder().removeHeader("Pragma").header("Cache-Control",
                        "public, " + "only-if-cached, max-stale=" + maxStale).build();
            }
            return response;
        }
    }

    private static class ReadCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .addHeader("Connection", "keep-alive");
            Set<String> localHeader = SPHelper.getInstance().getStringSet(Constant
                    .COMMON_REQUEST_HEADERS);

            for (String cookie : localHeader) {
                builder.addHeader("Cookie", cookie);
                LogUtil.i("Adding Header: " + cookie); // This is done so I know which headers
                // are being added; this interceptor is used after the normal logging of OkHttp
            }
            return chain.proceed(builder.build());
        }
    }

    private static class SaveCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());

            if (!response.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();
                for (String header : response.headers("Set-Cookie")) {
                    cookies.add(header);
                }
                SPHelper.getInstance().set(Constant.COMMON_REQUEST_HEADERS, cookies);
            }

            LogUtil.i("返回请求头为：" + response.header("Set-Cookie"));
            return response;
        }

    }
}
