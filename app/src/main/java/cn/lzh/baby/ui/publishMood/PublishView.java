package cn.lzh.baby.ui.publishMood;

import cn.lzh.baby.base.BaseView;

import java.io.File;
import java.util.List;

/**
 * Created by shetj on 2016/12/11.
 */

public interface PublishView extends BaseView{
	List<File> getUploadImage();
	String getLoction();
	String getContent();

}
