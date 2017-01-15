package cn.lzh.baby.ui.InvitationCode;

import android.text.TextUtils;

import cn.lzh.baby.api.AddAttentionApi;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.BaseInfo;
import cn.lzh.baby.ui.login.LoginView;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.T;

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

    public void addAttention(){
        String code = invitationCodeView.getCode();
        if (TextUtils.isEmpty(code)){
            T.showShort(invitationCodeView.getContext(), "请输入邀请码");
            return;
        }
        String chenghu = invitationCodeView.getChenghu();
        if ("请选择称呼".equals(chenghu)){
            T.showShort(invitationCodeView.getContext(), "请选择称呼");
            return;
        }
        boolean isMainBaby = invitationCodeView.isMainBaby();
        String mianFlag = "";
        if (isMainBaby){
            mianFlag = "1";
        }else {
            mianFlag = "0";
        }
        AddAttentionApi addAttentionApi = new AddAttentionApi();
        addAttentionApi.setData(code, chenghu, mianFlag);
        manager.doHttpDeal(addAttentionApi);

    }

    @Override
    public void onNext(String result, String mothead) {
        BaseInfo baseInfo = (BaseInfo) GsonKit.jsonToBean(result, BaseInfo.class);
        if (baseInfo.getCode() == 1){
            invitationCodeView.getContext().finish();
        }
        T.showShort(invitationCodeView.getContext(), baseInfo.getMessage());
    }

    @Override
    public void onError(Throwable e) {

    }
}
