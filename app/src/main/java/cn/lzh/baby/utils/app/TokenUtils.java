package cn.lzh.baby.utils.app;


import cn.lzh.baby.APP;
import cn.lzh.baby.utils.file.SPUtils;

/**
 *  token 的工具类  <br>
 * User: shetj(375105540@qq.com) <br>
 * create at 2016/11/28 21:06<br>
 */  

public class TokenUtils {
	/**
	* 保存token
	*/
	public static void saveToken(String token){
		SPUtils.put(APP.app,"token",token);
	}
	
	/**
	* 获得token
	*/
	public static String getToken(){
		return (String) SPUtils.get(APP.app,"token","");
	}


}
