package cn.lzh.baby.api;

import android.text.TextUtils;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import cn.lzh.baby.modle.Baby;
import rx.Observable;

/**
 */

public class AddBabyApi extends BaseApi {
	private Baby baby;

	public AddBabyApi(){
		setShowProgress(true);
		setCache(false);
		setCancel(true);
		setMothed("add");
		setCookieNetWorkTime(60);
		setCookieNoNetWorkTime(24*60*60);
	}

	public void setBabyInfo(Baby baby){
		this.baby=baby;
	}
	@Override
	public Observable getObservable(HttpService methods) {
		String sex="2";
		if (TextUtils.equals(baby.getSex(),"男")){
			sex="1";
		}
		return methods.add(baby.getNickname(),sex,baby.getBirthday(),baby.getPortrait());
	}
}
