package cn.lzh.baby.api;

import android.text.TextUtils;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.utils.app.UserUitls;
import rx.Observable;

/**
 */

public class AddBabyApi extends BaseApi {
	private Baby baby;
	private String chenghu;

	public AddBabyApi(){
		setShowProgress(false);
		setCache(false);
		setCancel(true);
		setMothed("baby/add");
		setCookieNetWorkTime(60);
		setCookieNoNetWorkTime(24*60*60);
	}

	public void setBabyInfo(Baby baby){
		this.baby=baby;
	}

	public void setBabyInfo(Baby baby, String chenghu){
		this.baby=baby;
		this.chenghu = chenghu;
	}

	@Override
	public Observable getObservable(HttpService methods) {
		String sex="2";
		if (TextUtils.equals(baby.getSex(),"男")){
			sex="1";
		}
		String token = UserUitls.getLoginInfo().getToken();
		return methods.add(baby.getNickname(),sex,baby.getBirthday(),baby.getPortrait(),
				chenghu, token);
	}
}
