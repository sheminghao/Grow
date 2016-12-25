package cn.lzh.baby.http2_rx.downlaod;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import cn.lzh.baby.http2_rx.HttpService;
import cn.lzh.baby.http2_rx.listener.HttpDownOnNextListener;

/**
 * apk下载请求数据基础类
 * Created by WZG on 2016/10/20.
 */
@Table(name="DownInfo")
public class DownInfo{
    //id 自动增长
    @Column(name = "id",isId = true,autoGen = true)
    private long id;
    /*存储位置*/
    @Column(name="savePath")
    private String savePath;
    /*文件总长度*/
    @Column(name="countLength")
    private long countLength;
    /*下载长度*/
    @Column(name="readLength")
    private long readLength;
    /*下载唯一的HttpService*/
    private HttpService service;
    /*回调监听*/
    private HttpDownOnNextListener listener;
    /*超时设置*/
    @Column(name="connectonTime")
    private  int connectonTime=6;
    /*state状态数据库保存*/
    @Column(name="stateInte")
    private int stateInte;
    /*url*/
    @Column(name="url")
    private String url;

    public DownInfo(String url,HttpDownOnNextListener listener) {
        setUrl(url);
        setListener(listener);
    }

    public DownInfo(String url) {
        setUrl(url);
    }

    public DownInfo(long id, String savePath, long countLength, long readLength,
            int connectonTime, int stateInte, String url) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInte = stateInte;
        this.url = url;
    }

    public DownInfo() {
    }


    public DownState getState() {
        switch (getStateInte()){
            case 0:
                return DownState.START;
            case 1:
                return DownState.DOWN;
            case 2:
                return DownState.PAUSE;
            case 3:
                return DownState.STOP;
            case 4:
                return DownState.ERROR;
            case 5:
            default:
                return DownState.FINISH;
        }
    }

    public void setState(DownState state) {
        setStateInte(state.getState());
    }


    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

    public HttpDownOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpDownOnNextListener listener) {
        this.listener = listener;
    }

    public HttpService getService() {
        return service;
    }

    public void setService(HttpService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }


    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConnectonTime() {
        return this.connectonTime;
    }

    public void setConnectonTime(int connectonTime) {
        this.connectonTime = connectonTime;
    }
}
