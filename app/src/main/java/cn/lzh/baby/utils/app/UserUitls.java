package cn.lzh.baby.utils.app;


import android.text.TextUtils;

import cn.lzh.baby.APP;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.modle.LoginInfo;
import cn.lzh.baby.modle.MainInfo;
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

	public static LoginInfo getLoginInfo(){
		String info=(String) SPUtils.get(APP.app,"loginInfo","");
		if (!EmptyUtils.isEmpty(info)) {
			return (LoginInfo) GsonKit.jsonToBean(info, LoginInfo.class);
		}
		return null;
	}

	public static void saveLoginInfo(LoginInfo loginInfo){
		if (null != loginInfo) {
			SPUtils.put(APP.app, "loginInfo", GsonKit.objectToJson(loginInfo));
		}
	}

	public static void clearLoginInfo(){
		SPUtils.remove(APP.app, "loginInfo");
	}

	public static String getToken(){
		String token=(String) SPUtils.get(APP.app,"token","");
		return token;
	}

	public static void saveToken(String token){
		if (!TextUtils.isEmpty(token)) {
			SPUtils.put(APP.app, "token", token);
		}
	}

	public static MainInfo getMainInfo(){
		String info=(String) SPUtils.get(APP.app,"mainInfo","");
		if (!EmptyUtils.isEmpty(info)) {
			return (MainInfo) GsonKit.jsonToBean(info, MainInfo.class);
		}
		return null;
	}

	public static void saveMainInfo(MainInfo mainInfo){
		if (!EmptyUtils.isEmpty(mainInfo)) {
			SPUtils.put(APP.app, "mainInfo", GsonKit.objectToJson(mainInfo));
		}
	}

	public static boolean isLogin(){
		boolean isLogin=(boolean) SPUtils.get(APP.app,"isLogin",false);
		return isLogin;
	}

	public static void setIsLogin(boolean isLogin){
		if (!EmptyUtils.isEmpty(isLogin)) {
			SPUtils.put(APP.app, "isLogin", isLogin);
		}
	}
}
