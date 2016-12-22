package cn.lzh.baby;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/12/22.
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
