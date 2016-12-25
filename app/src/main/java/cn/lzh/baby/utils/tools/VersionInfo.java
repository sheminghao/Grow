package cn.lzh.baby.utils.tools;

import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * 类名称：VersionInfo<br>
 * 内容摘要： //说明主要功能。<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016/8/9 17:17 <br>
 * 公司：深圳市华移科技股份有限公司<br>
 *
 * @author shetj<br>
 */


public class VersionInfo {
	public static int  getVersionCode(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			int versionCode=info.versionCode;
			return versionCode;
		} catch (Exception e) {
			L.e("版本号", e.getMessage());
		}
		return 0;
	}
}
