package cn.lzh.baby.api;



import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import okhttp3.MultipartBody;
import rx.Observable;

/**
 * 上传请求api
 * Created by WZG on 2016/10/20.
 */

public class UploadApi extends BaseApi {
    /*需要上传的文件*/
    private MultipartBody multipartBody;
    public UploadApi() {
        setMothed("file/upload");
        setCache(false);
    }

    public MultipartBody getPart() {
        return multipartBody;
    }

    public void setPart(MultipartBody multipartBody) {
        this.multipartBody = multipartBody;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.uploadImageWithRequestBody(getPart());
    }

}
