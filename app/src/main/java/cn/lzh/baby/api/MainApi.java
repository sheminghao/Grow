package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.Api.UrlConfig;
import cn.lzh.baby.http2_rx.HttpService;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/8.
 */

public class MainApi extends BaseApi {

    private String token;

    public MainApi(String token){
        setShowProgress(false);
        setCache(false);
        setCancel(true);
        setMothed(UrlConfig.MAININFO);
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
        this.token = token;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.mainInfo(token);
    }
}
