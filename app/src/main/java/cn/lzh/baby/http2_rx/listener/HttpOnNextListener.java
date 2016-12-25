package cn.lzh.baby.http2_rx.listener;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface  HttpOnNextListener {
    /**
     * 成功后回调方法
     * @param result
     * @param mothead
     */
   void onNext(String result, String mothead);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     * @param e
     */
   void onError(Throwable e);
}
