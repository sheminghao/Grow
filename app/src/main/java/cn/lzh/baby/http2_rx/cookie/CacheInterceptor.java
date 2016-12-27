package cn.lzh.baby.http2_rx.cookie;

import java.io.IOException;

import cn.lzh.baby.APP;
import cn.lzh.baby.http2_rx.AppUtil;
import cn.lzh.baby.utils.tools.L;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * get缓存方式拦截器
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!AppUtil.isNetworkAvailable(APP.app)) {//没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        Response responseLatest;
        if (AppUtil.isNetworkAvailable(APP.app)) {
            int maxAge = 60; //有网失效一分钟
            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();

        } else {
            int maxStale = 60 * 60 * 6; // 没网失效6小时
            responseLatest= response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        L.i("https?-->"+request.isHttps());
        L.i("httpUrl-->"+request.method() + ' ' + request.url() + ' ' );
        L.i("body-->"+request.body());
        L.i("httpResponse--> "+response.message() + ' ' +  response.code()  + ' ' + response.request().url() );
        return  responseLatest;
    }

}
