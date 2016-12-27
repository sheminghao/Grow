package cn.lzh.baby.utils.app;


import cn.lzh.baby.APP;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.modle.User;
import cn.lzh.baby.utils.file.SPUtils;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.EmptyUtils;

/**
 *  用户工具类，用来保存用户信息，更新用户信息
 *   后面可以加上某些<br>
 * User: shetj(375105540@qq.com) <br>
 * create at 2016/11/26 13:23<br>
 */
public class UserUitls {
	public static User getUserInfo(){
		String info=(String) SPUtils.get(APP.app,"userInfo","");
		if (!EmptyUtils.isEmpty(info)) {
			return (User) GsonKit.jsonToBean(info, User.class);
		}
		return null;

	}

	public static void saveUserInfo(User user){
		SPUtils.put(APP.app,"userInfo", GsonKit.objectToJson(user));
	}

	/**
	 * 通过本地的用户信息中的特殊字段，获取线上最新的用户信息
	 */
	public void UpdateUserInfo(){

	}

	public static Baby getBabyInfo(){
		String info=(String) SPUtils.get(APP.app,"babyInfo","");
		if (!EmptyUtils.isEmpty(info)) {
			return (Baby) GsonKit.jsonToBean(info, Baby.class);
		}
		return null;
	}

	public static void saveBabyInfo(Baby baby){
		if (!EmptyUtils.isEmpty(baby)) {
			SPUtils.put(APP.app, "babyInfo", GsonKit.objectToJson(baby));
		}
	}

}
