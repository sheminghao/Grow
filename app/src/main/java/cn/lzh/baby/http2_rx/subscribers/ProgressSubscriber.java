package cn.lzh.baby.http2_rx.subscribers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.lzh.baby.APP;
import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.AppUtil;
import cn.lzh.baby.http2_rx.Db.CookieDbUtil;
import cn.lzh.baby.http2_rx.cookie.CookieResulte;
import cn.lzh.baby.http2_rx.exception.HttpTimeException;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    /*是否弹框*/
    private boolean showPorgress=true;
    //    回调接口
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    //    弱引用反正内存泄露
    private SoftReference<Context> mActivity;
    //    加载框可自己定义
    private ProgressDialog pd;
    /*请求数据*/
    private BaseApi api;

    private CookieDbUtil db;

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    /**
     * 构造
     * @param api
     */
    public ProgressSubscriber(BaseApi api, SoftReference<HttpOnNextListener> listenerSoftReference, SoftReference<Context>
            mActivity ){
        this.api=api;
        this.mSubscriberOnNextListener =listenerSoftReference;
        this.mActivity = mActivity;
        db=new CookieDbUtil();
        setShowPorgress(api.isShowProgress());
        if(api.isShowProgress()){
            initProgressDialog(api.isCancel());
        }
    }



    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        onCancelProgress();
                    }
                });
            }
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if(!isShowPorgress())return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if(!isShowPorgress())return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
        /*缓存并且有网*/
        if(api.isCache()&& AppUtil.isNetworkAvailable(APP.app)){
             /*获取缓存数据*/
            CookieResulte cookieResulte= db.queryCookieBy(api.getUrl());
            if(cookieResulte!=null){
                long time= (System.currentTimeMillis()-cookieResulte.getTime())/1000;
                if(time< api.getCookieNetWorkTime()){
                    if( mSubscriberOnNextListener.get()!=null){
                        mSubscriberOnNextListener.get().onNext(cookieResulte.getResulte(),api.getMothed());
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        /*需要緩存并且本地有缓存才返回*/
        if(api.isCache()){
            Observable.just(api.getUrl()).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    errorDo(e);
                }

                @Override
                public void onNext(String s) {
                    /*获取缓存数据*/
                    CookieResulte cookieResulte= db.queryCookieBy(s);
                    if(cookieResulte==null){
                        throw new HttpTimeException("网络错误");
                    }
                    long time= (System.currentTimeMillis()-cookieResulte.getTime())/1000;
                    if(time<api.getCookieNoNetWorkTime()){
                        if( mSubscriberOnNextListener.get()!=null){
                            mSubscriberOnNextListener.get().onNext(cookieResulte.getResulte(),api.getMothed());
                        }
                    }else{
                        db.deleteCookie(cookieResulte);
                        throw new HttpTimeException("网络错误");
                    }
                }
            });
        }else{
            errorDo(e);
        }
    }

    /*错误统一处理*/
    private void errorDo(Throwable e){
        Context context = mActivity.get();
        if (context == null) return;
        else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case REQUEST_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case GATEWAY_TIMEOUT:
                    Toast.makeText(context, "无网络，读取缓存数据失败", Toast.LENGTH_SHORT).show();
                    break;
                case NOT_FOUND:
                    Toast.makeText(context, "404，读取数据失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (e instanceof HttpTimeException) {
            Toast.makeText(context, "错误 " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            Toast.makeText(context, "解析错误", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            Toast.makeText(context, "证书验证失败", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
        }
        if(mSubscriberOnNextListener.get()!=null){
            mSubscriberOnNextListener.get().onError(e);
        }


    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
         /*缓存处理*/
        if(api.isCache()){
            CookieResulte resulte=db.queryCookieBy(api.getUrl());
            long time=System.currentTimeMillis();
            /*保存和更新本地数据*/
            if(resulte==null){
                resulte  =new CookieResulte(api.getUrl(),t.toString(),time);
                db.saveCookie(resulte);
            }else{
                resulte.setResulte(t.toString());
                resulte.setTime(time);
                db.updateCookie(resulte);
            }
        }
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext((String) t,api.getMothed());
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }
}