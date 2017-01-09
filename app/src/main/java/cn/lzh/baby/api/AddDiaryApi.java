package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.Api.UrlConfig;
import cn.lzh.baby.http2_rx.HttpService;
import rx.Observable;

/**
 */

public class AddDiaryApi extends BaseApi{

    private String content;
    private String location;
    private String spend;
    private String token;

    public AddDiaryApi(){
        setMothed(UrlConfig.ADD_DIARY);
        setCancel(false);
        setCache(false);
    }

    public void setData(String content, String location, String spend, String token){
        this.content=content;
        this.location=location;
        this.spend=spend;
        this.token=token;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.addDiary(content, location, spend, token);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpend() {
        return spend;
    }

    public void setSpend(String spend) {
        this.spend = spend;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
