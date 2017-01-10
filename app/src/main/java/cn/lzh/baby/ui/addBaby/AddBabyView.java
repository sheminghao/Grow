package cn.lzh.baby.ui.addBaby;

import cn.lzh.baby.base.BaseView;
import cn.lzh.baby.modle.Baby;

/**
 */

public interface AddBabyView extends BaseView {
	/**
	* 获取要修改的宝宝信息
	*/
	Baby getBabyInfo();

	String  getFilePath();

	String getChenghu();
}
