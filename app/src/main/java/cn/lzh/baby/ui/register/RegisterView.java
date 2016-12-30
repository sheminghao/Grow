package cn.lzh.baby.ui.register;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Administrator on 2016/12/26.
 */

public interface RegisterView {

    void registerSucceed();
    void registerFail(String msg);
    String getUsername();
    String getPassword();
    String getConfirmPassword();
    void showMsg(String msg);
    void showLoging();

    /**
     * 返回当前的activity
     * @return
     */
    RxAppCompatActivity getContext();

}
