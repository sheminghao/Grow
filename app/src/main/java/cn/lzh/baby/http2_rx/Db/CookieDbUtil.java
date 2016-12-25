package cn.lzh.baby.http2_rx.Db;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

import cn.lzh.baby.http2_rx.cookie.CookieResulte;
import cn.lzh.baby.utils.tools.DbUtils;


/**
 * 数据缓存
 * 数据库工具类-geendao运用
 */

public class CookieDbUtil {
    private DbManager db;

    public  CookieDbUtil(){
        db= DbUtils.getDbManager();
    }




    public void saveCookie(CookieResulte info){
        try {
            db.save(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateCookie(CookieResulte info){
        try {
            db.saveOrUpdate(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteCookie(CookieResulte info){
        try {
            db.delete(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public CookieResulte queryCookieBy(String  url) {
        try {
            CookieResulte cook = db.selector(CookieResulte.class).where("url","=",url).findFirst();
            if (cook!=null) {
                return cook;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CookieResulte> queryCookieAll() {
        try {
            List<CookieResulte> list=  db.findAll(CookieResulte.class);
            if (list!=null) {
                return list;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
