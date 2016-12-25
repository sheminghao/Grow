package cn.lzh.baby.http2_rx.cookie;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * post請求緩存数据
 * Created by WZG on 2016/10/26.
 */
@Table(name = "CookieResulte")
public class CookieResulte {
    //id 自动增长
    @Column(name = "id",isId = true,autoGen = true)
    private long id;
    /*url*/
    @Column(name = "url")
    private String url;
    /*返回结果*/
    @Column(name = "resulte")
    private String resulte;
    /*时间*/
    @Column(name = "time")
    private long time;

    public CookieResulte(String url, String resulte, long time) {
        this.url = url;
        this.resulte = resulte;
        this.time = time;
    }


    public CookieResulte(long id, String url, String resulte, long time) {
        this.id = id;
        this.url = url;
        this.resulte = resulte;
        this.time = time;
    }

    public CookieResulte() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getResulte() {
        return this.resulte;
    }
    public void setResulte(String resulte) {
        this.resulte = resulte;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
