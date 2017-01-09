package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.Api.UrlConfig;
import cn.lzh.baby.http2_rx.HttpService;
import rx.Observable;

/**
 */

public class AttentionApi extends BaseApi {

    private String token;

    public AttentionApi(String token){
        setShowProgress(false);
        setMothed(UrlConfig.USER_BABY_LIST);
        setCancel(false);
        setCache(true);
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
        this.token = token;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.userBabylist(token);
    }
}
