package cn.lzh.baby;

import android.app.Application;
import android.content.Context;

import org.xutils.x;


/**
 */

public class APP extends Application{
	public static Context app;

	@Override
	public void onCreate() {
		super.onCreate();
		//xUtils3
		x.Ext.init(this);
		x.Ext.setDebug(false); //是否输出debug日志，开启debug会影响性能。
		app=getApplicationContext();
	}

}
