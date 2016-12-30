package cn.lzh.baby.ui.register;

import android.text.TextUtils;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.lzh.baby.api.LoginApi;
import cn.lzh.baby.api.RegisterApi;
import cn.lzh.baby.http2_rx.Api.UrlConfig;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.BaseInfo;
import cn.lzh.baby.modle.LoginInfo;
import cn.lzh.baby.ui.login.LoginPresenter;
import cn.lzh.baby.ui.login.LoginView;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.json.GsonKit;

/**
 * Created by Administrator on 2016/12/26.
 */

public class RegisterPresenter implements HttpOnNextListener {

    private RegisterView registerView;
    private HttpManager manager;
    public RegisterPresenter(RegisterView registerView){
        this.registerView=registerView;
        manager=new HttpManager(this,registerView.getContext());
    }

    void register(){
        String userName = registerView.getUsername();
        String password = registerView.getPassword();
        String confirmPassword = registerView.getConfirmPassword();
        if (TextUtils.isEmpty(userName)) {
            registerView.showMsg("请输入用户名!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            registerView.showMsg("请输入密码!");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            registerView.showMsg("请输入确认密码!");
            return;
        }
        if (!password.equals(confirmPassword)){
            registerView.showMsg("输入密码不一致，请重新输入!");
            return;
        }
        //调用接口登录
        registerView.showLoging();
        RegisterApi registerApi = new RegisterApi(userName, password);
        manager.doHttpDeal(registerApi);
    }

    @Override
    public void onNext(String result, String mothead) {
        if (UrlConfig.REGISTER.equals(mothead)) {
            BaseInfo baseInfo = (BaseInfo) GsonKit.jsonToBean(result, BaseInfo.class);
            if (baseInfo.getCode() == 1) {
            LoginApi loginApi = new LoginApi(registerView.getUsername(), registerView.getPassword());
            manager.doHttpDeal(loginApi);
            } else {
                registerView.showMsg(baseInfo.getMessage());
            }
        }else if(UrlConfig.LOGIN.equals(mothead)){
            LoginInfo loginInfo = (LoginInfo) GsonKit.jsonToBean(result, LoginInfo.class);
            if (loginInfo.getCode() == 1) {
                UserUitls.saveLoginInfo(loginInfo);
                registerView.registerSucceed();
            }else {
                registerView.showMsg(loginInfo.getMessage());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        registerView.registerFail(e.getMessage());
    }

}
