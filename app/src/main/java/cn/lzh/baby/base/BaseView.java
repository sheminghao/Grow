package cn.lzh.baby.base;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by shetj on 2016/12/11.
 */

public interface BaseView {
	/**
	 * 正在加载
	 */
	void showLoging();
	/**
	 * 保存成功
	 */
	void loadingSuccese(String msg);
	/**
	 * 保存失败
	 */
	void loadingFail(String msg);

	/**
	 * 返回当前的activity
	 * @return
	 */
	RxAppCompatActivity getContext();
}
