package cn.lzh.baby.utils.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import kr.co.namee.permissiongen.PermissionGen;


/**
 * 检查权限的工具类
 * <p/>
 */
public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    public boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    public void getPermission(Activity activity,String per,int code){
        PermissionGen.with(activity)
                .addRequestCode(code)
                .permissions(per)
                .request();
    }
}