package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import cn.lzh.baby.utils.app.UserUitls;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/14.
 */

public class AddAttentionApi extends BaseApi {

    private String code;
    private String appellation;
    private String mainFlag;
    private String token;

    public AddAttentionApi(){
        setShowProgress(true);
        setCache(false);
        setCancel(true);
        setMothed("baby/follow");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }

    public void setData(String code, String appellation, String mainFlag){
        this.code = code;
        this.appellation = appellation;
        this.mainFlag = mainFlag;
        token = UserUitls.getToken();
    }


    @Override
    public Observable getObservable(HttpService methods) {
        return methods.addAttention(code, appellation, mainFlag, token);
    }
}
