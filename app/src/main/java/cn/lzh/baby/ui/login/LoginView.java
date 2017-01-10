package cn.lzh.baby.ui.login;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 */

public interface LoginView{
	/**
	* 获取用户名
	*/
	String getUsername();
	/**
	 * 获取密码
	 */
	String getPassword();
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

	void goRegister();

	void goMain();
}
