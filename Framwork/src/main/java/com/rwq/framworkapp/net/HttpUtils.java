package com.rwq.framworkapp.net;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * http工具类
 * Created by Administrator on 2017/12/8.
 */
public class HttpUtils {
    //public static final String BASE_URL = "http://192.168.1.179:80/";
    public static final String BASE_URL = "http://shengzhicheng.com:80/";
    private static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR;
    private static Context context;
    private static Retrofit retrofit;
    private static ProjectService service;
    private static HashSet<String> set;

    /**
     * 初始化 Retrofit
     */
    public static synchronized ProjectService initRetrofit() {
        if (service == null) {
            retrofit = getRetrofit();
            service = retrofit.create(ProjectService.class);
        }
        return service;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create())  //直接将返回值转为Javabean
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //.client(setCache(context))
                    .client(setHeader())
                    .build();
        }
        return retrofit;
    }


    private static OkHttpClient setHeader() {
        Interceptor getSessionId = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());

                if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                    HashSet<String> cookies = new HashSet<>();
                    for (String header : originalResponse.headers("Set-Cookie")) {
                        cookies.add(header);
                    }
                    SPUtils.getInstance().put("cookies", cookies);
                }
                return originalResponse;
            }
        };
        Interceptor responseHeader = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                try {
                    if (!SPUtils.getInstance().getStringSet("cookies").isEmpty()) {
                        HashSet<String> preferences = (HashSet<String>) SPUtils.getInstance().getStringSet("cookies");
                        for (String cookie : preferences) {
                            builder.addHeader("Cookie", cookie);
                            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    return chain.proceed(builder.build());
                }
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(getSessionId)
                .addInterceptor(responseHeader)
                .build();
        return client;
    }


    public static OkHttpClient setCache(final Context c) {
        REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                cacheBuilder.maxStale(365, TimeUnit.DAYS);
                CacheControl cacheControl = cacheBuilder.build();
                Request request = chain.request();
                if (!NetworkUtils.isConnected()) {
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                okhttp3.Response originalResponse = chain.proceed(request);
                if (NetworkUtils.isConnected()) {
                    int maxAge = 0; // read from cache
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public ,max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };
        File httpCacheDirectory = new File(c.getCacheDir(), "response");
        int size = 20 * 1024 * 1024; //20MB
        Cache cache = new Cache(httpCacheDirectory, size);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache).build();
        return client;
    }

    /**
     * 请在MyApplication 中 初始化
     *
     * @param c
     */
    public static synchronized void initContext(Context c) {
        if (context == null) {
            context = c;
        }
    }

}
