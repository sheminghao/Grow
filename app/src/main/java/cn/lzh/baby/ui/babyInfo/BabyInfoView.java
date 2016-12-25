package cn.lzh.baby.ui.babyInfo;

import cn.lzh.baby.base.BaseView;
import cn.lzh.baby.modle.Baby;

/**
 */

public interface BabyInfoView extends BaseView {
	/**
	* 获取要修改的宝宝信息
	*/
	Baby getBabyInfo();

	String  getFilePath();
}
