package cn.lzh.baby.http2_rx.Api;

import java.util.Map;

/**
 * 回调信息统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseResultEntity {
    //  判断标示
    private int code;
    //    提示信息
    private String message;
    //显示数据（用户需要关心的数据）
    private String data;

    private String failed;
    private Map<String,String>  datum;

    public Map<String, String> getDatum() {
        return datum;
    }

    public void setDatum(Map<String, String> datum) {
        this.datum = datum;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    @Override
    public String toString() {
        return "BaseResultEntity{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", failed='" + failed + '\'' +
                ", datum=" + datum +
                '}';
    }
}
