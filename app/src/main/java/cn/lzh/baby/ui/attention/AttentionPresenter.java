package cn.lzh.baby.ui.attention;

import android.util.Log;
import android.widget.Toast;

import cn.lzh.baby.api.AttentionApi;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.UserBabyList;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.json.GsonKit;

/**
 * Created by Administrator on 2016/12/28.
 */

public class AttentionPresenter implements HttpOnNextListener{

    private AttentionView attentionView;
    private HttpManager manager;

    public AttentionPresenter(AttentionView attentionView){
        this.attentionView=attentionView;
        manager=new HttpManager(this,attentionView.getContext());
    }

    public void loadData(){
        String token = UserUitls.getLoginInfo().getToken();
        attentionView.showLoging();
        AttentionApi attentionApi = new AttentionApi(token);
        manager.doHttpDeal(attentionApi);
    }

    @Override
    public void onNext(String result, String mothead) {
        UserBabyList userBabyList = (UserBabyList) GsonKit.jsonToBean(result, UserBabyList.class);
        if (userBabyList.getCode() == 1) {
            attentionView.refresh(userBabyList);
            attentionView.loadingSuccese("");
        }else{
            Toast.makeText(attentionView.getContext(), userBabyList.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
