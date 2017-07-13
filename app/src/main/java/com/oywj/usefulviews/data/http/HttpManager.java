package com.oywj.usefulviews.data.http;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.bumptech.glide.util.Preconditions;
import com.oywj.usefulviews.BuildConfig;
import com.oywj.usefulviews.ui.basic.BasicApplication;
import com.oywj.usefulviews.utils.FileUtils;
import com.oywj.usefulviews.utils.LogUtils;
import com.oywj.usefulviews.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * HttpManager：主要负责的功能是进行网络请求的调度工作。
 * <p>
 * 1.将网络请求进行接口化--（Retrofit）。
 * <p>
 * 2.将实际的网络请求分发到OkHttpClient执行--（OKHttp）。
 * <p>
 * 3.对网络请求完成后的数据结果进行同步、异步分发--（RxJava），且支持配置ConvertFactory对数据解析处理。
 */
public class HttpManager {
    public static final String BASE_URL = "BASE_URL";
    private static final long DEFAULT_CONNECT_TIME = 15000;
    private static final long DEFAULT_READ_WRITE_TIME = 2000;
    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private static volatile HttpManager singleton;
    private Context context;
    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mDefaultHttpBuilder;

    private HttpManager(Context context) {
        this.context = context;
        mRetrofitBuilder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());

        mDefaultHttpBuilder =
                new OkHttpClient.Builder()
                        .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.MILLISECONDS)
                        .readTimeout(DEFAULT_READ_WRITE_TIME, TimeUnit.MILLISECONDS)
                        .addInterceptor(new UserAgentInterceptor(generateUserAgentValue()))
                        .addNetworkInterceptor(new CacheInterceptor())
                        .cache(generateCache());

    }

    public static HttpManager with(Context context) {
        if (singleton == null) {
            synchronized (HttpManager.class) {
                if (singleton == null) {
                    singleton = new HttpManager(context.getApplicationContext());
                }
            }
        }
        return singleton;
    }

    private Cache generateCache() {
        File cacheDirectory = FileUtils.getCacheDirectory(context, false);
        return new Cache(cacheDirectory, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }

    private String generateUserAgentValue() {
        String userAgentValue = Build.DEVICE + "," + Build.MODEL + "," + Build.BRAND + "," + Build.VERSION.SDK_INT + ";" +
                BuildConfig.APPLICATION_ID + "," + BuildConfig.VERSION_NAME + "," + BuildConfig.VERSION_CODE;
        LogUtils.d("userAgentValue == " + userAgentValue);
        return userAgentValue;
    }

    /**
     * 设置连接超时时间。
     *
     * @param connectTime 连接超时时间
     * @param timeUnit    时间单位
     */
    public void setConnectTime(long connectTime, TimeUnit timeUnit) {
        mDefaultHttpBuilder.connectTimeout(connectTime, timeUnit);
    }

    /**
     * 设置读写超时时间。
     *
     * @param readOrWriteTime 读写超时时间
     * @param timeUnit        时间单位
     */
    public void setReadOrWriteTime(long readOrWriteTime, TimeUnit timeUnit) {
        mDefaultHttpBuilder.readTimeout(readOrWriteTime, timeUnit);
        mDefaultHttpBuilder.writeTimeout(readOrWriteTime, timeUnit);
    }

    /**
     * 设置BaseUrl。
     *
     * @param baseUrl BaseUrl
     */
    public void setBaseUrl(String baseUrl) {
        mRetrofitBuilder.baseUrl(baseUrl);
    }

    /**
     * 用于设置在Https通信中证书。
     * <a href="http://blog.csdn.net/lmj623565791/article/details/48129405">Https配置参考文章</a>
     *
     * @param certificates 证书的输入流
     */
    public void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            mDefaultHttpBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建具体网络请求接口的实现类对象
     *
     * @param apiClass Retrofit中使用的网络请求接口
     * @param <Api>    实际的类型
     * @return 网络请求接口实现类
     */
    public <Api> Api createApi(Class<Api> apiClass) {
        return createApi(apiClass, mDefaultHttpBuilder);
    }

    /**
     * 根据自定义的OkHttpClient.Builder来创建具体的网络请求接口的实现类对象。
     *
     * @param apiClass Retrofit中使用的网络请求接口
     * @param builder  OkHttpClient.Builder
     * @param <Api>    实际的类型
     * @return 网络请求接口实现类
     */
    public <Api> Api createApi(Class<Api> apiClass, OkHttpClient.Builder builder) {
        String baseUrl = getBaseUrl(apiClass);
        if (!TextUtils.isEmpty(baseUrl)) {
            mRetrofitBuilder.baseUrl(baseUrl);
        }
        Retrofit retrofit = mRetrofitBuilder
                .client(builder.build())
                .build();
        return retrofit.create(apiClass);
    }

    /**
     * 创建具体的网络请求接口实现类对象，并且返回数据的原始形式。
     *
     * @param apiClass 网络请求接口
     * @return 网络请求接口实现类对象
     */
    public <Api> Api createApiForOriginal(Class<Api> apiClass) {
        return createApiForOriginal(apiClass, mDefaultHttpBuilder);
    }

    /**
     * 创建具体的网络请求接口实现类对象，并且返回数据的原始形式。
     *
     * @param apiClass 网络请求接口
     * @param builder  OkHttpBuilder
     */
    public <Api> Api createApiForOriginal(Class<Api> apiClass, OkHttpClient.Builder builder) {
        String baseUrl = getBaseUrl(apiClass);
        Retrofit.Builder retrofitBuild = new Retrofit.Builder();
        if (!TextUtils.isEmpty(baseUrl)) {
            retrofitBuild.baseUrl(baseUrl);
        }

        Retrofit retrofit = retrofitBuild
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();

        return retrofit.create(apiClass);
    }

    // 反射获取Api接口定义的baseUrl
    private <Api> String getBaseUrl(Class<Api> apiClass) {
        try {
            Field field = apiClass.getField(BASE_URL);
            return (String) field.get(apiClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 用户代理拦截器：用于指定Http请求头中的“User-Agent”头字段的值。
     */
    static final class UserAgentInterceptor implements Interceptor {
        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        public UserAgentInterceptor(String userAgentHeaderValue) {
            this.userAgentHeaderValue = Preconditions.checkNotNull(userAgentHeaderValue);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request originalRequest = chain.request();
            final Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 缓存拦截器
     */
    static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            boolean connected = NetworkUtil.isNetConnected(BasicApplication.getInstance());
            if (!connected) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            } else {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            }
            return chain.proceed(request);
        }
    }

}
