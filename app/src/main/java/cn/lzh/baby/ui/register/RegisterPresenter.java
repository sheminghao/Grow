package cn.lzh.baby.ui.register;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.ui.login.LoginView;

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
        registerView.register();
    }

    @Override
    public void onNext(String result, String mothead) {

    }

    @Override
    public void onError(Throwable e) {

    }

}
