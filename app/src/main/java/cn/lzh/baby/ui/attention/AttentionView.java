package cn.lzh.baby.ui.attention;

import cn.lzh.baby.base.BaseView;
import cn.lzh.baby.modle.UserBabyList;

/**
 * Created by Administrator on 2016/12/28.
 */

public interface AttentionView extends BaseView{

    void loadData();

    void refresh(UserBabyList userBabyList);

}
