package cn.lzh.baby.ui.splash;

import cn.lzh.baby.api.IndexApi;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.ReUserInfo;
import cn.lzh.baby.modle.User;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.file.SPUtils;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.EmptyUtils;
import cn.lzh.baby.utils.tools.L;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 */

public class SplashPresenter implements HttpOnNextListener{
	private SplashView splashView;
	private HttpManager manager;
	public SplashPresenter(SplashView splashView){
		this.splashView=splashView;
		manager=new HttpManager(this,splashView.getContext());
	}

	public void goLogin(int view){

		final String appellation=splashView.getSelect(view);
		final String babyId= (String) SPUtils.get(splashView.getContext(),"babyId","");
		if (EmptyUtils.isNotEmpty(appellation)&&EmptyUtils.isNotEmpty(babyId)) {
			Observable.create(new Observable.OnSubscribe<IndexApi>() {
				@Override
				public void call(Subscriber<? super IndexApi> subscriber) {
					IndexApi api=new IndexApi();
					api.setDate(appellation,Integer.parseInt(babyId),0);
					subscriber.onNext(api);
				}
			}).subscribe(new Action1<IndexApi>() {
				@Override
				public void call(IndexApi indexApi) {
					manager.doHttpDeal(indexApi);
				}
			});
		}else{
			splashView.goLogin();
		}
	}

	@Override
	public void onNext(String result, String mothead) {
		if (EmptyUtils.isNotEmpty(result)) {
			ReUserInfo userInfo = (ReUserInfo) GsonKit.jsonToBean(result, ReUserInfo.class);
			User user = new User();
			ReUserInfo.UserInfo userI = userInfo.getDatum();
			user.setNickName(userI.getAppellation());
			user.setBabyId(userI.getBaby_id() + "");
			user.setUserId(userI.getId() + "");
			UserUitls.saveUserInfo(user);
			splashView.goMain();
		}
	}

	@Override
	public void onError(Throwable e) {
		L.i(e.getMessage());
	}
}
