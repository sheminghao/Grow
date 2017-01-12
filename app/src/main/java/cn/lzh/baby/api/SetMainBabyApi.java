package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import cn.lzh.baby.utils.app.UserUitls;
import rx.Observable;

/**
 */

public class SetMainBabyApi extends BaseApi{

    private String babyId;
    private String token;

    public SetMainBabyApi(String babyId){
        setShowProgress(false);
        setCache(false);
        setCancel(true);
        setMothed("baby/setMainBaby");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
        this.babyId = babyId;
        token = UserUitls.getLoginInfo().getToken();
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.setMainBaby(babyId, token);
    }
}
