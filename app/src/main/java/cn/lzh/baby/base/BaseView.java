package cn.lzh.baby.base;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by shetj on 2016/12/11.
 */

public interface BaseView {
	/**
	 * 正在登录
	 */
	void showLoging();
	/**
	 * 登录成功
	 */
	void loadingSuccese(String msg);
	/**
	 * 登录失败
	 */
	void loadingFail(String msg);

	/**
	 * 返回当前的activity
	 * @return
	 */
	RxAppCompatActivity getContext();
}
