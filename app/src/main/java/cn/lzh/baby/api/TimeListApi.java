package cn.lzh.baby.api;

import android.util.Log;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.Api.UrlConfig;
import cn.lzh.baby.http2_rx.HttpService;
import cn.lzh.baby.utils.app.UserUitls;
import rx.Observable;

/**
 */

public class TimeListApi extends BaseApi {

    private String date;
    private String token;

    public TimeListApi(){
        setShowProgress(false);
        setCache(false);
        setCancel(true);
        setMothed(UrlConfig.TIMELIST);
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }

    public void setDate(String date){
        this.date = date;
        token= UserUitls.getToken();
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.timeList(date, token);
    }
}
