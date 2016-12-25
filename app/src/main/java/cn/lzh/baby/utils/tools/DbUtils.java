package cn.lzh.baby.utils.tools;



import cn.lzh.baby.utils.file.SDCardUtils;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 */


public class DbUtils {
	static DbManager.DaoConfig daoConfig;
	private final static String dbPath = SDCardUtils.getPath("DbDate");
	/**
	 * 获取DaoConfig
	 * @return
	 */
	public static DbManager.DaoConfig getDaoConfig(){
		File file=new File(dbPath);
		if(daoConfig==null){
			daoConfig=new DbManager.DaoConfig()
							.setDbName("DbDate.db")
							.setDbDir(file)
							.setDbVersion(1)
							.setAllowTransaction(true)
							.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
								@Override
								public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

								}
							});
		}
		return daoConfig;
	}
	/**
	 * dbManager
	 */
	public static DbManager getDbManager()
	{
		if (daoConfig!=null) {
			return x.getDb(daoConfig);
		}else{
			return x.getDb(getDaoConfig());
		}
	}
}
