package cn.lzh.baby.ui.login;

import android.content.Context;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by shetj on 2016/12/3.
 */

public interface LoginView {
	/**
	* 获取输入的内容
	*/
	String getId();
	/**
	* 正在登录
	*/
	void showLoging();
	/**
	* 登录成功
	*/
	void loginSuccese(String msg);
	/**
	* 登录失败
	*/
	void loginFail(String msg);

	/**
	 * 返回当前的activity
	 * @return
	 */
	RxAppCompatActivity getContext();

	void showMsg(String msg);

	void goBabyInfo();

	void goMain();
}
