package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import okhttp3.MultipartBody;
import rx.Observable;

/**
 * 上传请求api
 * Created by WZG on 2016/10/20.
 */

public class UploadImageApi extends BaseApi {
    /*需要上传的文件*/
    private MultipartBody.Part parts ;
    public UploadImageApi() {
        setShowProgress(true);
        setCache(false);
        setCancel(true);
        setMothed("file/upload");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }

    public MultipartBody.Part getPart() {
        return parts;
    }

    public void setPart(MultipartBody.Part parts) {
        this.parts = parts;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.upload(getPart());
    }

}
