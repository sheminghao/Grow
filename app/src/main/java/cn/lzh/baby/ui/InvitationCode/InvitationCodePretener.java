package cn.lzh.baby.ui.InvitationCode;

import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.ui.login.LoginView;

/**
 * Created by Administrator on 2017/1/10.
 */

public class InvitationCodePretener implements HttpOnNextListener{

    private InvitationCodeView invitationCodeView;
    private HttpManager manager;
    public InvitationCodePretener(InvitationCodeView invitationCodeView){
        this.invitationCodeView=invitationCodeView;
        manager=new HttpManager(this,invitationCodeView.getContext());
    }

    @Override
    public void onNext(String result, String mothead) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
