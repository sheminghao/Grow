package cn.lzh.baby.http2_rx.Db;



import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

import cn.lzh.baby.http2_rx.downlaod.DownInfo;
import cn.lzh.baby.utils.tools.DbUtils;


/**
 * 断点续传
 * 数据库工具类-xutils3 db运用
 */

public class DownInfoDbUtil {
    private DbManager  db;

    public  DownInfoDbUtil(){
        db= DbUtils.getDbManager();
    }


    public void save(DownInfo info){
        try {
            db.save(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void update(DownInfo info){
        try {
            db.saveOrUpdate(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteDowninfo(DownInfo info){
        try {
            db.delete(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public DownInfo queryDownBy(long Id) {
        try {
         return  db.findById(DownInfo.class,Id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DownInfo> queryDownAll() {
        try {
            return db.findAll(DownInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
