package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.Api.UrlConfig;
import cn.lzh.baby.http2_rx.HttpService;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/28.
 */

public class RegisterApi extends BaseApi {

    private String username;
    private String password;

    public RegisterApi(String username, String password){
        setShowProgress(false);
        setCache(false);
        setCancel(true);
        setMothed(UrlConfig.REGISTER);
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
        this.username = username;
        this.password = password;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.register(username, password);
    }
}
